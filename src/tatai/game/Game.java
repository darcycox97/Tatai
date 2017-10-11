package tatai.game;

import java.util.List;

import tatai.htk.HTK;
import tatai.htk.HTKListener;
import tatai.question.Question;

/**
 * General representation of a game. Encapsulates information common to all games.
 * The game controllers are designed to work with this class and all games inherit from this class.
 */
public abstract class  Game {
	
	// constants specifying the range of numbers for each difficulty
	public static final int EASY_RANGE = 9;
	public static final int HARD_RANGE = 99;
	
	private Question currentQuestion;
	private HTK htk;
	
	protected List<Question> questions;
	protected boolean currentResult;
	protected GameDifficulty difficulty;
	protected double score;
	protected int attempts;
	protected int questionIndex;
	protected final int RANGE;
	
	/**
	 * Game constructor.
	 * @param RANGE the range of numbers 
	 */
	public Game(GameDifficulty difficulty) {
		this.difficulty = difficulty;
		if (difficulty.equals(GameDifficulty.EASY)) {
			RANGE = EASY_RANGE;
		} else {
			RANGE = HARD_RANGE;
		}
		questions = initializeQuestions(RANGE);
		questionIndex = 0;
		attempts = 0;
		currentQuestion = null; // becomes not null when nextQuestion() is called.
		htk = new HTK();
	}
	
	
	/**
	 * Used to specify the game mode, so classes using Game know what type of game this is.
	 */
	public abstract GameMode getGameMode();
	
	/**
	 * Sets up the list of questions for the game, with all answers from 1 
	 * up to the specified range.
	 */
	protected abstract List<Question> initializeQuestions(int range);
	
	/**
	 * Returns whether or not the game has any questions remaining 
	 */
	public abstract boolean hasNextQuestion();
	
	/**
	 * Forwards the game state to the next question to be attempted, and returns
	 * this question.
	 */
	public Question nextQuestion() {
		attempts = 0;
		currentQuestion = questions.get(questionIndex);
		
		questionIndex++;
		return currentQuestion;
	}
	/**
	 * Passes the job on to HTK, which will check the correctness and update the score 
	 * when it is complete.
	 * @param l The HTKListener to be notified when recording is complete
	 */
	public void attemptQuestion(HTKListener l) {
		attempts++;
		htk.recordQuestion(currentQuestion, l);
	}
	
	/**
	 * To be called by external classes to notify the game object whether a question attempt
	 * was correct(true) or incorrect(false)
	 */
	public abstract void updateScore(boolean correct);
	
	public int getQuestionNumber() {
		return questionIndex;
	}
	
	/**
	 * Return the raw value of the score.
	 */
	public double getScoreValue() {
		return score;
	}
	
	/**
	 * Returns formatted string representation of the score
	 */
	public abstract String getScore();
	
	/**
	 * Returns whether the latest attempt was correct or not.
	 */
	public boolean getLatestResult() {
		return currentResult;
	}
	
	/**
	 * Returns the number of attempts on the current question
	 */
	public int getNumAttempts() {
		return attempts;
	}
	
	/**
	 * Returns either Game.EASY_RANGE or Game.HARD_RANGE
	 */
	public int getRange() {
		return RANGE;
	}
	
	/**
	 * Returns the difficulty of the game
	 */
	public GameDifficulty getDifficulty() {
		return difficulty;
	}
	
}
