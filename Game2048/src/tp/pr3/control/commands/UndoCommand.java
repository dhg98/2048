package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.logic.multigames.Game;

public class UndoCommand extends NoParamsCommand {

	/**public UndoCommand()
	 * Inicializa el objeto de tipo UndoCommand
	 */
	public UndoCommand(){
		/*Implementacion: Se inicializa con el constructor de la super clase con la cadena de ayuda y el tipo de comando.		 * 
		 */
		super("undo one movement", "undo"); 
	}
	
	/**public void execute(Game game , Controller controller)
	 * Metodo que ejecuta el comando undo, que deshace el ultimo movimiento ejecutado.
	 */
	public boolean execute(Game game, Scanner in){
		/*Implementacion: Deshace el ultimo movimiento realizado llamando al metodo undo de la clase Game. Tambien 
		 * actualiza el atributo de la clase controler, printGameState, a true. 
		 */
		game.undo();
		return true;
	}
	
	/**public String helpText()
	 *Metodo que muestra la ayuda de un comando de tipo undo. 
	 */
	public String helpText() {
		/*Implementacion: Muestra la ayuda de un comando, llamando a la super clase e introduciendo un salto de linea. 
		 */
		return super.helpText()+ ".\n";
	}
	
}
