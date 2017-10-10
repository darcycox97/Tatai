package tatai.question;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Encapsulates a helper method to turn a maori number from 1 to 99 into a string.
 */
public class MaoriTranslator {
	
	private static final File dictionary = new File("resources/dictionary.txt"); // the file containing spelling of maori numbers from 1 to 10.

	public static String translate(int value) {
		
		if (value <= 10) {
			
			return getSpellingOfNumberFrom1to10(value);
			
		} else {
			
			int firstDigit = Integer.parseInt(String.valueOf(value).substring(0,1));
			int secondDigit = Integer.parseInt(String.valueOf(value).substring(1,2));
			
			if (value % 10 == 0) {
				return getSpellingOfNumberFrom1to10(firstDigit) + " tekau";
				
			} else if (firstDigit == 1) {
				return "tekau maa " + getSpellingOfNumberFrom1to10(secondDigit);
				
			} else {
				return getSpellingOfNumberFrom1to10(firstDigit) + " tekau maa " + getSpellingOfNumberFrom1to10(secondDigit);
			}
		}
	}
	
	/**
	 * Helper method to determine the spelling of a maori number from one to ten. 
	 * The nature of maori numbers allow us to construct the spelling of any number using 
	 * the first 10 numbers.
	 */
	private static String getSpellingOfNumberFrom1to10(int num) {

		String line;
		BufferedReader reader;
		try {
			reader = new BufferedReader(new FileReader(dictionary));
			line = reader.readLine();

			while (!line.equals(String.valueOf(num))) {
				line = reader.readLine();
			}
			
			// go down one more line to access the spelling of the value
			line = reader.readLine();
			reader.close();
			return line;
			
		} catch (IOException e) {
			System.out.println("Error reading dictionary");
			e.printStackTrace();
			return null; // null is returned if there was an error
		}
	}
}
