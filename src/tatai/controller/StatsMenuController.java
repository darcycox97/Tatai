package tatai.controller;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import tatai.TataiPrototype;
import tatai.view.Screen;

public class StatsMenuController extends ScreenController {
	
	@FXML private Button btnMe;
	@FXML private Button btnClass;
	@FXML private Button btnHome;

	public void initialize() {
		setup();
	}
	
	@FXML
	public void loadMyStats() {
		setPreviousScreen();
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
		setPreviousScreen();
		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/ClassStatsScreen.fxml"));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected Screen getScreen() {
		return Screen.STATS_MENU;
	}

}
