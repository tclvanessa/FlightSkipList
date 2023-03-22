package flightList;

/**
 * Represents the key in the FlightNode. Stores origin, destination, date and
 * time. Needs to implement Comparable<FlightKey>.
 * Fill in the code in the compareTo method.
 */
public class FlightKey implements Comparable<FlightKey> {
	// Each key is a tuple: origin, destination, date, time
	private String origin;
	private String dest;
	private String date;
	private String time;

	/**
	 *  FlightKey constructor
	 * @param or origin
	 * @param dest destination
	 * @param date date
	 * @param time time
	 */
	public FlightKey(String or, String dest, String date, String time) {
		this.origin = or;
		this.dest = dest;
		this.date = date;
		this.time=  time;
	}

	/**
	 * FlightKey - copy constructor
	 * @param other the other FlightKey
	 */
	public FlightKey(FlightKey other) {
		origin = other.getOrigin();
		dest = other.getDest();
		date = other.getDate();
		time = other.getTime();
	}

	// Getters for origin, destination, date and time
	public String getOrigin() {
		return origin;
	}
	public String getDest() {
		return dest;
	}
	public String getDate() {
		//System.out.println(date);
		return date;
	}
	public String getTime() {
		//System.out.println(time);
		return time;
	}

	/**
	 * Compares a given flight key with the one given as a parameter.
	 * @param other
	 * @return -1, if this key is < other, > -1 if the opposite, and 0 if equal.  </>
	 */
	public int compareTo(FlightKey other) {
		// first, compare origins alphabetically (hint: use compareTo method in class String),
		// if origins are equal, compare destinations
		// if origins and destinations are equal, compare the dates
		// if dates are equal, compare the time

		int info1 = this.origin.compareTo(other.origin);
		int info2 = this.dest.compareTo(other.dest);
		int info3 = this.date.compareTo(other.date);

		if (info1 == 0 && info2 == 0 && info3 == 0) {
			return this.time.compareTo(other.time);
		}

		if (info1 == 0 && info2 == 0) {
			return info3;
		}

		if (info1 == 0) {
			return info2;
		}

		return info1;
	}

	/**
	 * Helper Method for Successor Method
	 * Compares a given flight key with the one given as a parameter.
	 * @param other
	 * @return true or false
	 */
	public boolean matchSuccessor(FlightKey other) {
		int info1 = this.origin.compareTo(other.origin);
		int info2 = this.dest.compareTo(other.dest);
		int info3 = this.date.compareTo(other.date);
		int info4 = this.getTime().compareTo(other.getTime());

		if (info1 == 0 && info2 == 0 && info3 == 0 && info4 >= 0) {
			return true;
		}
		return false;
	}

	/**
	 * Helper Method for Predecessor Method
	 * Compares a given flight key with the one given as a parameter.
	 * @param other
	 * @return true or false
	 */
	public boolean matchPredecessor(FlightKey other) {
		int info1 = this.origin.compareTo(other.origin);
		int info2 = this.dest.compareTo(other.dest);
		int info3 = this.date.compareTo(other.date);
		int info4 = this.getTime().compareTo(other.getTime());

		if (info1 == 0 && info2 == 0 && info3 == 0 && info4 <= 0) {
			return true;
		}
		return false;
	}

	/**
	 * Returns a string representation of the key
	 * @return String
	 */
	public String toString() {
		if (getOrigin() != null)
			return "(" + getOrigin() + "," + getDest() + "," + getDate() + "," + getTime() + ") ";
		return "";
	}

	// Feel free to add helper methods if needed
}


