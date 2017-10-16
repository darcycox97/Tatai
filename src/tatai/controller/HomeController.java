package tatai.controller;

import java.io.IOException;

import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import tatai.game.GameDifficulty;
import tatai.game.GameFactory;
import tatai.game.GameMode;
import tatai.statistics.User;

public class HomeController {

	@FXML private Label lblMain;
	@FXML private MenuButton menuUser;
	@FXML private Button btnGames;
	@FXML private Button btnPractice;
	@FXML private Button btnStatistics;
	
	@FXML
	public void initialize() {
		
		// set text of user button to show who is logged in
		menuUser.setText(User.getInstance().getName());
		
		// set up log out menu
		
		menuUser.getItems().clear(); // clear any pre existing menu items
		MenuItem logOutMenu = new MenuItem("Log Out");
		
		logOutMenu.setOnAction(e -> {
			// ask for log out confirmation
			Alert confirm = new Alert(
				AlertType.WARNING,
				"Are you sure you want to log out?",
				ButtonType.YES,
				ButtonType.NO
			);
			
			Optional<ButtonType> result = confirm.showAndWait();
			
			if (result.isPresent() && result.get().equals(ButtonType.YES)) {
				try {	
					FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/Login.fxml"));
					Parent root = loader.load();
					Scene scene = new Scene(root,700,300);
					((Stage)menuUser.getScene().getWindow()).setScene(scene);
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			} else {
				return; // user does not wish to log out
			}
		});
		
		menuUser.getItems().add(logOutMenu);
	}
	
	@FXML
	public void openGames() {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameMenu.fxml"));
			Parent root = loader.load();
			btnGames.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void openPractice() {
		
		GameFactory.getInstance().setCurrentGame(GameMode.PRACTICE, GameDifficulty.EASY, null);
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/GameScreen.fxml"));
			Parent root = loader.load();
			btnGames.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void openStatistics() {
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/StatsMenu.fxml"));
			Parent root = loader.load();
			btnGames.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
