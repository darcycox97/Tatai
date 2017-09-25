package tatai;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;

/**
 *	Defines the event handlers for the controls on the Tatai leaderboard screen.
 */
public class LeaderboardController {

	@FXML
	private Label leaderboardTitle;
	@FXML
	private Button btnExit;
	@FXML
	private Button btnShowEasyAllTime;
	@FXML
	private Button btnShowHardAllTime;
	@FXML
	private Button btnShowEasyCurrent;
	@FXML
	private Button btnShowHardCurrent;
	@FXML
	private TableView<Leader> leadersList = new TableView<Leader>();
	@FXML
	private ObservableList<Leader> leaders = FXCollections.observableArrayList();


	@FXML 
	public void initialize() {
		showEasyAllTimeLeaders();
	}

	@FXML
	public void returnHomeScreen(ActionEvent e) {
		BorderPane root;
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
			((Node) e.getSource()).getScene().setRoot(root);
		} catch (IOException e1) {
			System.out.println("Error exiting leaderboard");
			//e1.printStackTrace();
		}
	}

	@FXML
	public void showEasyAllTimeLeaders() {
		switchLeaderboard("EasyAllTime");
		leaderboardTitle.setText("All Time Leaders - Easy");
	}

	@FXML
	public void showHardAllTimeLeaders() {
		switchLeaderboard("HardAllTime");
		leaderboardTitle.setText("All Time Leaders - Hard");
	}
	
	@FXML
	public void showEasyCurrentLeaders() {
		switchLeaderboard("EasyCurrent");
		leaderboardTitle.setText("Current Leaders - Easy");
	}
	
	@FXML
	public void showHardCurrentLeaders() {
		switchLeaderboard("HardCurrent");
		leaderboardTitle.setText("Current Leaders - Hard");
	}

	@FXML
	public void switchLeaderboard(String level) {

		leaders.clear();
		ProcessBuilder pbEasy = new ProcessBuilder("bash", "-c", "echo "
				+ "\"$(cat .leaderboard" + level + ")\"");

		try {
			Process p = pbEasy.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String inputLine;

			while ((inputLine = in.readLine()) != null) {

				String[] getName = inputLine.split(" ");
				String name = getName[0];

				if (getName.length > 1) {
					String[] getScore = getName[1].split("/");
					Integer score = Integer.valueOf(getScore[0]);
					Leader leader = new Leader(name, score);
					leaders.add(leader);
				} else {
					Leader leader = new Leader("",null);
					leaders.add(leader);
				}

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Collections.sort(leaders, new Comparator<Leader>(){
			public int compare(Leader o1, Leader o2){
				return o2.getScore() - o1.getScore();
			}
		});

		String range = null;

		if (level.contains("Easy")) {
			range = "10";
		} else {
			range = "20";
		}

		Integer rank = 1;
		for (Leader leader : leaders) {
			if (leader.getName() != "") {
				leader.setRank(rank);
				rank++;
			}
		}

		leadersList.setItems(leaders);

		TableColumn<Leader,Integer> rankCol = new TableColumn<Leader,Integer>("Rank");
		rankCol.setCellValueFactory(new PropertyValueFactory<Leader,Integer>("rank"));

		TableColumn<Leader,String> nameCol = new TableColumn<Leader,String>("Player Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Leader,String>("name"));

		TableColumn<Leader,Integer> scoreCol = new TableColumn<Leader,Integer>("Score / " + range);
		scoreCol.setCellValueFactory(new PropertyValueFactory<Leader,Integer>("score"));

		leadersList.getColumns().setAll(rankCol, nameCol, scoreCol);

	}

}
