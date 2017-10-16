package tatai.controller;

import java.io.IOException;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import tatai.TataiPrototype;
import tatai.game.GameDifficulty;
import tatai.game.GameFactory;
import tatai.game.GameMode;
import tatai.statistics.CSVFile;
import tatai.statistics.CSVFile.CSVName;

public class CustomQuizMenuController {
	
	@FXML private VBox quizBox;
	@FXML private Button btnGoBack;
	@FXML private Button btnStartQuiz;
	@FXML private Label lblNoDisplay;
	
	private ToggleGroup quizToggle;
	
	
	@FXML
	public void initialize() {
		
		List<String> quizzes = CSVFile.getAllTitles(CSVName.QUIZZES);
		
		if (quizzes.size() == 0) {
			lblNoDisplay.setVisible(true);
		} else {
			quizToggle = new ToggleGroup();
			for (String q : quizzes) {
				RadioButton button = new RadioButton(q);
				button.setUserData(q);
				button.setToggleGroup(quizToggle);
				quizBox.getChildren().add(button);
			}
		}
	}
	
	@FXML
	public void openGameMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/GameMenu.fxml"));
			Parent root = loader.load();
			btnGoBack.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void startQuiz() {
		String quizName = (String) quizToggle.getSelectedToggle().getUserData();
		GameFactory.getInstance().setCurrentGame(GameMode.CUSTOM, GameDifficulty.EASY, quizName);
		
		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/GameScreen.fxml"));
			Parent root = loader.load();
			btnGoBack.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

}
