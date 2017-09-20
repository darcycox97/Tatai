package tatai.htk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import tatai.question.Question;

/**
 * Objects of this class will provide ease of use of the htk tool.
 */
public class HTK {
	
	public HTK() {}
	// this points to a script file i wrote that ensures when GoSpeech is run, it has access to the needed subfolders
	private static final String COMMAND = "resources/HTK/MaoriNumbers/GoSpeech"; 
	private static final File OUTPUT_FILE = new File("resources/HTK/MaoriNumbers/recout.mlf");
	
	/**
	 * Method to be called when the user attempts to record themselves saying the answer to a given question.
	 * Returns true if the user was correct, and false if not.
	 */
	public boolean recordQuestion(Question q) {
		ProcessBuilder pb = new ProcessBuilder("bash", COMMAND); // pass the RunGoSpeech script to bash
		try {
			Process p = pb.start();
			p.waitFor();
		} catch (IOException | InterruptedException e) {
			System.out.println("Error starting GoSpeech");
			e.printStackTrace();
			return false;
		}
		
		String[] wordsToMatch = q.getHTKWords();
		String firstWord = wordsToMatch[0];
		try {
			BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_FILE));
			String line = reader.readLine();
			while (line != null && !line.equals(firstWord)) {
				line = reader.readLine();
			}
			
			// if line is null then first word wasnt matched
			if (line == null) {
				reader.close();
				return false;
			}
			
			// line now equals the first HTK word. we need to iterate through the remaining words and check the file has them
			for (int i = 1; i < wordsToMatch.length; i++) {
				line = reader.readLine(); // move on to the next word
				if (!line.equals(wordsToMatch[i])) {
					reader.close();
					return false;
				}
			}
			
			// if we got to this point, then all words matched
			reader.close();
			return true;
			
		} catch (IOException e) {
			System.out.println("Error reading output file");
			e.printStackTrace();
			return false;
		}
	}
	

}
