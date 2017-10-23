package tatai.controller;

import java.io.IOException;

import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import tatai.TataiPrototype;
import tatai.statistics.CSVFile;
import tatai.statistics.User;

public class MyStatsScreenController extends ScreenController {

	@FXML private LineChart<String,Double> progressChart;
	@FXML private NumberAxis xAxis;
	@FXML private NumberAxis yAxis;
	@FXML private Button btnHome;
	@FXML private Button btnBack;
	
	@FXML private ComboBox<String> comboGamemode;
	@FXML private ComboBox<String> comboLevel;
	
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
		comboLevel.getItems().addAll("Easy", "Hard");
		loadProgress();
	}

	@FXML
	public void loadHomeScreen() {
		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/Home.fxml"));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void loadStatsMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/StatsMenu.fxml"));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private void loadProgress() {
		
		String username = User.getInstance().getName();
		String gamemode = null;
		String level = null;

		String selectedMode = comboGamemode.getSelectionModel().getSelectedItem();
		String selectedLevel = comboLevel.getSelectionModel().getSelectedItem();

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
			if (selectedLevel.equals("Easy")) {
				level = "EASY";
			} else if (selectedLevel.equals("Hard")) {
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
	public void loadPreviousScreen() {
		// TODO Auto-generated method stub
		
	}

}
