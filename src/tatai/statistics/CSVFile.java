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

	public static void appendToCSV(String username, String gamemode, String scoreString) {

		String[] scoreArray = scoreString.split("/");
		double score = Double.parseDouble(scoreArray[0]);

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
				bw.write(username + ",0,0,0," + gamemode + "," + score);
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

		setAverage(username, gamemode);

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

	public static void setAverage(String username, String gamemode) {

		double average = calculateAverage(username, gamemode);

		try {

			BufferedReader br = new BufferedReader(new FileReader(STATS_FILE_NAME));
			BufferedWriter bw = new BufferedWriter(new FileWriter(TEMP_FILE_NAME));

			String line = br.readLine();
			String newLine = "";
			int index = 1;

			while (line != null) {
				String[] lineArray = line.split(",");

				if (lineArray[0].equals(username)) {

					if (gamemode.equals("TEN_QUESTIONS")) {
						index = 1;
					} else if (gamemode.equals("TEN_QUESTIONS_TIMED")){
						index = 2;
					} else if (gamemode.equals("ONE_MINUTE_BLITZ")){
						index = 3;
					}

					for (int i = 0; i < index; i++) {
						newLine += lineArray[i] + ",";
					}

					newLine += String.valueOf(average) + ",";

					for (int i = index + 1; i < lineArray.length; i++) {
						newLine += lineArray[i] + ",";
					}

					bw.write(newLine);
					bw.newLine();
					line = br.readLine();

				} else {

					bw.write(line);
					bw.newLine();
					line = br.readLine();
				}

			}

			bw.close(); br.close();

			// Delete the old version of the file
			File oldFile = new File(STATS_FILE_NAME);
			oldFile.delete();

			// Rename the temporary file to "statistics.csv"
			File newFile = new File(TEMP_FILE_NAME);
			newFile.renameTo(new File(STATS_FILE_NAME));

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static String getAverage(String username, String gamemode) {

		setAverage(username, gamemode);
		String average = "not set";
		
		try {

			BufferedReader br = new BufferedReader(new FileReader(STATS_FILE_NAME));

			String line = br.readLine();

			while (line != null) {
				String[] lineArray = line.split(",");
				if (lineArray[0].equals(username)) {
					average = lineArray[1];
				}
				line = br.readLine();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return average;

	}


	private static double calculateAverage(String username, String gamemode) {

		double scores = 0;
		int scoreCount = 0;
		double average = 0;

		try {

			BufferedReader br = new BufferedReader(new FileReader(STATS_FILE_NAME));
			String line = br.readLine();

			while (line != null) {
				String[] lineArray = line.split(",");
				if (lineArray[0].equals(username)) {
					for (int i = 0; i < lineArray.length - 1; i++) {
						if (lineArray[i].equals(gamemode)) {
							scoreCount++;
							scores += Double.parseDouble(lineArray[i + 1]);
						}
					}
				}
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

		average = scores / scoreCount;

		return average;
	}

	public static Series<String, Double> getData(String username, String gamemode) {

		XYChart.Series data = new XYChart.Series<>();
		int gameNumber = 1;

		try {
			BufferedReader br = new BufferedReader(new FileReader(STATS_FILE_NAME));
			String line = br.readLine();

			while (line != null) {
				String[] lineArray = line.split(",");
				if (lineArray[0].equals(username)) {
					for (int i = 0; i < lineArray.length - 1; i++) {
						if (lineArray[i].equals(gamemode)) {
							data.getData().add(new XYChart.Data(gameNumber, Double.parseDouble(lineArray[i+1])));
							gameNumber++;
						}
					}
				}
				line = br.readLine();
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return data;
	}

}
