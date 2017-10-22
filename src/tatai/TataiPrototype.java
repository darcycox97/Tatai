package tatai;

import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;

/**
 * Launches the GUI when run.
 */
public class TataiPrototype extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("view/Login.fxml"));
			Scene scene = new Scene(root,700,300);
			primaryStage.setScene(scene);
			primaryStage.setTitle("Tātai!");
			primaryStage.setResizable(false);
			primaryStage.show();
			
			Stage tutorial = new Stage();
			Parent tut= FXMLLoader.load(getClass().getResource("view/TutorialScreen.fxml"));
			Scene tutScene = new Scene(tut,700,600);
			tutorial.setScene(tutScene);
			tutorial.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		launch(args);
	}
}
