package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.logic.multigames.Game;

public class HelpCommand extends NoParamsCommand{
	
	/**public HelpCommand()
	 * Inicializa el objeto de tipo helpCommand
	 */
	public HelpCommand() {
		/*Implementacion: Se inicializa con el constructor de la super clase con la cadena de ayuda y el tipo de comando.		 * 
		 */
		super("print this help message", "help");
	}
	
	/**public void execute(Game game , Controller controller)
	 * Metodo que ejecuta el comando help. Mostrando por pantalla los comandos disponibles y su descripcion.
	 */
	public boolean execute(Game game, Scanner in) {
		/*Implementacion: a traves del metodo commandHelp, muestra por pantalla, los comandos disponibles y sus respectivos 
		 * mensajes de ayuda. Actualiza el atributo de controller printGameState a false.		 * 
		 */
		System.out.println("The avaliable commands are: \n");
		System.out.println(CommandParser.commandHelp());
		return false;
	}
	
	/**public String helpText()
	 *Metodo que muestra la ayuda de un comando de tipo help. 
	 */
	public String helpText() {
		/*Implementacion: Muestra la ayuda de un comando, llamando a la super clase e introduciendo un salto de linea. 
		 */
		return super.helpText()+ ".\n";
	}
}
