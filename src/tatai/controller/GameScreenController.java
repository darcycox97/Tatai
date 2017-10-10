package tatai.controller;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleBooleanProperty;
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
import javafx.util.Duration;
import tatai.question.Question;
import tatai.statistics.Leader;
import tatai.statistics.LeadersInstance;
import tatai.game.Game;
import tatai.game.GameDifficulty;
import tatai.game.GameInstance;
import tatai.game.NumberGame;
import tatai.htk.HTKListener;

public class GameScreenController implements HTKListener{
	
	private static final String RECORDING = "Recording";
	
	private static final int NEXT_LEVEL_THRESHOLD = 8;
	
	private static final String CORRECT_COLOR = "#1DAD0C";
	private static final String INCORRECT_COLOR = "#F93930";
	private static final String HALF_MARK_COLOR = "#FFA500";
	private static final String CORRECT = "Correct!";
	private static final String INCORRECT = "Incorrect!";

	private static final File AUDIO = new File("resources/HTK/MaoriNumbers/question_attempt.wav"); // points to the file that contains the output of the user's recordings.

	private Game game;

	private MediaPlayer player;
	private List<Animation> recordingAnimations;

	@FXML private Button returnHome;
	@FXML private Button btnRecord;
	@FXML private Label questionLabel;
	@FXML private Label lblQuestionNumber;
	@FXML private Button btnNext;
	@FXML private Button btnPlayBack;
	@FXML private HBox tryAgainBox;
	@FXML private HBox gameFinishedGoodScoreOptions;
	@FXML private HBox gameFinishedBadScoreOptions;
	@FXML private VBox totalScoreBox;
	@FXML private Label lblGamePrompts;
	@FXML private Label lblScore;
	@FXML private Label lblTotalScore;
	@FXML private Label lblTimer;

	@FXML private Circle circle1;
	@FXML private Circle circle2;
	@FXML private Circle circle3;
	@FXML private Circle circle4;
	@FXML private Circle circle5;
	@FXML private Circle circle6;
	@FXML private Circle circle7;
	@FXML private Circle circle8;
	@FXML private Circle circle9;
	@FXML private Circle circle10;
	HashMap<Integer,Circle> circleMap;

	@FXML 
	public void initialize() {
		game = GameInstance.getInstance().getCurrentGame();
		
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
		
		recordingAnimations = getRecordAnimations();
	}

