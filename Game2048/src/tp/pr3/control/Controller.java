package tp.pr3.control;

import java.util.*;

import tp.pr3.control.commands.*;
import tp.pr3.exceptions.ExitException;
import tp.pr3.exceptions.FatalErrorException;
import tp.pr3.exceptions.GameOverException;
import tp.pr3.exceptions.LoadException;
import tp.pr3.logic.multigames.*;

public class Controller {
	private Game game;
	private Scanner in;
	private boolean exit;
	public static final String commandErrorMsg = "Unknown command. Use 'help' to see the available commands";
	
	public Controller(Game game, Scanner in) {
		this.game = game;
		this.in = in;
		this.exit = false;
	}
	
	public void setExit(boolean salir) {
		this.exit = salir;
	}
	
	/**Metodo que se encarga de controlar la ejecucion del juego. 
	 */
	public void run() throws FatalErrorException, GameOverException {
		/*Implementacion: En primer lugar, se generan las celdas inicialmente llenas del tablero. Una vez se ha hecho esto, se entra en un bucle
		 * del que no se sale mientras que no se haya pulsado salir, no se haya alcanzado la puntuacion maxima del juego y se puedan hacer
		 * movimientos. Una vez dentro de este bucle, se comprueba que se haya alcanzado el limite del juego y se muestra el tablero en caso
		 * de que no se haya pulsado la ayuda. Una vez nos hemos asegurado de que no se ha alcanzado el limite, procedemos a pedir un comando
		 * por pantalla y se llama al CommandParser que va a determinar que tipo de comando ha sido el introducido En caso de que el comando
		 * sea uno de los especificados se ejecuta dicho comando y si no se muestra que ese comando no existe, y se pide otro.*/
		boolean finished = false, printGameState = true;
		this.game.getCurrentRules().getRules().initBoard(this.game.get_Board(), this.game.get_initCells(), this.game.get_MyRandom());
		this.game.setResults(this.game.getCurrentRules().getRules().getWinValue(this.game.get_Board()));
		boolean _2048 = false;
		while (!_2048 && !finished && !this.exit) {
			if (this.game.getCurrentRules().getRules().win(this.game.get_Board())) _2048 = true;
			if(printGameState){
				System.out.println(this.game);
			}
			if (!_2048) {
				System.out.print("Command > "); //Se pide un comando por pantalla.
				String comando = in.nextLine();
				String [] word = comando.split("\\s+");
				String [] words = null;
				word[0] = word[0].toLowerCase();
				if(word[0].equals("save")|| word[0].equals("load")) {
					words = word;
				}
				else { 
					String cadena = comando.toLowerCase(); //Hace falta crear una cadena nueva para pasarla a minusculas.
					words = cadena.split("\\s+");
				}
				Command command = null;
				try {
					command = CommandParser.parseCommand(words, in);
				}
				catch (NullPointerException g) {
					System.out.println(commandErrorMsg + "\n");
					printGameState = false;
				}
				catch (Exception c) {
					System.out.println(c.getMessage() + "\n");
					printGameState = false;
				}				
				if (command != null)
					try {
						printGameState = command.execute(game, in);
					} catch (ExitException x) {
						this.exit = true;
						in.close();
						printGameState = false;
					}
					catch (LoadException l) {
						printGameState = false;
						System.out.println(l.getMessage() + "\n");
					}
			
				finished = this.game.getCurrentRules().getRules().lose(this.game.get_Board()); /*En cada iteracion del bucle se comprueba que se haya acabado el juego, para 
				 												no continuar en caso de que sea asi.*/
			}
			else {
				in.close();
				throw new GameOverException("CONGRATULATIONS");
			}
		}
		
		if (finished) { //Si se ha perdido se muestra el tablero y se indica la derrota.
			System.out.println(this.game);
			in.close();
			throw new GameOverException("GAME OVER");
		}
	}
}


