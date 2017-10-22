package tatai.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import tatai.TataiPrototype;
import javafx.util.Duration;

public class TutorialScreenController {
	
	@FXML private AnchorPane tutorialPane;
	@FXML private Pane infoPane1;
	@FXML private Pane infoPane2;
	@FXML private Pane infoPane3;
	@FXML private Pane infoPane4;
	@FXML private Pane infoPane5;
	@FXML private Pane infoPane6;
	@FXML private Pane infoPane7;
	@FXML private Label lblTutorialComplete;
	@FXML private AnchorPane nextPane;
	@FXML private ImageView nextIcon;
	@FXML private AnchorPane prevPane;
	@FXML private ImageView prevIcon;
	@FXML private AnchorPane exitPane;
	@FXML private Button btnQuit;
	
	
	
	private static final int NUM_PANES = 6;
	private static final String BEGINNING_CLASS = "tutorial-start";
	private static final String QUESTION_ATTEMPT_CLASS = "tutorial-question-attempt";
	private static final String END_CLASS = "tutorial-end";
	
	private final Map<Integer,Pane> paneMap = new HashMap<Integer,Pane>(); // allows ease of accessing the panes in correct order.
	private int currentPane;
	
	private static final double EXIT_PANE_INIT_HEIGHT = 30;
	private static final double EXIT_PANE_FINAL_HEIGHT = 70;
	
	
	
	@FXML
	public void initialize() {
		
		exitPane.setPrefHeight(EXIT_PANE_INIT_HEIGHT);
		
		currentPane = 1;
		paneMap.put(1,infoPane1);
		paneMap.put(2,infoPane2);
		paneMap.put(3,infoPane3);
		paneMap.put(4,infoPane4);
		paneMap.put(5,infoPane5);
		paneMap.put(6,infoPane6);
		paneMap.put(7,infoPane7);
		
		showTutorialPane(currentPane);
		
		btnQuit.managedProperty().bind(btnQuit.visibleProperty());
		btnQuit.setVisible(false);
		
	}
	
	@FXML
	public void showNextPane() {
		
		if (currentPane <= NUM_PANES) {
			currentPane++;
		}
		
		showTutorialPane(currentPane);
	}
	
	@FXML
	public void showPrevPane() {

		if (currentPane > 1) {
			currentPane--; // will never go below 1
		}
	
		showTutorialPane(currentPane);
	}
	
	@FXML
	public void showExitPane() {
		
		// create slide animation to show the exit pane.
		Timeline slideUp = new Timeline();
		slideUp.getKeyFrames().addAll(
			new KeyFrame(Duration.ZERO, new KeyValue(exitPane.prefHeightProperty(), EXIT_PANE_INIT_HEIGHT)),
			new KeyFrame(Duration.millis(300), new KeyValue(exitPane.prefHeightProperty(), EXIT_PANE_FINAL_HEIGHT)),
			new KeyFrame(Duration.millis(300), new KeyValue(btnQuit.visibleProperty(), true))
		);
		
		slideUp.play();
		
		exitPane.setStyle("-fx-background-color:#ffc387;");
		
		
	}
	
	@FXML
	public void hideExitPane() {
		
		btnQuit.setVisible(false);

		// create slide down animation to hide the exit pane.
		Timeline slideDown = new Timeline();
		slideDown.getKeyFrames().addAll(
			new KeyFrame(Duration.ZERO, new KeyValue(exitPane.prefHeightProperty(), EXIT_PANE_FINAL_HEIGHT)),
			new KeyFrame(Duration.millis(300), new KeyValue(exitPane.prefHeightProperty(), EXIT_PANE_INIT_HEIGHT)),
			new KeyFrame(Duration.millis(300), new KeyValue(exitPane.styleProperty(), "-fx-background-color:transparent;"))
		);

		slideDown.play();
	}
	
	@FXML
	public void openGameMenu() {
	
	try {
		FXMLLoader loader = new FXMLLoader(TataiPrototype.class.getResource("view/GameMenu.fxml"));
		Parent root = loader.load();
		btnQuit.getScene().setRoot(root);
	} catch (IOException e) {
		e.printStackTrace();
	}
}

	/**
	 * Helper method to display the given tutorial pane, and hide all others.
	 * Abstracts away the management of style classes to ensure the correct background image is set.
	 */
	private void showTutorialPane(int paneNumber) {
		
		tutorialPane.getStyleClass().removeAll(BEGINNING_CLASS, QUESTION_ATTEMPT_CLASS, END_CLASS);
		if (paneNumber < 3) {
			tutorialPane.getStyleClass().add(BEGINNING_CLASS);
			
			if (paneNumber <= 1) {
				prevPane.setVisible(false);
				prevIcon.setVisible(false);
			} else {
				prevPane.setVisible(true);
				prevIcon.setVisible(true);
			}
			nextPane.setVisible(true);
			nextIcon.setVisible(true);
			lblTutorialComplete.setVisible(false);
			
		} else if (paneNumber <= NUM_PANES) {
			tutorialPane.getStyleClass().add(QUESTION_ATTEMPT_CLASS);
			prevPane.setVisible(true);
			prevIcon.setVisible(true);
			nextPane.setVisible(true);
			nextIcon.setVisible(true);
			lblTutorialComplete.setVisible(false);
			
		} else {
			tutorialPane.getStyleClass().add(END_CLASS);
			prevPane.setVisible(true);
			prevIcon.setVisible(true);
			nextPane.setVisible(false);
			nextIcon.setVisible(false);
			lblTutorialComplete.setVisible(true);
		}
		
		for (int i = 1; i <= NUM_PANES; i++) {
			if (i == paneNumber) {
				paneMap.get(i).setVisible(true);
			} else {
				paneMap.get(i).setVisible(false);
			}
		}
	}

	
	
}
