package tatai;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import tatai.GameScreenController.Difficulty;

/**
 *	Defines the event handlers for the controls on the Tatai home screen.
 */
public class HomeScreenController {
	
	@FXML
	private Button btnEasy;
	
	@FXML
	private Button btnHard;
	
	@FXML
	public void startEasyGame(ActionEvent e) {
		BorderPane root;
		try {
			GameScreenController.setDifficulty(Difficulty.EASY);
			root = (BorderPane)FXMLLoader.load(getClass().getResource("GameScreen.fxml"));
			((Node) e.getSource()).getScene().setRoot(root);
		} catch (IOException e1) {
			System.out.println("Error starting easy game");
			e1.printStackTrace();
		}

	}
	
	@FXML
	public void startHardGame(ActionEvent e) {
		BorderPane root;
		try {
			GameScreenController.setDifficulty(Difficulty.HARD);
			root = (BorderPane)FXMLLoader.load(getClass().getResource("GameScreen.fxml"));
			((Node) e.getSource()).getScene().setRoot(root);
		} catch (IOException e1) {
			System.out.println("Error starting hard game");
			//e1.printStackTrace();
		}

	}
	
	
	
}
