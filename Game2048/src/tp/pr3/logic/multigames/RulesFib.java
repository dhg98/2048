package tp.pr3.logic.multigames;

import java.util.Random;

import tp.pr3.logic.*;

public class RulesFib implements GameRules{
	public static final int STOP_VALUE = 144;
    
	/**public int getWinValue(Board board)
	 * Metodo que obtiene el valor mas cercano a la victoria de un tablero dado.
	 */
	public int getWinValue(Board board){
		return board.getMaxValue();
	}
	
	/**public void addNewCellAt(Board board, Position pos, Random rand)
	 *  Metodo que añade una nueva celda (con probabilidad 0.9 de que sea un 1 y 0.1 de que sea un 2 al tablero en una posicion
	 * dada como parametro.
	 */
	public void addNewCellAt(Board board, Position pos, Random rand) {
		int num = rand.nextInt(10);
		if (num == 9) {
			num = 2;
		} //10% de probabilidad de sacar un 2.
		else {
			num = 1;
			//90% de probabilidad de sacar un 1.
		}
		try {
			board.getCell(pos).setValorCelda(num); 
		}
		catch (IndexOutOfBoundsException q){
			System.out.println("The position generated randomly is out of bounds.");
		}
	}
	
	/**public static int nextFib (int previous)
	 * Metodo que calcula el siguiente numero en la sucesion de fibonacci a partir de uno dado.
	 */
	public static int nextFib (int previous) {
		
		double phi = (1 + Math.sqrt(5))/2;
		return (int) Math.round(phi*previous);
		
	}
	
	/**public int merge(Cell self, Cell other)
	 * Metodo que devuelve el valor de la puntuacion tras el movimiento.
	 */
	public int merge(Cell self, Cell other){
		/*Implementacion: Segun la definicion de la sucesion de fibonacci, f(0) = 1, f(1) = 1 y f(n) = f(n - 1) + f(n - 2).
		 Por tanto, y segun la funcion dada que calcula el siguiente numero en la serie de fibonacci, en caso de que ambos valores de la
		 celda sean 1 se podra realizar la fusion, devolviendo como puntuacion del movimiento 2 y cambiando el valor de la celda a 2.
		 En caso de que no se este en este caso tan particular, hay que comprobar si alguna de las celdas dadas es la siguiente en la
		 sucesion de fibonacci a la otra, porque en ese caso se podra realizar la fusion. Ahora bien, es necesario cual de las dos es
		 mayor, cambiar el valor de la celda a ese valor y devolver esa puntuacion. En caso de que las celdas cumplan ninguna de las
		 condiciones anteriores, no se podra realizar la fusion y se devolvera 0.*/
		
		if (self.getValorCelda() == 1 && other.getValorCelda() == 1) {
			return 2;
		}
		else if (self.getValorCelda() == nextFib(other.getValorCelda()) || other.getValorCelda() == nextFib(self.getValorCelda())){
			int num1 = nextFib(other.getValorCelda());
			int num2 = nextFib(self.getValorCelda());
			if (num1 >= num2) {
				return num1;
			}
			else {
				return num2;
			}
		}
		else return 0;
	}
	
	/**public void changeCell(Cell self, Cell other)
	 * Metodo que cambia el valor de la celda other segun el juego 2048.
	 */
	public void changeCell(Cell self, Cell other){
		if (self.getValorCelda() == 1 && other.getValorCelda() == 1) {
			other.setValorCelda(2);
		}
		else if (self.getValorCelda() == nextFib(other.getValorCelda()) || other.getValorCelda() == nextFib(self.getValorCelda())){
			int num1 = nextFib(other.getValorCelda());
			int num2 = nextFib(self.getValorCelda());
			if (num1 >= num2) {
				other.setValorCelda(num1);
			}
			else {
				other.setValorCelda(num2);
			}
		}
	}
	
	/**public boolean win(Board board)
	 * Metodo que comprueba si se ha ganado la partida segun las reglas del inverse. 
	 */
	public boolean win(Board board){
		return (getWinValue(board) == STOP_VALUE);
	}
}
