import flightList.FlightData;
import flightList.FlightKey;
import flightList.FlightList;
import flightList.FlightNode;
import org.junit.Assert;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Test file for Project 2 */
public class Project2Test {

        public static String filename = "flights";

        @Test
        /** Load flights from the file  and insert to the FlightList.
         * Then run the find method for each flight key to make sure it is in the list.
         */
        public void testInsertAndFind() {
            boolean passed = true;
            FlightList list = new FlightList(filename);
            BufferedReader br = null;
            try  {
                br = new BufferedReader(new FileReader(filename));
                String s;
                while ((s = br.readLine()) != null) {
                    String[] arr = s.split(" ");
                    if (arr.length != 6)
                        throw new IndexOutOfBoundsException();
                    FlightKey key = new FlightKey(arr[0], arr[1], arr[2], arr[3]);
                    // make sure that a flight with this key is in the skip list:
                    if (!list.find(key)) {
                        Assert.fail("Failed to find the following flight: " + s);
                    }
                }
            }
            catch (IOException e) {
                Assert.fail("IOException occured while reading from the file: " + e);
            }

        }

        @Test
        /** Tests whether findFligths method returns the correct result for a few selected flights
         *
         */
        public void testFindFlights() {
            List<FlightKey> flightsToTest = new ArrayList<FlightKey>();
            int[] timeDifference = new int[5];
            List<FlightNode[]> expectedResults = new ArrayList<FlightNode[]>();
            List<FlightKey> flightsToTestPredecessors = new ArrayList<FlightKey>();
            List<FlightKey> flightsToTestSuccessors = new ArrayList<FlightKey>();
            List<FlightNode[]> expectedPredecessors = new ArrayList<>();
            List<FlightNode[]> expectedSuccessors = new ArrayList<>();

            // Call helper method that prepares some test data
            prepareTestData(flightsToTest, timeDifference,expectedResults,
                    flightsToTestPredecessors, flightsToTestSuccessors,
                    expectedPredecessors, expectedSuccessors);

            FlightList list = new FlightList(filename);
            for (int i =0 ; i < flightsToTest.size(); i++) {
                FlightKey key = flightsToTest.get(i);
                List<FlightNode> results = list.findFlights(key, timeDifference[i]);

                FlightNode[] expectedResult = expectedResults.get(i);
                if (expectedResult.length != results.size()) {
                    System.out.println("In testFindFlights: the number of flights returned for " + key + " is not what was expected");
                    System.out.println("Expected size versus actual size: " + expectedResult.length + " vs " + results.size());
                    System.out.println("The expected output of findFlights method on this flight  with " + " time difference of " + timeDifference[i] + " hours is: ");
                    for (int l=0; l < expectedResult.length; l++)
                        System.out.println(expectedResult[l].getKey() + " ");
                    Assert.fail();

                }
                if (!compareToExpectedResult(expectedResult, results))
                    Assert.fail("At least one of the flights returned by findFlights is incorrect: ");

            }

        }

        @Test
        /** Test predecessors() method */
        public void testPredecessors() {

            List<FlightKey> flightsToTest = new ArrayList<FlightKey>();
            int[] timeDifference = new int[5];
            List<FlightNode[]> expectedResults = new ArrayList<FlightNode[]>();
            List<FlightKey> flightsToTestPredecessors = new ArrayList<FlightKey>();
            List<FlightKey> flightsToTestSuccessors = new ArrayList<FlightKey>();
            List<FlightNode[]> expectedPredecessors = new ArrayList<>();
            List<FlightNode[]> expectedSuccessors = new ArrayList<>();

            // Call helper method that prepares some test data
            prepareTestData(flightsToTest, timeDifference,expectedResults,
                    flightsToTestPredecessors, flightsToTestSuccessors,
                    expectedPredecessors, expectedSuccessors);

            FlightList list = new FlightList(filename);

            String message1, message2, message3;
            List<FlightKey> testedFlights = flightsToTestPredecessors;
            message1 =  "In testPredecessors:";
            message2 = "The expected output of predecessors() method on this flight is:";

            for (int i =0 ; i < testedFlights.size(); i++) {
                FlightKey key = testedFlights.get(i);
                List<FlightNode> results;
                FlightNode[] expectedResult;

                results = list.predecessors(key);
                expectedResult = expectedPredecessors.get(i);

                if (expectedResult.length != results.size()) {
                    System.out.println(message1 + " the number of flights returned for " + key + " is not what was expected");
                    System.out.println(message2);
                    for (int l=0; l < expectedResult.length; l++)
                        System.out.println(expectedResult[l].getKey() + " ");
                    Assert.fail();
                }
                if (!compareToExpectedResult(expectedResult, results))
                    Assert.fail( "At least one of the flights returned by predecessors() is incorrect: ");
            }
        }


