package tatai.statistics;

/**
 * Singleton class that encapsulates information about a user of the Tatai app.
 */
public class User {
	
	private static final User INSTANCE = new User();
	private String name;
	
	private User() {}
	
	public static User getInstance() {
		return INSTANCE;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

}
