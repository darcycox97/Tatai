package tatai.game;

/**
 * Singleton that holds a reference to the current game being played. Can change the current game with getters and setters.
 *
 */
public class GameInstance {
	
	private static GameInstance instance = new GameInstance();
	
	private static Game currentGame;
	
	private GameInstance() {}
	
	public static GameInstance getInstance() {
		return instance;
	}
	
	public Game getCurrentGame() {
		return currentGame;
	}
	
	public void setCurrentGame(Game game) {
		currentGame = game;
	}
}
