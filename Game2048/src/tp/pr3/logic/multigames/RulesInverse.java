package tp.pr3.logic.multigames;

import java.util.Random;

import tp.pr3.logic.*;

public class RulesInverse implements GameRules {
	public static final int STOP_VALUE = 1;
	
	
	/**public int getWinValue(Board board)
	 * Metodo que obtiene el valor mas cercano a la victoria de un tablero dado.
	 */
	public int getWinValue(Board board){
		return board.getMinValue();
	}
	
	/**public void addNewCellAt(Board board, Position pos, Random rand){
	 * Metodo que añade una nueva celda (con probabilidad 0.9 de que sea un 2048 y 0.1 de que sea un 1024 al tablero en una posicion
	 * dada como parametro.
	 */
	public void addNewCellAt(Board board, Position pos, Random rand){
		int num = rand.nextInt(10);
		if (num == 9) {
			num = 1024;
		} //10% de probabilidad de sacar un 1024.
		else {
			num = 2048;
			//90% de probabilidad de sacar un 2048.
		}
		try {
			board.getCell(pos).setValorCelda(num); 
		}
		catch (IndexOutOfBoundsException q){
			System.out.println("The position generated randomly is out of bounds.");
		}
	}
	
	/**public int merge(Cell self, Cell other){
	 * Metodo que devuelve el valor de la puntuacion tras el movimiento.
	 */
	public int merge(Cell self, Cell other){
		if (self.getValorCelda() == other.getValorCelda()){
			return (4096 / self.getValorCelda());
		}
		else return 0;
	}
	
	/**public void changeCell(Cell self, Cell other)
	 * Metodo que cambia el valor de la celda other segun el juego Inverse.
	 */
	public void changeCell(Cell self, Cell other){
		other.setValorCelda(other.getValorCelda()/2);
	}
	
	/**public boolean win(Board board)
	 * Metodo que comprueba si se ha ganado la partida segun las reglas del inverse. 
	 */
	public boolean win(Board board) {
		return (getWinValue(board) == STOP_VALUE);
	}
}

