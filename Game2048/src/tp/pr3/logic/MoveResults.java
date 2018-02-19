package tp.pr3.logic;

public class MoveResults {
	private boolean moved;
	private int points;
	private int maxToken;
	
	/**public MoveResults()
	 * Metodo constructor de la clase MoveResults. Inicializa a 0 los puntos del movimiento y la celda y pone a falso el movimiento.
	 */
	public MoveResults () {
		this.moved = false;
		this.points = 0;
		this.maxToken = 0;
	}
	
	/**public boolean getMoved()
	 * Metodo accedente a la variable booleana moved.
	 */
	public boolean getMoved() {
		return this.moved;
	}
	
	/**public int getPoints()
	 * Metodo accedente a la variable entera points.
	 */
	public int getPoints() {
		return this.points;
	}
	
	/**public int getMaxToken()
	 * Metodo accedente a la variable entera maxToken
	 */
	public int getMaxToken() {
		return this.maxToken;
	}
	
	/**public void setMaxToken(int valor)
	 * Metodo mutador de la variable entera maxToken.
	 */
	public void setMaxToken(int valor) {
		this.maxToken = valor;
	}
	
	/**public void setMoved(boolean mov)
	 * Metodo mutador de la variable booleana moved.
	 */
	public void setMoved(boolean mov) {
		this.moved = mov;
	}
	
	/**public void setPoints(int valor)
	 * Metodo mutador de la variable entera points.
	 */
	public void setPoints(int points) {
		this.points = points;
	}

}
