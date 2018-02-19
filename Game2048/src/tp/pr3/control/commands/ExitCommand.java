package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.exceptions.ExitException;
import tp.pr3.logic.multigames.Game;

public class ExitCommand extends NoParamsCommand {
	
	/**public ExitCommand()
	 * Inicializa el objeto de tipo exitCommand
	 */
	public ExitCommand() {
		/*Implementacion: Se inicializa con el constructor de la super clase con la cadena de ayuda y el tipo de comando.		 * 
		 */ 
		super("Terminate the program", "exit");
	}
	
	/**public void execute(Game game , Controller controller)
	 * Metodo que ejecuta el comando exit, saliendose del juego.
	 * @throws ExitException 
	 */
	public boolean execute(Game game, Scanner in) throws ExitException {
		// Implementacion:  Muestra un mensaje de GAME OVER y modifica el atributo de controller exit a true. 
		System.out.println("GAME OVER"); 
		throw new ExitException();
	}
	
	/**public String helpText()
	 *Metodo que muestra la ayuda de un comando de tipo exit. 
	 */
	public String helpText() {
		/*Implementacion: Muestra la ayuda de un comando, llamando a la super clase e introduciendo un salto de linea. 
		 */
		return super.helpText()+ ".\n";
	}
}
