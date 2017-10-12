package tatai.game;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import tatai.question.Equation;
import tatai.question.Question;

public class TimedEquationGame extends FiniteGame {
	
	public TimedEquationGame(GameDifficulty difficulty) {
		super(difficulty);
	}

	@Override
	public GameMode getGameMode() {
		return GameMode.TIME_ATTACK;
	}

	@Override
	protected List<Question> initializeQuestions(int range) {
		
		List<Question> questions = new ArrayList<Question>();
		
		for (int i = 0; i < NUM_QUESTIONS; i++) {
			questions.add(new Equation(difficulty));
		}
		
		return questions;
	}
	
	@Override
	public String getScore() {
		// format time as 2 d.p
		Formatter f = new Formatter();
		f.format("%1$.2f", score);
		return f.toString() + " seconds";
	}

}
