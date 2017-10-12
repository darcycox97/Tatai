package tatai.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class QuizCreatorController {

	private static final int NUM_EQUATIONS = 10;
	
	private final File quizCSV = new File("resources/quizzes.csv");

	@FXML private TextField eqn1;
	@FXML private TextField eqn2;
	@FXML private TextField eqn3;
	@FXML private TextField eqn4;
	@FXML private TextField eqn5;
	@FXML private TextField eqn6;
	@FXML private TextField eqn7;
	@FXML private TextField eqn8;
	@FXML private TextField eqn9;
	@FXML private TextField eqn10;
	Map<Integer,TextField> eqnMap = new HashMap<Integer,TextField>();
	
	@FXML private Button btnGoBack;
	@FXML private Button btnSaveQuiz;
	@FXML private TextField txtQuizName;
	
	@FXML
	public void initialize() {
		
		// initialize hashmap of equations (the key is the equation number)
		eqnMap.put(1, eqn1);
		eqnMap.put(2, eqn2);
		eqnMap.put(3, eqn3);
		eqnMap.put(4, eqn4);
		eqnMap.put(5, eqn5);
		eqnMap.put(6, eqn6);
		eqnMap.put(7, eqn7);
		eqnMap.put(8, eqn8);
		eqnMap.put(9, eqn9);
		eqnMap.put(10, eqn10);
		
		for (int i = 1; i <= NUM_EQUATIONS; i++) {
			//TODO: implement a listener for the textfields
			// eqnMap.get(i).addListener // listen to each text field to check for valid input
		}
	}
	
	@FXML public void loadTeacherMenu() {
		//TODO: 
	}
	
	@FXML
	public void saveQuiz() {
		// the save quiz button will only become enabled when all text fields contain a valid equation.
		// this means no checks need to be done at this point.
		
		// convert quiz into its csv form.
		
		StringBuilder sb = new StringBuilder();
		sb.append(txtQuizName.getText()); // first entry should be the quiz name
		
		for (int i = 1; i <= NUM_EQUATIONS; i++) {
			sb.append("," + eqnMap.get(i).getText());
		}
		
		addQuizToCSV(sb.toString());
	}
	
	/**
	 * Takes the CSV line representation of the quiz (which consists of
	 * a name followed by ten entries of the form "a [+-x] b"). Adds this
	 * line to the end of the quiz csv file.
	 */
	private void addQuizToCSV(String quiz) {
		try {
			List<String> oldFile = Files.readAllLines(quizCSV.toPath());
			List<String> newFile = new ArrayList<String>(oldFile.size() + 1);
			
			// copy oldfile into new file, then add last line
			for (int i = 0; i < oldFile.size(); i++) {
				newFile.add(oldFile.get(i));
			}
			newFile.add(quiz);
			
			Files.write(quizCSV.toPath(), newFile);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
