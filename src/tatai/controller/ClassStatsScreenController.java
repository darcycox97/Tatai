package tatai.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import tatai.TataiPrototype;
import tatai.game.GameDifficulty;
import tatai.statistics.CSVFile;
import tatai.statistics.Medallist;
import tatai.statistics.Medallist.MedalType;

public class ClassStatsScreenController {

	@FXML private LineChart<String,Double> progressChart;
	@FXML private NumberAxis xAxis;
	@FXML private NumberAxis yAxis;
	@FXML private Button btnHome;
	@FXML private Button btnBack;
	@FXML private ComboBox<String> comboGamemode;
	@FXML private ComboBox<String> comboLevel;

	@FXML private Label lblFirstPlace;
	@FXML private Label lblSecondPlace;
	@FXML private Label lblThirdPlace;

	@FXML private Label lblGoldUser;
	@FXML private Label lblGoldScore;
	@FXML private Label lblSilverUser;
	@FXML private Label lblSilverScore;
	@FXML private Label lblBronzeUser;
	@FXML private Label lblBronzeScore;

	@FXML
	public void initialize() {

		lblGoldUser.setText("--");
		lblGoldScore.setText("--");
		lblSilverUser.setText("--");
		lblSilverScore.setText("--");
		lblBronzeUser.setText("--");
		lblBronzeScore.setText("--");

		comboGamemode.getItems().addAll("Classic", "Arcade", "Time Attack");
		comboLevel.getItems().addAll("Easy", "Hard");
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

	@FXML public void loadBestScores() {

		if (comboGamemode.getSelectionModel().getSelectedItem() != null) {
			if (comboLevel.getSelectionModel().getSelectedItem() != null) {

				Medallist[] medallists = new Medallist[3];

				medallists = getMedallists();

				setMedalLabels(medallists);
			}

		}

	}

	@FXML Medallist[] getMedallists() {

		String selectedMode = comboGamemode.getSelectionModel().getSelectedItem();
		String gamemode = null;

		String selectedLevel = comboLevel.getSelectionModel().getSelectedItem();
		String level = null;

		if (selectedMode.equals("Classic")) {
			gamemode = "CLASSIC";
		} else if (selectedMode.equals("Time Attack")) {
			gamemode = "TIME_ATTACK";
		} else if (selectedMode.equals("Arcade")) {
			gamemode = "ARCADE";
		}

		if (selectedLevel.equals("Easy")) {
			level = "EASY";
		} else if (selectedLevel.equals("Hard")) {
			level = "HARD";
		}

		Medallist[] medallists = new Medallist[3];

		medallists[0] = CSVFile.getMedallist(MedalType.GOLD, gamemode, level);
		medallists[1] = CSVFile.getMedallist(MedalType.SILVER, gamemode, level);
		medallists[2] = CSVFile.getMedallist(MedalType.BRONZE, gamemode, level);

		return medallists;
	}

	private void setMedalLabels(Medallist[] medallists) {

		for (Medallist medallist : medallists) {
			if (medallist.getUsername() == null) {
				medallist.setDashes();
			}
		}

		lblGoldUser.setText(medallists[0].getUsername());
		lblSilverUser.setText(medallists[1].getUsername());
		lblBronzeUser.setText(medallists[2].getUsername());

		lblGoldScore.setText(medallists[0].getScore());
		lblSilverScore.setText(medallists[1].getScore());
		lblBronzeScore.setText(medallists[2].getScore());

	}

}
