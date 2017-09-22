package tatai;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import tatai.question.Question;
import tatai.game.Game;
import tatai.game.GameInstance;
import tatai.htk.HTKListener;

public class GameScreenController implements HTKListener{
	
	private Game game;
	//private Question currentQuestion;

	@FXML
	private Button returnHome;
	@FXML
	private Button btnRecord;
	@FXML
	private Label questionLabel;
	@FXML
	private Button btnNext;
	@FXML
	private Button btnPlayBack;
	@FXML
	private Label lblOutcome;
	@FXML
	private Label lblScore;

	@FXML 
	public void initialize() {
		
		game = GameInstance.getInstance().getCurrentGame();
		
		playerNamePrompt();
		
		displayQuestion(game.nextQuestion());
		btnRecord.setVisible(true);
		btnNext.setVisible(false);
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
		
		btnRecord.setDisable(true);// record button should remain disabled until recording is finished
		
		game.attemptQuestion(this);
		
	}
	
	/**
	 * Defines what the gui should do when the recording has finished
	 */
	public void recordingComplete() {
		
		btnRecord.setDisable(false);
		btnRecord.setVisible(false);
		btnNext.setVisible(true);
		
		displayResults(game.getResult());
	}
	
	@FXML
	public void nextQuestion() {
		if (game.hasNextQuestion()) {
			displayQuestion(game.nextQuestion());
			btnRecord.setVisible(true);
		} else {
			questionLabel.setText("Game Over!");
		}
		btnNext.setVisible(false);
		lblOutcome.setText("");
	}
	
	
	private void displayResults(boolean correct) {
		if (correct) {
			lblOutcome.setText("Correct!");
		} else {
			lblOutcome.setText("Incorrect");
		}
		
		lblScore.setText(game.getScore());
	}
	
	private void displayQuestion(Question q) {
		questionLabel.setText(q.getDisplayText());
	}
	
	private void playerNamePrompt() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Player Name");
		dialog.setHeaderText("Enter your name: ");
		
		Optional<String> result = dialog.showAndWait();
		String enteredName = "no name";
		
		if (result.isPresent()) {
			enteredName = result.get();
		}
		
		game.setPlayerName(enteredName);
	}
	

}
