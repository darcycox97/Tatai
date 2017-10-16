package tatai.game;

import java.util.ArrayList;
import java.util.List;

import tatai.question.Equation;
import tatai.question.Equation.Operation;
import tatai.question.Question;
import tatai.statistics.CSVFile;
import tatai.statistics.CSVFile.CSVName;

public class CustomEquationGame extends FiniteGame {

	private String quizName;
	
	public CustomEquationGame(String quizName) {
		super(GameDifficulty.EASY);
		this.quizName = quizName;
		questions = initializeQuestions(quizName);
	}

	@Override
	public GameMode getGameMode() {
		return GameMode.CUSTOM;
	}

	/**
	 * Overloaded method to initialize questions to a custom game.
	 * Scans the provided quiz and constructs the equations.
	 */
	private List<Question> initializeQuestions(String quizName) {
		
		List<Question> questions = new ArrayList<Question>();
		
		String csvQuiz = CSVFile.getLineInCSV(CSVName.QUIZZES, quizName);
		String[] equations = csvQuiz.split(",");
		
		// go through each question, first entry represents the title and so it is skipped
		for (int i = 1; i < equations.length; i++) {
			String[] equation = parseEquation(equations[i]);
			int operand1 = Integer.parseInt(equation[0]);
			int operand2 = Integer.parseInt(equation[2]);
			String opString = equation[1];
			Operation operation;
			
			// determine the operation for the equation
			if (opString.equals("x")) {
				operation = Operation.TIMES;
			} else if (opString.equals("+")) {
				operation = Operation.PLUS;
			} else {
				operation = Operation.MINUS;
			}
			
			// construct the equation
			questions.add(new Equation(operand1, operation, operand2));
		}
		
		
		
		return questions;
	}
	
	/**
	 * Will return a string array where entry 0 is operand1,
	 * entry 1 is the operation, and entry 2 is operand2.
	 */
	private String[] parseEquation(String equation) {
		
		String op1, op, op2;
		int i = 0;
		while (i < equation.length() && Character.isDigit(equation.charAt(i))) {
			i++;
		}
		
		// i now points to character containing operation.
		op1 = equation.substring(0, i); // extract operand 1
		op = equation.substring(i, i+1); // extract equation
		op2 = equation.substring(i + 1, equation.length());
		
		return new String[] {op1, op, op2};
		
	}
	
	@Override
	public String getQuizName() {
		return quizName;
	}

}