        @Test
        /** Test successors() method */
        public void testSuccessors() {

            List<FlightKey> flightsToTest = new ArrayList<FlightKey>();
            int[] timeDifference = new int[5];
            List<FlightNode[]> expectedResults = new ArrayList<FlightNode[]>();
            List<FlightKey> flightsToTestPredecessors = new ArrayList<FlightKey>();
            List<FlightKey> flightsToTestSuccessors = new ArrayList<FlightKey>();
            List<FlightNode[]> expectedPredecessors = new ArrayList<>();
            List<FlightNode[]> expectedSuccessors = new ArrayList<>();

            // Call helper method that prepares some test data
            prepareTestData(flightsToTest, timeDifference,expectedResults,
                    flightsToTestPredecessors, flightsToTestSuccessors,
                    expectedPredecessors, expectedSuccessors);

            FlightList list = new FlightList(filename);

            String message1, message2, message3;
            List<FlightKey> testedFlights = flightsToTestSuccessors;
            message1 = "In testSuccessors:";
            message2 = "The expected output of successors() method on this flight is:";

            for (int i =0 ; i < testedFlights.size(); i++) {
                FlightKey key = testedFlights.get(i);
                List<FlightNode> results;
                FlightNode[] expectedResult;

                results = list.successors(key);
                expectedResult = expectedSuccessors.get(i);

                if (expectedResult.length != results.size()) {
                    System.out.println(message1 + " the number of flights returned for " + key + " is not what was expected");
                    System.out.println(message2);
                    for (int l=0; l < expectedResult.length; l++)
                        System.out.println(expectedResult[l].getKey() + " ");
                    Assert.fail();
                }
                if (!compareToExpectedResult(expectedResult, results))
                    Assert.fail( "At least one of the flights returned by successors() is incorrect: ");
            }
        }

