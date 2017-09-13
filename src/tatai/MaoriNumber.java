package tatai;

/**
 * Class to encapsulate information about a maori number.
 */
public class MaoriNumber {
	private String[] text; // array that holds each word in the spelling of 
	private int value; // the actual value of the number
	
	public MaoriNumber(int value) {
		this.value = value;
		text = getSpelling(value);
	}
	
	public String[] getText() {
		return text;
	}
	
	// returns the string representation of this number, in maori spelling
	private String[] getSpelling(int value) {
		int numDigits = String.valueOf(value).length();
		if (numDigits == 1) {
			
		} else {
			if (String.valueOf(value).substring(0,1).equals("1")) {
				
			} else {
				
			}
		}
		return null;
	}
}
