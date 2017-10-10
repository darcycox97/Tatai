package tatai.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import tatai.statistics.Leader;
import tatai.statistics.LeadersInstance;

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
	private ComboBox<String> selectLevel;
	@FXML
	private TableView<Leader> leadersList = new TableView<Leader>();
	@FXML
	private ObservableList<Leader> leaders = FXCollections.observableArrayList();


	@FXML 
	public void initialize() {
		selectLevel.getItems().add("Easy");
		selectLevel.getItems().add("Hard");
	}

	@FXML
	public void returnHomeScreen(ActionEvent e) {
		BorderPane root;
		try {
			root = (BorderPane)FXMLLoader.load(getClass().getResource("../view/HomeScreen.fxml"));
			((Node) e.getSource()).getScene().setRoot(root);
		} catch (IOException e1) {
			System.out.println("Error exiting leaderboard");
			//e1.printStackTrace();
		}
	}

	@FXML
	public void showEasyLeaders() {
		leaders = LeadersInstance.getLeadersListEasy();
	}

	@FXML
	public void showHardLeaders() {
		leaders = LeadersInstance.getLeadersListHard();
	}

	@SuppressWarnings("unchecked")
	@FXML
	public void loadLeaderboard(ActionEvent e) {

		String level = selectLevel.getValue();

		if (level == "Easy") {
			showEasyLeaders();
		} else {
			showHardLeaders();
		}

		leaderboardTitle.setText("Scores for '" + level + "' Level");

		Collections.sort(leaders, new Comparator<Leader>(){
			public int compare(Leader o1, Leader o2){
				return o2.getScore() - o1.getScore();
			}
		});

		String range = "10";

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
		rankCol.setSortable(false);

		TableColumn<Leader,String> nameCol = new TableColumn<Leader,String>("Player Name");
		nameCol.setCellValueFactory(new PropertyValueFactory<Leader,String>("name"));
		nameCol.setSortable(false);

		TableColumn<Leader,Integer> scoreCol = new TableColumn<Leader,Integer>("Score / " + range);
		scoreCol.setCellValueFactory(new PropertyValueFactory<Leader,Integer>("score"));
		scoreCol.setSortable(false);
		
		leadersList.getColumns().setAll(rankCol, nameCol, scoreCol);

	}

}
