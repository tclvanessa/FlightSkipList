package flightList;

import org.junit.Assert;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** The class that represents the flight database using a skip list */
public class FlightList {
	final static String min = "AAA"; // "-infinity" key
	final static String max = "zzz"; // "+infinity" key
	private FlightNode head; // reference to the top left node
	private FlightNode tail; // reference to the top right node
	private int height; // height of the skip list
	private static Random rand = new Random();

	/**
	 * Constructor.
	 * Reads flight data from the file and inserts it into this skip list.
	 * @param filename the name of the file
	 */
	public FlightList(String filename) {
		// FILL IN CODE
		// Read the file; for each line, create a flight key and a flight data
		// and call the insert method to insert them into the skip list.

		FlightKey negInf = new FlightKey(min, min, min, min);
		FlightKey posInf = new FlightKey(max, max, max, max);
		head = new FlightNode(negInf, null);
		tail = new FlightNode(posInf, null);
		head.setNext(tail);
		tail.setPrev(head);
		height = 1;

		try (FileReader f = new FileReader(filename)) {
			BufferedReader br = new BufferedReader(f);
			String line;

			while ((line = br.readLine()) != null) {
				String[] arr = line.split(" ");
				if (arr.length != 6) {
					continue;
				}
				String origin = arr[0];
				String dest = arr[1];
				String date = arr[2];
				String time = arr[3];
				String flightNumber = arr[4];
				double price = Double.parseDouble(arr[5]);

				FlightKey key = new FlightKey(origin, dest, date, time);
				FlightData data = new FlightData(flightNumber, price);

				insert(key, data);
			}
		} catch (IOException e) {
			System.out.println("No file found.");
		}
	}

	/**
	 * Returns true if the node with the given key exists in the skip list,
	 * false otherwise. This method needs to be efficient.
	 *
	 * @param key flight key
	 * @return true if the key is in the skip list, false otherwise
	 */
	public boolean find(FlightKey key) {
		// FILL IN CODE
		FlightNode curr = head;

		while (curr != null && curr.getNext() != null && curr.getKey().compareTo(tail.getKey()) != 0) {
			FlightKey nextKey = curr.getNext().getKey();

			if (nextKey.compareTo(key) == 0) {
				return true;
			}

			if (curr.getNext() == tail || nextKey.compareTo(key) > 0) {
				curr = curr.getDown();
			} else if (nextKey.compareTo(key) < 0) {
				curr = curr.getNext();
			}
		}
		return false;
	}

	/**
	 * Insert a (key, value) pair to the skip list. Returns true if it was able
	 * to insert. The method has been provided to you, no need to modify.
	 *
	 * @param key flight key
	 * @param data associated flight data
	 * @return true if insertion was successful
	 */
	public boolean insert(FlightKey key, FlightData data) {
		if (find(key))
			return false;

		// how many levels for this node
		int levels = 1;
		while(rand.nextInt(2) == 1) { // toss a coin
			levels++;
		}

		// Create a tower of new nodes from top to bottom, setting up&down pointers
		FlightNode newNode = new FlightNode(key, data);
		FlightNode tmp = newNode;
		for(int i=0; i<levels; i++){
			FlightNode levelNewNode = new FlightNode(key, data);
			tmp.setDown(levelNewNode);
			levelNewNode.setUp(tmp);
			tmp = tmp.getDown();
		}

		// add (height - levels) empty levels that have just min and max nodes
		// add them on top of the current levels
		while (levels > this.height) {
			FlightKey minKey = new FlightKey(min, min, min, min);
			FlightKey maxKey = new FlightKey(max, max, max, max);
			FlightNode newHead = new FlightNode(minKey, null);
			FlightNode newTail = new FlightNode(maxKey, null);
			newHead.setDown(head);
			newHead.setNext(newTail);
			newTail.setDown(tail);
			head.setUp(newHead);
			newTail.setPrev(newHead);
			tail.setUp(newTail);

			head = newHead;
			tail = newTail;
			height++;
		}

		FlightNode cur = head;
		int heightLeft = height;

		// Insert each copy of the new node into the skip list
		boolean success = false;
		while (cur != null && cur.getNext() != null) {
			if (key.compareTo(cur.getNext().getKey()) == 0) {
				return false;
			}
			else if (key.compareTo(cur.getNext().getKey()) < 0 && levels >= heightLeft) {
				FlightNode tmp2 = cur.getNext();
				cur.setNext(newNode);
				newNode.setNext(tmp2);
				newNode.setPrev(cur);
				tmp2.setPrev(newNode);
				cur = cur.getDown();
				newNode = newNode.getDown();
				heightLeft--;
				success = true;
			}
			else if (key.compareTo(cur.getNext().getKey()) < 0){
				// one of the top levels where we don't need to insert, because height >#levels
				heightLeft--;
				cur = cur.getDown();
			}
			else if (key.compareTo(cur.getNext().getKey()) > 0){
				cur = cur.getNext();
			}
		}
		return success;
	}

