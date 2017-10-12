package tatai.controller;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import tatai.game.GameDifficulty;
import tatai.game.GameFactory;
import tatai.game.GameMode;
import tatai.game.NumberGame;

/**
 *	Defines the event handlers for the controls on the Tatai home screen.
 */
public class HomeScreenController {
	
	@FXML private Button btnEasy;
	@FXML private Button btnHard;
	@FXML private Button btnScores;
	
	@FXML
	public void startEasyGame(ActionEvent e) {
		BorderPane root;
		try {
			GameFactory.getInstance().setCurrentGame(GameMode.PRACTICE, GameDifficulty.EASY);
			root = (BorderPane)FXMLLoader.load(getClass().getResource("../view/GameScreen.fxml"));
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
			GameFactory.getInstance().setCurrentGame(GameMode.PRACTICE, GameDifficulty.HARD);
			root = (BorderPane)FXMLLoader.load(getClass().getResource("../view/GameScreen.fxml"));
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
			root = (BorderPane)FXMLLoader.load(getClass().getResource("../view/LeaderboardScreen.fxml"));
			((Node) e.getSource()).getScene().setRoot(root);
		} catch (IOException e1) {
			System.out.println("Error entering leaderboard screen");
			e1.printStackTrace();
		}
	}
	
}
