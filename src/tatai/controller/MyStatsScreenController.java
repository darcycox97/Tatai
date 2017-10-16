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

public class MyStatsScreenController {

	@FXML private LineChart<String,Double> progressChart;
	@FXML private NumberAxis xAxis;
	@FXML private NumberAxis yAxis;
	@FXML private Button btnHome;
	@FXML private Button btnBack;
	@FXML private ComboBox<String> comboGamemode;
	@FXML private Label lblBestScore;
	@FXML private Label lblAverageScore;
	@FXML private Label lblNoScores;
	@FXML private Label lblPlayGames;
	@FXML private Label lblChooseMode;

	@FXML
	public void initialize() {
		comboGamemode.getItems().addAll("Classic", "Arcade", "Time Attack");
		loadProgressChart();
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
	private void loadProgressChart() {
		
		String username = User.getInstance().getName();
		String gamemode = null;

		String selection = comboGamemode.getSelectionModel().getSelectedItem();

		if (selection == null) {
		} else {
			if (selection.equals("Classic")) {
				gamemode = "CLASSIC";
			} else if (selection.equals("Time Attack")) {
				gamemode = "TIME_ATTACK";
			} else if (selection.equals("Arcade")) {
				gamemode = "ARCADE";
			}
		}

		lblAverageScore.setText(CSVFile.getAverage(username, gamemode));
		List<Double> scores = CSVFile.getUserData(username, gamemode);
		lblBestScore.setText(CSVFile.getBest(scores, gamemode));

		progressChart.setTitle(User.getInstance().getName() + "'s Progress");
		progressChart.getData().clear();
		progressChart.getData().add(CSVFile.getSeriesData(username, gamemode));
		
		if (selection == null) {
			progressChart.setVisible(false);
			lblBestScore.setText("--");
			lblAverageScore.setText("--");
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

}
