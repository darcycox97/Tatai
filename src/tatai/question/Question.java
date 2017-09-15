package tatai.question;

/**
 * General question type. Represents anything that could be displayed in the Tatai application
 * as a "question" during the game mode. The answer to any question should be an integer.
 * e.g an equation, or a number itself.
 */
public interface Question {
	
	/**
	 * Defines what component of this Question is displayed to the user. 
	 * e.g the value itself, or the spelling of the value.
	 * @return The string that is to be displayed to the user.
	 */
	public String getDisplayText();
	
	/**
	 * Returns the integer answer of the question
	 */
	public int getValue();
	
	/**
	 * Returns a string array of words to check against the output of HTK.
	 */
	public String[] getHTKWords();
	

}
