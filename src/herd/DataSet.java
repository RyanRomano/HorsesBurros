package herd;
import java.io.*;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * A dataset is a collection of statistics
 */
public class DataSet {
	final String FILE_NAME = "src/herd/serialize.ser";
	/** the collection of statistics **/
	private ArrayList<Statistic> stats = new ArrayList<>(20);

	/**
	 * the full-argument constructor
	 * @param stats the ArrayList of Statistics
	 */
	public DataSet(ArrayList<Statistic> stats) {
		this.stats = stats;
	}
		
	/**
	 * the no-argument constructor which initializes the stats collection
	 */
	public DataSet() {
		this.stats = new ArrayList<>();
	}

	/**
	 * addStatistic will add a stat to the stats collection
	 * @param stat the statistic to add
	 */
	public void addStatistic(Statistic stat) {
		this.stats.add(stat);
	}

	/**
	 * standard accessor method
	 * @return the stats
	 */
	public ArrayList<Statistic> getStats() {
		return stats;
	}

	/**
	 * standard mutator method
	 * @param stats the stats to set
	 */
	public void setStats(ArrayList<Statistic> stats) {
		this.stats = stats;
	}

	public void serializeStatistic(Statistic statistic)	throws StatisticDataNotFoundException{
		try {
			FileOutputStream fileOut = new FileOutputStream(FILE_NAME);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(statistic);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data for state %s is saved in %s\n",
					((StateStatistic) statistic).getState(), FILE_NAME);
		}
		catch (IOException i) {
			throw new StatisticDataNotFoundException("Serialize file not found: " + FILE_NAME + " " + LocalTime.now());
		}
	}

	public void deserializeStatistic() throws StatisticDataNotFoundException{
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));
			StateStatistic stat = (StateStatistic) in.readObject();
			System.out.println("State: " +stat.getState() + " \tHorses: "+ stat.getNumHorses()
					+ " \tBurros: " + stat.getNumBurros());
		}
		catch (Exception e) {
			throw new StatisticDataNotFoundException("Nothing to deserialize.");
		}
	}
}