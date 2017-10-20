package tatai.controller;

import java.io.IOException;
import java.util.regex.Pattern;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import tatai.TataiPrototype;
import tatai.statistics.CSVFile;
import tatai.statistics.CSVFile.CSVName;
import tatai.statistics.User;
import javafx.scene.control.PasswordField;

public class LoginController {

	@FXML private Button btnLogin;
	@FXML private ComboBox<String> comboUsers;
	@FXML private TextField txtUserName;
	@FXML private PasswordField passwordField;

	private static final String NEW_USER = "New User";
	private static final String TEACHER = "Teacher";
	private static final String TEACHER_PASSWORD = "admin";
	
	private boolean passwordCorrect = false;

	@FXML
	public void initialize() {

		txtUserName.setVisible(false); // becomes visible when new user is selected
		btnLogin.setDisable(true); // becomes enabled when valid selection is made

		comboUsers.getItems().add(TEACHER);
		
		// add option for new user, and set up to show text field if selected
		comboUsers.getItems().add(NEW_USER);
		comboUsers.getSelectionModel().selectedItemProperty().addListener(e -> {
			
			String selected = comboUsers.getSelectionModel().getSelectedItem();
			if (selected.equals(NEW_USER)) {
				txtUserName.setVisible(true);
				passwordField.setVisible(false);
				btnLogin.setDisable(true);
			} else if(selected.equals(TEACHER)) {
				txtUserName.setVisible(false);
				passwordField.setVisible(true);
				btnLogin.setDisable(false);
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
		
		// checks the correctness of the password for teacher.
		passwordField.textProperty().addListener(e -> {
			String text = passwordField.getText();
			passwordCorrect = text.equals(TEACHER_PASSWORD);
		});

	}


	@FXML
	public void login() {
		
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
			
			// add new line to stats csv if this is a new username
			if (!CSVFile.titleExists(CSVName.STATISTICS, username)) {
				CSVFile.appendToCSV(CSVName.STATISTICS, username);
			} else {
				Alert alert = new Alert(
					AlertType.INFORMATION,
					"The username \"" + username + "\" is already taken.",
					ButtonType.OK
				);
				alert.showAndWait();
				return;
			}
		} else {
			// pre existing user name is selected
			username = selected;
		}

		User.getInstance().setName(username);

		if (username.equals(TEACHER)) {
			
			if (passwordCorrect) {
				// load teacher menu
				try {
					BorderPane root = (BorderPane)FXMLLoader.load(TataiPrototype.class.getResource("view/TeacherMenu.fxml"));
					Scene scene = new Scene(root,700,600);
					((Stage)btnLogin.getScene().getWindow()).setScene(scene);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				Alert alert = new Alert(AlertType.WARNING, "That password is not correct, please try again.", ButtonType.OK);
				alert.showAndWait();
				return;
			}

		} else {
			// load the home screen
			try {
				BorderPane root = (BorderPane)FXMLLoader.load(TataiPrototype.class.getResource("view/Home.fxml"));
				Scene scene = new Scene(root,700,600);
				((Stage)btnLogin.getScene().getWindow()).setScene(scene);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
