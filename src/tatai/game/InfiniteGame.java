package tatai.game;

import java.util.List;

import tatai.question.Question;

public abstract class InfiniteGame extends Game {

	protected static final int NUM_ELS_IN_LIST = 100;
	private int numTimesListRegenerated;
	
	public InfiniteGame(GameDifficulty difficulty) {
		super(difficulty);
		numTimesListRegenerated = 0;
	}
	
	@Override
	public abstract GameMode getGameMode();

	@Override
	protected abstract List<Question> initializeQuestions(int range);
	
	@Override
	public boolean hasNextQuestion() {
		if (questionIndex >= NUM_ELS_IN_LIST) {
			questions = initializeQuestions(RANGE);
			questionIndex = 0;
			numTimesListRegenerated++;
		}
		
		return true;
	}

	@Override
	public void updateScore(boolean correct) {
		if (correct) {
			score++;
		}
	}

	@Override
	public String getScore() {
		 double numCorrect = getScoreValue();
		 int numAttempted = NUM_ELS_IN_LIST * numTimesListRegenerated + questionIndex;
		 double percentCorrect = numCorrect / numAttempted;
		 
		 return percentCorrect + "% correct";
	}
	
	// override this method so question number doesn't roll back to 1.
	@Override
	public int getQuestionNumber() {
		return NUM_ELS_IN_LIST * numTimesListRegenerated + questionIndex;
	}

}
