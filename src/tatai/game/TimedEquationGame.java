package tatai.game;

import java.util.ArrayList;
import java.util.List;

import tatai.question.Equation;
import tatai.question.Question;

public class TimedEquationGame extends FiniteGame {
	
	public TimedEquationGame(GameDifficulty difficulty) {
		super(difficulty);
	}

	@Override
	public GameMode getGameMode() {
		return GameMode.TEN_QUESTIONS_TIMED;
	}

	@Override
	protected List<Question> initializeQuestions(int range) {
		
		List<Question> questions = new ArrayList<Question>();
		
		for (int i = 0; i < NUM_QUESTIONS; i++) {
			questions.add(new Equation(difficulty));
		}
		
		return questions;
	}

}
