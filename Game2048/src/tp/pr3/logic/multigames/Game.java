package tp.pr3.logic.multigames;
import tp.pr3.exceptions.LoadException;
import tp.pr3.logic.*;

import java.io.*;
import java.io.IOException;
import java.util.*;

public class Game {
	private Board _board;
	private MoveResults results;
	private int _size;
	private int _initCells;
	private Random _myRandom;
	private long seed;
	private GameStateStack undoStack = new GameStateStack();
	private GameStateStack redoStack = new GameStateStack();
	private GameType currentRules;

	public Game(GameType rules, int size, int initCells, Random random, long seed) {
		this._size = size;
		this._initCells = initCells;
		this._myRandom = random;
		this._board = new Board (size) ;
		this.results = new MoveResults();
		this.seed = seed;
		this.currentRules = rules;
	}
	
	
	public void setMyRandom(long seed) {
		this._myRandom = new Random(seed);
	}
	
	public void setUndoStack(GameStateStack stack) {
		this.undoStack = stack;
	}
	
	public void setRedoStack(GameStateStack stack) {
		this.redoStack = stack;
	}
	
	public void setSeed(long seed) {
		this.seed = seed;
	}
	
	public void setInitCells(int cells)
	{
		this._initCells = cells;
	}
	
	public void setSize(int size)
	{
		this._size = size;
	}
	
	public int get_initCells()
	{
		return this._initCells;
	}
	
	public void set_board(Board board) {
		this._board = board;
	}
	
	public GameType getCurrentRules() {
		return this.currentRules;
	}
	
	public void setCurrentRules(GameType rule)
	{
		this.currentRules = rule;
	}
	
	public Random get_MyRandom() {
		return this._myRandom;
	}
	
	public int getSize() {
		return this._size;
	}
	
	public Board get_Board() {
		return this._board;
	}
	
	public MoveResults getResults()
	{
		return this.results;
	}
	
	public void setScore(int num) {
		this.results.setPoints(num);
	}
	
	public void setResults(int max) {
		this.results.setMaxToken(max);
	}
	
	/**public void reset()
	 * Metodo que resetea el tablero a la situacion inicial.
	 */
	public void reset() {
		/*Implementacion: Se recorre la matriz poniendola a 0 al completo. Se resetean los valores de puntuacion y de celda maxima y se pone a false
		 * la variable booleana moved. Una vez se ha hecho esto, se vuelve a crear el objeto de tipo Random a partir de la semilla de la clase Game
		 * (a misma semilla, mismas posiciones aleatorias). Una vez se ha reseteado el aleatorio, se vuelven a crear las posiciones iniciales.
		 * Se resetea tambien la pila undo y la redo, y se establece el best value a lo que nos indique el current rules.*/
		Position pos = new Position();
		for (int i = 0; i < this._size; ++i){
			for (int j = 0; j < this._size; ++j) {
				pos.setFila(i);
				pos.setColumna(j);
				this._board.getCell(pos).setValorCelda(0);
			}
		}
		this.results.setMoved(false);
		this.results.setPoints(0);
		this._myRandom = new Random(this.seed);
		this.currentRules.getRules().initBoard(this._board, this._initCells, this._myRandom);
		this.undoStack = new GameStateStack();
		this.redoStack = new GameStateStack();
		this.results.setMaxToken(this.currentRules.getRules().getWinValue(this._board));
	}
	
	/**public void move (Direction dir)
	 * Metodo que se encarga de realizar el movimiento y generar un numero aleatorio.
	 */
	public void move (Direction dir) {
		/*Implementacion: Se crea un nuevo array antes de comprobar si se ha podido realizar el movimiento para guardar el movimiento anterior.
		 * En caso de que se haya podido realizar el movimiento (comprobacion que se realizar llamando a la funcion executeMove), se
		 * guarda en la pila de deshacer y se crea una nueva celda atendiendo a las reglas del juego. Se establece tambien el maxToken
		 * al valor maximo tras hacer el movimiento*/
		int[][] aux2 = new int [this._size][this._size];
		GameState aux = new GameState(this.results.getPoints(), aux2);
		aux = this.getState();
		this._board.executeMove(dir, this.results, this.currentRules.getRules());
		if (results.getMoved()) { //Si ha habido movimiento se genera una nueva baldosa. La actualizacion de la puntuacion ya se hace en executeMove.
			undoStack.push(aux);
			currentRules.getRules().addNewCell(this._board, this._myRandom);
			this.results.setMaxToken(this.currentRules.getRules().getWinValue(this._board));
		}
	}
	
