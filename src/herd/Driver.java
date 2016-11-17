package herd;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Driver {

	public static void main(String[] args) {
		String fileName = "/home/ryan/Desktop/OOPDA/HorsesBurros/src/herd/herdManagement.csv";
		DataSet data = new DataSet();
		try {
			loadStatistics(data, fileName, 3);
		}
		catch (StatisticDataNotFoundException e) {
			System.out.println(e.getMessage());
		}
		displayStatistics(data);
		ArrayList<Statistic> stats = data.getStats();

		Random random = new Random();
		int statsize;
		statsize = stats.size();
		if (statsize>0) {
			int randomState = random.nextInt(stats.size() - 1);
			StateStatistic state = (StateStatistic) stats.get(randomState);
			System.out.println("~~~~~~~~~~~~~~~~~~~~Serialize~~~~~~~~~~~~~~~~~");
			data.serializeStatistic(state);
			System.out.println("~~~~~~~~~~~~~~~~~~~~Deserialize~~~~~~~~~~~~~~~");
			data.deserializeStatistic();
		}
	}

	private static void loadStatistics(DataSet data, String fileName, int numOfHeaderRows) throws StatisticDataNotFoundException {
		ArrayList<Statistic> stats = new ArrayList<>();
		String line;
		String[] stateInformaton;
		try {
			BufferedReader buff = new BufferedReader(new FileReader(fileName));
			for (int i = 0; i < numOfHeaderRows; i++) {
				buff.readLine();
			}
			while ((line = buff.readLine()) != null) {
				stateInformaton = line.split(",");
				State state = State.valueOf(stateInformaton[0]);
				long herdAcresBLM = Long.parseLong(stateInformaton[1]);
				long herdAreaAcresOther = Long.parseLong(stateInformaton[2]);
				long herdManagementAreaAcresBLM = Long.parseLong(stateInformaton[3]);
				long herdManagementAreaAcresOther = Long.parseLong(stateInformaton[4]);
				long numHorses = Long.parseLong(stateInformaton[5]);
				long numBurros = Long.parseLong(stateInformaton[6]);
				stats.add(new StateStatistic(state, herdAcresBLM, herdAreaAcresOther,
						herdManagementAreaAcresBLM, herdManagementAreaAcresOther, numHorses, numBurros));
			}
			buff.close();
		}
		catch (IOException e) {
			throw new StatisticDataNotFoundException("File not found.");
		}
		data.setStats(stats);
	}
	
	private static void displayStatistics(DataSet data) {
		for (Statistic statistic : data.getStats()){
			System.out.println("State: " + ((StateStatistic) statistic).getState()
					+ " Horses: "+((StateStatistic) statistic).getNumHorses()
					+ " Burros: " + ((StateStatistic) statistic).getNumBurros());
		}
	}
}