        /** Helper method. Prepares test data
         *
         * @param flightsToTest An Arraylist of flight keys
         * @param timeDifference  An array of time differences
         * @param expectedResults An ArrayList of FlightNode arrays - expected results
         */
        public void prepareTestData(List<FlightKey> flightsToTest, int[] timeDifference,
                                    List<FlightNode[]> expectedResults,
                                    List<FlightKey> flightsToTestPredecessors,  List<FlightKey> flightsToTestSuccessors,
                                    List<FlightNode[]> expectedPredecessors,
                                    List<FlightNode[]> expectedSuccessors) {
            flightsToTest.add(new FlightKey("FRA", "JFK", "01/03/2024", "05:50"));
            flightsToTest.add(new FlightKey("FRA", "JFK", "01/03/2024", "16:00"));
            flightsToTest.add(new FlightKey("FRA", "JFK", "01/03/2024", "12:00"));
            flightsToTest.add(new FlightKey("FRA", "SFO", "01/03/2024", "07:10"));
            flightsToTest.add(new FlightKey("SFO", "ORD", "01/03/2024", "05:00"));
            timeDifference[0] = 2;
            timeDifference[1] = 7;
            timeDifference[2] = 5;
            timeDifference[3] = 2;
            timeDifference[4] = 18;

            FlightKey resKey1 = new FlightKey("FRA", "JFK",  "01/03/2024", "05:50");
            FlightData data1 = new FlightData("LH113", 400);
            FlightKey resKey2 = new FlightKey("FRA", "JFK",  "01/03/2024", "07:00");
            FlightData data2 = new FlightData("LH123", 400);
            FlightNode[] expectedList1 = {new FlightNode(resKey1, data1), new FlightNode(resKey2, data2)};

            resKey1 = new FlightKey("FRA", "JFK",  "01/03/2024", "16:00");
            data1 = new FlightData("LH143", 500);
            resKey2 = new FlightKey("FRA", "JFK",  "01/03/2024", "17:00");
            data2 = new FlightData("AA123", 400);
            FlightKey resKey3 = new FlightKey("FRA", "JFK",  "01/03/2024", "22:00");
            FlightData data3 = new FlightData("DL324", 400);
            FlightNode[] expectedList2 = {new FlightNode(resKey1, data1), new FlightNode(resKey2, data2), new FlightNode(resKey3, data3)};

            resKey1 = new FlightKey("FRA", "JFK",  "01/03/2024", "07:00");
            data1 = new FlightData("LH123", 400);
            resKey2 = new FlightKey("FRA", "JFK",  "01/03/2024", "16:00");
            data2 = new FlightData("LH143", 500);
            resKey3 = new FlightKey("FRA", "JFK",  "01/03/2024", "17:00");
            data3 = new FlightData("AA123", 400);
            FlightNode[] expectedList3 = {new FlightNode(resKey1, data1), new FlightNode(resKey2, data2), new FlightNode(resKey3, data3)};

            resKey1 = new FlightKey("FRA", "SFO",  "01/03/2024", "07:10");
            data1 = new FlightData("DL113", 400);
            resKey2 = new FlightKey("FRA", "SFO",  "01/03/2024", "09:10");
            data2 = new FlightData("DL891", 500);
            FlightNode[] expectedList4 = {new FlightNode(resKey1, data1), new FlightNode(resKey2, data2)};

            resKey1 = new FlightKey("SFO", "ORD",  "01/03/2024", "05:00");
            data1 = new FlightData("DL394", 500);
            resKey2 = new FlightKey("SFO", "ORD",  "01/03/2024", "23:10");
            data2 = new FlightData("AA234", 400);
            FlightNode[] expectedList5 = {new FlightNode(resKey1, data1), new FlightNode(resKey2, data2)};

            expectedResults.add(expectedList1);
            expectedResults.add(expectedList2);
            expectedResults.add(expectedList3);
            expectedResults.add(expectedList4);
            expectedResults.add(expectedList5);

            prepareTestDataPredecessors(flightsToTestPredecessors, expectedPredecessors);
            prepareTestDataSuccessors(flightsToTestSuccessors, expectedSuccessors);
        }

        /**
         * Helper method - prepares test data for successors
         * @param flightsToTestSuccessors flights to test
         * @param expectedSuccessors expected successor for each flight
         */
        public void prepareTestDataSuccessors(List<FlightKey> flightsToTestSuccessors, List<FlightNode[]> expectedSuccessors) {

            flightsToTestSuccessors.add(new FlightKey("FRA", "JFK", "01/03/2024", "05:50"));
            flightsToTestSuccessors.add(new FlightKey("FRA", "JFK", "01/03/2024", "16:30"));
            flightsToTestSuccessors.add(new FlightKey("FRA", "SFO", "01/03/2024", "06:00"));

            FlightKey resKey1, resKey2, resKey3, resKey4;
            FlightData data1, data2, data3, data4;

            // expected successors for the first test flight
            resKey1 = new FlightKey("FRA", "JFK",  "01/03/2024", "07:00");
            data1 = new FlightData("LH123", 400);
            resKey2 = new FlightKey("FRA", "JFK",  "01/03/2024", "16:00");
            data2 = new FlightData("LH143", 500);
            resKey3 = new FlightKey("FRA", "JFK",  "01/03/2024", "17:00");
            data3 = new FlightData("AA123", 400);
            resKey4 = new FlightKey("FRA", "JFK",  "01/03/2024", "22:00");
            resKey4 = new FlightKey("FRA", "JFK",  "01/03/2024", "22:00");
            data4 = new FlightData("DL324", 400);
            FlightNode[] expectedList1 = {new FlightNode(resKey1, data1), new FlightNode(resKey2, data2), new FlightNode(resKey3, data3), new FlightNode(resKey4, data4)};


            // expected successors for the second test flight
            FlightNode[] expectedList2 = {new FlightNode(resKey3, data3), new FlightNode(resKey4, data4)};

            // expected successors for the third test flight
            resKey1 = new FlightKey("FRA", "SFO",  "01/03/2024", "07:10");
            data1 = new FlightData("DL113", 400);
            resKey2 = new FlightKey("FRA", "SFO",  "01/03/2024", "09:10");
            data2 = new FlightData("DL891", 400);
            FlightNode[] expectedList3 = {new FlightNode(resKey1, data1), new FlightNode(resKey2, data2)};

            expectedSuccessors.add(expectedList1);
            expectedSuccessors.add(expectedList2);
            expectedSuccessors.add(expectedList3);
        }

