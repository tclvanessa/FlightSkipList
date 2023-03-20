package flightList;

/**
 * The class that represents a node in a flight skip list. 
 * Contains the key of type FlightKey and the data of type FlightData. 
 * Also stores the following pointers to FlightNode(s): next, down, prev and up.
 */
public class FlightNode {

	private FlightKey key;
	private FlightData data;

	private FlightNode next;
	private FlightNode down;
	private FlightNode prev;
	private FlightNode up;

	/**
	 * Copy constructor for FlightNode
	 * @param node FlightNode
	 */
	public FlightNode(FlightNode node) {
		key = new FlightKey(node.getKey());
		data = new FlightData(node.getData());
	}

	/**
	 * FlightNode Constructor
	 * @param key flight key
	 * @param data fight daya
	 */
	public FlightNode(FlightKey key, FlightData data) {
		this.key = key;
		this.data = data;
	}

	public FlightKey getKey() {
		return key;
	}
	public FlightData getData() {
		return data;
	}

	public FlightNode getNext() {
		return this.next;
	}
	public FlightNode getPrev() {
		return this.prev;
	}
	public FlightNode getDown() {
		return this.down;
	}
	public FlightNode getUp() {
		return this.up;
	}

	public void setNext(FlightNode next) {
		this.next = next;
	}
	public void setPrev(FlightNode prev) {
		this.prev = prev;
	}
	public void setDown(FlightNode down){
		this.down = down;
	}
	public void setUp(FlightNode up){
		this.up = up;
	}
}
