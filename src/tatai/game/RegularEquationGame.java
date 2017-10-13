package tatai.game;

public class RegularEquationGame extends FiniteGame {

	public RegularEquationGame(GameDifficulty difficulty) {
		super(difficulty);
	}

	@Override
	public GameMode getGameMode() {
		return GameMode.CLASSIC;
	}

}
