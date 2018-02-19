package tp.pr3.control.commands;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import tp.pr3.exceptions.LoadException;
import tp.pr3.exceptions.NotRightFileException;
import tp.pr3.logic.multigames.Game;
import tp.pr3.logic.multigames.GameType;
import tp.pr3.utils.MyStringUtils;

public class LoadCommand extends Command{
	private String fileName;
	public static final String welcomeMessage = "This file stores a saved 2048 game";
	
	/**Inicializa el objeto de tipo LoadCommand
	 */
	public LoadCommand(){
		/*Implementacion: Se inicializa con el constructor de la super clase con la cadena de ayuda y el tipo de comando.		 * 
		 */
		super("load a board", "load <file>"); 
	}
	
	public LoadCommand(String fileName) {
		super("load a board", "load <file>");
		this.fileName = fileName;
	}
	
	/**public void execute(Game game , Controller controller)
	 * Metodo que ejecuta el comando save, que guarda la partida.
	 */
	public boolean execute(Game game, Scanner in) throws LoadException {
		/*Implementacion: 
		 */
		try (BufferedReader inFich = new BufferedReader(new FileReader(this.fileName))) {
			String message = inFich.readLine();
			if (message.equals(welcomeMessage)) {
				message = inFich.readLine();
				if (message.equals("")) {
					GameType type;
					type = game.load(inFich);
					if (type != null) {
						System.out.println("Game successfully loaded from file: " + type.toString() + "\n");
					}
					else throw new LoadException ("Load failed: invalid file format");
				}
				else throw new LoadException ("Load failed: invalid file format");
			}
			else throw new LoadException ("Load failed: invalid file format");
		inFich.close();
		}
		catch (IOException e) {
			System.out.println("The game wasn´t successfully loaded.\n");
			return false;
		}
	
		return true;
	}
	
	/**public Command parse(String[] commandWords, Controller controller)
	 */
	public Command parse(String[] commandWords, Scanner in) throws LoadException, NotRightFileException {
		if (commandWords[0].equals(commandName)) {
			if (commandWords.length == 2) {
				MyStringUtils utilisimo = new MyStringUtils();
				String nameFile = null;
				nameFile = utilisimo.confirmFileNameStringForRead(commandWords[1],in);
				if (nameFile != null) {
					return new LoadCommand(nameFile);
				}
				else throw new LoadException("File not found");
			}
			else if (commandWords.length < 2) {
				throw new LoadException("Load must be followed by a filename");
			}
			else throw new LoadException("Invalid filename: The filename contains spaces");
		}
		else {
			return null;
		}
	}
	
	/**public String helpText()
	 *Metodo que muestra la ayuda de un comando de tipo save. 
	 */
	public String helpText() {
		/*Implementacion: Muestra la ayuda de un comando, llamando a la super clase e introduciendo un salto de linea. 
		 */
		return super.helpText()+ ".\n";
	}
}
