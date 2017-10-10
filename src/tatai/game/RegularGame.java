package tatai.game;

import java.util.List;

import tatai.question.Question;

public class RegularGame extends FiniteGame {

	public RegularGame(int range) {
		super(range);
	}

	@Override
	protected List<Question> initializeQuestions(int range) {


		if (range == Game.EASY_RANGE) {
			// only include +, -
			for (int i = 0; i < NUM_QUESTIONS; i++) {
				//TODO: generate 10 equations in the given range.
			}
		} else {
			//include +,-,/,*
		}


		return null;
	}

}
