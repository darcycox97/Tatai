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

public class GameScreenController {

	public enum Difficulty {
		EASY, HARD
	}
	
	private static final int HARD_RANGE = 99;
	private static final int EASY_RANGE = 9;

	private static Difficulty difficulty;

	@FXML
	private Button returnHome;
	@FXML
	private Label questionLabel;

	@FXML 
	public void initialize() {
		if (difficulty != null) {
			questionLabel.setText(generateNumber());
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
	
	// generates a number in the range specified by the difficulty
	private String generateNumber() {
		int random;
		if (difficulty.equals(Difficulty.EASY)) {
			random = (int)(Math.random() * EASY_RANGE + 1);	
		} else {
			random = (int)(Math.random() * HARD_RANGE + 1);	
		}
		System.out.println(random);
		return String.valueOf(random);
	}
	

}