	/**public toString()
	 * Redefinicion del metodo toString para la clase Game.
	 */
	public String toString(){
		/*Implementacion: Se concatena la cadena que devuelve el metodo toString de Board y posteriormente se incorpora la casilla de mayor valor
		 * y la puntuacion de la partida.*/
		String tablero = "";
		tablero += this._board.toString();
		tablero += String.format("best value: %1$-5d score: %2$-5d%n", this.results.getMaxToken(), this.results.getPoints());
		return tablero;
	}
	
	/**public GameState getState()
	 * Metodo que devuelve un GameState segun el estado del tablero board.
	 */
	public GameState getState()
	{
		/*Implementacion: Se crea una matriz de enteros que va a llevar todos los valores del board, para despues crear un objeto de tipo
		 * GameState donde se van a llevar los puntos del board y los valores de board.
		 * 
		 */
		int [][] a = this._board.getState();
		GameState state = new GameState(results.getPoints(), a);
		return state;
	}
	
	/**public void setState(GameState aState)
	 * Metodo que establece el tablero del juego segun un GameState dado.
	 */
	public void setState(GameState aState)
	{
		/*Implementacion: Se establecen los puntos a los que se reciben en el objeto de tipo GameState y se cambia el tablero a lo que se
		 * recibe como parametro. */
		this.results.setPoints(aState.getScore());
		this._board.setState(aState.getBoardState());
	}
	
	/**public void undo()
	 * Metodo que deshace un movimiento
	 */
	public void undo()
	/*Implementacion: Una vezque se ha comprobado que se esta en los limites de la pila de deshacer, se guarda en la pila de rehacer
	 * el estado actual (para poder rehacer posteriormente) y se establece el estado actual del tablero al que se encontraba en la pila
	 * de deshacer. Por ultimo, se modifica el best value segun el juego al que se este jugando.*/
	{
		try {
			GameState copia = undoStack.pop();
			redoStack.push(this.getState());
			System.out.println("Undoing one movement...\n");
			setState(copia);
			this.setResults(this.currentRules.getRules().getWinValue(this._board));			
		}
		catch (EmptyStackException e) {
			System.out.println("It is not possible to undo one movement");
		}		
	}

	/**public void redo()
	 * Metodo que deshace un movimiento.
	 */
	public void redo() {
		/*Implementacion: En caso de que se pueda rehacer (es decir, que el tamaño este en los limites establecidos, se introduce el estado
		en la pila de undo para despues modificar el estado del tablero a partir del ultimo tablero presente en la pila redo. Por ultimo,
		se modifica el best value a lo que nos devuelve la funcion getWinValue.*/ 
		try {
			GameState copia = redoStack.pop();
			undoStack.push(this.getState());
			System.out.println("Redoing one movement...\n");
			setState(copia);
			this.setResults(this.currentRules.getRules().getWinValue(this._board));
		}
		catch (EmptyStackException f) {
			System.out.println("It is not possible to redo one movement");
		}
	}
	
	public void store(BufferedWriter out) throws IOException{
		this._board.store(out);
		out.newLine();
		out.write(this._initCells + " " + results.getPoints() + " " + currentRules.externalise());
	}
	
	public GameType load(BufferedReader in) throws IOException, LoadException {
		int[][] aState = this._board.load(in);
		try {
			String cadena = in.readLine();
			int init, score;
			String words [] = cadena.split("\\s+");
			if (words.length == 3) {
				init = Integer.parseInt(words[0]);
				score = Integer.parseInt(words[1]);
				GameType type = GameType.parse(words[2]);
				if (type == null) throw new LoadException("Load failed: invalid file format");
				else {
					this.undoStack = new GameStateStack();
					this.redoStack = new GameStateStack();
					this.results.setPoints(score);
					this._initCells = init;
					this.seed = new Random().nextInt(1000);
					this._myRandom = new Random(this.seed);
					this.currentRules = type;
					this._size = aState.length;
					GameState state = new GameState(score, aState);
					this.setState(state);
					this.results.setMaxToken(type.getRules().getWinValue(this._board));
					return type;
					}
			}
			else throw new LoadException ("Load failed: invalid file format");
		}
		catch (NumberFormatException e) {
			throw new LoadException ("Load failed: invalid file format");
		}
	}
	
}
