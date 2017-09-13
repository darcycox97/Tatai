package tatai;

/**
 * Defines general behaviour of numbers in this application. Numbers could be in any language, it is up to sub-classes to determine 
 */
public abstract class Number {
	private String text; // the string representation of the number (as a word) e.g 1 = "one" in English
	private int value; // the actual value of the number
	
	public Number(int value) {
		this.value = value;
		this.text = getSpelling(value);
	}
	
	public int getValue() {
		return value;
	}
	
	public String toString() {
		return text;
	}
	
	/**
	 * To be implemented by subclasses, defines how to spell any number.
	 * @param value The number to get the spelling of
	 */
	protected abstract String getSpelling(int value);
}
