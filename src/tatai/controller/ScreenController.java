package tatai.controller;

import java.io.IOException;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import tatai.TataiPrototype;
import tatai.game.GameDifficulty;
import tatai.game.GameFactory;
import tatai.game.GameMode;
import tatai.statistics.User;

public class ScreenController {

	@FXML protected Button btnBack;
	@FXML protected Button btnHome;
	@FXML protected Button btnGames;
	@FXML protected Button btnPractice;
	@FXML protected Button btnStats;
	@FXML protected Button btnHelp;
	@FXML protected Button btnLogout;
	@FXML protected ImageView menuIcon;
	
	@FXML protected Pane navigationPane;
	
	protected static final double NAV_PANE_INIT_WIDTH = 0;
	protected static final double NAV_PANE_FINAL_WIDTH = 140;
	
	@FXML
	public void setup() {
		
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
		btnStats.setVisible(false);
		btnHelp.setVisible(false);
		btnLogout.setVisible(false);
		
	}
	
	@FXML
	public void showNavigationBar() {
		
		// create slide animation to show the navigation bar.
		Timeline slideIn = new Timeline();
		slideIn.getKeyFrames().addAll(
			new KeyFrame(Duration.ZERO, new KeyValue(navigationPane.prefWidthProperty(), NAV_PANE_INIT_WIDTH)),
			new KeyFrame(Duration.millis(300), new KeyValue(navigationPane.prefWidthProperty(), NAV_PANE_FINAL_WIDTH)),
			new KeyFrame(Duration.millis(300), new KeyValue(btnBack.visibleProperty(), true)),
			new KeyFrame(Duration.millis(300), new KeyValue(btnHome.visibleProperty(), true)),
			new KeyFrame(Duration.millis(300), new KeyValue(btnGames.visibleProperty(), true)),
			new KeyFrame(Duration.millis(300), new KeyValue(btnPractice.visibleProperty(), true)),
			new KeyFrame(Duration.millis(300), new KeyValue(btnStats.visibleProperty(), true)),
			new KeyFrame(Duration.millis(300), new KeyValue(btnHelp.visibleProperty(), true)),
			new KeyFrame(Duration.millis(300), new KeyValue(btnLogout.visibleProperty(), true))
		);
		
		navigationPane.setStyle("-fx-background-color:#ffc387;");
		slideIn.play();
		
	}
	
	@FXML
	public void hideNavigationBar() {

		// create slide down animation to hide the exit pane.
		Timeline slideOut = new Timeline();
		slideOut.getKeyFrames().addAll(
			new KeyFrame(Duration.millis(300), new KeyValue(btnBack.visibleProperty(), false)),
			new KeyFrame(Duration.millis(300), new KeyValue(btnHome.visibleProperty(), false)),
			new KeyFrame(Duration.millis(300), new KeyValue(btnGames.visibleProperty(), false)),
			new KeyFrame(Duration.millis(300), new KeyValue(btnPractice.visibleProperty(), false)),
			new KeyFrame(Duration.millis(300), new KeyValue(btnStats.visibleProperty(), false)),
			new KeyFrame(Duration.millis(300), new KeyValue(btnHelp.visibleProperty(), false)),
			new KeyFrame(Duration.millis(300), new KeyValue(btnLogout.visibleProperty(), false)),
			new KeyFrame(Duration.ZERO, new KeyValue(navigationPane.prefWidthProperty(), NAV_PANE_FINAL_WIDTH)),
			new KeyFrame(Duration.millis(300), new KeyValue(navigationPane.prefWidthProperty(), NAV_PANE_INIT_WIDTH)),
			new KeyFrame(Duration.millis(300), new KeyValue(navigationPane.styleProperty(), "-fx-background-color:transparent;"))
		);

		slideOut.play();

	}
	
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
	public void loadPreviousScreen() {
		// To be implemented in each child class
	}
	
	@FXML
	public void loadGameScreen() {
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
	public void loadHelpScreen() {
		try {
			FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/TutorialScreen.fxml"));
			Parent root = loader.load();
			btnHome.getScene().setRoot(root);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
