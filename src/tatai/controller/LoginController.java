package tatai.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tatai.statistics.CSVFile;
import tatai.statistics.CSVFile.CSVName;
import tatai.statistics.User;

public class LoginController {

	@FXML private Button btnLogin;
	@FXML private ComboBox<String> comboUsers;
	@FXML private TextField txtUserName;

	private static final String NEW_USER = "New User";
	private static final String TEACHER = "Teacher";

	@FXML
	public void initialize() {

		txtUserName.setVisible(false); // becomes visible when new user is selected

		comboUsers.getItems().add(TEACHER);
		
		// add option for new user, and set up to show text field if selected
		comboUsers.getItems().add(NEW_USER);
		comboUsers.getSelectionModel().selectedItemProperty().addListener(e -> {
			if (comboUsers.getSelectionModel().getSelectedItem().equals(NEW_USER)) {
				txtUserName.setVisible(true);
				btnLogin.setDisable(true);
			} else {
				txtUserName.setVisible(false);
				btnLogin.setDisable(false);
			}
		});


		for (String name : CSVFile.getAllTitles(CSVName.STATISTICS)) {
			comboUsers.getItems().add(name);
		}
		

		// apply a regex to the text field so we can only login if valid input is supplied
		// only allow hyphens underscores and alphanumerics
		txtUserName.textProperty().addListener(e-> {
			String text = txtUserName.getText();
			if (Pattern.matches("[\\w\\-]+", text)) {
				btnLogin.setDisable(false);
			} else {
				btnLogin.setDisable(true);
			}
		});

	}


	@FXML
	public void login() {

		//TODO: password protect teacher login.
		
		// determine if username provided, and if so set the current user for the session

		String username;
		String selected = comboUsers.getSelectionModel().getSelectedItem();
		if (selected == null) {

			Alert alert = new Alert(
					AlertType.INFORMATION,
					"Please select a username before logging in"
					);
			alert.showAndWait();
			return;

		} else if (selected.equals(NEW_USER)) {
			// no need for validation check because login button can't be clicked if invalid
			username = txtUserName.getText();
		} else {
			// pre existing username is selected
			username = selected;
		}

		User.getInstance().setName(username);

		if (username.equals(TEACHER)) {
			// load teacher menu
			try {
				BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../view/TeacherMenu.fxml"));
				Scene scene = new Scene(root,700,600);
				((Stage)btnLogin.getScene().getWindow()).setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} else {
			// load the home screen
			try {
				BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
				Scene scene = new Scene(root,700,600);
				((Stage)btnLogin.getScene().getWindow()).setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
