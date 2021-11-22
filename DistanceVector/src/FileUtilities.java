import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Samuel Dalvai
 * Student ID: 17682
 * Email: samdalvai@unibz.it
 * Course: Computer Networks 2020/2021
 * Assignment 3 (Distance vector algorithm)
 * <p>
 * Utilities for file manipulation
 */
public class FileUtilities {

    // reads a file and creates a Graph object
    public static Graph readGraph(String filename) {

        Graph nodes = new Graph();
        Connection con;

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

                con = new Connection(nodeA, nodeB, cost);
                nodes.addConnection(con);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }

    // reads a file and creates a List with the order of distance vector messages sent
    public static List<Character> readMessages(String filename) {

        List<Character> nodes = new ArrayList<>();
        char node;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(new File(filename)));

            int r;
            while ((r = reader.read()) != -1) {
                node = (char) r;

                if (node != ' ') // discard empty spaces
                    nodes.add(node);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return nodes;
    }

    // write routing tables to files
    public static void writeRoutingTable(RoutingTable table) {
        try {
            File dir = new File("../output");

            if (!dir.exists())
                if (!dir.mkdir())
                    throw new IOException("Error in creating the directory for the files...");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String filename = "../output/" + table.getRouter() + ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filename)));
            writer.write(table.toFile());
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
