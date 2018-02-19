package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.logic.multigames.Game;

public class RedoCommand extends NoParamsCommand {
	
	/**public RedoCommand()
	 * Inicializa el objeto de tipo redoCommand
	 */
	public RedoCommand() {
		/*Implementacion: Se inicializa con el constructor de la super clase con la cadena de ayuda y el tipo de comando.		 * 
		 */
		super("redo one movement after undoing one movement", "redo"); //Revisar constructora.
	}
	
	/**public void execute(Game game , Controller controller)
	 * Metodo que ejecuta el comando redu, que rehace un movimiento deshecho anteriormente.
	 */
	public boolean execute(Game game, Scanner in)
	{
		/*Implementacion: Rehace un movimiento deshecho anteriormente llamando al metodo redo de la clase Game. Tambien 
		 * actualiza el atributo de la clase controler, printGameState a true. 
		 */
		game.redo();
		return true;
	}
	
	/**public String helpText()
	 *Metodo que muestra la ayuda de un comando de tipo redo. 
	 */
	public String helpText() {
		/*Implementacion: Muestra la ayuda de un comando, llamando a la super clase e introduciendo un salto de linea. 
		 */
		return super.helpText()+ ".\n";
	}
	
}
