package tatai.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import tatai.TataiPrototype;
import tatai.statistics.CSVFile;
import tatai.statistics.CSVFile.CSVName;
import tatai.view.Screen;

public class QuizCreatorController extends ScreenController {

	private static final int NUM_EQUATIONS = 10;
	
	// map to be used to check validity of each equation field
	private Map<Integer, Boolean> validityMap;

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
	
	@FXML private Label lbl1;
	@FXML private Label lbl2;
	@FXML private Label lbl3;
	@FXML private Label lbl4;
	@FXML private Label lbl5;
	@FXML private Label lbl6;
	@FXML private Label lbl7;
	@FXML private Label lbl8;
	@FXML private Label lbl9;
	@FXML private Label lbl10;
	Map<Integer,Label> lblMap = new HashMap<Integer,Label>();
	
	@FXML private Button btnGoBack;
	@FXML private Button btnSaveQuiz;
	@FXML private TextField txtQuizName;
	
	@FXML
	public void initialize() {
		
		setup();
		
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
		
		// initialize hash map of labels
		lblMap.put(1, lbl1);
		lblMap.put(2, lbl2);
		lblMap.put(3, lbl3);
		lblMap.put(4, lbl4);
		lblMap.put(5, lbl5);
		lblMap.put(6, lbl6);
		lblMap.put(7, lbl7);
		lblMap.put(8, lbl8);
		lblMap.put(9, lbl9);
		lblMap.put(10, lbl10);
		
		validityMap = new HashMap<Integer,Boolean>();
		
		for (int i = 1; i <= NUM_EQUATIONS; i++) {
			// initialize all equations to be invalid
			validityMap.put(i, false);
			
			// listen to each text field to check for valid input
			TextField addTo = eqnMap.get(i);
			addTo.textProperty().addListener(new EquationValidifier(i));
		}
	}
	
	@FXML
	public void saveQuiz() {
		
		boolean validFields = true;
		for (int i = 1; i <= NUM_EQUATIONS; i++) {
			if (!validityMap.get(i)) {
				// not valid if one or more equations aren't valid
				validFields = false;
			}
		}
		
		String name = txtQuizName.getText();
		if (!Pattern.matches("[\\w\\-\\s]+", name)) {
			// only allow alphanumeric, hyphens, and underscores
			validFields = false;
		}
		
		if (validFields) {
			// convert quiz into its csv form.
			
			if (CSVFile.titleExists(CSVName.QUIZZES, txtQuizName.getText())) {
				Alert alert = new Alert(
					AlertType.INFORMATION,
					"That quiz name already exists",
					ButtonType.OK
				);
				alert.showAndWait();
				return;
			}
			
			StringBuilder sb = new StringBuilder();
			String text;
			if (txtQuizName.getText().replace(" ","").length() == 0) {
				text = "Unnamed Quiz";
			} else {
				text = txtQuizName.getText();
			}
			sb.append(text); // first entry should be the quiz name
			
			for (int i = 1; i <= NUM_EQUATIONS; i++) {
				sb.append("," + eqnMap.get(i).getText().replaceAll(" ", ""));
			}
			
			CSVFile.appendToCSV(CSVName.QUIZZES, sb.toString());
			
			Alert success = new Alert(
				AlertType.INFORMATION,
				"Quiz saved!"
			);
			success.showAndWait();
			
			// clear existing text fields and graphics
			txtQuizName.setText("");
			for (int i = 1; i <= NUM_EQUATIONS; i++) {
				eqnMap.get(i).setText("");
				lblMap.get(i).setGraphic(null);
			}
			
		} else {
			// prompt user to provide valid input
			Alert info = new Alert(
				AlertType.INFORMATION,
				"You must provide ten valid equations \nand a valid quiz name to save this quiz."
			);
			info.showAndWait();
		}
		
	}
	
	
	/**
	 * Listener to take care of listening for validity of text fields
	 */
	private class EquationValidifier implements ChangeListener<String> {
		
		private int observed; // the text field to check input of

		public EquationValidifier(int eqnNumber) {
			observed = eqnNumber;
		}
		
		@Override
		public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
			
			boolean validEquation = false;
			
			// remove spaces from the equation so it is of the form a+b
			String equation = newValue.replace(" ", "");
			
			// check that equation consists of two numbers either side of either "+", "-", or "x".
			
			int i = 0;
			while (i < equation.length() && Character.isDigit(equation.charAt(i))) {
				i++;
			}
			
			if (i != equation.length() && i != 0) {
				
				// i points to the index of the first non digit character. 
				int operand1 = Integer.parseInt(equation.substring(0, i));

				// check that first non digit character is a valid operation
				char operation = equation.charAt(i);
				if (operation == 'x' || operation == '+' || operation == '-') {
	
					// all good so far, check last part of equation is also a digit
					i++;
					
					int operand2Index = i;
					
					while (i < equation.length() && Character.isDigit(equation.charAt(i))) {
						i++;
					}
					
					if (i == equation.length() && operand2Index != equation.length()) {
						// remaining characters all digits, check answer is from 1 to 99.
						int operand2 = Integer.parseInt(equation.substring(operand2Index, equation.length()));
						
						int answer;
						if (operation == '+') {
							answer = operand1 + operand2;
						} else if (operation == 'x') {
							answer = operand1 * operand2;
						} else {
							answer = operand1 - operand2;
						}
						
						if (answer >= 1 && answer <= 99) {
							validEquation = true;
						}
					}
				}
			}
			
			if (validEquation) {
				// display tick next to text field
				lblMap.get(observed).setGraphic(new ImageView(new Image(TataiPrototype.class.getResourceAsStream("view/icons/tick.png"),25,25,true,true)));
				validityMap.put(observed, true); // update validity of this text field
			} else {
				// display cross next to text field
				lblMap.get(observed).setGraphic(new ImageView(new Image(TataiPrototype.class.getResourceAsStream("view/icons/cross.png"),25,25,true,true)));
				validityMap.put(observed, false); // update validity of this text field
			}
		}
	}
	
	@Override
	protected Screen getScreen() {
		return Screen.CREATE_QUIZ;
	}
	
}
