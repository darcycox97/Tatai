package tatai.game;

import java.util.ArrayList;
import java.util.List;

import tatai.question.MaoriNumber;
import tatai.question.Question;

public class NumberGame extends Game {
	
	private static final int DEFAULT_RANGE = 10;
	
	private final int RANGE;

	/**
	 * Constructor allows us to specify the max number to generate
	 * @param numQuestions The number of questions in the game
	 * @param range The maximum number that can be displayed (the lowest number is 1)
	 */
	public NumberGame(int numQuestions, int range) {
		super(numQuestions);
		this.RANGE = range;
	}
	
	public NumberGame(int range) {
		super();
		this.RANGE = range;
	}
	
	public NumberGame() {
		super();
		this.RANGE = DEFAULT_RANGE;
	}
	
	protected List<Question> initializeQuestions() {
		ArrayList<Question> questions = new ArrayList<Question>(NUM_QUESTIONS);
		for (int i = 0; i < NUM_QUESTIONS; i++) {
			questions.add(new MaoriNumber(generateNumberInRange()));
		}
		return questions;
	}

	
	private int generateNumberInRange() {
		return (int)(Math.random()*RANGE + 1);
	}
}
