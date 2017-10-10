package tatai.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import tatai.game.GameDifficulty;
import tatai.game.GameInstance;
import tatai.game.RegularEquationGame;

public class GameMenuController {
	
	@FXML private ToggleGroup toggleDifficulty;
	@FXML private RadioButton toggleEasy;
	@FXML private RadioButton toggleHard;
	@FXML private Tooltip tooltipTest;
	@FXML private Label lblHelp;
	@FXML private Button btnHome;
	@FXML private Button btnRegularGame;

	public void initialize() {}
	
	@FXML
	public void loadHomeScreen() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameMenu.fxml"));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void startRegularGame() {
		
		// determine what difficulty the game should be in
		if (toggleEasy.isSelected()) {
			GameInstance.getInstance().setCurrentGame(new RegularEquationGame(GameDifficulty.EASY));
		} else {
			GameInstance.getInstance().setCurrentGame(new RegularEquationGame(GameDifficulty.HARD));
		}
		
		// load the game screen
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameScreen.fxml"));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
