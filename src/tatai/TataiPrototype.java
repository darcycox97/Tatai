package tatai;
	
import java.io.File;

import javafx.application.Application;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.fxml.FXMLLoader;

/**
 * Launches the GUI when run.
 */
public class TataiPrototype extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			BorderPane root = (BorderPane)FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
			Scene scene = new Scene(root,700,600);
			scene.getStylesheets().add(getClass().getResource("TataiStyle.css").toExternalForm());
			
			
	//		Font.loadFont(getClass().getResource("pathtofont").toExternalForm(), 10);
	//		Font.loadFont("../../resources/fonts/waltograph42.otf", 10);
		//  ^^^^^^ use this syntax to load any custom fonts. Place these fonts into the resources folder
			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Tātai!");
			primaryStage.setResizable(false);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void stop() {
		File easyCurrent = new File(".leaderboardEasyCurrent");
		easyCurrent.delete();
		File hardCurrent = new File(".leaderboardHardCurrent");
		hardCurrent.delete();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
