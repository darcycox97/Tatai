package tatai.controller;

import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import tatai.TataiPrototype;
import tatai.view.Screen;

public class TeacherMenuController extends ScreenController {

	@FXML private MenuButton menuLogout;
	@FXML private Button btnCreateQuiz;
	@FXML private Button btnMainStats;

	@FXML
	public void initialize() {

		setup();

		// initialize log out menu item
		menuLogout.setText("Teacher");
		menuLogout.getItems().clear();

		MenuItem logout = new MenuItem("Log Out");
		logout.setOnAction(e -> {
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
					((Stage)menuLogout.getScene().getWindow()).setScene(scene);

				} catch (IOException e1) {
					e1.printStackTrace();
				}

			} else {
				return; // user does not wish to log out
			}
		});

		menuLogout.getItems().add(logout);
	}

	@FXML
	public void openQuizCreator() {
		if (confirmExit()) {
			setPreviousScreen();
			try {
				FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/QuizCreator.fxml"));
				Parent root = loader.load();
				btnCreateQuiz.getScene().setRoot(root);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected Screen getScreen() {
		return Screen.TEACHER_MENU;
	}

}
