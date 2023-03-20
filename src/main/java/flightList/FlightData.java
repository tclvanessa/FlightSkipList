package flightList;

/**
 * Represents data in the FlightNode. Contains the flight number and the price.
 */
public class FlightData {
	private String flightNumber;
	private double price;

	public FlightData(FlightData data) {
		if (data == null) {
			return;
		}
		this.flightNumber = data.getFlightNumber();
		this.price = data.getPrice();
	}

	/**
	 * Constructor for FlightData
	 * @param fnum flight number
	 * @param price price of the flight
	 */
	public FlightData(String fnum, double price) {
		this.flightNumber = fnum;
		this.price = price;
	}

	/**
	 * Returns the number of the flight
	 * @return flight number
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/**
	 * Returns the price of the flight
	 * @return price
	 */
	public double getPrice() {
		return price;
	}
}

