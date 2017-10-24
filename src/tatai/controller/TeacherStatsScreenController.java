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
import tatai.statistics.CSVFile.CSVName;
import tatai.view.Screen;

public class TeacherStatsScreenController extends ScreenController {

	@FXML private LineChart<String,Double> progressChart;
	@FXML private NumberAxis xAxis;
	@FXML private NumberAxis yAxis;
	@FXML private Button btnHome;
	@FXML private Button btnBack;
	
	@FXML private ComboBox<String> comboGamemode;
	@FXML private ComboBox<String> comboStudent;
	@FXML private ToggleGroup difficultyToggle;
	@FXML private RadioButton btnEasy;
	@FXML private RadioButton btnHard;
	
	@FXML private Label lblBestScore;
	@FXML private Label lblAverageScore;
	@FXML private Label lblGamesPlayed;
	@FXML private Label lblNoScores;
	@FXML private Label lblChooseMode;

	@FXML
	public void initialize() {
		setup();
		comboGamemode.getItems().addAll("Classic", "Arcade", "Time Attack");
		comboStudent.getItems().addAll(CSVFile.getAllTitles(CSVName.STATISTICS));
		comboStudent.getSelectionModel().selectFirst();
		loadProgress();
		
		// ensure data is updated when new difficulty selected
		difficultyToggle.selectedToggleProperty().addListener(e -> loadProgress());
	}

	@FXML
	private void loadProgress() {
		
		String gamemode = null;
		String level = null;

		String username = comboStudent.getSelectionModel().getSelectedItem();
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

		progressChart.setTitle(username + "'s Progress");
		progressChart.getData().clear();
		progressChart.getData().add(CSVFile.getSeriesData(username, gamemode, level));
		
		if (selectedMode == null || selectedLevel == null || username == null) {
			progressChart.setVisible(false);
			lblBestScore.setText("--");
			lblAverageScore.setText("--");
			lblGamesPlayed.setText("--");
			lblNoScores.setVisible(false);
			lblChooseMode.setVisible(true);
		} else if (scores.size() == 0) {
			progressChart.setVisible(false);
			lblBestScore.setText("--");
			lblAverageScore.setText("--");
			lblNoScores.setVisible(true);
			lblChooseMode.setVisible(false);
		} else {
			progressChart.setVisible(true);
			lblNoScores.setVisible(false);
			lblChooseMode.setVisible(false);
		}

	}

	@Override
	protected Screen getScreen() {
		return Screen.TEACHER_STATS;
	}

}
