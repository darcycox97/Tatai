package tatai.game;

import java.util.List;

import tatai.htk.HTK;
import tatai.htk.HTKListener;
import tatai.question.Question;

/**
 * General representation of a game. Subclasses will define the type of questions e.g Numbers or Equations.
 */
public abstract class Game {
	
	private static final int DEFAULT_NUM_QUESTIONS = 10;
	private static final int DEFAULT_RANGE = 10;
	
	protected final int NUM_QUESTIONS;
	
	protected final int RANGE;
	
	private int currentQuestionIndex;
	
	private List<Question> questions;
	
	private Question currentQuestion;
	
	private int score;
	
	private HTK htk;
	
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
		currentQuestion = questions.get(currentQuestionIndex);
		currentQuestionIndex++;
		return currentQuestion;
	}
	
	public Question getCurrentQuestion() {
		return currentQuestion;
	}
	
	/**
	 * Returns a string representation of the score that can be used to display in the GUI. e.g "7/10"
	 */
	public String getScore() {
		return score + "/" + currentQuestionIndex;
	}
	
	/**
	 * Simply passes the job onto htk, which will take care of the recording in a background thread.
	 */
	public void attemptQuestion(HTKListener o) {
		htk.recordQuestion(currentQuestion, o);
		
	}
	
	/**
	 * Called by external classes that handle the recording of a question, once they have calculated whether
	 * or not the user said the answer correctly
	 */
	public void updateScore(boolean correct) {
		if (correct) {
			score++;
		}
	}
}
