package tp.pr3.logic.multigames;

import tp.pr3.logic.*;

import java.util.*;

public interface GameRules {
	void addNewCellAt(Board board, Position pos, Random rand);
	int merge(Cell self, Cell other);
	void changeCell(Cell self, Cell other);
	int getWinValue(Board board);
	boolean win(Board board);
	
	/**default boolean lose(Board board)
	 * Metodo que determina cuando se ha perdido una partida.
	 */
	default boolean lose(Board board)
	{
		/*Implementacion: Se recorre la matriz comprobando que ninguna posicion este vacia (porque en ese caso la partida no habra finalizado) y
		 * para cada posicion, se comprueba la de abajo y la de la derecha (no hace falta comprobar las 4 porque la comprobacion entre dos celdas
		 * se haria dos veces. En caso de que se este en el limite inferior o margen derecho del tablero, solo se comprueban la celda de
		 * la derecha o la de abajo respectivamente. 
		 */
		boolean finished = true;
		int i = 0, size = board.getBoardSize();
		while (i <= size - 1 && finished) {
			int j = 0;
			while (j <= size - 1 && finished) {
				Position posi = new Position();
				posi.setColumna(j);
				posi.setFila(i);
				if (board.getCell(posi).isEmpty()) {
					finished = false;
				}
				else {
					Cell value = new Cell();
					
					value.setValorCelda(board.getCell(posi).getValorCelda());
					if (i >= 0 && j >= 0 && j < size - 1 && i < size - 1) {
						int k = 0;
						Position pos = new Position();
						Position neighbour = new Position();
						while (k < 2 && finished) {
							pos.setFila(i);
							pos.setColumna(j);
							if (k == 0) { neighbour = pos.getNeighbour(Direction.DOWN, size); }
							else {neighbour = pos.getNeighbour(Direction.RIGHT, size); }
							if (board.getCell(neighbour).doMerge(value, this) != 0) finished = false;
							++k;
						}
					}
					else if (j == size - 1 && i >= 0 && i < size - 1) {
						Position pos = new Position();
						pos.setFila(i);
						pos.setColumna(j);
						Position neighbour = new Position();
						neighbour = pos.getNeighbour(Direction.DOWN, size);
						if (board.getCell(neighbour).doMerge(value, this) != 0) finished = false;
					}
					else if (i == size - 1 && j >= 0 && j < size - 1) {
						Position pos = new Position();
						pos.setFila(i);
						pos.setColumna(j);
						Position neighbour = new Position();
						neighbour = pos.getNeighbour(Direction.RIGHT, size);
						if (board.getCell(neighbour).doMerge(value, this) != 0) finished = false;
					}
				}
				j++;
			}
			i++;
		}

		return finished;
		
	}
	
	/**default Board createBoard(int size)
	 * Metodo que crea un tablero nuevo con un tamanio determinado.
	 */
	default Board createBoard(int size)
	{
		return new Board(size);
	}
	
	/**default void addNewCell(Board board, Random rand)
	 * Metodo que introduce una celda en el tablero.
	 */
	default void addNewCell(Board board, Random rand) throws IllegalArgumentException
	{
		/*Implementacion: Se crea el array de posiciones vacias y se genera una posicion aleatoria de ese array. Una vez se ha hecho eso, 
		 * se llama a la funcion que va a determinar que valor se va a anadir en esa posicion segun el juego al que se este jugando.
		 * 
		 */
			if(board.getBoardSize() < 0 ) throw new IllegalArgumentException();
			Position freeCells[] = new Position[board.getBoardSize() * board.getBoardSize()];
			int cont = board.getBoard().generateVacio(freeCells);
			int pos = rand.nextInt(cont); //Se obtiene una posicion aleatoria del array.
			Position posi = new Position();
			posi.setFila(freeCells[pos].getFila());
			posi.setColumna(freeCells[pos].getColumna());
			this.addNewCellAt(board, posi, rand);		
	}
	
	/**default void initBoard(Board board, int numCells, Random rand)
	 * Metodo que crea las posiciones iniciales del tablero segun el parametro introducido.
	 */
	default void initBoard(Board board, int numCells, Random rand)
	{
		/*Implementacion: Se hace un bucle y se llama tantas veces como haga falta a la funcion que introduce una nueva celda. 
		 */
		for (int i = 0; i < numCells; ++i) {
			this.addNewCell(board, rand);
		}
	}
}
