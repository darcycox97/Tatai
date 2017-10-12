package tatai.game;

/**
 * General representation of a game with a finite number of questions. 
 * Subclasses will define the type of questions e.g Numbers or Equations.
 */
public abstract class FiniteGame extends Game {
	
	protected static final int NUM_QUESTIONS = 10;

	public FiniteGame(GameDifficulty difficulty) {
		super(difficulty);
	}

	@Override
	public boolean hasNextQuestion() {
		return questionIndex < NUM_QUESTIONS;
	}

	@Override
	public void updateScore(boolean correct) {
		
		// update current result
		currentResult = correct;
		
		// give a full mark if a single attempt was made, otherwise half a mark
		if (correct) {
			if (attempts == 1) {
				score = score + 1;
			} else if (attempts == 2) {
				score = score + 0.5;
			}
		}
	}

	@Override
	public String getScore() {
		return score + "/" + NUM_QUESTIONS;
	}

}