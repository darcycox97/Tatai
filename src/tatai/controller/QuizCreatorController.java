package tatai.controller;

import java.awt.TextField;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class QuizCreatorController {

	@FXML private TextField eqn1;
	@FXML private TextField eqn2;
	@FXML private TextField eqn3;
	@FXML private TextField eqn4;
	@FXML private TextField eqn5;
	@FXML private TextField eqn6;
	@FXML private TextField eqn7;
	@FXML private TextField eqn8;
	@FXML private TextField eqn9;
	@FXML private TextField eqn10;
	Map<Integer,TextField> eqnMap = new HashMap<Integer,TextField>();
	
	@FXML private Button btnGoBack;
	@FXML private Button btnSaveQuiz;
	
	@FXML
	public void initialize() {
		
		// initialize hashmap of equations (the key is the equation number)
		eqnMap.put(1, eqn1);
		eqnMap.put(2, eqn2);
		eqnMap.put(3, eqn3);
		eqnMap.put(4, eqn4);
		eqnMap.put(5, eqn5);
		eqnMap.put(6, eqn6);
		eqnMap.put(7, eqn7);
		eqnMap.put(8, eqn8);
		eqnMap.put(9, eqn9);
		eqnMap.put(10, eqn10);
		
		for (int i = 1; i <= 10; i++) {
			// eqnMap.get(i).addListener // listen to each text field to check for valid input
		}
		
		
	}
	
}
