package tatai.test;

import static org.junit.Assert.*;


import org.junit.Test;

import tatai.question.MaoriNumber;

public class TestGetSpelling {

	@Test
	public void test() {
		MaoriNumber num1 = new MaoriNumber(87);
		MaoriNumber num2 = new MaoriNumber(3);
		
	
		
		assertEquals("waru tekau ma whitu", num1.toString());
		assertEquals("toru", num2.toString());
	}

}
