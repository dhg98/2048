package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.exceptions.ExitException;
import tp.pr3.logic.multigames.Game;

public abstract class NoParamsCommand extends Command {
	
	public NoParamsCommand(String helpInfo, String commandInfo)
	{
		super(helpInfo, commandInfo);
	}
	
	public abstract boolean execute(Game game, Scanner in) throws ExitException;
	
	/**public Command parse(String[] commandWords, Controller controller)
	 * Metodo que comprueba si el comando introducido por el usuario es del mismo tipo que cualquiera de los comandos 
	 * sin parametros del juego.
	 */
	public Command parse(String[] commandWords, Scanner in) {
		/*Implementacion: Si la primera y unica cadena del comando introducido por el usuario se corresponde con el nombre
		 * del comando que estamos comprobando, se devuelve ese comando, en caso contrario se devuelve null. 
		 */
		if (commandWords[0].equals(this.commandName) && commandWords.length == 1) {
			return this;
		}
		else return null;	
	}
	
}