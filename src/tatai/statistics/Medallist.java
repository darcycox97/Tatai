package tatai.statistics;

public class Medallist {

	String username;
	String score;
	
	public enum MedalType {
		GOLD, SILVER, BRONZE;
	}
	
	public Medallist(String username, String score) {
		this.username = username;
		this.score = score;
	}
	
	public String getScore() {
		return this.score;
	}
	
	public String getUsername() {
		return this.username;
	}
	
	public void setDashes() {
		this.username = "--";
		this.score = "--";
	}
	
}
