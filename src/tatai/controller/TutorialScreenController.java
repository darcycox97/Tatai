package tatai.controller;

import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

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
	
	
	private static final int NUM_PANES = 7;
	
	private final Map<Integer,Pane> paneMap = new HashMap<Integer,Pane>(); // allows ease of accessing the panes in correct order.
	private int currentPane;
	
	
	@FXML
	public void initialize() {
		
		currentPane = 1;
		paneMap.put(1,infoPane1);
		paneMap.put(2,infoPane2);
		paneMap.put(3,infoPane3);
		paneMap.put(4,infoPane4);
		paneMap.put(5,infoPane5);
		paneMap.put(6,infoPane6);
		paneMap.put(7,infoPane7);
		
		showTutorialPane(currentPane);
		
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
	
	/**
	 * Helper method to display the given tutorial pane, and hide all others.
	 */
	private void showTutorialPane(int paneNumber) {
		
		tutorialPane.getStyleClass().clear();
		if (paneNumber < 3) {
			tutorialPane.getStyleClass().add("tutorial-start");
			
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
			tutorialPane.getStyleClass().add("tutorial-question-attempt");
			prevPane.setVisible(true);
			prevIcon.setVisible(true);
			nextPane.setVisible(true);
			nextIcon.setVisible(true);
			lblTutorialComplete.setVisible(false);
			
		} else {
			tutorialPane.getStyleClass().add("tutorial-end");
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
