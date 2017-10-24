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
import tatai.view.Screen;

public class CustomQuizMenuController extends ScreenController {
	
	@FXML private VBox quizBox;
	@FXML private Button btnStartQuiz;
	@FXML private Label lblNoDisplay;
	
	private ToggleGroup quizToggle;
	
	
	@FXML
	public void initialize() {
		
		setup();
		
		List<String> quizzes = CSVFile.getAllTitles(CSVName.QUIZZES);
		
		if (quizzes.size() == 0) {
			lblNoDisplay.setVisible(true);
		} else {
			quizToggle = new ToggleGroup();
			for (String q : quizzes) {
				RadioButton button = new RadioButton(q);
				button.setUserData(q);
				button.setToggleGroup(quizToggle);
				button.getStyleClass().add("label-20px");
				quizBox.getChildren().add(button);
			}
		}
	}
	
	@FXML
	public void startQuiz() {
		
		confirmExit();
		setPreviousScreen();
		
		if (quizToggle.getSelectedToggle() != null) {
			String quizName = (String) quizToggle.getSelectedToggle().getUserData();
			GameFactory.getInstance().setCurrentGame(GameMode.CUSTOM, GameDifficulty.EASY, quizName);

			try {
				FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/GameScreen.fxml"));
				Parent root = loader.load();
				btnStartQuiz.getScene().setRoot(root);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected Screen getScreen() {
		return Screen.CUSTOM_QUIZ_MENU;
	}
	

}
