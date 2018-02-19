package tp.pr3.utils;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.util.Scanner;

import tp.pr3.exceptions.NotRightFileException;

public class MyStringUtils {

	private boolean filename_confirmed;
	public static final String filenameInUseMsg = "The file already exists ; do you want to overwrite it ? (Y/N)";
	public static final int MARGIN_SIZE = 4;

	public static String centre(String text, int len) {
		String out = String
				.format("%" + len + "s%s%" + len + "s", "", text, "");
		float mid = (out.length() / 2);
		float start = mid - (len / 2);
		float end = start + len;
		return out.substring((int) start, (int) end);
	}

	public static boolean validFileName(String filename) {
		File file = new File(filename);
		if (file.exists())
			return canWriteLocal(file);
		else {
			try {
				file.createNewFile();
				file.delete();
				return true;
			} catch (Exception e) {
				return false;
			}
		}
	}

	public static boolean canWriteLocal(File file) {
		try {
			new FileOutputStream(file, true).close();
		} catch (IOException e) {
			return false;
		}
		return true;
	}
	
	public String confirmFileNameStringForRead(String filenameString, Scanner in) throws NotRightFileException {
		if (MyStringUtils.validFileName(filenameString)) {
			File file = new File(filenameString);
			if (file.exists()) {
				return filenameString;
			}
			else return null;
		}
		else throw new NotRightFileException("Invalid filename: the filename contains invalid characters");
	}

	public String confirmFileNameStringForWrite(String filenameString, Scanner in) throws NotRightFileException {
		String loadName = filenameString;
		this.filename_confirmed = false;
		while (!this.filename_confirmed) {
			if (MyStringUtils.validFileName(loadName)) {
				File file = new File(loadName);
				if (!file.exists())
					this.filename_confirmed = true;
				else {
					loadName = getLoadName(filenameString, in);
				}
			} else {
				System.out.println("Invalid filename: the filename contains invalid characters\n");
				this.filename_confirmed = true;
				//loadName = null;
			}
		}
		return loadName;
	}
	
	public String getLoadName(String filenameString, Scanner in) throws NotRightFileException {
		String newFilename = null;
		 boolean yesOrNo = false;
		 while (!yesOrNo) {
			 System.out.print(filenameInUseMsg + ": ");
			 String[] responseYorN = in.nextLine().toLowerCase().trim().split("\\s+");
			 if (responseYorN.length == 1) {
				 switch (responseYorN[0]) {
				 case "y": {
					 yesOrNo = true;
					 newFilename = filenameString;
					 if(MyStringUtils.validFileName(newFilename)) this.filename_confirmed = true;
				 } break;
				 case "n": {
					 yesOrNo = true;
					 System.out.print("Please enter another filename: ");
					 //newFilename = in.nextLine();
					 String[] words = in.nextLine().toLowerCase().trim().split("\\s+");
					 newFilename = confirmFileNameStringForWrite(words[0], in);
					 if (words.length > 1) throw new NotRightFileException("Invalid filename: The filename contains spaces");
				 } break;
				 default: System.out.println("Please answer ’Y’ or ’N’");
				 }
			 } 
			 else System.out.println("Please answer ’Y’ or ’N’");
		 }
		 return newFilename;
	}
	
	
}
