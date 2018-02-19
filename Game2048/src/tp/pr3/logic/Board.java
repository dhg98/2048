package tp.pr3.logic;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import tp.pr3.exceptions.LoadException;
import tp.pr3.logic.multigames.*;

public class Board {
	private Cell[][] _board;
	private int _boardSize;

	/**public Board(int size)
	 * Metodo constructor que recibe un parametro size con el tamanio del tablero y que instancia el tablero.
	 */
	public Board (int size) {
		this._boardSize = size;
		_board = new Cell[size][size];
		for (int i = 0 ; i < size; ++i){
			for(int j = 0; j < size; ++j)
				this._board[i][j] = new Cell();
		}
	}
	
	public int getBoardSize(){
		return this._boardSize;
	}
	/**public Cell getCell (Position pos)
	 * Metodo accedente que obtiene el valor de la celda que determina la posicion pos.
	 */
	public Cell getCell (Position pos) {
		return this._board[pos.getFila()][pos.getColumna()];
	}

	/**public void setCell (Position pos, int value)
	 * Metodo mutador que modifica el valor de la celda a value en la posicion que determina pos
	 */
	public void setCell (Position pos, int value) {
		this._board[pos.getFila()][pos.getColumna()].setValorCelda(value);
	}
	
	public Board getBoard()
	{
		return this;
	}
	
	/**public MoveResults executeMove(Direction dir, MoveResults results, GameRules rules)
	 * Metodo que realiza el movimiento de las celdas en funcion de la direccion indicada.
	 */
	public void executeMove(Direction dir, MoveResults results, GameRules rules) {
		/*Implementacion: Se realiza un switch con la direccion y dependiendo de la direccion se llama a una funcion a otra para realizar el
		 * movimiento.	 
		 */
		switch (dir){

		case UP: {
			move_up(results, rules);
		}
		break;
		case LEFT: {
			move_left(results, rules);
		}
		break;
		case RIGHT: {
			move_right(results, rules);
		}
		break;
		case DOWN: {
			move_down(results, rules);
		}
		break;
		}
	}

	/**private MoveResults move_left()
	 * Metodo que mueve a la izquierda el tablero.
	 */
	private void move_left(MoveResults results, GameRules rules){
		/*Implementacion: Como se indica en las transparencias, mover a la izquierda es equivalente a reflejar, mover a la
		 * derecha y volver a reflejar, en ese orden.*/
		this.reflejar();
		this.move_right(results, rules);
		this.reflejar();

	}

	/**private MoveResults move_up()
	 * Metodo que mueve hacia arriba en el tablero.
	 */
	private void move_up(MoveResults results, GameRules rules){
		/*Implementacion: Como se indica en las transparencias, mover hacia arriba es equivalente a trasponer, mover a la 
		 * izquierda y trasponer, en ese orden. */
		this.trasponer();
		this.move_left(results, rules);
		this.trasponer();

	}

	/**private MoveResults move_down()
	 * Metodo que mueve hacia abajo en el tablero.
	 */
	private void move_down(MoveResults results, GameRules rules){
		/*Implementacion: Como se indica en las transparencias, mover hacia abajo es equivalente a trasponer, mover a la
		 * derecha y trasponer, en ese orden.*/
		this.trasponer();
		this.move_right(results, rules);
		this.trasponer();
	}
	
	
	/**private void move_right(MoveResults movement, GameRules rules)
	 * Metodo que se encarga de mover a la derecha el tablero y actualiza la puntuacion.
	 */
	private void move_right(MoveResults movement, GameRules rules) {
		/*Implementacion: En primer lugar se desplazan todas las fichas hacia la derecha, empezando desde la derecha. Una vez se han desplazado, 
		 * se fusionan en la medida de lo posible segun las reglas dadas. Por ultimo, se vuelven a desplazar siguiendo el mismo esquema.*/
		movement.setMoved(false);
		for (int i = 0; i < this._boardSize; ++i) {
			for (int j = this._boardSize - 2; j >= 0; --j) {
				if (!this._board[i][j].isEmpty()) {
					for (int k = j + 1; k < this._boardSize; ++k) { //Es necesario ver si se puede mover la celda hasta el margen derecho.
						if (this._board[i][k].isEmpty()) {
							movement.setMoved(true);
							Position pos = new Position();
							pos.setFila(i);
							pos.setColumna(k);
							Position neighbour = new Position();
							neighbour = pos.getNeighbour(Direction.LEFT, this._boardSize);
							this._board[i][k].setValorCelda(this._board[neighbour.getFila()][neighbour.getColumna()].getValorCelda());
							this._board[neighbour.getFila()][neighbour.getColumna()].setValorCelda(0);
						}	
					}
				}
			}
		} //Desplaza todas las fichas a la derecha del tablero para despues fusionarlas.
		
		boolean mov = false;
		for (int i = 0; i < this._boardSize; ++i) {
			for (int j = this._boardSize - 2; j >= 0; --j) { 
				Position pos = new Position();
				pos.setFila(i);
				pos.setColumna(j);
				Position neighbour = new Position();
				neighbour = pos.getNeighbour(Direction.RIGHT, this._boardSize);
				if (!this._board[neighbour.getFila()][neighbour.getColumna()].isEmpty() && !this._board[i][j].isEmpty()) {
					int valor = this._board[neighbour.getFila()][neighbour.getColumna()].doMerge(this._board[i][j], rules);
					mov = (valor != 0); 
					if (mov) {
						rules.changeCell(this._board[i][j], this._board[neighbour.getFila()][neighbour.getColumna()]);
						movement.setPoints(valor + movement.getPoints());
						this._board[i][j].setValorCelda(0);
						movement.setMoved(mov);
					}
				}
			}
		} //Fusiona las fichas
		for (int i = 0; i < this._boardSize; ++i) {
			for (int j = this._boardSize - 2; j >= 0; --j) {
				if (!this._board[i][j].isEmpty()) {
					for (int k = j + 1; k < this._boardSize; ++k) {
						if (this._board[i][k].isEmpty()) {
							movement.setMoved(true);
							Position pos = new Position();
							pos.setFila(i);
							pos.setColumna(k);
							Position neighbour = new Position();
							neighbour = pos.getNeighbour(Direction.LEFT, this._boardSize);
							this._board[i][k].setValorCelda(this._board[neighbour.getFila()][neighbour.getColumna()].getValorCelda());
							this._board[neighbour.getFila()][neighbour.getColumna()].setValorCelda(0);
						}
					}
				}
			}
		} //Vuelve a desplazarlas
	}