	@FXML
	public void homeClicked() {
		BorderPane root;
		try {

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Exit Confirmation");
			alert.setHeaderText("Are you sure you wish to exit?");
			alert.setContentText("All progress will be lost.");

			ButtonType buttonTypeYes = new ButtonType("Yes");
			ButtonType buttonTypeCancel = new ButtonType("No", ButtonData.CANCEL_CLOSE);

			alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeCancel);

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == buttonTypeYes){
				root = (BorderPane)FXMLLoader.load(getClass().getResource("../view/Home.fxml"));
				returnHome.getScene().setRoot(root);
			} else {
				// ... user chose CANCEL or closed the dialog. stay on the game screen
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void startRecording() {
		btnRecord.setDisable(true);// record button should remain disabled until recording is finished
		
		// initiate recording animation
		lblGamePrompts.setText(RECORDING);
		
		for(Animation a : recordingAnimations) {
			a.playFromStart();
		}
		
		game.attemptQuestion(this);
	}

	/**
	 * Defines what the gui should do when the recording has finished
	 */
	public void recordingComplete() {

		btnRecord.setDisable(false);
		
		for (Animation a : recordingAnimations) {
			a.stop();
		}

		boolean correct = game.getLatestResult();

		if (correct || game.getNumAttempts() > 1) {
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
		GameDifficulty currentDifficulty = game.getDifficulty();
		GameInstance.getInstance().setCurrentGame(new NumberGame(currentDifficulty));
		initialize();
	}

	@FXML
	public void startEasyGame() {
		GameInstance.getInstance().setCurrentGame(new NumberGame(GameDifficulty.EASY));
		initialize();
	}

	@FXML
	public void startHardGame() {
		GameInstance.getInstance().setCurrentGame(new NumberGame(GameDifficulty.HARD));
		initialize();
	}


	/**************** Helpers ********************/

	private void displayResults(boolean correct) {
		
		int circleNumber = game.getQuestionNumber();
		Circle circleToChange = circleMap.get(circleNumber);
		
		if (correct) {
			
			lblGamePrompts.setText(CORRECT);
			lblGamePrompts.setStyle("-fx-background-color:" + CORRECT_COLOR + ";");
			
			if (game.getNumAttempts() == 1) {
				circleToChange.setStyle("-fx-fill:" + CORRECT_COLOR + ";");
			} else {
				circleToChange.setStyle("-fx-fill:" + HALF_MARK_COLOR + ";");
			}
			
		} else {
			
			lblGamePrompts.setText(INCORRECT);
			lblGamePrompts.setStyle("-fx-background-color:" + INCORRECT_COLOR + ";");
			circleToChange.setStyle("-fx-fill:" + INCORRECT_COLOR + ";");
			
		}

		lblScore.setText(game.getScore());
	}

	private void displayQuestion(Question q) {
		questionLabel.setText(q.getDisplayText());
		lblQuestionNumber.setText("Question #" + game.getQuestionNumber());
	}

	private void playerNamePrompt() {
		
		// display text dialog for user to enter their name in
		TextInputDialog dialog = new TextInputDialog();
		dialog.setTitle("Player Name");
		dialog.setHeaderText("Enter your name: ");

		// whenever the input text changes, check if it is valid and set the disabled property of the button
		dialog.getEditor().textProperty().addListener( e-> {

			SimpleBooleanProperty disabled = new SimpleBooleanProperty(false); // becomes false when invalid input is given.
			dialog.getDialogPane().lookupButton(ButtonType.OK).disableProperty().bind(disabled); // the button becomes disabled on invalid input

			String currentInput = dialog.getEditor().getText();
			
			boolean valid;
			if (currentInput.length() == 0) {
				// empty input counts as a valid name
				valid = true;
			} else {
				// allow alphanumeric, hyphens, and underscores
				valid = Pattern.matches("[\\w\\-]+", currentInput); 
			}
			
			disabled.setValue(!valid);

		});

		Optional<String> result = dialog.showAndWait();
		String enteredName = "Anonymous";

		if (result.isPresent()) {
			enteredName = result.get();
		}
		
	//	game.setPlayerName(enteredName);
		
	}
	
	private List<Animation> getRecordAnimations() {
		
		List<Animation> animations = new ArrayList<Animation>();
		
		FadeTransition recording = new FadeTransition(Duration.millis(700), lblGamePrompts);
		recording.setFromValue(0);
		recording.setToValue(100);
		recording.setAutoReverse(true);
		recording.setCycleCount(5);
		recording.setOnFinished(e -> System.out.println("Finsihed"));
		
		// opacity is set to full when recording is complete (and animation is stopped)
		recording.statusProperty().addListener(e -> {
			if (recording.statusProperty().get().equals(Animation.Status.STOPPED)) {
				lblGamePrompts.setOpacity(1.0);
			}
		});
		
		animations.add(recording);
		
		Timeline countdown = new Timeline(
			new KeyFrame(
				Duration.millis(0),
				new KeyValue(lblTimer.textProperty(),"3")
			),
			new KeyFrame(
				Duration.millis(1000),
				new KeyValue(lblTimer.textProperty(),"2")
			),
			new KeyFrame(
				Duration.millis(2000),
				new KeyValue(lblTimer.textProperty(),"1")
			)
		);
		
		animations.add(countdown);
		
		return animations;
	}


	/*********** Helper methods to set up the gui for a certain state ***************/

	private void recordView() {
		AUDIO.delete();
		btnRecord.setVisible(true);
		btnPlayBack.setVisible(false);
		tryAgainBox.setVisible(false);
		questionLabel.setVisible(true);
		btnNext.setVisible(false);
		lblQuestionNumber.setVisible(true);
		lblGamePrompts.setVisible(true);
		lblGamePrompts.setText("");
		lblGamePrompts.setStyle("-fx-background-color:transparent;");
		lblTimer.setVisible(true);
		lblTimer.setText("");
		gameFinishedGoodScoreOptions.setVisible(false);
		gameFinishedBadScoreOptions.setVisible(false);
		totalScoreBox.setVisible(false);
		lblScore.setVisible(true);
	}

	private void tryAgainView() {

		lblTimer.setVisible(false);
		lblQuestionNumber.setVisible(true);
		lblGamePrompts.setVisible(true);
		btnRecord.setVisible(false);
		btnPlayBack.setVisible(true);
		tryAgainBox.setVisible(true);
		questionLabel.setVisible(true);
		btnNext.setVisible(false);
		gameFinishedGoodScoreOptions.setVisible(false);
		gameFinishedBadScoreOptions.setVisible(false);
		totalScoreBox.setVisible(false);
		lblScore.setVisible(true);

		displayResults(game.getLatestResult());
	}

	private void nextQuestionView() {
		lblTimer.setVisible(false);
		lblQuestionNumber.setVisible(true);
		lblGamePrompts.setVisible(true);
		btnRecord.setVisible(false);
		btnPlayBack.setVisible(true);
		tryAgainBox.setVisible(false);
		questionLabel.setVisible(true);
		btnNext.setVisible(true);
		gameFinishedGoodScoreOptions.setVisible(false);
		gameFinishedBadScoreOptions.setVisible(false);
		totalScoreBox.setVisible(false);
		lblScore.setVisible(true);

		displayResults(game.getLatestResult());
	}

	private void gameFinished() {
  
  	playerNamePrompt();
		
	//	Leader leader = new Leader(game.getPlayerName(), game.getScoreValue());
    
		if (game.getDifficulty().equals(GameDifficulty.EASY)) {
//			appendToEasyLeaderboard(leader);
		} else {
//			appendToHardLeaderboard(leader);
		}

		lblGamePrompts.setVisible(true);
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
			if (game.getDifficulty().equals(GameDifficulty.EASY)) {
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
	
	private void appendToEasyLeaderboard(Leader leader) {
		LeadersInstance.addLeaderEasy(leader);
	}

	private void appendToHardLeaderboard(Leader leader) {
		LeadersInstance.addLeaderHard(leader);
	}
	
}
