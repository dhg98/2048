package tp.pr3.control.commands;

import java.util.Scanner;

import tp.pr3.exceptions.ExitException;
import tp.pr3.exceptions.FatalErrorException;
import tp.pr3.exceptions.LoadException;
import tp.pr3.exceptions.MoveException;
import tp.pr3.exceptions.NotRightFileException;
import tp.pr3.exceptions.PlayException;
import tp.pr3.exceptions.SaveException;
import tp.pr3.logic.multigames.Game;

public abstract class Command {
    private String helpText;
    private String commandText;
    protected final String commandName;
    
    public Command(String helpInfo, String commandInfo) { 
    	this.commandText = commandInfo;
    	this.helpText = helpInfo;
    	String [] commandInfoWords = this.commandText.split("\\s+");
    	this.commandName = commandInfoWords[0];
    }
    
    public abstract boolean execute(Game game, Scanner in) throws FatalErrorException, ExitException, LoadException;
    
    public abstract Command parse(String[] commandWords, Scanner in) throws MoveException, PlayException, SaveException, NotRightFileException, LoadException;
    
    /**Metodo que devuelve una cadena compuesta del comando y su mensaje de ayuda.
	 */
    public String helpText() { 
    	//Implementacion: Devuelve una cadena con el nombre del comando y su mensaje de ayuda. 
    	return " " + this.commandText + ": " + this.helpText; 
    }
    
    /**Metodo que comprueba si un nombre de fichero contiene caracteres validos. Estos serian, todos los que se encuentran ahi.
     * 
     */
    public boolean rightFile(String name){
    	boolean correcto = true;
    	for (int i = 0; i < name.length() && correcto; i++){
    		if(name.charAt(i) == '/' || name.charAt(i) == ' ' ||name.charAt(i) == '*' ||name.charAt(i) == '<' ||name.charAt(i) == '>' ||name.charAt(i) == '|' ||name.charAt(i) == ':' ||name.charAt(i) == '?' ||name.charAt(i) == '"')
    			correcto = false;
    	}
    	
    	return correcto;
    }
   
}