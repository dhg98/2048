package tp.pr3.logic;
import tp.pr3.exceptions.*;
import java.util.*;
import tp.pr3.logic.multigames.*;
import tp.pr3.control.*;


public class Game2048 {
	public final static int CELLS_INI_DEFECTO = 2;
	public final static int TAM_BOARD_DEFECTO = 4;
	public final static int TAM_MAXIMO = 10;
	public static void main(String[] args) throws GameOverException {
		/*Implementacion: En primer lugar, se convierte a entero el primer y segundo parametro recibido en el array de cadenas args, siempre
		 * que esto sea posible. En caso de que no sea asi, se comenzara el juego con los parametros por defecto definidos en constates. Ahora, 
		 * en caso de que haya tres argumentos, se coge el tercero como semilla, y si no se crea una nueva. Una vez hecho esto, se crea un objeto
		 * de la clase Random, un objeto de la clase game y un escaner, para a continuacion crear un unico objeto de la clase controller. Por ultimo,
		 * se ejecuta el metodo run.*/
		int dim, numCeldasIni;
		long seed = 0;
		try {
			dim = Integer.parseInt(args[0]);
			if (dim > TAM_MAXIMO) throw new BoardSizeException("");
		}
		catch (BoardSizeException g) {
			String parentesis = "(" + TAM_MAXIMO + ")";
			System.out.println("Se ha excedido el tamanio maximo definido " + parentesis + ".");
			dim = TAM_BOARD_DEFECTO;
		}
		catch (IndexOutOfBoundsException e) {
			System.out.println("Se va a usar " + TAM_BOARD_DEFECTO + " como size por defecto.");
			dim = TAM_BOARD_DEFECTO;
		}
		catch (NumberFormatException numero) {
			System.out.println("Se va a usar " + TAM_BOARD_DEFECTO + " como size por defecto.");
			dim = TAM_BOARD_DEFECTO;
		}
		try {
			numCeldasIni = Integer.parseInt(args[1]);
			if (numCeldasIni > dim * dim){
				throw new CellsIniException("Se ha introducido un numero de initial cells superior al posible"); 
			}
			if (numCeldasIni <= 0) {
				throw new CellsIniException("Se ha introducido un numero de initial cells inferior al posible");
			}
		}
		catch (NumberFormatException nume2){
			System.out.println("Se va a usar " + CELLS_INI_DEFECTO + " como initial cells por defecto.");
			numCeldasIni = CELLS_INI_DEFECTO;
		}
		catch (IndexOutOfBoundsException f){
			System.out.println("Se va a usar " + CELLS_INI_DEFECTO + " como initial cells por defecto.");
			numCeldasIni = CELLS_INI_DEFECTO;
		}
		catch (CellsIniException hello){
			System.out.println("Se va a usar " + CELLS_INI_DEFECTO + " como initial cells por defecto.");
			numCeldasIni = CELLS_INI_DEFECTO;
		}
		try {
			seed = Long.parseLong(args[2]);
		}
		catch (Exception g){
			seed = new Random().nextInt(1000);
			System.out.println("Se va a usar como semilla generadora del random el numero: " + seed);
		}
		Random rand = new Random(seed);
		Game game = new Game(GameType.ORIG ,dim, numCeldasIni, rand, seed);
		Scanner scan = new Scanner(System.in);
		Controller controller = new Controller(game, scan);
		try {
			controller.run();
		} 
		catch (FatalErrorException e) {
			e.printStackTrace();
		}
		catch (GameOverException r) {
			String aux;
			aux = r.getMessage();
			if(aux.equals("GAME OVER"))
				System.out.println("GAME OVER");
			else System.out.println("CONGRATULATIONS");
		}
	}
}
