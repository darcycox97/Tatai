package tatai.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;

public class GameMenuController {
	
	@FXML private ToggleGroup toggleDifficulty;
	@FXML private RadioButton toggleEasy;
	@FXML private RadioButton toggleHard;
	@FXML private Tooltip tooltipTest;
	@FXML private Label lblHelp;

	public void initialize() {
		//lblHelp.setTooltip(new Tooltip("Testing"));
	}
}
