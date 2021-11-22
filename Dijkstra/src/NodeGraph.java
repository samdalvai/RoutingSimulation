import java.util.*;

/**
 * Author: Samuel Dalvai
 * Student ID: 17682
 * Email: samdalvai@unibz.it
 * Course: Computer Networks 2020/2021
 * Assignment 2 (Dijkstra algorithm)
 * <p>
 * Class that represents an undirected graph with nodes and edges
 * Each edge connecting two nodes has a cost that is represented
 * in a matrix labeled with letters consisting of arbitrary characters
 * The order of the letters in the matrix is not important
 * <p>
 * Example of a graph:
 * A B C D E F
 * A 0 8 5 2 * *
 * B 8 0 * 1 3 4
 * C 5 * 0 * 2 1
 * D 2 1 * 0 * *
 * E * 3 2 * 0 1
 * F * 4 1 * 1 0
 * <p>
 * The class contains methods to add edges represented by two nodes and the cost
 * of the connection, every node by default has a cost of 0 to reach itself,
 * otherwise if no cost is specified the cost is "infinite" since the
 * two nodes are not connected. (value * represents infinite cost)
 * <p>
 * Every time a new edge is added, the matrix of nodes is updated with
 * the two new nodes (only if the nodes are not already present in the graph)
 * and the cost is updated.
 * <p>
 * The class contains also methods to compute the best path from a source
 * node to a destination
 */
public class NodeGraph {

    // maps name of the node to index in nodesMatrix
    private final Map<Character, Integer> nodesMap;
    private int nodesIndex;
    // contains cost of the connection between nodes
    int[][] nodesMatrix;

    public NodeGraph() {
        // initialize the two attributes as empty
        this.nodesMap = new LinkedHashMap<>();
        nodesIndex = 0;
        this.nodesMatrix = new int[0][0];
    }

    public Map<Character, Integer> getNodesMap() {
        return nodesMap;
    }

    /**
     * Add edge consisting of two nodes and the cost between the two nodes
     * Add the two edges to the graph and update cost
     * If a node is already in the graph, that node is not added (see addNodeKey method)
     * The cost is updated for both directions between the nodes, since
     * the graph is undirected
     */
    public void addEdge(Character nodeA, Character nodeB, int cost) {

        addNodeKey(nodeA);
        addNodeKey(nodeB);
        updateCost(nodeA, nodeB, cost);
        setInfiniteConnections();
    }

    // get the connection cost between two nodes
    public int getEdgeCost(Character nodeA, Character nodeB) {

        int indexNodeA = nodesMap.get(nodeA);
        int indexNodeB = nodesMap.get(nodeB);

        return nodesMatrix[indexNodeA][indexNodeB];
    }

    // add new node to map of nodes and update value by 1
    // and grow nodesMatrix by 1 column and 1 row
    // if node is already mapped, do nothing
    private void addNodeKey(Character label) {

        if (!nodesMap.containsKey(label)) {
            nodesMap.put(label, nodesIndex);
            growNodesMatrix();
            nodesIndex++;
        }
    }

    // grow matrix by 1 and copy previous content
    private void growNodesMatrix() {

        int[][] copyMatrix = new int[nodesMatrix.length + 1][nodesMatrix.length + 1];

        for (int i = 0; i < nodesMatrix.length; i++)
            System.arraycopy(nodesMatrix[i], 0, copyMatrix[i], 0, nodesMatrix[0].length);

        nodesMatrix = copyMatrix;
    }

    // get the indexes of the two nodes and update the node matrix
    // with the value of the cost in both directions
    private void updateCost(Character nodeA, Character nodeB, int cost) {

        int indexNodeA = nodesMap.get(nodeA);
        int indexNodeB = nodesMap.get(nodeB);

        nodesMatrix[indexNodeA][indexNodeB] = cost;
        nodesMatrix[indexNodeB][indexNodeA] = cost;
    }

    /**
     * update the cost of the non connected nodes and set it
     * to infinite. The maximum value for integers has been chosen to represent
     * infinity. The cost of the connection from a node to itself
     * remains set to 0 (default value for arrays)
     */
    private void setInfiniteConnections() {

        for (int i = 0; i < nodesMatrix.length; i++)
            for (int j = 0; j < nodesMatrix[0].length; j++)
                if (nodesMatrix[i][j] == 0 && i != j)
                    nodesMatrix[i][j] = Integer.MAX_VALUE;
    }

    // get all the nodes of the graph as a list
    public List<Character> getNodesAsList() {

        ArrayList<Character> nodesList = new ArrayList<>();

        for (Map.Entry<Character, Integer> entry : nodesMap.entrySet()) {
            nodesList.add(entry.getKey());
        }

        return nodesList;
    }

    // check whether a node has a connection to another node
    // the connection to itself is not considered
    public boolean hasConnection(Character nodeA, Character NodeB) {

        return getEdgeCost(nodeA, NodeB) != Integer.MAX_VALUE;// && getEdgeCost(nodeA, NodeB) != 0;
    }

    // get a list of all the direct connections from this node
    public List<Character> getConnections(Character node) {

        List<Character> connections = new ArrayList<>();

        for (Map.Entry<Character, Integer> entry : nodesMap.entrySet())
            if (hasConnection(node, entry.getKey()))
                connections.add(entry.getKey());

        return connections;
    }

    @Override
    public String toString() {

        if (nodesMap.size() == 0 && nodesMatrix.length == 0)
            return "*** no values in the graph***";

        StringBuilder output = new StringBuilder("  ");

        for (Map.Entry<Character, Integer> entry : nodesMap.entrySet()) {
            output.append(entry.getKey()).append(" ");// + entry.getValue() + " ";
        }

        output.append("\n");

        int index = 0;

        for (Map.Entry<Character, Integer> entry : nodesMap.entrySet()) {
            output.append(entry.getKey()).append(" ");

            for (int i = 0; i < nodesMatrix.length; i++)
                if (nodesMatrix[index][i] == Integer.MAX_VALUE)
                    output.append("* ");
                else
                    output.append(nodesMatrix[index][i]).append(" ");

            output.append("\n");
            index++;
        }
        return output.toString();
    }
}
