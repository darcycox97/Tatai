package tatai.game;

import java.util.ArrayList;
import java.util.List;

import tatai.question.MaoriNumber;
import tatai.question.Question;

public class NumberGame extends FiniteGame {
	
	public NumberGame(GameDifficulty difficulty) {
		super(difficulty);
	}
	
	protected List<Question> initializeQuestions(int range) {
		ArrayList<Question> questions = new ArrayList<Question>(NUM_QUESTIONS);
		for (int i = 0; i < NUM_QUESTIONS; i++) {
			questions.add(new MaoriNumber(generateNumber(range)));
		}
		return questions;
	}
	
	private int generateNumber(int range) {
		int random = (int)(Math.random() * range + 1);
		return random;
	}

	@Override
	public GameMode getGameMode() {
		return GameMode.PRACTICE;
	}
}
