package tatai.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import tatai.question.Question;
import tatai.statistics.CSVFile;
import tatai.statistics.User;
import tatai.view.Screen;
import tatai.statistics.CSVFile.CSVName;
import tatai.game.Game;
import tatai.game.GameDifficulty;
import tatai.game.GameFactory;
import tatai.game.GameMode;
import tatai.htk.HTKListener;

public class GameScreenController extends ScreenController implements HTKListener{
	
	private static final String RECORDING = "Recording";
	
	// these constants define what is considered a good score for each game mode.
	private static final int CLASSIC_THRESHOLD = 8;
	private static final int ARCADE_THRESHOLD = 10;
	private static final int TIME_ATTACK_THRESHOLD = 60;

	// integer codes to use for 
	private static final int COUNT_UP = 0;
	private static final int COUNT_DOWN = 1;
	
	private GameMode gamemode;
	
	private static final String CORRECT_COLOR = "#1DAD0C";
	private static final String INCORRECT_COLOR = "#F93930";
	private static final String HALF_MARK_COLOR = "#FFA500";
	private static final String CORRECT = "Correct!";
	private static final String INCORRECT = "Incorrect!";

	private static final File INCORRECT_SOUND = new File("resources/sounds/incorrect.wav");
	private static final File CORRECT_SOUND = new File("resources/sounds/correct.mp3");
	private static final File AUDIO = new File("resources/HTK/MaoriNumbers/question_attempt.wav"); // points to the file that contains the output of the user's recordings.

	private Game game;
	private MediaPlayer player;
	private List<Animation> recordingAnimations;
	private Timeline countingAnimation;
	private SimpleDoubleProperty gameDuration;

	@FXML private Button btnRecord;
	@FXML private Label questionLabel;
	@FXML private Label lblQuestionNumber;
	@FXML private Button btnNext;
	@FXML private Button btnPlayBack;
	@FXML private Button btnSkip;
	@FXML private HBox tryAgainBox;
	@FXML private HBox gameFinishedBox;
	@FXML private Label lblGamePrompts;
	@FXML private Label lblScore;
	@FXML private VBox totalScoreBox;
	@FXML private Label lblTotalScore;
	@FXML private Label lblScoreTitle;
	@FXML private Label lblRecordTimer;
	@FXML private Label lblNumCorrect;
	
	@FXML private HBox circleBox;
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

		setup();
		
		game = GameFactory.getInstance().getCurrentGame();
		gamemode = game.getGameMode();
		
		lblScore.setText("");
		lblGamePrompts.setText("");
		recordView(); // put gui into ready to record state
		displayQuestion(game.nextQuestion());
		
		if (gamemode.equals(GameMode.ARCADE)) {
			startTimer(COUNT_DOWN);
		} else if (gamemode.equals(GameMode.TIME_ATTACK)) {
			startTimer(COUNT_UP);
		}

		if (gamemode.equals(GameMode.CLASSIC) || (gamemode.equals(GameMode.TIME_ATTACK)) || (gamemode.equals(GameMode.CUSTOM))) {
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
			circleBox.setVisible(true);
		} else {
			circleBox.setVisible(false);
		}
		
