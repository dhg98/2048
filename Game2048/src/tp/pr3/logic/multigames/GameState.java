package tp.pr3.logic.multigames;

public class GameState {
	private int[][] boardState;
	private int score;
	
	/**public GameState(int score, int[][] state)
	 * Constructora que inicializa un GameState con los valores de una matriz de enteros recibida como parametro y establece
	 * la puntuacion de ese GameState al parametro score.
	 */
	public GameState(int score, int[][] state)
	{
		this.score = score;
		this.boardState = new int [state.length][state.length];
		for (int i = 0; i < state.length; ++i) {
			for (int j = 0; j < state.length; ++j) {
				this.boardState[i][j] = state[i][j];
			}
		}
	}
	
	/**public int[][] getBoardState()
	 * Metodo accedente que devuelve una matriz de enteros.
	 */
	public int[][] getBoardState()
	{
		return this.boardState;
	}
	
	/**public int getScore()
	 * Metodo accedente que devulve la puntuacion de un objeto GameState.
	 */
	public int getScore()
	{
		return this.score;
	}
}
