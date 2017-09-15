package tatai.question;

/**
 * Defines general behaviour of numbers in this application. Numbers could be in any language, it is up to sub-classes to determine 
 */
public abstract class AbstractNumber implements Question {
	private String text; // the string representation of the number (as a word) e.g 1 = "one" in English
	private int value; // the actual value of the number
	
	public AbstractNumber(int value) {
		this.value = value;
		this.text = getSpelling(value);
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	@Override
	public int getValue() {
		return value;
	}
	
	@Override
	public String[] getHTKWords() {
		return text.split("\\W+");
	}
	
	@Override
	public String getDisplayText() {
		return this.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AbstractNumber) {
			return this.value == ((AbstractNumber)obj).value;
		} else {
			return false;
		}
	}
	
	/**
	 * To be implemented by subclasses, defines how to spell any number.
	 * @param value The number to get the spelling of
	 */
	protected abstract String getSpelling(int value);
}
