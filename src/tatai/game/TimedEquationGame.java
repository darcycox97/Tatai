package tatai.game;

import java.util.Formatter;

public class TimedEquationGame extends FiniteGame {
	
	public TimedEquationGame(GameDifficulty difficulty) {
		super(difficulty);
	}

	@Override
	public GameMode getGameMode() {
		return GameMode.TIME_ATTACK;
	}
	
	@Override
	public String getScore() {
		// format time as 2 d.p
		Formatter f = new Formatter();
		f.format("%1$.2f", score);
		String score = f.toString();
		f.close();
		return score + " seconds";
	}

}
