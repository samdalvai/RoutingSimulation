/**
 * Author: Samuel Dalvai
 * <p>
 * Computes and writes the Routing tables of a Graph using the
 * Distance Vector Algorithm
 * Two files are needed as parameter to the app:
 * - topology.txt file to load the topology of the Graph
 * - messages.txt file to load the order in which the nodes
 * send their distance vector to the neighboring nodes
 */
public class DistanceVector {

    public static void main(String[] args) {

        try {
            //String topology = args[0];
            //String messages = args[1];
            String topology = "../topology.txt";
            String messages = "../messages.txt";
            DVCalculator calc = new DVCalculator(topology, messages);
            calc.run();

        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error... Too few arguments...\n" +
                    "Please specify topology.txt and messages.txt files to be opened...");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
