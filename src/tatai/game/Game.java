package tatai.game;

import java.util.List;

import tatai.htk.HTK;
import tatai.htk.HTKListener;
import tatai.question.Question;

/**
 * General representation of a game. Subclasses will define the type of questions e.g Numbers or Equations.
 */
public abstract class Game {
	
	public static final int EASY_RANGE = 9;
	public static final int HARD_RANGE = 99;
	public static final int DEFAULT_NUM_QUESTIONS = 10;
	public static final int DEFAULT_RANGE = 9;
	
	protected final int NUM_QUESTIONS;
	protected final int RANGE;
	
	private int currentQuestionIndex;
	private int attempts;
	private List<Question> questions;
	private Question currentQuestion;
	private boolean currentResult; // holds the result of the most recently played question
	private int score;
	private HTK htk;
	private String playerName;

	
	/**
	 * Constructor that sets number of questions and the range to default.
	 */
	public Game() {
		this(DEFAULT_NUM_QUESTIONS, DEFAULT_RANGE);
	}

	/**
	 * Constructor to specify number of questions in the game
	 */
	public Game(int numOfQuestions, int range) {
		this.NUM_QUESTIONS = numOfQuestions;
		this.RANGE = range;
		this.questions = initializeQuestions();
		this.currentQuestionIndex = 0;
		this.currentQuestion = this.questions.get(0);
		this.score = 0;
		this.attempts = 0;
		this.htk = new HTK();
	}
	
	/**
	 * This method should create a list that defines the questions to be displayed during the game.
	 * @return a list of questions to be iterated through during gameplay.
	 */
	protected abstract List<Question> initializeQuestions();
	
	public boolean hasNextQuestion() {
		return currentQuestionIndex < NUM_QUESTIONS;
	}
	
	/**
	 * Returns the next question in the list of questions. Stores the current question.
	 */
	public Question nextQuestion() {
		attempts = 0; //reset number of attempts because we are moving onto a new question
		currentQuestion = questions.get(currentQuestionIndex);
		currentQuestionIndex++;
		return currentQuestion;
	}
	
	public Question getCurrentQuestion() {
		return currentQuestion;
	}
	
	/**
	 * Returns the range of the current game
	 */
	public int getRange() {
		return RANGE;
	}
	
	/**
	 * Returns the number of the question that we are currently attempting
	 */
	public int getQuestionNumber() {
		return currentQuestionIndex;
	}
	
	/**
	 * Returns a string representation of the score that can be used to display in the GUI. e.g "7/10"
	 */
	public String getScore() {
		return score + "/" + NUM_QUESTIONS;
	}
	
	/**
	 * Returns the integer value of the score
	 */
	public int getScoreValue() {
		return score;
	}
	
	/**

	 * Simply passes the job onto htk, which will take care of the recording in a background thread.
	 * @param l The HTKListener that is to be notified when recording is finished
	 */
	public void attemptQuestion(HTKListener l) {
		htk.recordQuestion(currentQuestion, l);
		attempts++;
		
	}
	
	/**
	 * Called by external classes that handle the recording of a question, once they have calculated whether
	 * or not the user said the answer correctly
	 */
	public void updateScore(boolean correct) {
		
		currentResult = correct;
		if (correct) {
			score++;
		}
	}
	
	public int numAttempts() {
		return attempts;
	}
	
	/**
	 * Returns a boolean value stating whether or not the most recent attempt was correct.
	 */
	public boolean getCurrentResult() {
		return currentResult;
	}
	

   /**
	 * Sets the value of the game's playerName field. Used for recording score on leader board.
	 */
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	/**
	 * Gets the value of the game's playerName field. Used for recording score on leader board.
	 */
	public String getPlayerName() {
		return this.playerName;

	}
}
