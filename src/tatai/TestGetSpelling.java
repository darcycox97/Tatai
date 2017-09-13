package tatai;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TestGetSpelling {

	@Test
	public void test() {
		MaoriNumber num1 = new MaoriNumber(87);
		MaoriNumber num2 = new MaoriNumber(3);
		
		ArrayList<String> num1List = new ArrayList<String>();
		num1List.add("waru");
		num1List.add("tekau");
		num1List.add("ma");
		num1List.add("whitu");
		
		ArrayList<String> num2List = new ArrayList<String>();
		num2List.add("toru");
		
		//assertEquals(num1List, num1.getText());
		assertEquals(num2List, num2.getText());
		
	}

}
