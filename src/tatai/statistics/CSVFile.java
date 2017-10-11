package tatai.statistics;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

	private static final String FILE_NAME = "resources/statistics.csv";
	private static final String TEMP_FILE_NAME = "resources/temporary.csv";

	CSVFile() {}

	private static final CSVFile INSTANCE = new CSVFile();

	public static CSVFile getInstance() {
		return INSTANCE;
	}

	public static void appendToCSV(String username, String gamemode, String scoreString) {

		String[] scoreArray = scoreString.split("/");
		double score = Double.parseDouble(scoreArray[0]);

		try {

			FileReader fr = new FileReader(FILE_NAME);
			BufferedReader br = new BufferedReader(fr);

			FileWriter fw = new FileWriter(TEMP_FILE_NAME);
			BufferedWriter bw = new BufferedWriter(fw);

			String line = br.readLine();
			Boolean found = false;

			while (line != null) {
				String[] lineArray = line.split(",");
				if (lineArray[0].equals(username)) {
					found = true;
					bw.write(line + "," + gamemode + "," + score);
					bw.newLine();
					line = br.readLine();
				} else {
					bw.write(line);
					bw.newLine();
					line = br.readLine();
				}
			}

			if (found == false) {
				bw.write(username + ",0,0,0," + gamemode + "," + score);
			}

			fw.close();
			fr.close();
			bw.close();
			br.close();

			// Delete the old version of the file
			File oldFile = new File(FILE_NAME);
			oldFile.delete();

			// Rename the temporary file to "statistics.csv"
			File newFile = new File(TEMP_FILE_NAME);
			newFile.renameTo(new File(FILE_NAME));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<String> getNames() {
		
		List<String> names = new ArrayList<String>();

		try {
			
			FileReader fr = new FileReader(FILE_NAME);
			BufferedReader br = new BufferedReader(fr);
			
			String line = br.readLine();
			
			while (line != null) {
				String[] lineArray = line.split(",");
				names.add(lineArray[0]);
				line = br.readLine();
			}
			
			br.close();
			fr.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return names;
	}

}
