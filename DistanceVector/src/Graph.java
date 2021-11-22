import java.util.*;

/**
 * Author: Samuel Dalvai
 * Student ID: 17682
 * Email: samdalvai@unibz.it
 * Course: Computer Networks 2020/2021
 * Assignment 3 (Distance vector algorithm)
 * <p>
 * Class that represent a graph consisting of nodes and connections between them
 * The edges of a node are undirected, which means that if node A is connected to B,
 * then node B is connected to A with the same cost
 */
public class Graph {

    // use set to avoid duplicates of Connection objects
    private final Set<Connection> connections;

    public Graph() {
        connections = new LinkedHashSet<>();
    }

    public void addConnection(Connection con) {
        connections.add(con);
    }

    public int getConnectionCost(Character source, Character destination) {

        int cost = -1;

        for (Connection c : connections)
            if (c.isConnected(source, destination))
                cost = c.getCost();

        return cost;
    }

    // get all the different nodes appearing in the connections Set
    public Set<Character> getAllNodes() {
        Set<Character> nodes = new TreeSet<>();

        for (Connection c : connections) {
            nodes.add(c.getNodeA());
            nodes.add(c.getNodeB());
        }

        return nodes;
    }

    // get a Set of all the connected nodes to a node
    public Set<Character> getConnectedNodes(Character node) {
        Set<Character> nodes = new LinkedHashSet<>();

        for (Connection c : connections)
            if (c.isConnected(node))
                nodes.add(c.getConnectedNode(node));

        return nodes;
    }

    @Override
    public String toString() {

        StringBuilder output = new StringBuilder();

        for (Connection c : connections)
            output.append(c).append("\n");

        return output.toString();
    }
}
