package tatai;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 *	Defines the event handlers for the controls on the Tatai home screen.
 */
public class HomeScreenController {
	@FXML
	private Button btn1to9;
	
	@FXML
	public void btnClicked() {
		btn1to9.setText("Changed");
	}
	
	
}
