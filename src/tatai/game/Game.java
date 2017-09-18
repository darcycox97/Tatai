package tatai.game;

import java.util.List;

import tatai.question.Question;

/**
 * General representation of a game. Subclasses will define the number of questions, and the type of questions e.g Numbers or Equations.
 */
public abstract class Game {
	
	private static final int DEFAULT_NUM_QUESTIONS = 10;
	
	protected final int NUM_QUESTIONS;
	
	private int currentQuestionIndex;
	
	private List<Question> questions;
	
	/**
	 * Constructor that sets number of questions to default.
	 */
	public Game() {
		this(DEFAULT_NUM_QUESTIONS);
	}

	/**
	 * Constructor to specify number of questions in the game
	 */
	public Game(int numOfQuestions) {
		this.NUM_QUESTIONS = numOfQuestions;
		this.questions = initializeQuestions();
		this.currentQuestionIndex = 0;
	}
	
	/**
	 * This method should create a list that defines the questions to be displayed during the game.
	 * @return a list of questions to be iterated through during gameplay.
	 */
	protected abstract List<Question> initializeQuestions();
	
	public boolean hasNextQuestion() {
		return currentQuestionIndex < NUM_QUESTIONS;
	}
	
	public Question nextQuestion() {
		Question nextQ = questions.get(currentQuestionIndex);
		currentQuestionIndex++;
		return nextQ;
	}
}
