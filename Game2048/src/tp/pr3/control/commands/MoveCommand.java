package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.exceptions.MoveException;
import tp.pr3.logic.Direction;
import tp.pr3.logic.multigames.*;

public class MoveCommand extends Command {
	private Direction dir;
	
	/**public MoveCommand()
	 * Inicializa el objeto de tipo moveCommand
	 */
	public MoveCommand(){
		/*Implementacion: Se inicializa con el constructor de la super clase con la cadena de ayuda y el tipo de comando, 
		 * asi como la direccion a objeto nulo.		 * 
		 */
		super("execute a move in one of the directions: up, down, left, right", "move <direction>");
		this.dir = null;
	}
	
	/**public void execute(Game game , Controller controller)
	 * Metodo que ejecuta el comando move en la direccion indicada.
	 */
	public boolean execute(Game game, Scanner in) {
		/*Implementacion: Hace un movimiento en la direccion del atributo dir, ademas actualiza el atributo
		 * printGameState de la clase controller a true.
		 */
		game.move (this.dir);
		return true;
	}
	
	/**public Command parse(String[] commandWords, Controller controller)
	 * Metodo que comprueba si el comando introducido por el usuario es del tipo move y, en caso de que sea asi, en que
	 * direccion se quiere mover.
	 */
	public Command parse(String[] commandWords, Scanner in) throws MoveException{
		/*Implementacion: Una vez comprobado que la primera cadena introducida por el usuario es "move", se comprueba que, 
		 * si el numero de cadenas es dos, la segunda sea una de las cuatro direcciones posibles, y en caso de que no 
		 * corresponda a ninguna o solo se haya introducido una cadena se devuelve null. 
		 */
		if (commandWords[0].equals(commandName)) {
			if (commandWords.length == 2) {
				if (commandWords[1].equals("right")) {
					this.dir = Direction.RIGHT;
					return this;
				}
				else if (commandWords[1].equals("left")) {
					this.dir = Direction.LEFT;
					return this;
				}
				else if (commandWords[1].equals("down")) {
					this.dir = Direction.DOWN;
					return this;
				}
				else if (commandWords[1].equals("up")) {
					this.dir = Direction.UP;
					return this;
				}
				else throw new MoveException("Move must be followed by a direction: right, left, up or down.");
			}
			else {
				throw new MoveException("Move must be followed by a direction: right, left, up or down.");
			}
		}
		else {
			return null;
		}
	}
	
	/**public String helpText()
	 *Metodo que muestra la ayuda de un comando de tipo move. 
	 */
	public String helpText() {
		/*Implementacion: Muestra la ayuda de un comando, llamando a la super clase e introduciendo un salto de linea. 
		 */
		return super.helpText()+ ".\n";
	}
}
