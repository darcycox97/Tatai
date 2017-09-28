package tatai;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import tatai.game.Game;
import tatai.game.GameInstance;
import tatai.game.NumberGame;

/**
 *	Defines the event handlers for the controls on the Tatai home screen.
 */
public class HomeScreenController {
	private static final int GAME_LENGTH = 10;
	
	@FXML
	private Button btnEasy;
	@FXML
	private Button btnHard;
	@FXML
	private Button btnScores;
	
	@FXML
	public void startEasyGame(ActionEvent e) {
		BorderPane root;
		try {
			GameInstance.getInstance().setCurrentGame(new NumberGame(GAME_LENGTH,Game.EASY_RANGE));
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
			GameInstance.getInstance().setCurrentGame(new NumberGame(GAME_LENGTH, Game.HARD_RANGE));
			root = (BorderPane)FXMLLoader.load(getClass().getResource("GameScreen.fxml"));
			((Node) e.getSource()).getScene().setRoot(root);
		} catch (IOException e1) {
			System.out.println("Error starting hard game");
			e1.printStackTrace();
		}

	}
	
	@FXML
	public void viewLeaderboard(ActionEvent e) {
		BorderPane root;
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("LeaderboardScreen.fxml"));
			((Node) e.getSource()).getScene().setRoot(root);
		} catch (IOException e1) {
			System.out.println("Error entering leaderboard screen");
			e1.printStackTrace();
		}
	}
	
}
