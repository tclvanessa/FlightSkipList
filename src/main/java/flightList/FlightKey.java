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
		if (this.getOrigin().compareTo(other.getOrigin()) < 0) {
			return -1;
		} else if (this.getOrigin().compareTo(other.getOrigin()) > 0) {
			return this.getOrigin().compareTo(other.getOrigin());
		} else {
			if (this.getDest().compareTo(other.getDest()) < 0) {
				return -1;
			} else if (this.getDest().compareTo(other.getDest()) > 0) {
				return this.getDest().compareTo(other.getDest());
			} else {
				if (this.getDate().compareTo(other.getDate()) < 0) {
					return -1;
				} else if (this.getDate().compareTo(other.getDate()) > 0) {
					return this.getDate().compareTo(other.getDate());
				} else {
					if (this.getTime().compareTo(other.getTime()) < 0) {
						return -1;
					} else if (this.getTime().compareTo(other.getTime()) > 0) {
						return this.getTime().compareTo(other.getTime());
					} else {
						return 0;
					}
				}
			}
		}
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


