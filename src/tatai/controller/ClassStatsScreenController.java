package tatai.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import tatai.game.GameMode;
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
	}

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
	public void loadStatsMenu() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/StatsMenu.fxml"));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML public void loadBestScores() {

		Medallist[] medallists = new Medallist[3];

		medallists = getMedallists();

		setMedalLabels(medallists);

	}

	@FXML Medallist[] getMedallists() {

		String selected = comboGamemode.getSelectionModel().getSelectedItem();
		String gamemode = null;

		if (selected.equals("Classic")) {
			gamemode = "CLASSIC";
		} else if (selected.equals("Time Attack")) {
			gamemode = "TIME_ATTACK";
		} else if (selected.equals("Arcade")) {
			gamemode = "ARCADE";
		}

		Medallist[] medallists = new Medallist[3];

		medallists[0] = CSVFile.getMedallist(MedalType.GOLD, gamemode);
		medallists[1] = CSVFile.getMedallist(MedalType.SILVER, gamemode);
		medallists[2] = CSVFile.getMedallist(MedalType.BRONZE, gamemode);

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
