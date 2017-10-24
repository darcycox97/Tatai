package tatai.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import tatai.TataiPrototype;
import tatai.game.GameDifficulty;
import tatai.game.GameFactory;
import tatai.game.GameMode;
import tatai.statistics.User;
import tatai.view.Screen;

public abstract class ScreenController {

	protected static Screen CURRENT_SCREEN = Screen.HOME;
	protected static Screen PREVIOUS_SCREEN = Screen.HOME;
	
	@FXML protected Button btnBack;
	@FXML protected Button btnHome;
	@FXML protected Button btnGames;
	@FXML protected Button btnPractice;
	@FXML protected Button btnCreateQuiz;
	@FXML protected Button btnStats;
	@FXML protected Button btnHelp;
	@FXML protected Button btnLogout;
	@FXML protected ImageView menuIcon;

	@FXML protected Pane navigationPane;

	protected static final double NAV_PANE_INIT_WIDTH = 0;
	protected static final double NAV_PANE_FINAL_WIDTH = 140;

	@FXML
	public void setup() {

		CURRENT_SCREEN = getScreen();

		navigationPane.setPrefWidth(NAV_PANE_INIT_WIDTH);
		navigationPane.setDisable(false);

		btnBack.managedProperty().bind(btnBack.visibleProperty());
		btnHome.managedProperty().bind(btnHome.visibleProperty());
		btnGames.managedProperty().bind(btnGames.visibleProperty());
		btnPractice.managedProperty().bind(btnPractice.visibleProperty());
		btnStats.managedProperty().bind(btnStats.visibleProperty());
		btnHelp.managedProperty().bind(btnHelp.visibleProperty());
		btnLogout.managedProperty().bind(btnLogout.visibleProperty());

		btnBack.setVisible(false);
		btnHome.setVisible(false);
		btnGames.setVisible(false);
		btnPractice.setVisible(false);
		btnCreateQuiz.setVisible(false);
		btnStats.setVisible(false);
		btnHelp.setVisible(false);
		btnLogout.setVisible(false);

	}

	// To be implemented by each child class
	protected abstract Screen getScreen();

	@FXML
	public void showNavigationBar() {

		// create slide animation to show the navigation bar.
		Timeline slideIn = new Timeline();
		List<KeyFrame> keyFrames = new ArrayList<KeyFrame>();

		keyFrames.add(new KeyFrame(Duration.ZERO, new KeyValue(navigationPane.prefWidthProperty(), NAV_PANE_INIT_WIDTH)));
		keyFrames.add(new KeyFrame(Duration.millis(300), new KeyValue(navigationPane.prefWidthProperty(), NAV_PANE_FINAL_WIDTH)));

		keyFrames.add(new KeyFrame(Duration.millis(300), new KeyValue(btnHome.visibleProperty(), true)));
		keyFrames.add(new KeyFrame(Duration.millis(300), new KeyValue(btnStats.visibleProperty(), true)));
		keyFrames.add(new KeyFrame(Duration.millis(300), new KeyValue(btnLogout.visibleProperty(), true)));

		if (User.getInstance().getName().equals("Teacher")) {

			keyFrames.add(new KeyFrame(Duration.millis(300), new KeyValue(btnCreateQuiz.visibleProperty(), true)));
			keyFrames.add(new KeyFrame(Duration.millis(300), new KeyValue(btnGames.visibleProperty(), false)));
			keyFrames.add(new KeyFrame(Duration.millis(300), new KeyValue(btnPractice.visibleProperty(), false)));

		} else {

			keyFrames.add(new KeyFrame(Duration.millis(300), new KeyValue(btnCreateQuiz.visibleProperty(), false)));
			keyFrames.add(new KeyFrame(Duration.millis(300), new KeyValue(btnGames.visibleProperty(), true)));
			keyFrames.add(new KeyFrame(Duration.millis(300), new KeyValue(btnPractice.visibleProperty(), true)));
		}

		if (PREVIOUS_SCREEN.equals(Screen.GAME) || PREVIOUS_SCREEN.equals(Screen.HOME)){

			keyFrames.add(new KeyFrame(Duration.millis(300), new KeyValue(btnBack.visibleProperty(), false)));

		} else {
			keyFrames.add(new KeyFrame(Duration.millis(300), new KeyValue(btnBack.visibleProperty(), true)));
		}

		if (getScreen().equals(Screen.GAME_MENU)) {
			keyFrames.add(new KeyFrame(Duration.millis(300), new KeyValue(btnHelp.visibleProperty(), true)));
		} else {
			keyFrames.add(new KeyFrame(Duration.millis(300), new KeyValue(btnHelp.visibleProperty(), false)));
		}

		slideIn.getKeyFrames().addAll(keyFrames);
		navigationPane.setStyle("-fx-background-color:#ffc387;");
		slideIn.play();

	}

