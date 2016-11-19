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

		//--------------------------------------------------------------------------------------------------
		//----------------Initialize DataSet with no parameters. Populate with loadStatistics.--------------
		//----------------------------------------DISPLAY STATE DATA----------------------------------------

		DataSet data = new DataSet();
		try {
			loadStatistics(data, fileName, 3);
		}
		catch (StatisticDataNotFoundException e) {
			System.out.println(e.getMessage() );
		}
		//Display all state's horses & burros data
		displayStatistics(data);

		//--------------------------------------------------------------------------------------------------
		//--------------------Get Random State, serialize, deserialize.-------------------------------------
		//--------------------------------------------------------------------------------------------------
		Random random = new Random();
		ArrayList<Statistic> statistics = data.getStats();
		if (statistics.size() > 0) {
			int randomState = random.nextInt(statistics.size());
			StateStatistic state = (StateStatistic) statistics.get(randomState);
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
	//--------------------------------------------------------------------------------------------------
	//----------------------------------LOAD DATASET ARRAY OF STATISTICS--------------------------------
	//--------------------------------------------------------------------------------------------------
	private static void loadStatistics(DataSet data, String fileName, int numOfHeaderRows) throws StatisticDataNotFoundException {
		String line;
		String[] stateInformation;
		try {
			BufferedReader buff = new BufferedReader(new FileReader(fileName));
			for (int i = 0; i < numOfHeaderRows; i++) {
				buff.readLine();
			}
			while ((line = buff.readLine()) != null) {
				stateInformation = line.split(",");
				State state = State.valueOf(stateInformation[0]);
				long herdAcresBLM = Long.parseLong(stateInformation[1]);
				long herdAreaAcresOther = Long.parseLong(stateInformation[2]);
				long herdManagementAreaAcresBLM = Long.parseLong(stateInformation[3]);
				long herdManagementAreaAcresOther = Long.parseLong(stateInformation[4]);
				long numHorses = Long.parseLong(stateInformation[5]);
				long numBurros = Long.parseLong(stateInformation[6]);
				data.addStatistic(new StateStatistic(state, herdAcresBLM, herdAreaAcresOther,
						herdManagementAreaAcresBLM, herdManagementAreaAcresOther, numHorses, numBurros));
			}
			buff.close();
		}
		catch (IOException e) {
			throw new StatisticDataNotFoundException("File not found : " + fileName + "\nAttempted access at: " + LocalTime.now());
		}
	}
	//--------------------------------------------------------------------------------------------------
	//-------------------------------------DISPLAY STATISTICS FOR EACH STATE---------------------------
	//--------------------------------------------------------------------------------------------------
	private static void displayStatistics(DataSet data) {
		int burros=0;
		long horses=0;
		for (Statistic statistic : data.getStats()){
			horses += ((StateStatistic) statistic).getNumHorses();
			burros += ((StateStatistic) statistic).getNumBurros();
			System.out.println("State: " + ((StateStatistic) statistic).getState()
					+ " \tHorses: " +((StateStatistic) statistic).getNumHorses()
					+ " \tBurros: " + ((StateStatistic) statistic).getNumBurros());
		}
		if (data.getStats().size() > 0) {
			System.out.println("\nTotal number of horses: " + horses + "\nTotal number of burros: " + burros + "\n");
		}
	}
}