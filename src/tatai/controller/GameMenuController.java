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

import tatai.TataiPrototype;
import tatai.game.GameDifficulty;
import tatai.game.GameFactory;
import tatai.game.GameMode;
import tatai.view.Screen;

public class GameMenuController extends ScreenController {
	
	@FXML private ToggleGroup toggleDifficulty;
	@FXML private RadioButton toggleEasy;
	@FXML private RadioButton toggleHard;
	@FXML private Tooltip tooltipTest;
	@FXML private Label lblHelp;
	@FXML private Button btnClassic;
	@FXML private Button btnArcade;
	@FXML private Button btnTimeAttack;
	@FXML private Button btnCustom;
	@FXML private Button btnTutorial;

	@FXML
	public void initialize() {
		setup();
	}
	
	@FXML
	public void startClassicGame() {
		
		// determine what difficulty the game should be in
		if (toggleEasy.isSelected()) {
			GameFactory.getInstance().setCurrentGame(GameMode.CLASSIC, GameDifficulty.EASY, null);
		} else {
			GameFactory.getInstance().setCurrentGame(GameMode.CLASSIC, GameDifficulty.HARD, null);
		}
		
		setPreviousScreen();
		
		// load the game screen
		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/GameScreen.fxml"));
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
			GameFactory.getInstance().setCurrentGame(GameMode.ARCADE, GameDifficulty.EASY, null);
		} else {
			GameFactory.getInstance().setCurrentGame(GameMode.ARCADE, GameDifficulty.HARD, null);
		}
		
		setPreviousScreen();
		
		// load the game screen
		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/GameScreen.fxml"));
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
			GameFactory.getInstance().setCurrentGame(GameMode.TIME_ATTACK, GameDifficulty.EASY, null);
		} else {
			GameFactory.getInstance().setCurrentGame(GameMode.TIME_ATTACK, GameDifficulty.HARD, null);
		}
		
		setPreviousScreen();
		
		// load the game screen
		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/GameScreen.fxml"));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@FXML
	public void openCustomQuizzes() {
	
		setPreviousScreen();
		
		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/CustomQuizMenu.fxml"));
			Parent root = loader.load();
			btnCustom.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Screen getScreen() {
		return Screen.GAME_MENU;
	}
}