	@FXML
	public void hideNavigationBar() {

		btnBack.setVisible(false);
		btnHome.setVisible(false);
		btnGames.setVisible(false);
		btnPractice.setVisible(false);
		btnCreateQuiz.setVisible(false);
		btnStats.setVisible(false);
		btnHelp.setVisible(false);
		btnLogout.setVisible(false);

		// create slide down animation to hide the exit pane.
		Timeline slideOut = new Timeline();
		slideOut.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO, new KeyValue(navigationPane.prefWidthProperty(), NAV_PANE_FINAL_WIDTH)),
				new KeyFrame(Duration.millis(300), new KeyValue(navigationPane.prefWidthProperty(), NAV_PANE_INIT_WIDTH)),
				new KeyFrame(Duration.millis(300), new KeyValue(navigationPane.styleProperty(), "-fx-background-color:transparent;"))
				);

		slideOut.play();

	}

	@FXML
	public void loadHomeScreen() {
		
		confirmExit();
		setPreviousScreen();

		String toLoad = null;
		if (User.getInstance().getName().equals("Teacher")) {
			toLoad = "view/TeacherMenu.fxml";
		} else {
			toLoad = "view/Home.fxml";
		}

		try {

			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource(toLoad));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	protected void confirmExit() {
		if (CURRENT_SCREEN.equals(Screen.GAME) || CURRENT_SCREEN.equals(Screen.CREATE_QUIZ)) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Exit Confirmation");

			alert.setHeaderText("Are you sure you wish to exit?");
			alert.setContentText("All progress will be lost.");

			ButtonType buttonTypeYes = new ButtonType("Yes");
			ButtonType buttonTypeCancel = new ButtonType("No", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() != buttonTypeYes){
				return;
			} 

		} 
	}

	protected void setPreviousScreen() {
		PREVIOUS_SCREEN = CURRENT_SCREEN;
	}

	@FXML
	public void loadPreviousScreen() {
		confirmExit();
		String toLoad = getFXMLString(PREVIOUS_SCREEN);
		setPreviousScreen();
		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource(toLoad));
			Parent root = loader.load();
			btnBack.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void loadGameScreen() {

		confirmExit();
		setPreviousScreen();

		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/GameMenu.fxml"));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void loadPracticeScreen() {

		confirmExit();
		setPreviousScreen();

		GameFactory.getInstance().setCurrentGame(GameMode.PRACTICE, GameDifficulty.EASY, null);

		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/GameScreen.fxml"));
			Parent root = loader.load();
			btnGames.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void loadStatsScreen() {
		
		confirmExit();
		setPreviousScreen();


		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/StatsMenu.fxml"));
			if (User.getInstance().getName().equals("Teacher")) {
				loader = new FXMLLoader(TataiPrototype.class.getResource("view/TeacherStatsScreen.fxml"));
			}
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void loadCreateScreen() {

		setPreviousScreen();
		
		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/QuizCreator.fxml"));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void loadHelpScreen() {

		setPreviousScreen();

		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/TutorialScreen.fxml"));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void logout() {
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
				((Stage)btnLogout.getScene().getWindow()).setScene(scene);

			} catch (IOException e1) {
				e1.printStackTrace();
			}

		} else {
			return; // user does not wish to log out
		}

	}

	protected String getFXMLString(Screen screen) {

		String FXMLString = "view/";

		switch (screen) {
		case HOME: FXMLString += "Home.fxml";
		break;
		case GAME_MENU: FXMLString += "GameMenu.fxml";
		break;
		case GAME: FXMLString += "GameScreen.fxml";
		break;
		case STATS_MENU: FXMLString += "StatsMenu.fxml";
		break;
		case MY_STATS: FXMLString += "MyStatsScreen.fxml";
		break;
		case CLASS_STATS: FXMLString += "ClassStatsScreen.fxml";
		break;
		case CREATE_QUIZ: FXMLString += "QuizCreator.fxml";
		break;
		case CUSTOM_QUIZ_MENU: FXMLString += "CustomQuizMenu.fxml";
		break;
		case TEACHER_MENU: FXMLString += "TeacherMenu.fxml";
		break;
		case TEACHER_STATS: FXMLString += "TeacherStatsScreen.fxml";
		break;
		case LOGIN: FXMLString += "Login.fxml";
		break;
		default:
			break;
		}

		return FXMLString;
	}

}
