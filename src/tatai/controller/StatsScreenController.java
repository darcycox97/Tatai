package tatai.controller;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import tatai.statistics.CSVFile;
import tatai.statistics.User;

public class StatsScreenController {

	@FXML private LineChart<String,Double> progressChart;
	@FXML private NumberAxis xAxis;
	@FXML private NumberAxis yAxis;
	@FXML private Button btnHome;

	@FXML
	public void initialize() {
		loadProgressChart();
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
	private void loadProgressChart() {
		
		String username = User.getInstance().getName();
		String gamemode = "TEN_QUESTIONS";
		
//		CSVFile.setAverage(username, gamemode);
		
		progressChart.setTitle(User.getInstance().getName() + "'s Progress");
//		progressChart.getData().add(CSVFile.getData(username, gamemode));
		
	}

}
