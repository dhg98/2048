package tp.pr3.logic;

public class Position {
private int fila, columna;
	
	/**public Position()
	 * Metodo que se encarga de inicalizar una fila y una columna a 0.
	 */
	public Position ()
	{
		this.fila = 0;
		this.columna = 0;
	}
	
	/**public int getFila()
	 * Metodo accedente al valor fila.
	 */
	public int getFila() {
		return this.fila;
	}
	
	/**public int getColumna()
	 * Metodo accedente al valor columna.
	 */
	public int getColumna() {
		return this.columna;
	}
		
	/**public void setFila()
	 * Metodo accedente al valor fila.
	 */
	public void setFila(int x) {
		this.fila = x;
	}
	
	/**public void setColumna()
	 * Metodo accedente al valor columna.
	 */
	public void setColumna(int y) {
		this.columna = y;
	}
	
	/**public Position getNeighbour(Direction direccion, int _boardSize)
	 * Metodo que obtiene una posicion vecina a una dada. En caso de estar en los limites del tablero devuelve la misma posicion.
	 */
	public Position getNeighbour(Direction direccion, int _boardSize) {
		/*Solamente implementa las direcciones derecha, izquierda y abajo ya que arriba no se usa en el resto del codigo.*/
		Position neighbour = new Position();
		if (direccion == Direction.RIGHT) {
			if (this.columna < _boardSize - 1) {
				neighbour.columna = this.columna + 1;
				neighbour.fila = this.fila;
				}
			else {
				neighbour.fila = this.fila;
				neighbour.columna = this.columna;
			}
		}
		else if (direccion == Direction.LEFT){
			if (this.columna > 0) {
				neighbour.columna = this.columna - 1;
				neighbour.fila = this.fila;
			}
			else {
				neighbour.fila = this.fila;
				neighbour.columna = this.columna;
			}
		}
		else if (direccion == Direction.DOWN) {
			if (this.fila < _boardSize - 1) {
				neighbour.fila = this.fila + 1;
				neighbour.columna = this.columna;
			}
			else {
				neighbour.fila = this.fila;
				neighbour.columna = this.columna;
			}
		}
		return neighbour;
	}

}