	/**
	 * Returns the list of nodes that are successors of a given key.
	 * These are flights that have the same origin and destination,
	 * but have a later time than the given key.
	 * Refer to the project pdf for a detailed description of the method.
	 *
	 * @param key flight key
	 * @return successors of the given key
	 */
	public List<FlightNode> successors(FlightKey key) {
		List<FlightNode> arr = new ArrayList<>();
		FlightNode curr = head;
		int height = this.height;

		while (curr != null && curr.getNext() != null && curr.getKey().compareTo(tail.getKey()) != 0) {
			FlightKey nextKey = curr.getNext().getKey();

			if (nextKey.compareTo(key) < 0) {
				curr = curr.getNext();
				continue;
			}

			if (nextKey.compareTo(key) > 0 && height != 1) {
				curr = curr.getDown();
				height--;
				continue;
			}

			if (nextKey.compareTo(key) == 0) {
				curr = curr.getNext();
				while (height != 1) {
					curr = curr.getDown();
					height--;
				}
				break;
			}

			if (nextKey.compareTo(key) > 0 && height == 1) {
				break;
			}
		}

		while (curr.getNext().getKey().matchSuccessor(key) == true && curr.getNext() != null) {
			arr.add(curr.getNext());
			curr = curr.getNext();
		}

		return arr;
	}

	/**
	 * Returns the list of nodes that are predecessors of a given key
	 * (that corresponds to flights that have the same origin and destination but
	 *  are earlier than the given flight).
	 *  Refer to the project pdf for a detailed description of the method.
	 *
	 * @param key flight key
	 * @return predecessors of the given key
	 */
	public List<FlightNode> predecessors(FlightKey key) {
		List<FlightNode> arr = new ArrayList<>();
		ArrayList<FlightNode> temp = new ArrayList<FlightNode>();
		FlightNode curr = head;
		int height = this.height;

		while (curr != null && curr.getNext() != null && curr.getKey().compareTo(tail.getKey()) != 0) {
			FlightKey nextKey = curr.getNext().getKey();

			if (nextKey.compareTo(key) < 0) {
				curr = curr.getNext();
				continue;
			}

			if (nextKey.compareTo(key) > 0 && height != 1) {
				curr = curr.getDown();
				height--;
				continue;
			}

			if (nextKey.compareTo(key) == 0) {
				curr = curr.getNext();
				while (height != 1) {
					curr = curr.getDown();
					height--;
				}
				break;
			}

			if (nextKey.compareTo(key) > 0 && height == 1) {
				curr = curr.getNext();
				break;
			}
		}

		while (curr.getPrev().getKey().matchPredecessor(key) == true && curr.getPrev() != null) {
			temp.add(curr.getPrev());
			curr = curr.getPrev();
		}

		for (int i = temp.size() - 1; i >= 0; i--) {
			arr.add(temp.get(i));
		}

		return arr;
	}

	/**
	 * Returns the string representing the SkipList (contains the elements on all levels starting at the
	 * top. Each level should be on a separate line, for instance:
	 * (SFO, PVD, 03/14, 09:15)
	 * (SFO, JFK, 03/15, 06:30), (SFO, PVD, 03/14, 09:15)
	 * (SFO, JFK, 03/15, 06:30),   (SFO, JFK, 03/15, 7:15), (SFO, JFK, 03/20, 5:00), (SFO, PVD, 03/14, 09:15)
	 */
	public String toString() {
		// FILL IN CODE
		StringBuilder sb = new StringBuilder();
		FlightNode node = head;

		while (node.getDown() != null) {
			sb.append(node.getKey());
			if (node.getNext() == null) {
				sb.append(System.lineSeparator());
				node = head.getDown();
			}
			sb.append(", ");
			node = node.getNext();
		}

		return sb.toString();
	}

	/**
	 * Outputs the SkipList to a file
	 * (prints the elements on all levels starting at the top.
	 * Each level should be printed on a separate line.
	 * @param filename the name of the file
	 */
	public void print(String filename) {
		// FILL IN CODE
		try (PrintWriter pw = new PrintWriter(filename)) {
			FlightNode curr = head;
			int levels = height;
			while (levels > 0) {
				FlightNode node = curr;

				while (curr != null && curr.getNext() != null & curr.getNext().getKey().compareTo(tail.getKey()) != 0) {
					pw.print(curr.getNext().getKey() + ", ");
					curr = curr.getNext();
				}
				pw.println();
				curr = node;
				curr = curr.getDown();
				levels--;
			}
		} catch (IOException e) {
			System.out.println("No file found.");
		}
	}

	/**
	 * Returns a list of nodes that have the same origin and destination cities
	 * and the same date as the key, with departure times within the given time
	 * frame of the departure time of the key.
	 *
	 * @param key flight key
	 * @param timeFrame interval of time
	 * @return list of flight nodes that have the same origin, destination and date
	 * as the key, and whose departure time is within a given timeframe
	 */
	public List<FlightNode> findFlights(FlightKey key, int timeFrame) {
		List<FlightNode> resFlights = new ArrayList<>();
		List<FlightNode> pre = predecessors(key);
		List<FlightNode> suc = successors(key);
		int hour = hour(key.getTime());
		int lowerTimeFrame = hour - timeFrame;
		int upperTimeFrame = hour + timeFrame;
		int flightHour;

		for (int i = 0; i < pre.size(); i++) {
			FlightNode currP = pre.get(i);
			flightHour = hour(currP.getKey().getTime());
			if (flightHour >= lowerTimeFrame) {
				resFlights.add(currP);
			}
		}

		for (int j = 0; j < suc.size(); j++) {
			FlightNode currS = suc.get(j);
			flightHour = hour(currS.getKey().getTime());
			if (flightHour <= upperTimeFrame) {
				resFlights.add(currS);
			}
		}

		return resFlights;
	}

	private static int hour(String time) {
		String[] h = time.split(":");
		int hour = Integer.parseInt(h[0]);

		return hour;
	}
}