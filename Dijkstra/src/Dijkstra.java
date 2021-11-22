/**
 * Author: Samuel Dalvai
 */
public class Dijkstra {

    public static void main(String[] args) {

        try {
            String topology = "../topology.txt";
            //String topology = args[0];
            DijkstraCalculator calculator = new DijkstraCalculator(topology);
            calculator.run();
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("Error... Argument passed is empty...\n" +
                    "Please specify a .txt file to be opened...");
            e.printStackTrace();
        }

    }

}
