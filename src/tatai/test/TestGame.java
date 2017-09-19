package tatai.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import tatai.game.Game;
import tatai.game.NumberGame;
import tatai.question.Question;

public class TestGame {
	Game game;

	@Before
	public void setUp() {
		game = new NumberGame(10,10);
	}

	@Test
	public void test() {

		// repeat the test 50 times to increase probability of failure due to random numbers
		int numQuestions;
		for (int i = 0; i < 50; i++) {
			game = new NumberGame(10,10);
			numQuestions = 0;
			while (game.hasNextQuestion()) {
				Question q = game.nextQuestion();
				assertTrue(q.getValue()>0 && q.getValue() <= 10); // check questions are within the specified range
				numQuestions++;
				System.out.println(numQuestions);
			}
			System.out.println(numQuestions);
			assertEquals(10,numQuestions); // check all questions are iterated through

			game = new NumberGame(10, 99);
			numQuestions = 0;
			while (game.hasNextQuestion()) {
				Question q = game.nextQuestion();
				assertTrue(q.getValue()>0 && q.getValue() <= 99); // check questions are within the specified range
				numQuestions++;
			}

			assertEquals(10,numQuestions);  // check all questions are iterated through
		}
	}

}
