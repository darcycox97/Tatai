package tatai.controller;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import tatai.statistics.CSVFile;
import tatai.statistics.Medallist;
import tatai.statistics.Medallist.MedalType;
import tatai.view.Screen;

public class ClassStatsScreenController extends ScreenController {

	@FXML private LineChart<String,Double> progressChart;
	@FXML private NumberAxis xAxis;
	@FXML private NumberAxis yAxis;
	@FXML private ComboBox<String> comboGamemode;
	@FXML private ToggleGroup difficultyToggle;
	@FXML private RadioButton btnEasy;
	@FXML private RadioButton btnHard;

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

		setup();
		
		lblGoldUser.setText("--");
		lblGoldScore.setText("--");
		lblSilverUser.setText("--");
		lblSilverScore.setText("--");
		lblBronzeUser.setText("--");
		lblBronzeScore.setText("--");

		comboGamemode.getItems().addAll("Classic", "Arcade", "Time Attack");
		
		// update scores whenever new toggle is selected
		difficultyToggle.selectedToggleProperty().addListener(e -> loadBestScores());
	}

	@FXML public void loadBestScores() {

		if (comboGamemode.getSelectionModel().getSelectedItem() != null) {
			if (difficultyToggle.getSelectedToggle() != null) {

				Medallist[] medallists = new Medallist[3];

				medallists = getMedallists();

				setMedalLabels(medallists);
			}

		}

	}

	@FXML Medallist[] getMedallists() {

		String selectedMode = comboGamemode.getSelectionModel().getSelectedItem();
		String gamemode = null;

		Toggle selectedLevel = difficultyToggle.getSelectedToggle();
		String level = null;

		if (selectedMode.equals("Classic")) {
			gamemode = "CLASSIC";
		} else if (selectedMode.equals("Time Attack")) {
			gamemode = "TIME_ATTACK";
		} else if (selectedMode.equals("Arcade")) {
			gamemode = "ARCADE";
		}

		if (selectedLevel.equals(btnEasy)) {
			level = "EASY";
		} else if (selectedLevel.equals(btnHard)) {
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

	@Override
	protected Screen getScreen() {
		return Screen.CLASS_STATS;
	}

}