	/**private void trasponer()
	 * Metodo que se encarga de trasponer una matriz.
	 */
	private void trasponer() {
		for (int i = 1; i < this._boardSize; ++i) {
			for (int j = 0; j < i; ++j) {
				int aux = this._board[i][j].getValorCelda();
				this._board[i][j].setValorCelda(this._board[j][i].getValorCelda());
				this._board[j][i].setValorCelda(aux);				
			}
		}		
	}	

	/**private void reflejar()
	 * Metodo que se encarga de reflejar una matriz en el eje Y.
	 */
	private void reflejar() {

		for(int i = 0; i < this._boardSize; ++i){
			for (int j = 0; j < (this._boardSize/2) ; ++j){
				int aux = this._board[i][j].getValorCelda();
				this._board[i][j].setValorCelda(this._board[i][this._boardSize - j - 1].getValorCelda());
				this._board[i][this._boardSize - j - 1].setValorCelda(aux);		
			}
		}
	}

	/**private static String centre(String text, int len)
	 * Metodo que recibe una cadena para mostrar y un entero que indica el espacio para mostrar y centra la cadena en funcion de ese espacio.
	 */
	private static String centre (String text, int len) {
		String out = String.format("%"+len+"s%s%" + len+"s", "", text,"");
		float mid = (out.length()/2);
		float start = mid - len/2;
		float end = start + len;
		return out.substring ((int)start, (int) end);
	}

	/**public String toString()
	 * Redefinicion del metodo toString para la clase Board. Se muestra el tablero como se indica en el enunciado de la practica.
	 */
	public String toString() {
		/*Implementacion: Se hace un bucle que va a recorrer la matriz por filas, y dentro de ese, se hacen dos bucles. El primero va a mostrar la
		 * linea de guiones superior a cada fila de valores y la segunda va a mostrar la fila de valores siempre que estos sean distintos de 0 
		 * (incorporando entre medias las correspondientes |) y usando la funcion Centre implementada mas arriba. Por ultimo, se vuelve a hacer un
		 * bucle para mostrar la ultima linea de guiones.*/
		int cellSize = 7;
		String out = "";
		String space = " ";
		String vDelimiter = "|";
		String hDelimiter = "-";
		for (int i = 0; i < this._boardSize; ++i) {
			out += space;
			for(int j = 0; j < cellSize * this._boardSize + (this._boardSize - 1); ++j)
				out += hDelimiter;
			out += "\n";
			for(int k = 0; k < this._boardSize; ++k) {
				out += vDelimiter;
				if (this._board[i][k].getValorCelda() != 0)
					out += centre (String.valueOf(this._board[i][k].getValorCelda()), cellSize);
				else 
					out += centre (String.valueOf(space), cellSize);
			}
			out += vDelimiter + "\n";	
		}
		out += space;
		for(int j = 0; j < cellSize * this._boardSize + (this._boardSize - 1); ++j)
			out += hDelimiter;
		out += "\n";		
		return out;
	}
	
