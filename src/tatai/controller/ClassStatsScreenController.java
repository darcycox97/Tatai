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
import tatai.statistics.CSVFile;
import tatai.statistics.Medallist;

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

//		lblFirstPlace.setVisible(false);
//		lblSecondPlace.setVisible(false);
//		lblThirdPlace.setVisible(false);
//
//		lblGoldUser.setVisible(false);
//		lblGoldScore.setVisible(false);
//		lblSilverUser.setVisible(false);
//		lblSilverScore.setVisible(false);
//		lblBronzeUser.setVisible(false);
//		lblBronzeScore.setVisible(false);

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

		String gamemode = comboGamemode.getSelectionModel().getSelectedItem();
		Medallist[] medallists = new Medallist[3];
		
		if (gamemode.equals("Classic")) {
			medallists = getClassicMedallists(gamemode);
		} else if (gamemode.equals("Arcade")) {
			medallists = getArcadeMedallists(gamemode);
		} else if (gamemode.equals("Time Attack")) {
			medallists = getTimeAttackMedallists(gamemode);
		}
		
		setMedalLabels(medallists);
		
	}
	
	@FXML Medallist[] getClassicMedallists(String gamemode) {

		Medallist gold = CSVFile.getGoldMedallist(gamemode);
		Medallist silver = CSVFile.getSilverMedallist(gamemode);
		Medallist bronze = CSVFile.getBronzeMedallist(gamemode);
		
		return setMedallists(gold, silver, bronze);
	}
	
	@FXML Medallist[] getArcadeMedallists(String gamemode) {
		
		Medallist gold = CSVFile.getGoldMedallist(gamemode);
		Medallist silver = CSVFile.getSilverMedallist(gamemode);
		Medallist bronze = CSVFile.getBronzeMedallist(gamemode);
		
		return setMedallists(gold, silver, bronze);
	}
	
	@FXML Medallist[] getTimeAttackMedallists(String gamemode) {
		
		Medallist gold = CSVFile.getGoldMedallist(gamemode);
		Medallist silver = CSVFile.getSilverMedallist(gamemode);
		Medallist bronze = CSVFile.getBronzeMedallist(gamemode);

		return setMedallists(gold, silver, bronze);
	}
	
	private Medallist[] setMedallists(Medallist gold, Medallist silver, Medallist bronze) {
		Medallist[] medallists = new Medallist[3];
		medallists[0] = gold;
		medallists[1] = silver;
		medallists[2] = bronze;
		return medallists;
	}
	
	private void setMedalLabels(Medallist[] medallists) {
		lblGoldUser.setText(medallists[0].getUsername());
		lblSilverUser.setText(medallists[1].getUsername());
		lblBronzeUser.setText(medallists[2].getUsername());
		
		lblGoldScore.setText(medallists[0].getScore());
		lblSilverScore.setText(medallists[1].getScore());
		lblBronzeScore.setText(medallists[2].getScore());
	}

}
