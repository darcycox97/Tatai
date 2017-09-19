package tatai.game;

import java.util.ArrayList;
import java.util.List;

import tatai.question.MaoriNumber;
import tatai.question.Question;

public class NumberGame extends Game {
	
	public NumberGame() {
		super();
	}
	
	public NumberGame(int numQuestions, int range) {
		super(numQuestions,range);
	}
	
	protected List<Question> initializeQuestions() {
		ArrayList<Question> questions = new ArrayList<Question>(NUM_QUESTIONS);
		for (int i = 0; i < NUM_QUESTIONS; i++) {
			questions.add(new MaoriNumber(generateNumberInRange()));
		}
		return questions;
	}

	
	private int generateNumberInRange() {
		int random = (int)(Math.random() * RANGE + 1);
		return random;
	}
}
