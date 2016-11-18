package herd;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class Driver {
	public static void main(String[] args) {
		String fileName = "src/herd/herdManagement.csv";

		//Initialize DataSet with no parameters. Populate with loadStatistics.
		DataSet data = new DataSet();
		try {
			loadStatistics(data, fileName, 3);
		}
		catch (StatisticDataNotFoundException e) {
			System.out.println(e.getMessage() + fileName + "\nAttempted access at: " + LocalTime.now());
		}
		//Display all state's horses & burros data
		displayStatistics(data);

		//Get Random State, serialize, deserialize.
		Random random = new Random();
		if (data.getStats().size() > 0) {
			int randomState = random.nextInt(data.getStats().size() - 1);
			StateStatistic state = (StateStatistic) data.getStats().get(randomState);
			System.out.println("~~~~~~~~~~~~~~~~~~~~Serialize~~~~~~~~~~~~~~~~~");
			try {
				data.serializeStatistic(state);
			}
			catch (StatisticDataNotFoundException e) {
				System.out.println(e.getMessage());
			}
			System.out.println("\n~~~~~~~~~~~~~~~~~~~~Deserialize~~~~~~~~~~~~~~~");
			try {
				data.deserializeStatistic();
			}
			catch (StatisticDataNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	private static void loadStatistics(DataSet data, String fileName, int numOfHeaderRows) throws StatisticDataNotFoundException {
		//Line is from each line of csv file
		//stateInformation to split each Line into component data
		ArrayList<Statistic> stats = new ArrayList<>();
		String line;
		String[] stateInformation;

		//Open Buffered reader, read first 3 lines.
		try {
			BufferedReader buff = new BufferedReader(new FileReader(fileName));
			for (int i = 0; i < numOfHeaderRows; i++) {
				buff.readLine();
			}

			//Until last line, read each line. Split line at commas, Parse state, and parse as long.
			//Add new Statistic to method ArrayList field stats.
			while ((line = buff.readLine()) != null) {
				stateInformation = line.split(",");
				State state = State.valueOf(stateInformation[0]);
				long herdAcresBLM = Long.parseLong(stateInformation[1]);
				long herdAreaAcresOther = Long.parseLong(stateInformation[2]);
				long herdManagementAreaAcresBLM = Long.parseLong(stateInformation[3]);
				long herdManagementAreaAcresOther = Long.parseLong(stateInformation[4]);
				long numHorses = Long.parseLong(stateInformation[5]);
				long numBurros = Long.parseLong(stateInformation[6]);
				stats.add(new StateStatistic(state, herdAcresBLM, herdAreaAcresOther,
						herdManagementAreaAcresBLM, herdManagementAreaAcresOther, numHorses, numBurros));
			}
			//Close stream
			buff.close();
		}
		catch (IOException e) {
			throw new StatisticDataNotFoundException("File not found : ");
		}
		//Set empty DataSet list of Statistics (cast as StateStatistics)
		data.setStats(stats);
	}
	
	private static void displayStatistics(DataSet data) {
		int burros=0;
		long horses=0;
		for (Statistic statistic : data.getStats()){
			horses += ((StateStatistic) statistic).getNumHorses();
			burros += ((StateStatistic) statistic).getNumBurros();
			System.out.println("State: " + ((StateStatistic) statistic).getState()
					+ " Horses: "+((StateStatistic) statistic).getNumHorses()
					+ " Burros: " + ((StateStatistic) statistic).getNumBurros());

		}
		System.out.println("\nTotal number of horses: " + horses + "\nTotal number of burros: " + burros + "\n");
	}
}