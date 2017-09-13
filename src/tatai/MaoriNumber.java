package tatai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Class to encapsulate information about a maori number.
 */
public class MaoriNumber {
	private static final File dictionary = new File("resources/dictionary.txt");

	private String text; // the maori spelling of the number
	private int value; // the actual value of the number

	public MaoriNumber(int value) {
		this.value = value;
		text = getSpelling(value);
	}

	public int getValue() {
		return value;
	}
	
	public String toString() {
		return text;
	}

	// returns the string representation of this number in maori spelling
	private String getSpelling(int value) {

		if (value <= 10) {
			return getSpellingOfNumber(value);
		} else {
			// get first digit
			int firstDigit = Integer.parseInt(String.valueOf(value).substring(0,1));
			int secondDigit = Integer.parseInt(String.valueOf(value).substring(1,2));
			
			if (firstDigit == 1) {
				return "tekau ma " + getSpellingOfNumber(value);
			} else {
				return getSpellingOfNumber(firstDigit) + " tekau ma " + getSpellingOfNumber(secondDigit);
			}
		}
	}

	private String getSpellingOfNumber(int num) {

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
