package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.logic.multigames.Game;

public class ResetCommand extends NoParamsCommand {
	
	/**public ResetCommand()
	 * Inicializa el objeto de tipo resetCommand
	 */
	public ResetCommand () {
		/*Implementacion: Se inicializa con el constructor de la super clase con la cadena de ayuda y el tipo de comando.		 * 
		 */
		super("start a new game", "reset");
	}
	
	/**public void execute(Game game , Controller controller)
	 * Metodo que ejecuta el comando reset, que resetea el tablero al inicial. 
	 */
	public boolean execute(Game game, Scanner in) {
		/*Implementacion:  Resetea el tablero actual utilizando el metodo reset de la clase Game. Actualiza el atributo
		 * printGameState de la clase controller a true.
		 */
		 game.reset(); 
		 return true;
	}
	
	/**public String helpText()
	 *Metodo que muestra la ayuda de un comando de tipo reset. 
	 */
	public String helpText() {
		/*Implementacion: Muestra la ayuda de un comando, llamando a la super clase e introduciendo un salto de linea. 
		 */
		return super.helpText()+ ".\n";
	}
}
