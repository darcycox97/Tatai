package tatai;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import tatai.question.Question;
import tatai.question.MaoriNumber;

public class GameScreenController {

	public enum Difficulty {
		EASY, HARD
	}
	
	private static final int HARD_RANGE = 99;
	private static final int EASY_RANGE = 9;
	private static final int NUM_QUESTIONS = 10;
	private static final String NEXT_QUESTION = "Next";
	private static final String RECORD = "Record";

	private static Difficulty difficulty;
	private ArrayList<Question> randomNums;
	private int questionNumber;

	@FXML
	private Button returnHome;
	@FXML
	private Button btnRecord;
	@FXML
	private Label questionLabel;
	@FXML
	private Button btnNext;

	@FXML 
	public void initialize() {
		randomNums = new ArrayList<Question>(NUM_QUESTIONS);
		questionNumber = 0;
		if (difficulty != null) {
			// initialize the questions for the game
			for (int i = 0; i < NUM_QUESTIONS; i++ ) {
				System.out.println("Initializing numbers");
				randomNums.add(new MaoriNumber(generateNumber()));
			}
			displayQuestion(questionNumber);
		} else {
			throw new RuntimeException("The difficulty was not set before generating a random number");
		}
	}
	
	public static void setDifficulty(Difficulty d) {
		difficulty = d;
	}
	
	@FXML
	public void homeClicked() {
		BorderPane root;
		try {

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Exit Confirmation");
			alert.setContentText("Are you sure?");

			ButtonType buttonTypeYes = new ButtonType("Yes");
			ButtonType buttonTypeCancel = new ButtonType("No", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeYes){
				root = (BorderPane)FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
				returnHome.getScene().setRoot(root);
			} else {
				// ... user chose CANCEL or closed the dialog
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void recordClicked() {
		btnRecord.setVisible(false);
		btnNext.setVisible(true);
		questionNumber++;
		if (questionNumber >= NUM_QUESTIONS) {
			questionLabel.setText("No More Questions");
		} else {
			btnRecord.setText(NEXT_QUESTION);
			displayQuestion(questionNumber);
		}
		
	}
	
	// generates a number in the range specified by the difficulty level
	private int generateNumber() {
		int random;
		if (difficulty.equals(Difficulty.EASY)) {
			random = (int)(Math.random() * EASY_RANGE + 1);	
		} else {
			random = (int)(Math.random() * HARD_RANGE + 1);	
		}
		System.out.println(random);
		return random;
	}
	
	private void displayQuestion(int num) {
		questionLabel.setText(randomNums.get(questionNumber).getDisplayText());
	}
	

}
