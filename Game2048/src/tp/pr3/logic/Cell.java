package tp.pr3.logic;

import tp.pr3.logic.multigames.*;

public class Cell {

private int valorCelda;
	/**public Cell()
	 * Metodo constructor que crea un objeto de tipo Cell.
	 */
	public Cell(){
		this.valorCelda = 0;
	}
	
	/**public int getValorCelda()
	 * Metodo accedente al entero valorCelda.
	 */
	public int getValorCelda() {
		return this.valorCelda;
	}
	
	/**public void setValorCelda(int valor)
	 * Metodo mutador al entero valorCelda.
	 */
	public void setValorCelda(int valor) {
		this.valorCelda = valor;
	}
	
	/**public boolean isEmpty()
	 * Metodo que comprueba que una celda esté vacía, o lo que es equivalente, que su valorCelda sea 0.
	 */
	public boolean isEmpty() {
		return this.valorCelda == 0;
	}
	
	/**public int doMerge (Cell neighbour)
	 * Metodo que devuelve el valor de la mezcla en caso de que se haya podido ejecutar dependiendo de las reglas del juego actuales.
	 */
	public int doMerge(Cell neighbour, GameRules rules) {
		return rules.merge(neighbour, this);
	}
}
