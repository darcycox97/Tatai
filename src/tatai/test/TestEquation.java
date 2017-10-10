package tatai.test;

import org.junit.Before;
import org.junit.Test;

import tatai.game.GameDifficulty;
import tatai.question.Equation;

public class TestEquation {

	@Before
	public void setUp() throws Exception {
	}

	
	@Test
	public void test() {
		
		// generate a large set of hard and easy equations, and print them to the console to be examined 
		
		for (int i = 0; i < 100; i++) {
			new Equation(GameDifficulty.EASY);
		}
		
		System.out.println("Generating Hard equations....");
		for (int i =0; i < 100; i++) {
			new Equation(GameDifficulty.HARD);
		}
	}

}
