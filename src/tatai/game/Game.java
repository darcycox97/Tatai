package tatai.game;

import java.util.List;

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
	
	/**
	 * Constructor that sets number of questions to default.
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
}