		recordingAnimations = getRecordAnimations();
	}

	@FXML
	public void startRecording() {
		btnRecord.setDisable(true);// record button should remain disabled until recording is finished
		btnSkip.setDisable(true);
		
		// initiate recording animation
		lblGamePrompts.setText(RECORDING);
		lblGamePrompts.setStyle("-fx-background-color:transparent;");
		
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
		btnSkip.setDisable(false);
		
		for (Animation a : recordingAnimations) {
			a.stop();
		}
		
		// do not proceed if game was finished during recording process.
		if (game.getFinished()) {
			return;
		}

		boolean correct = game.getLatestResult();

		if (gamemode.equals(GameMode.ARCADE)) {
			// for this game mode, don't allow second attempts
			nextQuestionView();
			
		} else if (gamemode.equals(GameMode.PRACTICE)) {
			if (correct) {
				nextQuestionView();
			} else {
				tryAgainView(); // allow infinite attempts in practice mode
			}
			
		} else if (gamemode.equals(GameMode.CLASSIC) || gamemode.equals(GameMode.CUSTOM)) {
			if (correct || game.getNumAttempts() > 1) {
				nextQuestionView();
			} else {
				// the user only gets to try again if they have made a single attempt
				tryAgainView();
			}
			
		} else {
			// game mode is TIME_ATTACK
			if (correct) {
				nextQuestionView();
			} else {
				// do not let user move on until correct.
				displayResults(correct);
				recordView();
			}
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
		GameMode currentMode = game.getGameMode();
		
		if (currentMode.equals(GameMode.CUSTOM)) {
			String quizName = game.getQuizName();
			GameFactory.getInstance().setCurrentGame(currentMode, currentDifficulty, quizName);
		} else {
			GameFactory.getInstance().setCurrentGame(currentMode, currentDifficulty, null);
		}
		
		initialize();
	}


	/**************** Helpers ********************/

	private void displayResults(boolean correct) {
		
		if (gamemode.equals(GameMode.ARCADE)) {
			lblNumCorrect.setText("Correct: " + game.getScore());
		}
		
		// update circles if we are in a finite game mode
		boolean finiteGame = ((gamemode.equals(GameMode.CLASSIC)) || (gamemode.equals(GameMode.TIME_ATTACK)) || (gamemode.equals(GameMode.CUSTOM)));
		
		if (correct) {
			
			// play positive sound to let user know, and have a fade animation to draw attention to the label
			if (player != null) {
				player.stop();
			}
			player = new MediaPlayer(new Media(CORRECT_SOUND.toURI().toString()));
			player.play();
			
			FadeTransition correctAnimation = new FadeTransition(Duration.millis(600), lblGamePrompts);
			correctAnimation.setFromValue(1.0);
			correctAnimation.setToValue(0.6);
			correctAnimation.setAutoReverse(true);
			correctAnimation.setCycleCount(2);
			correctAnimation.play();
			
			lblGamePrompts.setText(CORRECT);
			lblGamePrompts.setStyle("-fx-background-color:" + CORRECT_COLOR + ";");
			
			if (finiteGame) {
				int circleNumber = game.getQuestionNumber();
				Circle circleToChange = circleMap.get(circleNumber);
				
				if (game.getNumAttempts() == 1) {
					circleToChange.setStyle("-fx-fill:" + CORRECT_COLOR + ";");
				} else {
					circleToChange.setStyle("-fx-fill:" + HALF_MARK_COLOR + ";");
				}
			}
			
		} else {
			
			// play the incorrect sound
			if (player != null) {
				player.stop();
			}
			player = new MediaPlayer(new Media(INCORRECT_SOUND.toURI().toString()));
			player.play();
			
			// make the main label shake to indicate answer was incorrect
	
			
			// make the main label shake to indicate answer was incorrect
			Timeline shakeAnimation = new Timeline();
			shakeAnimation.getKeyFrames().addAll(
				new KeyFrame(Duration.ZERO, new KeyValue(questionLabel.translateXProperty(), 0)),
				new KeyFrame(Duration.millis(30), new KeyValue(questionLabel.translateXProperty(), 10)),
				new KeyFrame(Duration.millis(60), new KeyValue(questionLabel.translateXProperty(), -10)),
				new KeyFrame(Duration.millis(90), new KeyValue(questionLabel.translateXProperty(), 0))
			);
					
			shakeAnimation.setCycleCount(2);		
			shakeAnimation.play();
			
			lblGamePrompts.setText(INCORRECT);
			lblGamePrompts.setStyle("-fx-background-color:" + INCORRECT_COLOR + ";");
			
			if (finiteGame) {
				int circleNumber = game.getQuestionNumber();
				Circle circleToChange = circleMap.get(circleNumber);
				circleToChange.setStyle("-fx-fill:" + INCORRECT_COLOR + ";");
			}
			
		}

		if (gamemode.equals(GameMode.PRACTICE) || gamemode.equals(GameMode.CLASSIC) || gamemode.equals(GameMode.CUSTOM)) {
			lblScore.setText(game.getScore());
		}
	}

	private void displayQuestion(Question q) {
		questionLabel.setText(q.getDisplayText());
		lblQuestionNumber.setText("Question #" + game.getQuestionNumber());
	}
	
	private List<Animation> getRecordAnimations() {
		
		List<Animation> animations = new ArrayList<Animation>();
		
		FadeTransition recording = new FadeTransition(Duration.millis(700), lblGamePrompts);
		recording.setFromValue(0);
		recording.setToValue(100);
		recording.setAutoReverse(true);
		recording.setCycleCount(5);
		
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
				new KeyValue(lblRecordTimer.textProperty(),"3")
			),
			new KeyFrame(
				Duration.millis(1000),
				new KeyValue(lblRecordTimer.textProperty(),"2")
			),
			new KeyFrame(
				Duration.millis(2000),
				new KeyValue(lblRecordTimer.textProperty(),"1")
			)
		);
		
		animations.add(countdown);
		
		return animations;
	}
	
	private void startTimer(int upOrDown) {
		
		// start timer and either have it counting up or down
		if (upOrDown == COUNT_UP) {
			
			gameDuration = new SimpleDoubleProperty(0);
			countingAnimation = new Timeline(
				new KeyFrame(
					Duration.millis(10),
					e -> {
						double currentTime = gameDuration.get();
						currentTime += 0.01;
						gameDuration.set(currentTime);
					}
				)
			);
			
			// timer will count up indefinitely, until stopped by the game ending
			countingAnimation.setCycleCount(Animation.INDEFINITE);
			
		} else {
			
			gameDuration = new SimpleDoubleProperty(60);
			countingAnimation = new Timeline(
				new KeyFrame(
					Duration.millis(10),
					e -> {
						double currentTime = gameDuration.get();
						currentTime -= 0.01;
						gameDuration.set(currentTime);
					}
				)
			);
			
			// timer will stop when it reaches zero (after one minute), and end the game.
			countingAnimation.setCycleCount(6000);
			countingAnimation.setOnFinished(e -> gameFinished());
		}
		
		lblScore.textProperty().bind(gameDuration.asString("%.2f"));
		countingAnimation.playFromStart();
		
		countingAnimation.statusProperty().addListener(e -> {
			if (countingAnimation.getStatus().equals(Status.STOPPED)) {
				lblScore.textProperty().unbind();
			}
		});
	}


	/*********** Helper methods to set up the gui for a certain state ***************/

	private void recordView() {
		
		AUDIO.delete();
		btnRecord.setVisible(true);
		lblQuestionNumber.setVisible(true);
		questionLabel.setVisible(true);
		lblRecordTimer.setVisible(true);
		lblGamePrompts.setVisible(true);
		
		lblRecordTimer.setText("");
		
		if (!gamemode.equals(GameMode.TIME_ATTACK)) {
			
			lblGamePrompts.setText("");
			lblGamePrompts.setStyle("-fx-background-color:transparent;");
		} else {
			
			if (game.getLatestResult()) {
				lblGamePrompts.setText("");
				lblGamePrompts.setStyle("-fx-background-color:transparent;");
			}
		}
		
		
		btnPlayBack.setVisible(false);
		tryAgainBox.setVisible(false);
		btnNext.setVisible(false);
		gameFinishedBox.setVisible(false);
		totalScoreBox.setVisible(false);
		lblScore.setVisible(true);
		
		if (gamemode.equals(GameMode.ARCADE) || gamemode.equals(GameMode.PRACTICE)) {
			btnSkip.setVisible(true);
		} else {
			btnSkip.setVisible(false);
		}
	}

	private void tryAgainView() {

		lblRecordTimer.setVisible(false);
		btnRecord.setVisible(false);
		btnNext.setVisible(false);
		gameFinishedBox.setVisible(false);
		totalScoreBox.setVisible(false);
		btnSkip.setVisible(false);
		
		lblQuestionNumber.setVisible(true);
		lblGamePrompts.setVisible(true);
		btnPlayBack.setVisible(true);
		tryAgainBox.setVisible(true);
		questionLabel.setVisible(true);
		lblScore.setVisible(true);
		
		if (gamemode.equals(GameMode.ARCADE)) {
			lblNumCorrect.setVisible(true);
		}

		displayResults(game.getLatestResult());
	}

	private void nextQuestionView() {
		
		lblRecordTimer.setVisible(false);
		lblQuestionNumber.setVisible(true);
		lblGamePrompts.setVisible(true);
		btnRecord.setVisible(false);
		btnPlayBack.setVisible(true);
		tryAgainBox.setVisible(false);
		questionLabel.setVisible(true);
		btnNext.setVisible(true);
		gameFinishedBox.setVisible(false);
		totalScoreBox.setVisible(false);
		lblScore.setVisible(true);
		btnSkip.setVisible(false);
		
		if (gamemode.equals(GameMode.ARCADE)) {
			lblNumCorrect.setVisible(true);
		}

		displayResults(game.getLatestResult());
	}

	private void gameFinished() {
		
		// stop recording animation in case it was still going
		for (Animation a : recordingAnimations) {
			a.stop();
		}
		
		game.setFinished(true);

		if (gamemode.equals(GameMode.TIME_ATTACK)) {
			// stop timer 
			countingAnimation.stop();
			game.setScoreValue(gameDuration.get());
			lblScoreTitle.setText("Your Time:");
		} else if (gamemode.equals(GameMode.ARCADE)) {
			lblScoreTitle.setText("Time up! Your score:");
		} else {
			lblScoreTitle.setText("Total Score:");
		}
		
		String username = User.getInstance().getName();
		String gamemode = game.getGameMode().toString();
		String level = game.getDifficulty().toString();
	
		double scoreValue = game.getScoreValue();
		String score = String.valueOf(scoreValue);
		
		String currentLine = CSVFile.getLineInCSV(CSVName.STATISTICS, username);
		String newLine = currentLine + "," + gamemode + "," + level + "," + score;
		
		CSVFile.replaceLine(CSVName.STATISTICS, username, newLine);

		lblGamePrompts.setVisible(true);
		lblGamePrompts.setStyle("-fx-background-color:transparent;");
		btnNext.setVisible(false);
		btnRecord.setVisible(false);
		tryAgainBox.setVisible(false);
		btnPlayBack.setVisible(false);
		questionLabel.setVisible(false);
		lblQuestionNumber.setVisible(false);
		lblScore.setVisible(false);
		btnSkip.setVisible(false);
		lblRecordTimer.setVisible(false);
		lblNumCorrect.setVisible(false);
		
		totalScoreBox.setVisible(true);
		gameFinishedBox.setVisible(true);
		
		lblTotalScore.setText(game.getScore());
	
		int threshold;
		switch (game.getGameMode()) {
		case CLASSIC:
		case CUSTOM:
			// custom games and classic games have the same threshold.
			threshold = CLASSIC_THRESHOLD;
			break;
		case ARCADE:
			threshold = ARCADE_THRESHOLD;
			break;
		case TIME_ATTACK:
			threshold = TIME_ATTACK_THRESHOLD;
			break;
		default:
			// other game modes never finish so this is not applicable, do nothing.
			threshold = 0;
			break;
		}
		
		// Calculate the user's best score to date for the relevant mode and level
		List<Double> userData = CSVFile.getUserData(username, gamemode, level);
		double userBest = Double.parseDouble(CSVFile.getBest(userData, gamemode));
		
		// depending on game mode, calculate whether the score is a "good score" 
		// or a personal best and display an appropriate message.
		if (game.getScoreValue() > userBest) {
			lblGamePrompts.setText("Ka Pai! Personal Best!");
		} else if (game.getGameMode().equals(GameMode.TIME_ATTACK)) {
			if (game.getScoreValue() <= threshold) {
				lblGamePrompts.setText("Ka Pai! Great score!");

			} else {
				lblGamePrompts.setText("Nice try!");
			}
		} else {
			if (game.getScoreValue() >= threshold) {
				lblGamePrompts.setText("Ka Pai! Great score!");

			} else {
				lblGamePrompts.setText("Nice try!");
			}
		}

	}

	private void stopAnimations() {
		
		if (recordingAnimations != null) {
			for (Animation a : recordingAnimations) {
				a.stop();
			}
		}

		if (countingAnimation != null) {
			countingAnimation.stop();
		}
	}
	
	
	// override exit methods so that animations are stopped to avoid possible errors.

	@FXML @Override
	public boolean loadHomeScreen() {
		boolean exit = super.loadHomeScreen();
		if (exit) { stopAnimations(); }
		return exit;
	}
	
	@FXML @Override
	public boolean loadPreviousScreen() {
		boolean exit = super.loadPreviousScreen();
		if (exit) { stopAnimations(); }
		return exit;
	}
	
	@FXML @Override 
	public boolean loadGameScreen() {
		boolean exit = super.loadGameScreen();
		if (exit) { stopAnimations(); }
		return exit;
	}
	
	@FXML @Override 
	public boolean loadPracticeScreen() {
		boolean exit = super.loadPracticeScreen();
		if (exit) { stopAnimations(); }
		return exit;
	}
	
	@FXML @Override
	public boolean loadStatsScreen() {
		boolean exit = super.loadStatsScreen();
		if (exit) { stopAnimations(); }
		return exit;
	}
	
	@FXML @Override
	public boolean logout() {
		boolean exit = super.logout();
		if (exit) { stopAnimations(); }
		return exit;
	}
	

	@Override
	protected Screen getScreen() {
		return Screen.GAME;
	}
	
}
