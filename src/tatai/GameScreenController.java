package tatai;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

public class GameScreenController {

	@FXML
	private Button returnHome;
	
	@FXML
	public void homeClicked() {
		BorderPane root;
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
			returnHome.getScene().setRoot(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
