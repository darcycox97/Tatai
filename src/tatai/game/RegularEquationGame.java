package tatai.game;

import java.util.ArrayList;
import java.util.List;

import tatai.question.Equation;
import tatai.question.Question;

public class RegularEquationGame extends FiniteGame {

	public RegularEquationGame(GameDifficulty difficulty) {
		super(difficulty);
	}

	@Override
	protected List<Question> initializeQuestions(int range) {

		List<Question> questions = new ArrayList<Question>();
		
		for (int i = 0; i < NUM_QUESTIONS; i++) {
			// generate list of equations corresponding to the games difficulty level
			questions.add(new Equation(difficulty));
		}

		return questions;
	}

	@Override
	public GameMode getGameMode() {
		return GameMode.CLASSIC;
	}

}
