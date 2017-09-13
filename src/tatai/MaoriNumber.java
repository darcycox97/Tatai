package tatai;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class to encapsulate information about a maori number.
 */
public class MaoriNumber {
	private static final File dictionary = new File("resources/dictionary.txt");

	private List<String> text; // array that holds each word in the spelling of 
	private int value; // the actual value of the number

	public MaoriNumber(int value) {
		this.value = value;
		text = getSpelling(value);
	}

	public List<String> getText() {
		return text;
	}

	// returns the string representation of this number, in maori spelling, with each word as part of an array
	private List<String> getSpelling(int value) {

		ArrayList<String> spelling = new ArrayList<String>();
		if (value <= 10) {
			spelling.add(getSpellingOfNumber(value));
			return spelling;
		} else {
			// get first digit
			int firstDigit = Integer.parseInt(String.valueOf(value).substring(0,1));
			int secondDigit = Integer.parseInt(String.valueOf(value).substring(1,2));
			if (firstDigit == 1) {
				spelling.add("tekau");
				spelling.add("ma");
				spelling.add(getSpellingOfNumber(value));
				return spelling;
			} else {
				spelling.add(getSpellingOfNumber(firstDigit));
				spelling.add("tekau");
				spelling.add("ma");
				spelling.add(getSpellingOfNumber(secondDigit));
				return spelling;
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
			e.printStackTrace();
			
			return null; // null is returned if there was an error
		}
	}
}
