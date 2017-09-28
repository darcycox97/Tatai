package tatai.leaders;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Singleton that holds a list of all scores. Allows leaders to be added to the leadersList.
 *
 */

public class LeadersInstance {

	private static LeadersInstance instance = new LeadersInstance();
	
	private static ObservableList<Leader> leadersListEasy = FXCollections.observableArrayList();
	
	private static ObservableList<Leader> leadersListHard = FXCollections.observableArrayList();
	
	public static LeadersInstance getInstance() {
		return instance;
	}
	
	public static ObservableList<Leader> getLeadersListEasy() {
		return leadersListEasy;
	}
	
	public static ObservableList<Leader> getLeadersListHard() {
		return leadersListHard;
	}
	
	public static void addLeaderEasy(Leader leader) {
		leadersListEasy.add(leader);
	}
	
	public static void addLeaderHard(Leader leader) {
		leadersListHard.add(leader);
	}
	
}
