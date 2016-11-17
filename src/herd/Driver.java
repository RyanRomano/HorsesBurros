package herd;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Driver {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		final String fileName = "herdManagement.csv";
		ArrayList<Statistic> stat = new ArrayList<>();
		DataSet data = new DataSet(stat);
		loadStatistics(data, fileName, 3);
		displayStatistics(data);
	}

	
	public static ArrayList<Statistic> loadStatistics(DataSet data, String fileName, int numOfHeaderRows) {
		ArrayList<Statistic> stats = new ArrayList<>();
		try {
			BufferedReader buff = new BufferedReader(
					new FileReader("/home/ryan/Desktop/OOPDA/HorsesBurros/src/herd/herdManagement.csv"));

			//--------------------------READ LINES 1,2,3, DO NOTHING-------------------------------
			for (int i = 0; i < numOfHeaderRows; i++){
				buff.readLine();
			}
			do {
				String stateInfo = buff.readLine();
				String[] stateInfoArray = stateInfo.split(",");
				for (int i=0; i < stateInfoArray.length; i++){
					System.out.println(stateInfoArray[i]);
				}


//				State state = State.valueOf(stateInfoArray[0]);
//
//				long herdAcresBLM = Long.parseLong(stateInfoArray[1]);
//				long herdAreaAcresOther = Long.parseLong(stateInfoArray[2]);
//				long herdManagementAreaAcresBLM = Long.parseLong(stateInfoArray[3]);
//				long herdManagementAreaAcresOther = Long.parseLong(stateInfoArray[4]);
//				long numHorses = Long.parseLong(stateInfoArray[5]);
//				long numBurros = Long.parseLong(stateInfoArray[6]);
//
//				stats.add(new StateStatistic(state, herdAcresBLM, herdAreaAcresOther,
//						herdManagementAreaAcresBLM, herdManagementAreaAcresOther, numHorses, numBurros));

			}
			while(buff.readLine() != null);
			buff.close();
		}
		catch (IOException e) {
			System.out.println("Something went wrong.\n" + e.getStackTrace());
		}
		return stats;
	}
	
	public static void displayStatistics(DataSet data) {
		for (Statistic statistic : data.getStats()){
			System.out.println("State: " + ((StateStatistic) statistic).getState()
					+ "Burros: " + ((StateStatistic) statistic).getNumBurros()
					+ "Horses: "+((StateStatistic) statistic).getNumHorses());
		}
	}
}
