package tatai.statistics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Series;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Singleton class that encapsulates information about the statistics.csv file 
 * containing all game statistics.
 */
public class CSVFile {

	private static final String STATS_FILE_NAME = "resources/statistics.csv";
	private static final String TEMP_FILE_NAME = "resources/temporary.csv";

	public static void createUser(String username) {

		try {

			BufferedReader br = new BufferedReader(new FileReader(STATS_FILE_NAME));
			BufferedWriter bw = new BufferedWriter(new FileWriter(TEMP_FILE_NAME));

			String line = br.readLine();

			while (line != null) {
				bw.write(line);
				bw.newLine();
				line = br.readLine();
			}

			bw.write(username + ",");

			br.close(); bw.close();

			// Delete the old version of the file
			File oldFile = new File(STATS_FILE_NAME);
			oldFile.delete();

			// Rename the temporary file to "statistics.csv"
			File newFile = new File(TEMP_FILE_NAME);
			newFile.renameTo(new File(STATS_FILE_NAME));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void appendToCSV(String username, String gamemode, String score) {

		try {

			// Read from existing data file
			BufferedReader br = new BufferedReader(new FileReader(STATS_FILE_NAME));

			// Write to a temporary file for storing updated data
			BufferedWriter bw = new BufferedWriter(new FileWriter(TEMP_FILE_NAME));

			// Read the first line of the file
			String line = br.readLine();
			Boolean found = false;

			while (line != null) {
				String[] lineArray = line.split(",");

				// Append the new score to the user's line of data
				if (lineArray[0].equals(username)) {
					found = true;
					bw.write(line + "," + gamemode + "," + score);
					bw.newLine();
					line = br.readLine();

					// Leave all other lines unchanged
				} else {
					bw.write(line);
					bw.newLine();
					line = br.readLine();
				}
			}

			// If the user is new, create a new line of data
			if (found == false) {
				bw.write(username + "," + gamemode + "," + score);
			}

			bw.close(); br.close();

			// Delete the old version of the file
			File oldFile = new File(STATS_FILE_NAME);
			oldFile.delete();

			// Rename the temporary file to "statistics.csv"
			File newFile = new File(TEMP_FILE_NAME);
			newFile.renameTo(new File(STATS_FILE_NAME));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<String> getNames() {

		List<String> names = new ArrayList<String>();

		try {

			BufferedReader br = new BufferedReader(new FileReader(STATS_FILE_NAME));

			String line = br.readLine();

			while (line != null) {
				String[] lineArray = line.split(",");
				names.add(lineArray[0]);
				line = br.readLine();
			}

			br.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return names;
	}

	public static String getAverage(String username, String gamemode) {

		double scores = 0;
		int scoreCount = 0;
		double average = 0;

		String[] userData = getUserData(username);

		for (int i = 0; i < userData.length - 1; i++) {
			if (userData[i].equals(gamemode)) {
				scoreCount++;
				scores += Double.parseDouble(userData[i + 1]);
			}
		}

		average = Math.round((scores * 10)/ scoreCount);
		average = average / 10;

		return String.valueOf(average);
	}

	public static String getUserBest(String username, String gamemode) {

		String[] userData = getUserData(username);
		double best = 0;

		if (gamemode.equals("TIME_ATTACK")) {
			if (userData.length > 1) {
				best = Double.parseDouble(userData[userData.length - 1]);
				for (int i = 0; i < userData.length - 1; i++) {
					if (userData[i].equals(gamemode)) {
						double score = Double.parseDouble(userData[i+1]);
						if (score < best) {
							best = score;
						}
					}
				}
			}

		} else {
			for (int i = 0; i < userData.length - 1; i++) {
				if (userData[i].equals(gamemode)) {
					double score = Double.parseDouble(userData[i+1]);
					if (score > best) {
						best = score;
					}
				}
			}
		}

		return String.valueOf(best);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static Series<String, Double> getData(String username, String gamemode) {

		XYChart.Series data = new XYChart.Series<>();
		int gameNumber = 1;
		String[] userData = getUserData(username);

		for (int i = 0; i < userData.length - 1; i++) {
			if (userData[i].equals(gamemode)) {
				data.getData().add(new XYChart.Data(gameNumber, Double.parseDouble(userData[i+1])));
				gameNumber++;
			}
		}

		return data;
	}

	private static String[] getUserData(String username) {

		String[] userData = null;
		String line;

		try {
			BufferedReader br = new BufferedReader(new FileReader(STATS_FILE_NAME));
			line = br.readLine();
			while (line != null) {
				String[] lineArray = line.split(",");
				if (lineArray[0].equals(username)) {
					userData = lineArray;
				}
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return userData;
	}
	
	public static Medallist getGoldMedallist(String gamemode) {

		double best = 0.0;
		String username = null;
		
		for (String name : getNames()) {
			double score = Double.parseDouble(getUserBest(name, gamemode));

			if (score > best) {
				best = score;
				username = name;
			}
		}
		
		Medallist medallist = new Medallist(username, String.valueOf(best));
		return medallist;
	}
	
	public static Medallist getSilverMedallist(String gamemode) {

		List<String> names = getNames();
		names.remove(getGoldMedallist(gamemode).getUsername());
		
		double best = 0.0;
		String username = null;
		
		for (String name : names) {
			double score = Double.parseDouble(getUserBest(name, gamemode));
			if (score > best) {
				best = score;
				username = name;
			}
		}
		
		Medallist medallist = new Medallist(username, String.valueOf(best));
		return medallist;
	}
	
	public static Medallist getBronzeMedallist(String gamemode) {
		
		List<String> names = getNames();
		names.remove(getGoldMedallist(gamemode).getUsername());
		names.remove(getSilverMedallist(gamemode).getUsername());
		
		double best = 0.0;
		String username = null;
		
		for (String name : names) {
			double score = Double.parseDouble(getUserBest(name, gamemode));
			if (score > best) {
				best = score;
				username = name;
			}
		}
		
		Medallist medallist = new Medallist(username, String.valueOf(best));
		return medallist;
	}

}