	/**public int generateVacio (Position freeCells[])
	 * Metodo que recibe un array de posiciones vacio y lo va a llenar con las posiciones vacias del tablero. Devuelve el tamaño del vector.
	 */
	public int generateVacio (Position freeCells[]) {
		/*Implementacion: Se recorre la matriz en busca de posiciones vacias y se insertan en el array en caso de que sea asi, aumentando el contador
		 * cada vez que se de esa condicion.*/
		int cont = 0;
			for (int i = 0; i < this._boardSize; ++i) {
				for (int j = 0; j < this._boardSize; ++j) {
					Position pos = new Position();
					pos.setColumna(j);
					pos.setFila(i);
					if (this.getCell(pos).isEmpty()) {
						if(cont > freeCells.length) throw new IndexOutOfBoundsException();
							freeCells[cont] = pos;
						cont++;
					}
				}
			}
		return cont;
	}

	/**public int[][] getState()
	 * Metodo que devuelve el estado del tablero en una matriz de enteros.
	 */
	public int[][] getState()
	{
		/*Implementacion: Se recorre el tablero y se va modificando la matriz de enteros habiendola inicializado antes al tamanio del tablero.
		 */
		int[][] aState = new int[this._boardSize][this._boardSize];
		for (int i = 0; i < this._boardSize; ++i) {
			for (int j = 0; j < this._boardSize; ++j) {
				aState[i][j] = this._board[i][j].getValorCelda();
			}
		}
		
		return aState;
	}
	
	/**public void setState(int[][] aState)
	 * Metodo que establece el tablero segun uno dado.
	 */
	public void setState(int[][] aState)
	{
		Board aux = new Board(aState.length);
		for (int i = 0; i < aux._boardSize; ++i) {
			for (int j = 0; j < aux._boardSize; ++j) {
				aux._board[i][j].setValorCelda(aState[i][j]);
			}
		}
		this._board = aux._board;
		this._boardSize = aux._boardSize;
	}
	
	/**public int getMaxValue()
	 * Metodo que calcula el mayor valor presente en el tablero.
	 */
	public int getMaxValue() {
		/*Implementacion: Se recorre la matriz buscando el valor maximo y se va modificando esta variable en cuanto se encuentre un valor
		 * mayor en la matriz. 
		 */
		int max = this._board[0][0].getValorCelda();
		
		for (int i = 0; i < this._boardSize; ++i)
			for (int j = 0; j < this._boardSize; ++j)
				if(max < this._board[i][j].getValorCelda())
					max = this._board[i][j].getValorCelda();
		
		return max;
	}

	/**public int getMinValue()
	 * Metodo que calcula el minimo valor presente en el tablero. 
	 */
	public int getMinValue(){
		/*Implementacion: Se recorre la matriz y se modifica el valor minimo en cuanto se encuentra un valor que sea distinto de 0. Esto se debe
		 * a que el minimo de la matriz seria el valor 0, pero este no se debe tener en cuenta y por lo tanto daria error en caso de intentar
		 * cambiar el minimo si se encuentra uno menor que el valor 0, porque esto no va a ocurrir. Una vez ya se tiene un valor positivo se
		 * modifica en el caso de que el valor encontrado (que debe ser positivo) sea menor que el que se tiene.
		 * 
		 */
		int min = this._board[0][0].getValorCelda();
		for (int i = 0; i < this._boardSize; ++i) {
			for (int j = 0; j < this._boardSize; ++j) {
				if((min > this._board[i][j].getValorCelda() && min != 0 && this._board[i][j].getValorCelda() > 0) || (min == 0 && this._board[i][j].getValorCelda() > 0))
					min = this._board[i][j].getValorCelda();
			}
		}
		return min;
	}
	
	public void store(BufferedWriter out) throws IOException{
		for (int i = 0; i < _boardSize; i++) {
			String fila = "";
			for (int j = 0; j < _boardSize; j++)
				fila += this._board[i][j].getValorCelda() + " ";
			
			out.write(fila);
			out.newLine();
		}
	}
	
	public int processLine(String line1, int[] linea) throws NumberFormatException {
		String [] word = line1.split("\\s+");
		int i = 0;
			try {while (i < word.length) {
				linea[i] = Integer.parseInt(word[i]);
				++i;
			}
		}
		catch (IndexOutOfBoundsException e) {
			return i;
		}
		return i;
	}
	
	public int[][] load(BufferedReader in) throws IOException, LoadException {
		try {
			String linea1 = in.readLine();
			int [] linea = new int[10];
			int longitud = processLine (linea1, linea);
			int [][] aState = new int [longitud][longitud];
			for (int i = 0; i < longitud; ++i) {
				aState[0][i] = linea[i];
			}
			linea1 = in.readLine();
			int j = 1;
			while (!linea1.equals("")) {
				longitud = processLine (linea1, linea);
				for (int i = 0; i < aState.length; ++i) {
					aState[j][i] = linea[i];
				}
				j++;
				linea1 = in.readLine();
			}
			return aState;
		}
		catch (NumberFormatException e) {
			throw new LoadException("Load failed: invalid file format");
		}
	}
	
}