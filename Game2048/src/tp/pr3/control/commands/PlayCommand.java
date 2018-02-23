package tp.pr3.control.commands;

import java.util.Scanner;
import tp.pr3.exceptions.*;
import tp.pr3.logic.multigames.*;

public class PlayCommand extends Command{
	protected int boardSize, initialCells;
	protected long randomSeed;
	protected GameType gameType;
	public final static int TAM_POR_DEFECTO = 4;
	public final static int CELLS_INI_POR_DEFECTO = 2;
	public final static int TAM_MAXIMO = 10;
	
	/**Inicializa el objeto de tipo playCommand
	 */
	public PlayCommand()
	{
		/*Implementacion: Se inicializa con el constructor de la super clase con la cadena de ayuda y el tipo de comando.		 * 
		 */
		super("start a new game of one of the game types: original, fib, inverse, eleven", "play <game>");
	}
	
	/**Metodo que ejecuta el comando play. Pide por pantalla todos los parametros necesarios para ejecutar tantas veces como
	 * sea necesario y cambia el juego.
	 */
	public boolean execute(Game game, Scanner in) throws FatalErrorException {
		/*Implementacion: Se pide cada uno de los parametros del juego por pantalla, tantas veces como sea necesario hasta
		 * obtener parametros aceptables. Una vez se han pedido los datos, se cambia el parametro GameRules asociado a game
		 * y se modifican todos los parametros del tablero, incluyendo el score, el maxToken, las pilas undo y redo, etc.		 * 
		 */
		do {
			System.out.print("Please enter a positive size for the board: ");
			String tam;
			try {
				tam = in.nextLine();
				if (tam.equals("")) {
					this.boardSize = TAM_POR_DEFECTO;
					System.out.println("The size of the board has been set to " + TAM_POR_DEFECTO);
				}
				else {
					this.boardSize = Integer.parseInt(tam);
					if(this.boardSize <= 1) throw new NegativeException("");
					if (this.boardSize > TAM_MAXIMO) throw new BoardSizeException("");
				}	
			}
			catch (BoardSizeException e) {
				System.out.println("The size of the board must be less than " + TAM_MAXIMO);
				this.boardSize = -1;
			}
			catch (Exception o){
				System.out.println("The size of the board must be a positive number greater than 1.");
			}
			
		} while (this.boardSize <= 0);
		do {
			System.out.print("Please enter the number of the initial cells: ");
			String cells;
			
			try {
				cells = in.nextLine();
				if (cells.equals("")){
					this.initialCells = CELLS_INI_POR_DEFECTO;
					System.out.println("The number of initial cells has been set to " + CELLS_INI_POR_DEFECTO);
				}
				else {
					this.initialCells = Integer.parseInt(cells);
					if (this.initialCells >= this.boardSize * this.boardSize || this.initialCells <= 0) throw new CellsIniException();
				}
			}
			catch (Exception p) {
				System.out.println("The number of initial size must be a number lower than board size raised to the second power.");
			}
		} while (this.initialCells <= 0 || this.initialCells > this.boardSize * this.boardSize);
		do {
			System.out.print("Please enter the seed for the pseudo-random number generator: ");
			String seed;
			
			try {
				seed = in.nextLine();
				int var;
				if (seed.equals("")) {
					var = game.get_MyRandom().nextInt(1000);
					this.randomSeed = var;
					System.out.println("The seed has been set to " + var);
				}
				else {
					this.randomSeed = Integer.parseInt(seed);
				}
			}
			catch (Exception q) {
				
			}
		} while (this.randomSeed < 0);
		game.setMyRandom(this.randomSeed);
		switch (this.gameType){
		case ORIG: {
			game.setCurrentRules(GameType.ORIG);
		}break;
		case FIB: {
			game.setCurrentRules(GameType.FIB);
		}break;
		case INV: {
			game.setCurrentRules(GameType.INV);
		}break;
		case ELEVEN: {
			game.setCurrentRules(GameType.ELEVEN);
		}
		break;
		default:
			throw new FatalErrorException("");
		}		
		game.set_board(game.getCurrentRules().getRules().createBoard(this.boardSize)); //Crea el tablero
		game.getCurrentRules().getRules().initBoard(game.get_Board(), this.initialCells, game.get_MyRandom()); //Crea las celdas iniciales
		game.setResults(game.getCurrentRules().getRules().getWinValue(game.get_Board()));
		game.setSeed(this.randomSeed);
		game.setInitCells(this.initialCells);
		game.setSize(this.boardSize);
		game.setScore(0);
		game.setUndoStack(new GameStateStack());
		game.setRedoStack(new GameStateStack());
		return true;
	}
	
	/**public Command parse(String[] commandWords, Controller controller)
	 * Se comprueba que lo introducido por teclado es del tipo play y se comprueba que coincida con alguno de los juegos definidos.
	 */
	public Command parse(String[] commandWords, Scanner in) throws PlayException {
		/*Implementacion: Se comprueba que la primera cadena sea play y, en caso de que asi sea, se comprueba que sea uno de 
		 * los tres juegos definidos (original, fib o inverse). En caso de que no lo sea, se devuelve null. Si solo se
		 * introduce una cadena (longitud 1) no es un comando de tipo play y si no es ninguno de estos casos, tampoco lo es. 
		 */
		if (commandWords[0].equals(commandName)) {
			if (commandWords.length == 2) {
				this.gameType = gameType.parse(commandWords[1]);
				if (this.gameType == null)throw new PlayException("Play must be followed by original, inverse or fib");
				else return this;
			}
			else {
				throw new PlayException("Play must be followed by original, inverse or fib");
			}
		}
		else {
			return null;
		}
	}
	
	/**public String helpText()
	 *Metodo que muestra la ayuda de un comando de tipo play. 
	 */
	public String helpText() {
		/*Implementacion: Muestra la ayuda de un comando, llamando a la super clase e introduciendo un salto de linea. 
		 */
		return " play: start a new game of one of the game types: " + gameType.externaliseAll() +".\n";
				//super.helpText()+ ;
	}
}
