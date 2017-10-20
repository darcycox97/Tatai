package tatai.htk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import tatai.game.GameFactory;
import tatai.question.Question;

/**
 * Objects of this class will provide ease of use of the htk tool.
 */
public class HTK {
	
	private static final String COMMAND = "resources/HTK/MaoriNumbers/GoSpeech"; 
	private static final File OUTPUT_FILE = new File("resources/HTK/MaoriNumbers/recout.mlf");
	
	private Service<Boolean> recordService;
	
	/**
	 * Method to be called when the user attempts to record themselves saying the answer to a given question.
	 * Passes the recording onto a background thread.
	 * The object parameter 
	 */
	public void recordQuestion(Question q, HTKListener l) {
		
		System.out.println("Answer: " + q.getValue());
		recordService = new Service<Boolean>(){
			@Override
			protected Task<Boolean> createTask() {
				return new HTKWorker(q);
			}
		};
		
		recordService.setOnSucceeded(e -> {
			GameFactory.getInstance().getCurrentGame().updateScore(recordService.getValue());
			if (l != null) {
				l.recordingComplete();
			}
		});
		
		recordService.restart();
	}
	
	private class HTKWorker extends Task<Boolean> {
		private Question qToMatch;
		
		public HTKWorker(Question q) {
			qToMatch = q;
		}

		@Override
		protected Boolean call() throws Exception {
			
			ProcessBuilder pb = new ProcessBuilder("bash", COMMAND); // pass the GoSpeech script to bash
			try {
				Process p = pb.start();
				p.waitFor();
			} catch (IOException | InterruptedException e) {
				System.out.println("Error starting GoSpeech");
				e.printStackTrace();
				return false;
			}
			
			String[] wordsToMatch = qToMatch.getHTKWords();
			
			try {
				BufferedReader reader = new BufferedReader(new FileReader(OUTPUT_FILE));
				
				// read lines until we reach either the end of the file, or match the first word
				String line = reader.readLine();
				while (line != null && !line.equals(wordsToMatch[0])) {
					line = reader.readLine();
				}
				
				// if line is null then first word wasn't matched
				if (line == null) {
					reader.close();
					return false;
				}
				
				// The next lines are the lines to check for the correct output.
				// the algorithm returns correct if all words to match are found in the correct order
				
				for (int i = 1; i < wordsToMatch.length; i++) {
					line = reader.readLine(); // move on to the next word
					if (line == null) {
						reader.close();
						return false;
					}
					if (!line.equals(wordsToMatch[i])) {
						reader.close();
						return false;
					}
				}
				
				reader.close();
				return true; // if we made it through the loop without terminating, all words matched
				
			} catch (IOException e) {
				System.out.println("Error reading output file");
				e.printStackTrace();
				return false;
			}
		}
	}
}
