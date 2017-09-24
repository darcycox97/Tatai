package tatai;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import tatai.question.Question;
import tatai.game.Game;
import tatai.game.GameInstance;
import tatai.game.NumberGame;
import tatai.htk.HTKListener;

public class GameScreenController implements HTKListener{
	
	private static final String CORRECT = "Correct!";
	private static final String INCORRECT = "Incorrect";
	private static final String RECORDING = "Recording....";
	private static final int NEXT_LEVEL_THRESHOLD = 8;

	private static final File AUDIO = new File("resources/HTK/MaoriNumbers/question_attempt.wav"); // points to the file that contains the output of the user's recordings.

	private Game game;

	private MediaPlayer player;

	@FXML
	private Button returnHome;
	@FXML
	private Button btnRecord;
	@FXML
	private Label questionLabel;
	@FXML
	private Button btnNext;
	@FXML
	private Button btnPlayBack;
	@FXML
	private HBox tryAgainBox;
	@FXML
	private HBox gameFinishedGoodScoreOptions;
	@FXML
	private HBox gameFinishedBadScoreOptions;
	@FXML
	private VBox totalScoreBox;
	@FXML
	private Label lblOutcome;
	@FXML
	private Label lblScore;
	@FXML
	private Label lblTotalScore;

	@FXML 
	public void initialize() {

		game = GameInstance.getInstance().getCurrentGame();
		playerNamePrompt();
		lblScore.setText("");
		recordView(); // put gui into ready to record state
		displayQuestion(game.nextQuestion());
	}

	@FXML
	public void homeClicked() {
		BorderPane root;
		try {

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Exit Confirmation");
			alert.setContentText("Are you sure?");

			ButtonType buttonTypeYes = new ButtonType("Yes");
			ButtonType buttonTypeCancel = new ButtonType("No", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeYes){
				root = (BorderPane)FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
				returnHome.getScene().setRoot(root);
			} else {
				// ... user chose CANCEL or closed the dialog
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void startRecording() {
		btnRecord.setDisable(true);// record button should remain disabled until recording is finished
		lblOutcome.setText(RECORDING);
		game.attemptQuestion(this);
	}

	/**
	 * Defines what the gui should do when the recording has finished
	 */
	public void recordingComplete() {

		btnRecord.setDisable(false);

		boolean correct = game.getResult();

		if (correct || game.numAttempts() > 1) {
			nextQuestionView();
		} else {
			// the user only gets to try again if they have made a single attempt
			tryAgainView();
		}
	}

	@FXML
	public void retryQuestion() {
		recordView(); // put gui into ready to record state
		startRecording(); // start recording
	}

	@FXML
	public void nextQuestion() {
		recordView();
		if (game.hasNextQuestion()) {
			displayQuestion(game.nextQuestion());
		} else {	
			gameFinished();		
		}
	}

	@FXML
	public void playbackAudio() {
		player = new MediaPlayer(new Media(AUDIO.toURI().toString()));
		player.play();
	}
	
	@FXML
	public void playAgain() {
		int currentRange = game.getRange();
		GameInstance.getInstance().setCurrentGame(new NumberGame(Game.DEFAULT_NUM_QUESTIONS,currentRange));
		initialize();
	}
	
	@FXML
	public void startEasyGame() {
		GameInstance.getInstance().setCurrentGame(new NumberGame(Game.DEFAULT_NUM_QUESTIONS,Game.EASY_RANGE));
		initialize();
	}
	
	@FXML
	public void startHardGame() {
		GameInstance.getInstance().setCurrentGame(new NumberGame(Game.DEFAULT_NUM_QUESTIONS,Game.HARD_RANGE));
		initialize();
	}


	/**************** Helpers ********************/
	
	private void displayResults(boolean correct) {
		if (correct) {
			lblOutcome.setText(CORRECT);
		} else {
			lblOutcome.setText(INCORRECT);
		}

		lblScore.setText(game.getScore());
	}

	private void displayQuestion(Question q) {
		questionLabel.setText(q.getDisplayText());
	}

	private void playerNamePrompt() {
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Player Name");
		dialog.setHeaderText("Enter your name: ");

		Optional<String> result = dialog.showAndWait();
		String enteredName = "no name";

		if (result.isPresent()) {
			enteredName = result.get();
		}

		game.setPlayerName(enteredName);
	}
	
	
	/*********** Helper methods to set up the gui for a certain state ***************/

	private void recordView() {
		AUDIO.delete();
		btnRecord.setVisible(true);
		btnPlayBack.setVisible(false);
		tryAgainBox.setVisible(false);
		questionLabel.setVisible(true);
		btnNext.setVisible(false);
		lblOutcome.setText("");
		gameFinishedGoodScoreOptions.setVisible(false);
		gameFinishedBadScoreOptions.setVisible(false);
		totalScoreBox.setVisible(false);
		lblScore.setVisible(true);
	}

	private void tryAgainView() {
		
		btnRecord.setVisible(false);
		btnPlayBack.setVisible(true);
		tryAgainBox.setVisible(true);
		questionLabel.setVisible(true);
		btnNext.setVisible(false);
		gameFinishedGoodScoreOptions.setVisible(false);
		gameFinishedBadScoreOptions.setVisible(false);
		totalScoreBox.setVisible(false);
		lblScore.setVisible(true);
		
		displayResults(game.getResult());
	}

	private void nextQuestionView() {
		btnRecord.setVisible(false);
		btnPlayBack.setVisible(true);
		tryAgainBox.setVisible(false);
		questionLabel.setVisible(true);
		btnNext.setVisible(true);
		gameFinishedGoodScoreOptions.setVisible(false);
		gameFinishedBadScoreOptions.setVisible(false);
		totalScoreBox.setVisible(false);
		lblScore.setVisible(true);
		
		displayResults(game.getResult());
	}
	
	private void gameFinished() {
		btnNext.setVisible(false);
		btnRecord.setVisible(false);
		tryAgainBox.setVisible(false);
		btnPlayBack.setVisible(false);
		questionLabel.setVisible(false);
		lblScore.setVisible(false);
		totalScoreBox.setVisible(true);
		lblTotalScore.setText(game.getScore());
		
		if (game.getScoreValue() >= NEXT_LEVEL_THRESHOLD) {
			if (game.getRange() == Game.EASY_RANGE) {
				gameFinishedGoodScoreOptions.setVisible(true);
				gameFinishedBadScoreOptions.setVisible(false);
			} else {
				gameFinishedBadScoreOptions.setVisible(true);
				gameFinishedGoodScoreOptions.setVisible(false);
			}
			lblOutcome.setText("That's a great score!");
			
		} else {
			lblOutcome.setText("Nice try!");
			gameFinishedBadScoreOptions.setVisible(true);
			gameFinishedGoodScoreOptions.setVisible(false);
		}
	
	}


}
