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
import tatai.game.GameFactory;
import tatai.game.GameMode;
import tatai.game.OneMinuteEquationGame;
import tatai.game.RegularEquationGame;
import tatai.game.TimedEquationGame;

public class GameMenuController {
	
	@FXML private ToggleGroup toggleDifficulty;
	@FXML private RadioButton toggleEasy;
	@FXML private RadioButton toggleHard;
	@FXML private Tooltip tooltipTest;
	@FXML private Label lblHelp;
	@FXML private Button btnHome;
	@FXML private Button btnClassic;
	@FXML private Button btnArcade;
	@FXML private Button btnTimeAttack;

	public void initialize() {}
	
	@FXML
	public void loadHomeScreen() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Home.fxml"));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void startClassicGame() {
		
		// determine what difficulty the game should be in
		if (toggleEasy.isSelected()) {
			GameFactory.getInstance().setCurrentGame(GameMode.CLASSIC, GameDifficulty.EASY);
		} else {
			GameFactory.getInstance().setCurrentGame(GameMode.CLASSIC, GameDifficulty.HARD);
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
	
	@FXML
	public void startArcadeGame() {

		// determine what difficulty the game should be in
		if (toggleEasy.isSelected()) {
			GameFactory.getInstance().setCurrentGame(GameMode.ARCADE, GameDifficulty.EASY);
		} else {
			GameFactory.getInstance().setCurrentGame(GameMode.ARCADE, GameDifficulty.HARD);
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
	
	@FXML
	public void startTimeAttackGame() {
		
		// determine what difficulty the game should be in
		if (toggleEasy.isSelected()) {
			GameFactory.getInstance().setCurrentGame(GameMode.TIME_ATTACK, GameDifficulty.EASY);
		} else {
			GameFactory.getInstance().setCurrentGame(GameMode.TIME_ATTACK, GameDifficulty.HARD);
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
