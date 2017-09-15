package tatai.test;

import static org.junit.Assert.*;

import org.junit.Test;

import tatai.question.MaoriNumber;
import tatai.question.Question;

public class TestQuestion {

	@Test
	public void testGetHTKWords() {
		Question mn34 = new MaoriNumber(34);
		String[] htk34 = mn34.getHTKWords();
		String[] expected34 ={"toru","tekau","ma","wha"};
		
		assertArrayEquals(expected34, htk34);
		
		Question mn4 = new MaoriNumber(1);
		String[] htk4 = mn4.getHTKWords();
		String[] expected4 ={"tahi"};
		
		assertArrayEquals(expected4, htk4);
		
		Question mn99 = new MaoriNumber(99);
		String[] htk99 = mn99.getHTKWords();
		String[] expected99 ={"iwa","tekau","ma","iwa"};
		
		assertArrayEquals(expected99, htk99);
	}
	
	@Test
	public void testGetValue() {
		Question mn1 = new MaoriNumber(1);
		assertTrue(mn1.getValue() == 1);
	}
	
	@Test
	public void testGetDisplayText() {
		Question mn78 = new MaoriNumber(78);
		
		assertEquals("78", mn78.getDisplayText());
	}

}
