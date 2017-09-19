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
import javafx.scene.layout.BorderPane;
import tatai.question.Question;
import tatai.game.Game;
import tatai.game.GameInstance;

public class GameScreenController {
	
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
	public void initialize() {
		game = GameInstance.getInstance().getCurrentGame();
		displayQuestion(game.nextQuestion());
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
		// use htk object to record user, compare the htk output with the expected words.

		
	}
	
	@FXML
	public void nextQuestion() {
		if (game.hasNextQuestion()) {
			displayQuestion(game.nextQuestion());
		} else {
			questionLabel.setText("Game Over");
		}
	}
	
	private void displayQuestion(Question q) {
		questionLabel.setText(q.getDisplayText());
	}
	

}
