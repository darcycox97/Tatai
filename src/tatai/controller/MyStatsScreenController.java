package tatai.controller;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import tatai.statistics.CSVFile;
import tatai.statistics.User;
import tatai.view.Screen;

public class MyStatsScreenController extends ScreenController {

	@FXML private LineChart<String,Double> progressChart;
	@FXML private NumberAxis xAxis;
	@FXML private NumberAxis yAxis;
	@FXML private Button btnHome;
	@FXML private Button btnBack;
	
	@FXML private ComboBox<String> comboGamemode;
	@FXML private ToggleGroup difficultyToggle;
	@FXML private RadioButton btnEasy;
	@FXML private RadioButton btnHard;
	
	@FXML private Label lblBestScore;
	@FXML private Label lblAverageScore;
	@FXML private Label lblGamesPlayed;
	@FXML private Label lblNoScores;
	@FXML private Label lblPlayGames;
	@FXML private Label lblChooseMode;
	@FXML private Label lblStatsTitle;
	

	@FXML
	public void initialize() {
		setup();
		comboGamemode.getItems().addAll("Classic", "Arcade", "Time Attack");
		loadProgress();
		
		// ensure data is updated when toggle is changed
		difficultyToggle.selectedToggleProperty().addListener(e -> loadProgress());
	}

	@FXML
	private void loadProgress() {
		
		String username = User.getInstance().getName();
		String gamemode = null;
		String level = null;

		String selectedMode = comboGamemode.getSelectionModel().getSelectedItem();
		Toggle selectedLevel = difficultyToggle.getSelectedToggle();

		if (selectedMode == null) {
		} else {
			if (selectedMode.equals("Classic")) {
				gamemode = "CLASSIC";
			} else if (selectedMode.equals("Time Attack")) {
				gamemode = "TIME_ATTACK";
			} else if (selectedMode.equals("Arcade")) {
				gamemode = "ARCADE";
			}
		}
		
		if (selectedLevel == null) {
		} else {
			if (selectedLevel.equals(btnEasy)) {
				level = "EASY";
			} else if (selectedLevel.equals(btnHard)) {
				level = "HARD";
			}
		}

		lblAverageScore.setText(CSVFile.getAverage(username, gamemode, level));
		List<Double> scores = CSVFile.getUserData(username, gamemode, level);
		lblBestScore.setText(CSVFile.getBest(scores, gamemode));
		lblGamesPlayed.setText(String.valueOf(scores.size()));

		lblStatsTitle.setText(User.getInstance().getName() + "'s Progress");
		progressChart.getData().clear();
		progressChart.getData().add(CSVFile.getSeriesData(username, gamemode, level));
		
		if (selectedMode == null || selectedLevel == null) {
			progressChart.setVisible(false);
			lblBestScore.setText("--");
			lblAverageScore.setText("--");
			lblGamesPlayed.setText("--");
			lblNoScores.setVisible(false);
			lblPlayGames.setVisible(false);
			lblChooseMode.setVisible(true);
		} else if (scores.size() == 0) {
			progressChart.setVisible(false);
			lblBestScore.setText("--");
			lblAverageScore.setText("--");
			lblNoScores.setVisible(true);
			lblPlayGames.setVisible(true);
			lblChooseMode.setVisible(false);
		} else {
			progressChart.setVisible(true);
			lblNoScores.setVisible(false);
			lblPlayGames.setVisible(false);
			lblChooseMode.setVisible(false);
		}

	}

	@Override
	protected Screen getScreen() {
		return Screen.MY_STATS;
	}

}
