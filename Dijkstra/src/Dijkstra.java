/**
 * Author: Samuel Dalvai
 * Student ID: 17682
 * Email: samdalvai@unibz.it
 * Course: Computer Networks 2020/2021
 * Assignment 2 (Dijkstra algorithm)
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
