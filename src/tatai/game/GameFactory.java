package tatai.game;

/**
 * Singleton that holds a reference to the current game being played. Can change the current game with getters and setters.
 */
public class GameFactory {
	
	private static GameFactory instance = new GameFactory();
	
	private static Game currentGame;
	
	private GameFactory() {}
	
	public static GameFactory getInstance() {
		return instance;
	}
	
	public Game getCurrentGame() {
		return currentGame;
	}
	
	/**
	 * Sets the appropriate game.
	 * @param game The game mode to instantiate
	 * @param difficulty the difficulty of the game
	 * @param quizName Used for setting custom game
	 */
	public void setCurrentGame(GameMode game, GameDifficulty difficulty, String quizName) {
		
		switch (game) {
		case CLASSIC:
			currentGame = new RegularEquationGame(difficulty);
			break;
		case ARCADE:
			currentGame = new OneMinuteEquationGame(difficulty);
			break;
		case TIME_ATTACK:
			currentGame = new TimedEquationGame(difficulty);
			break;
		case PRACTICE:
			currentGame = new PracticeEquationGame(difficulty);
			break;
		case CUSTOM:
			currentGame = new CustomEquationGame(quizName);
			break;
		}
	}

}