        /**
         * Helper method to prepare test data for predecessors.
         * @param flightsToTestPredecessors flights to test
         * @param expectedPredecessors expected predecessors for each flight
         */
        public void prepareTestDataPredecessors(List<FlightKey> flightsToTestPredecessors, List<FlightNode[]> expectedPredecessors) {

            flightsToTestPredecessors.add(new FlightKey("FRA", "JFK", "01/03/2024", "05:50"));
            flightsToTestPredecessors.add(new FlightKey("FRA", "JFK", "01/03/2024", "16:00"));
            flightsToTestPredecessors.add(new FlightKey("FRA", "JFK", "01/03/2024", "19:00"));

            // expected predecessors for the first test flight
            FlightNode[] expectedList1 = new FlightNode[0];

            // expected predecessors for the second test flight
            FlightKey resKey1 = new FlightKey("FRA", "JFK",  "01/03/2024", "05:50");
            FlightData data1 = new FlightData("LH113", 400);
            FlightKey resKey2 = new FlightKey("FRA", "JFK",  "01/03/2024", "07:00");
            FlightData data2 = new FlightData("LH123", 400);
            FlightNode[] expectedList2 = {new FlightNode(resKey1, data1), new FlightNode(resKey2, data2)};

            // expected predecessors for the third test flight
            resKey1 = new FlightKey("FRA", "JFK",  "01/03/2024", "05:50");
            data1 = new FlightData("LH113", 400);
            resKey2 = new FlightKey("FRA", "JFK",  "01/03/2024", "07:00");
            data2 = new FlightData("LH123", 400);
            FlightKey resKey3 = new FlightKey("FRA", "JFK",  "01/03/2024", "16:00");
            FlightData data3 = new FlightData("LH143", 500);
            FlightKey resKey4 = new FlightKey("FRA", "JFK",  "01/03/2024", "17:00");
            FlightData data4 = new FlightData("AA123", 400);
            FlightNode[] expectedList3 = {new FlightNode(resKey1, data1), new FlightNode(resKey2, data2), new FlightNode(resKey3, data3), new FlightNode(resKey4, data4)};

            expectedPredecessors.add(expectedList1);
            expectedPredecessors.add(expectedList2);
            expectedPredecessors.add(expectedList3);
        }

        /** Compares the expected array of flight nodes to the given array list of flight nodes.
         *
         * @param expectedResult Expected results
         * @param results Your results
         * @return true if the results match expected results, false otherwise
         */
        public boolean compareToExpectedResult(FlightNode[] expectedResult, List<FlightNode> results) {
            for (int k = 0; k < expectedResult.length; k++) {
                FlightNode node1 =  expectedResult[k];
                FlightNode node2 = results.get(k);
                FlightKey ndkey1 = node1.getKey();
                FlightKey ndkey2 = node2.getKey();
                boolean bol = (((ndkey1.getOrigin()).equals(ndkey2.getOrigin())) && ((ndkey1.getDest()).equals(ndkey2.getDest()))
                        && ((ndkey1.getDate()).equals(ndkey2.getDate())) && ((ndkey1.getTime()).equals(ndkey2.getTime())));
                {
                    if (!bol) {
                        System.out.println("Mismatch in key: " + ndkey1);
                        return false;
                    }
                }
            }
            return true;
        }

}
