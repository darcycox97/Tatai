package tatai;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

/**
 *	Defines the event handlers for the controls on the Tatai home screen.
 */
public class HomeScreenController {
	
	@FXML
	private Button btn1to9;
	
	@FXML
	public void btnClicked() {
		BorderPane root;
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("GameScreen.fxml"));
			btn1to9.getScene().setRoot(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
