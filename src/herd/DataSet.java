package herd;
import java.io.*;
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



	public void serializeStatistic(Statistic statistic)	{
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
			i.printStackTrace();
		}
	}

	public void deserializeStatistic() {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));
			StateStatistic ss = (StateStatistic) in.readObject();
			System.out.println("State: " +ss.getState() + " Horses: "+ ss.getNumHorses()
					+ " Burros: " + ss.getNumBurros());
		}
		catch (Exception e) {
			System.out.println("No serialized values to deserialize.");
		}
	}
}