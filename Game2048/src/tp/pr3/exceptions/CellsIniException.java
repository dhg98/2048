package tp.pr3.exceptions;

public class CellsIniException extends Exception{
	
	public CellsIniException() {
		 super("Ninio mete un valor bueno co�e");
	}
	
	public CellsIniException(String text) {
		super(text);
	}
}
