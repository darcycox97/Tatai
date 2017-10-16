package tatai.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import tatai.TataiPrototype;

public class StatsMenuController {
	
	@FXML private Button btnMe;
	@FXML private Button btnClass;
	@FXML private Button btnHome;

	public void initialize() {}
	
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
	public void loadMyStats() {
		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/MyStatsScreen.fxml"));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void loadClassStats() {
		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/ClassStatsScreen.fxml"));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
