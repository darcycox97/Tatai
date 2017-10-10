package tatai.question;

/**
 * Class to encapsulate information about a maori number.
 */
public class MaoriNumber extends AbstractNumber {

	public MaoriNumber(int value) {
		super(value);
	}

	/**
	 * This implementation constructs the maori spelling of the number.
	 */
	protected String getSpelling(int value) {
		return MaoriTranslator.translate(value);
	}
}
