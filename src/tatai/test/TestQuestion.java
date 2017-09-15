package tatai.test;

import static org.junit.Assert.*;

import org.junit.Test;

import tatai.question.MaoriNumber;
import tatai.question.Question;

public class TestQuestion {

	@Test
	public void testGetHTKWords() {
		Question mn14 = new MaoriNumber(14);
		String[] htk14 = mn14.getHTKWords();
		String[] expected14 ={"tekau","ma","wha"};
		
		assertArrayEquals(expected14, htk14);
		
		Question mn4 = new MaoriNumber(1);
		String[] htk4 = mn4.getHTKWords();
		String[] expected4 ={"tahi"};
		
		assertArrayEquals(expected4, htk4);
		
		Question mn90 = new MaoriNumber(90);
		String[] htk90 = mn90.getHTKWords();
		String[] expected90 ={"iwa","tekau"};
		
		assertArrayEquals(expected90, htk90);
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
