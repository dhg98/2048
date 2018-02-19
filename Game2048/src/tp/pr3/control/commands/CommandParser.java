package tp.pr3.control.commands;

import java.util.Scanner;
import tp.pr3.exceptions.*;

public class CommandParser {
	private static Command[] availableCommands = { new MoveCommand(), new PlayCommand(), new SaveCommand(), new LoadCommand(), new HelpCommand(), new ResetCommand(), new ExitCommand(), new UndoCommand(), new RedoCommand()};
	
	/**public static Command parseCommand(String[] commandWords, Controller controller)
	 * Metodo que comprueba si el comando introducido por el usuario corresponde con cualquiera de los tipos de comandos 
	 * definidos en el programa. 
	 * @throws PlayException 
	 */
	public static Command parseCommand(String[] commandWords, Scanner in) throws PlayException, MoveException, NullPointerException, SaveException, NotRightFileException, LoadException {
		/*Implementacion: Se recorre el array de availableCommands comprobando, a traves del metodo parse de NoParamsCommand,
		 * MoveCommand y PlayCommand, si el comando introducido por el usuario se corresponde con él, devolviendo null en caso de que no
		 * corresponda con ninguno.
		 */
		Command actual = null;
		int i = 0;
		while(i < availableCommands.length && actual == null) {
			actual = availableCommands[i].parse(commandWords, in);
			++i;
		}
		if (actual == null) {
			throw new NullPointerException();
		}
		else return actual;
	}
	
	/**public static Command parseCommand(String[] commandWords, Controller controller)
	 * Metodo que forma una cadena de caracteres que contiene la informacion de Help de cada uno de los comandos.  
	 */
	public static String commandHelp() {
		/*Implementacion: Se recorre el array de availableCommands, formando una única cadena de caracteres con toda la informacion
		 * de Help de cada uno de los comandos definidos en el programa.
		 */
		String out = "";
		for (Command i: availableCommands) {
			 out += i.helpText();
		}
		return out;
	}
}
