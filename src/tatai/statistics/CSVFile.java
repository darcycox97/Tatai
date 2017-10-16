package tatai.statistics;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;
import tatai.statistics.Medallist.MedalType;

import java.io.File;

/**
 * Singleton class that encapsulates information about the statistics.csv file 
 * containing all game statistics.
 */
public class CSVFile {

	public enum CSVName {
		STATISTICS, QUIZZES
	}

	private static final String STATS_FILE = "resources/statistics.csv";
	private static final String QUIZZES_FILE = "resources/quizzes.csv";

	//private static final String TEMP_FILE_NAME = "resources/temporary.csv";


	/**
	 * Appends the provided line to the specified csv file
	 * @param csv the csv to append to
	 * @param line the line to add to the csv
	 */
	public static void appendToCSV(CSVName file, String line) {

		try {

			List<String> oldFile;
			List<String> newFile;
			File toChange;
			if (file.equals(CSVName.STATISTICS)) {
				toChange = new File(STATS_FILE);
			} else {
				toChange = new File(QUIZZES_FILE);
			}

			oldFile = Files.readAllLines(toChange.toPath());
			newFile = new ArrayList<String>();

			for (int i = 0; i < oldFile.size(); i++) {
				newFile.add(oldFile.get(i));
			}
			newFile.add(line);

			Files.write(toChange.toPath(), newFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets a specific line in the csv file.
	 * Returns null if line does not exist.
	 * @param fileToRead the csv to read
	 * @param title the first entry of the line in the csv file e.g name of user or name of quiz
	 */
	public static String getLineInCSV(CSVName fileToRead, String title) {
		try {

			File toRead;
			if (fileToRead.equals(CSVName.STATISTICS)) {
				toRead = new File(STATS_FILE);
			} else {
				toRead = new File(QUIZZES_FILE);
			}

			List<String> lines = Files.readAllLines(toRead.toPath());

			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				String[] entries = line.split(",");
				String firstEntry = entries[0];

				if (firstEntry.equals(title)) {
					return line;
				}
			}

			return null;

			// if we got this far, there were no matches, so return null

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}


	/**
	 * Replaces a specified line with the provided string.
	 * Returns true if was successful, false if not
	 * @param title the title of the line to be replaced.
	 * @param line the line to be inserted into the csv
	 */
	public static boolean replaceLine(CSVName csv, String title, String line) {

		try {
			File toChange;
			if (csv.equals(CSVName.STATISTICS)) {
				toChange = new File(STATS_FILE);
			} else {
				toChange = new File(QUIZZES_FILE);
			}

			List<String> contents = Files.readAllLines(toChange.toPath());

			for (int i = 0; i < contents.size(); i++) {
				String currentLine = contents.get(i);
				String[] entries = currentLine.split(",");
				String firstEntry = entries[0];

				if (firstEntry.equals(title)) {
					// we have found the line to overwrite
					contents.set(i, line);
					Files.write(toChange.toPath(), contents); // overwrite file with new line inserted
					return true;
				}
			}

			return false;	

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

	}

	/**
	 * Gets a list containing all first entries of the CSV file e.g Usernames or quiznames
	 */
	public static List<String> getAllTitles(CSVName csv)
	{
		try {
			File toScan;
			if (csv.equals(CSVName.STATISTICS)) {
				toScan = new File(STATS_FILE);
			} else {
				toScan = new File(QUIZZES_FILE);
			}

			List<String> contents = Files.readAllLines(toScan.toPath());
			List<String> titles = new ArrayList<String>();
			for (String s : contents) {
				String[] entries = s.split(",");
				titles.add(entries[0]);
			}

			return titles;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	// STATISTICS SPECIFIC METHODS ...	


	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Series<String, Double> getSeriesData(String username, String gamemode) {

		XYChart.Series data = new XYChart.Series<>();
		int gameNumber = 1;

		String[] lineArray = getLineInCSV(CSVName.STATISTICS, username).split(",");

		for (int i = 0; i < lineArray.length - 1; i++) {
			if (lineArray[i].equals(gamemode)) {
				data.getData().add(new XYChart.Data(gameNumber, Double.parseDouble(lineArray[i+1])));
				gameNumber++;
			}
		}

		return data;
	}

	public static List<Double> getUserData(String username, String gamemode) {

		List<Double> data = new ArrayList<Double>();

		String[] lineArray = getLineInCSV(CSVName.STATISTICS, username).split(",");

		for (int i = 0; i < lineArray.length - 1; i++) {
			if (lineArray[i].equals(gamemode)) {
				data.add(Double.parseDouble(lineArray[i+1]));
			}
		}

		return data;
	}

	public static String getAverage(String username, String gamemode) {

		double sum = 0.0;
		List<Double> userData = getUserData(username, gamemode);

		for (Double value : userData) {
			sum += value;
		}

		double average = sum / userData.size();
		average = Math.round(average*10);
		average = average / 10;

		return String.valueOf(average);
	}

	public static String getBest(List<Double> data, String gamemode) {

		Double best = null;

		if (data.size() > 0) {
			best = data.get(0);
			for (Double value : data) {
				if (gamemode.equals("TIME_ATTACK")) {
					if (value < best) {
						best = value;
					}
				} else {
					if (value > best) {
						best = value;
					}
				}
			}
		}

		return String.valueOf(best);
	}

	public static Medallist getMedallist(MedalType medalType, String gamemode) {

		List<String> names = getAllTitles(CSVName.STATISTICS);
		List<Double> bestScores = new ArrayList<Double>();
		List<String> usernames = new ArrayList<String>();

		if (medalType.equals(MedalType.SILVER)) {
			names.remove(getMedallist(MedalType.GOLD, gamemode).getUsername());
		} else if (medalType.equals(MedalType.BRONZE)) {
			names.remove(getMedallist(MedalType.GOLD, gamemode).getUsername());
			names.remove(getMedallist(MedalType.SILVER, gamemode).getUsername());
		}

		for (String username : names) {
			List<Double> scores = getUserData(username, gamemode);
			if (scores.size() > 0) {
				String userBest = getBest(scores, gamemode);
				if (userBest.equals(null)) {
				} else {
					bestScores.add(Double.parseDouble(userBest));
					usernames.add(username);
				}
			}
		}

		
		String score = null;
		String username = null;
		
		if (bestScores.size() > 0) {
			score = getBest(bestScores, gamemode);
			username = usernames.get(bestScores.indexOf(Double.parseDouble(score)));
		}

		Medallist medallist = new Medallist(username, score);

		return medallist;
	}

}
