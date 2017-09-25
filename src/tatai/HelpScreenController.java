package tatai;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

/**
 *	Defines the event handlers for the controls on the Tatai help screen.
 */
public class HelpScreenController {

	@FXML
	private Label leaderboardTitle;
	@FXML
	private Button btnExit;

	@FXML
	public void returnHomeScreen(ActionEvent e) {
		BorderPane root;
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
			((Node) e.getSource()).getScene().setRoot(root);
		} catch (IOException e1) {
			System.out.println("Error exiting leaderboard");
			//e1.printStackTrace();
		}
	}

}
