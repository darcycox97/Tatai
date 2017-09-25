package tatai;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
import javafx.scene.shape.Circle;
import tatai.question.Question;
import tatai.game.Game;
import tatai.game.GameInstance;
import tatai.game.NumberGame;
import tatai.htk.HTKListener;

public class GameScreenController implements HTKListener{
	
	private static final String RECORDING = "Recording....";
	private static final int NEXT_LEVEL_THRESHOLD = 8;
	private static final String CORRECT_COLOR = "#1DAD0C";
	private static final String INCORRECT_COLOR = "#F93930";

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
	private Label lblQuestionNumber;
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
	private Label lblGamePrompts;
	@FXML
	private Label lblCorrectOutcome;
	@FXML
	private Label lblIncorrectOutcome;
	@FXML
	private Label lblScore;
	@FXML
	private Label lblTotalScore;
	
	@FXML
	Circle circle1;
	@FXML
	Circle circle2;
	@FXML
	Circle circle3;
	@FXML
	Circle circle4;
	@FXML
	Circle circle5;
	@FXML
	Circle circle6;
	@FXML
	Circle circle7;
	@FXML
	Circle circle8;
	@FXML
	Circle circle9;
	@FXML
	Circle circle10;
	HashMap<Integer,Circle> circleMap;

	@FXML 
	public void initialize() {

		game = GameInstance.getInstance().getCurrentGame();
		playerNamePrompt();
		lblScore.setText("");
		recordView(); // put gui into ready to record state
		displayQuestion(game.nextQuestion());
		
		// set up hashmap for circles and set their fill to transparent
		circleMap = new HashMap<Integer,Circle>();
		circleMap.put(1, circle1);
		circleMap.put(2, circle2);
		circleMap.put(3, circle3);
		circleMap.put(4, circle4);
		circleMap.put(5, circle5);
		circleMap.put(6, circle6);
		circleMap.put(7, circle7);
		circleMap.put(8, circle8);
		circleMap.put(9, circle9);
		circleMap.put(10, circle10);
		for (int i = 1; i <= 10; i++) {
			circleMap.get(i).setStyle("-fx-fill:transparent;");
		}
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
		lblGamePrompts.setText(RECORDING);
		game.attemptQuestion(this);
	}

	/**
	 * Defines what the gui should do when the recording has finished
	 */
	public void recordingComplete() {

		btnRecord.setDisable(false);

		boolean correct = game.getCurrentResult();

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
		if (player != null) {
			player.stop();
		}
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
		int circleNumber = game.getQuestionNumber();
		Circle circleToChange = circleMap.get(circleNumber);
		if (correct) {
			lblCorrectOutcome.setVisible(true);
			lblIncorrectOutcome.setVisible(false);
			circleToChange.setStyle("-fx-fill:" + CORRECT_COLOR + ";");
		} else {
			lblIncorrectOutcome.setVisible(true);
			lblCorrectOutcome.setVisible(false);
			circleToChange.setStyle("-fx-fill:" + INCORRECT_COLOR + ";");
		}

		lblScore.setText(game.getScore());
	}

	private void displayQuestion(Question q) {
		questionLabel.setText(q.getDisplayText());
		lblQuestionNumber.setText("Question #" + game.getQuestionNumber());
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
		lblIncorrectOutcome.setVisible(false);
		lblCorrectOutcome.setVisible(false);
		lblQuestionNumber.setVisible(true);
		lblGamePrompts.setVisible(true);
		lblGamePrompts.setText("");
		gameFinishedGoodScoreOptions.setVisible(false);
		gameFinishedBadScoreOptions.setVisible(false);
		totalScoreBox.setVisible(false);
		lblScore.setVisible(true);
	}

	private void tryAgainView() {
		
		lblQuestionNumber.setVisible(true);
		lblCorrectOutcome.setVisible(false);
		lblIncorrectOutcome.setVisible(false);
		lblGamePrompts.setVisible(false);
		btnRecord.setVisible(false);
		btnPlayBack.setVisible(true);
		tryAgainBox.setVisible(true);
		questionLabel.setVisible(true);
		btnNext.setVisible(false);
		gameFinishedGoodScoreOptions.setVisible(false);
		gameFinishedBadScoreOptions.setVisible(false);
		totalScoreBox.setVisible(false);
		lblScore.setVisible(true);
		
		displayResults(game.getCurrentResult());
	}

	private void nextQuestionView() {
		lblQuestionNumber.setVisible(true);
		lblCorrectOutcome.setVisible(false);
		lblIncorrectOutcome.setVisible(false);
		lblGamePrompts.setVisible(false);
		btnRecord.setVisible(false);
		btnPlayBack.setVisible(true);
		tryAgainBox.setVisible(false);
		questionLabel.setVisible(true);
		btnNext.setVisible(true);
		gameFinishedGoodScoreOptions.setVisible(false);
		gameFinishedBadScoreOptions.setVisible(false);
		totalScoreBox.setVisible(false);
		lblScore.setVisible(true);
		
		displayResults(game.getCurrentResult());
	}
	
	private void gameFinished() {
		lblGamePrompts.setVisible(true);
		lblCorrectOutcome.setVisible(false);
		lblIncorrectOutcome.setVisible(false);
		btnNext.setVisible(false);
		btnRecord.setVisible(false);
		tryAgainBox.setVisible(false);
		btnPlayBack.setVisible(false);
		questionLabel.setVisible(false);
		lblQuestionNumber.setVisible(false);
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
			lblGamePrompts.setText("That's a great score!");
			
		} else {
			lblGamePrompts.setText("Nice try!");
			gameFinishedBadScoreOptions.setVisible(true);
			gameFinishedGoodScoreOptions.setVisible(false);
		}
	
	}


}
