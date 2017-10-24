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
import tatai.TataiPrototype;
import tatai.statistics.User;
import tatai.view.Screen;

public class HomeController extends ScreenController {

	@FXML private Label lblMain;
	@FXML private MenuButton menuUser;
	@FXML private Button btnMainGames;
	@FXML private Button btnMainPractice;
	@FXML private Button btnMainStats;
	
	@FXML
	public void initialize() {
		
		setup();
		
		navigationPane.setVisible(false);
		
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
					FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/Login.fxml"));
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

	@Override
	protected Screen getScreen() {
		return Screen.HOME;
	}
	
	
}
