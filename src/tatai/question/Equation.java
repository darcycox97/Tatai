package tatai.question;

import tatai.game.Game;
import tatai.game.GameDifficulty;

/**
 * Representation of an equation. Has two operands, an operation, and an answer.
 */
public class Equation implements Question {
	
	enum Operation {
		PLUS,
		MINUS,
		TIMES
	}
	
	private static final Operation[] easyOperationsArray = {Operation.PLUS, Operation.MINUS};
	private static final Operation[] hardOperationsArray = {Operation.PLUS, Operation.MINUS, Operation.TIMES};
	
	private static final int MULTIPLICATION_LIMIT = 10;
	
	private Operation operation;
	private int operand1;
	private int operand2;
	private int answer;
	
	/**
	 * Generates a random equation of the specified difficulty.
	 * Answers will always be in the range 1 to 99.
	 */
	public Equation(GameDifficulty difficulty) {

		if (difficulty.equals(GameDifficulty.EASY)) {

			/* an "easy" equation consists of numbers from 1 to 9 only
			 * and consist of addition and subtraction operations */
			
			this.answer = (int)(Math.random() * Game.EASY_RANGE) + 1;
			
			do {
				
				int operationIndex = (int)(Math.random() * easyOperationsArray.length);
				operation = easyOperationsArray[operationIndex];

				// generate operand on left side of equation. must be between 1 and the range for easy games
				operand1 = (int)(Math.random() * Game.EASY_RANGE) + 1;

				// work backwards to find operand2
				switch(operation) {
				case PLUS:
					operand2 = answer - operand1;
					break;
				case MINUS:
					operand2 = operand1 - answer;
					break;
				case TIMES:
					// not applicable for easy mode
					break;
				}
			} while (((operand2 <= 0) || (operand2 > Game.EASY_RANGE))); // dont need to check for operand1 due to how it was generated.

		} else {
			
			/* generate hard equation. A hard equation consists of numbers from 1 to 99
			 * with addition, subtraction, or multiplication operations.
			 * Multiplication is simplified to operands from 1 to 10. */
			this.answer = (int)(Math.random() * Game.HARD_RANGE) + 1;
			
			do {
				
				int operationIndex = (int)(Math.random() * hardOperationsArray.length);
				operation = hardOperationsArray[operationIndex];
				
				operand1 = (int)(Math.random() * Game.HARD_RANGE) + 1;

				// work backwards to find operand2
				switch(operation) {
				case PLUS:
					operand2 = answer - operand1;
					break;
					
				case MINUS:
					operand2 = operand1 - answer;
					break;
					
				case TIMES:
					/* generate multiplication differently. 
					 * Each operand can be from 1 to 10, as long as the 
					 * answer does not exceed */
					
					do {
						operand1 = (int)(Math.random() * MULTIPLICATION_LIMIT) + 1;
						operand2 = (int)(Math.random() * MULTIPLICATION_LIMIT) + 1;
						this.answer = operand1 * operand2;
					} while (answer > Game.HARD_RANGE);
					
					break;
				}
				
			} while (((operand2 <= 0) || (operand2 > Game.HARD_RANGE))); // dont need to check for operand1 due to how it was generated.

			
		}

		System.out.println(getDisplayText() + " = " + getValue());
		
	}
	
	/**
	 * Constructor to build a specified equation.
	 * The answer is the result of applying the operation between operand1 and operand2.
	 * operand1 is on the left of the equation.
	 */
	public Equation(int operand1, int operand2, Operation operation) {
		this.operand1 = operand1;
		this.operand2 = operand2;
		this.operation = operation;
		
		switch (operation) {
		case PLUS:
			answer = operand1 + operand2;
			break;
		case MINUS:
			answer = operand1 - operand2;
			break;
		case TIMES:
			answer = operand1 * operand2;
			break;
		}
	}
	
	@Override
	public String getDisplayText() {
		String operationString = null;
		switch (operation) {
		case PLUS:
			operationString = "+";
			break;
		case MINUS:
			operationString = "-";
			break;
		case TIMES:
			operationString = "x";
			break;
		}
		
		return operand1 + " " + operationString + " " + operand2;
	}

	@Override
	public int getValue() {
		return answer;
	}

	@Override
	public String[] getHTKWords() {
		String asWord = MaoriTranslator.translate(answer);
		return asWord.split("\\W+");
	}

}
