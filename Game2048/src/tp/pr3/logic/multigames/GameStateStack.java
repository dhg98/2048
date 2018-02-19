package tp.pr3.logic.multigames;

import java.util.EmptyStackException;

public class GameStateStack {
	private static final int CAPACITY = 20;
	private GameState[] buffer = new GameState[CAPACITY];
	private int tam = 0;
	
	/**public int getTam()
	 * Metodo accedente que devuelve el tamanio de la pila.
	 */
	public int getTam()
	{
		return this.tam;
	}
	
	/**public GameState getGameState(int num)
	 *Funcion que devuelve el estado de la pila dado por el parametro num.
	 */
	public GameState getGameState(int num) {
		return this.buffer[num];
	}
	
	/**public GameState pop()
	 * Metodo que elimina un elemento de la pila.
	 */
	public GameState pop() throws EmptyStackException
	{
		/*Implementacion: Si estamos dentro de los limites de la pila, disminuimos el contador y devolvemos el estado del tablero de 
		 * la posicion mas externa de la pila. En caso de que no estemos dentro de los limites de la pila devolvemos null.*/
		if (!this.isEmpty()) {
			--this.tam;
			return this.buffer[this.tam];
		}
		else throw new EmptyStackException();
	}
	
	/**public void push(GameState state)
	 * Metodo que introduce un nuevo estado del tablero en la pila.
	 */
	public void push(GameState state) 
	{
		/*Implementacion: En caso de que el tamanio de la pila sea igual al tamanio maximo, hay que desplazar todos los estados del juego
		 * hacia abajo para poder introducir uno nuevo en la posicion mas alta. En caso contrario, se introduce el estado y se modifica
		 * el contador.*/
		if (this.tam == CAPACITY) {
			for (int i = 1; i < CAPACITY; ++i) {
				this.buffer[i - 1] = this.buffer[i];
			}
			this.buffer[this.tam - 1] = state;
		}
		else {
			this.buffer[this.tam] = state;
			++this.tam;
		}
	}
	
	/**public boolean isEmpty()
	 * Metodo que comprueba si una pila esta vacia.
	 */
	public boolean isEmpty()
	{
		return this.tam == 0;
	}
	
}
