package tatai.game;

import java.util.ArrayList;
import java.util.List;

import tatai.question.Equation;
import tatai.question.Question;

public class OneMinuteEquationGame extends InfiniteGame {

	public OneMinuteEquationGame(GameDifficulty difficulty) {
		super(difficulty);
	}
	
	@Override
	public GameMode getGameMode() {
		return GameMode.ONE_MINUTE_BLITZ;
	}

	@Override
	protected List<Question> initializeQuestions(int range) {
		List<Question> questions = new ArrayList<Question>();
		
		for (int i = 0; i < NUM_ELS_IN_LIST; i++) {
			questions.add(new Equation(difficulty));
		}
		
		return questions;
	}
	
	@Override
	public String getScore() {
		return (int) score + "";
	}

}
