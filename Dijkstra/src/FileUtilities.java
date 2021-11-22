import java.io.*;
import java.util.List;

/**
 * Author: Samuel Dalvai
 * Student ID: 17682
 * Email: samdalvai@unibz.it
 * Course: Computer Networks 2020/2021
 * Assignment 2 (Dijkstra algorithm)
 * <p>
 * Class that contains utilities to read and write files
 */
public class FileUtilities {

    // reads a file and creates a NodeGraph object
    public static NodeGraph readGraph(String filename) {

        NodeGraph nodes = new NodeGraph();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));

            String line;
            char nodeA;
            char nodeB;
            int cost;

            while ((line = reader.readLine()) != null) {
                line = line.replace(" ", "");

                if (line.length() < 3)
                    throw new IOException("Malformed input for nodes and cost");

                nodeA = line.charAt(0);
                nodeB = line.charAt(1);
                cost = Integer.parseInt(line.substring(2));

                nodes.addEdge(nodeA, nodeB, cost);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nodes;
    }

    // write routing table to file, get the name of the router from the Routing Table
    // objects
    public static void writeRoutingTable(List<RoutingTable> routingTable) {
        try {
            File dir = new File("../output");

            if (!dir.exists())
                if (!dir.mkdir())
                    throw new IOException("Error in creating the directory for the files...");
        } catch (IOException e) {
            e.printStackTrace();
        }

        routingTable.forEach(table -> {

            try {
                String filename = "../output/" + table.getRouter() + ".txt";
                BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
                writer.write(table.toFile());
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        System.out.println("Routing tables written to file...");
    }

}
