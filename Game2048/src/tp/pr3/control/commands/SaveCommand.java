package tp.pr3.control.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import tp.pr3.exceptions.NotRightFileException;
import tp.pr3.exceptions.SaveException;
import tp.pr3.logic.multigames.Game;
import tp.pr3.utils.MyStringUtils;

public class SaveCommand extends Command {
	private String fileName;
	/**
	 * public SaveCommand() Inicializa el objeto de tipo SaveCommand
	 */
	public SaveCommand() {
		/*
		 * Implementacion: Se inicializa con el constructor de la super clase
		 * con la cadena de ayuda y el tipo de comando. *
		 */
		super("save a board", "save <file>");
	}
	
	public SaveCommand(String fileName){
		super("save a board", "save <file>");
		this.fileName = fileName;
	}

	/**
	 * public void execute(Game game , Scanner in) Metodo que ejecuta el comando
	 * save, que guarda la partida.
	 */
	public boolean execute(Game game, Scanner in) {
		/*
		 * Implementacion:
		 */
		try (BufferedWriter out = new BufferedWriter(new FileWriter(this.fileName))) {
			out.write("This file stores a saved 2048 game");
			out.newLine(); 
			out.newLine();
			game.store(out);
			System.out.println("Game successfully saved to file; use load command to reload it.\n");
			out.close();
			return false;
		} 
		catch (IOException w) {
			System.out.println("The game wasn´t successfully saved.\n");
			return false;
		}
	}

	/**
	 * public Command parse(String[] commandWords, Controller controller)
	 */
	public Command parse(String[] commandWords, Scanner in) throws SaveException, NotRightFileException{
		/* Implementacion: */
		if (commandWords[0].equals(commandName)) {
			if (commandWords.length == 2) {
				if (rightFile(commandWords[1])) {
					MyStringUtils utilisimo = new MyStringUtils();
					String nameFile = utilisimo.confirmFileNameStringForWrite(commandWords[1],in);
					if(nameFile != null)
						return new SaveCommand(nameFile);
					else throw new NotRightFileException("It's impossible to save in this file");
				} else
					throw new NotRightFileException("Invalid filename: The file contains invalid characters");
			}
			else if (commandWords.length > 2) throw new NotRightFileException("Invalid filename: The filename contains spaces");
			else throw new SaveException("Save must be followed by a filename");
		}
		else return null;
	}

	/**
	 * Metodo que muestra la ayuda de un comando de tipo save.
	 */
	public String helpText() {
		/*
		 * Implementacion: Muestra la ayuda de un comando, llamando a la super
		 * clase e introduciendo un salto de linea.
		 */
		return super.helpText() + ".\n";
	}

}
