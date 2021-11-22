import java.util.*;

/**
 * Author: Samuel Dalvai
 * <p>
 * Class that manages the creation of a graph of nodes and the computation
 * of the Dijkstra algorithm to create the routing tables.
 * Each step is computed by it's own method, see the description
 * of the methods for more details
 */
public class DijkstraCalculator {

    private NodeGraph graph;
    String inputTable;

    private final List<RoutingTable> routingTables;
    private final List<DijkstraEntry> dijkstraTables;
    private boolean[] visited;
    private int currentPathCost;

    public DijkstraCalculator(String inputTable) {

        this.inputTable = inputTable;
        this.routingTables = new ArrayList<>();
        this.dijkstraTables = new ArrayList<>();
    }

    /**
     * Read a graph from an input file
     * Compute the routing tables with Dijkstra algorithm
     * Display the resulting routing tables
     * Print the routing tables to files
     */
    public void run() {

        graph = FileUtilities.readGraph(inputTable);
        computeTables();
        displayRoutingTables();
        FileUtilities.writeRoutingTable(routingTables);
        System.out.println("Quitting program...");
    }

    // compute Dijkstra tables and routing tables for every node (router)
    private void computeTables() {

        List<Character> nodesList = graph.getNodesAsList();
        nodesList.forEach(source -> {
            computeDijkstraTable(source);
            computeRoutingTable(source);
        });

        // sort the tables in case the nodes are not in
        // alphabetical order
        routingTables.sort(Comparator.naturalOrder());

        for (RoutingTable table : routingTables) {
            table.sort();
        }
    }

    // Compute DijkstraTable following the Dijkstra algorithm
    // at each step update the cost of the current path
    private void computeDijkstraTable(Character source) {

        initDijkstraTable(source);

        Character closestNode;

        for (int i = 0; i < graph.getNodesMap().size(); i++) {
            closestNode = getNextClosestNode(source);
            currentPathCost = getCurrentPathCost(source, dijkstraTables
                    .get(graph.getNodesMap().get(closestNode)));
            updateDijkstraTable(closestNode);
        }

    }

    /**
     * get the next closest node from the source node and mark it as visited
     * begin with all the adjacent nodes, after all adjacent nodes have
     * been marked as visited, proceed with the next closest node
     * connected with the adjacent nodes and continue until
     * all nodes have been visited
     */
    private Character getNextClosestNode(Character source) {

        Set<Character> sourceList = new TreeSet<>();
        sourceList.add(source);

        return getNextClosestNode(sourceList);
    }

    private Character getNextClosestNode(Set<Character> source) {

        Set<Character> connectedNodes = new TreeSet<>();

        for (Character n : source) {
            List<Character> connections = graph.getConnections(n);
            connectedNodes.addAll(connections);
        }

        Character closestNode = ' ';

        int tempCost = Integer.MAX_VALUE;

        // among every connected node to the actual source node
        // that has not yet been visited, find the node with
        // the least cost to reach
        for (Character s : source)
            for (Character node : connectedNodes)
                if (!visited[graph.getNodesMap().get(node)])
                    if (graph.getEdgeCost(s, node) < tempCost) {
                        closestNode = node;
                        tempCost = graph.getEdgeCost(s, node);
                    }

        if (closestNode != ' ') {
            visited[graph.getNodesMap().get(closestNode)] = true;
            return closestNode;
        } else
            return getNextClosestNode(connectedNodes);
    }

    // initialize Dijkstra table as empty and cost equal to infinite for all nodes
    // for the source node entry, set cost to 0 and predecessor equal to itself
    private void initDijkstraTable(Character source) {

        dijkstraTables.clear();
        List<Character> nodesList = graph.getNodesAsList();

        for (Character n : nodesList) {
            if (n == source)
                dijkstraTables.add(new DijkstraEntry(n, 0, n));
            else
                dijkstraTables.add(new DijkstraEntry(n, Integer.MAX_VALUE, ' '));
        }

        visited = new boolean[graph.getNodesMap().size()];
        currentPathCost = 0;
    }

    // update the entries in the Dijkstra table if a less
    // expensive path from this node is found
    private void updateDijkstraTable(Character node) {

        visited[graph.getNodesMap().get(node)] = true;

        List<Character> connectedNodes = graph.getConnections(node);

        // for every connected node to this node
        for (Character n : connectedNodes) {
            if (node != n) {
                // get index of the node
                int index = graph.getNodesMap().get(n);

                // get current cost to that node
                int cost = dijkstraTables.get(index).getCost();
                int edgecost = graph.getEdgeCost(node, n);

                // if the current path cost + the edge cost is less
                // than the cost stored in the entry, update the entry
                // with the new cost and predecessor
                if (currentPathCost + edgecost < cost) {
                    dijkstraTables.get(index).setCost(currentPathCost + edgecost);
                    dijkstraTables.get(index).setPredecessor(node);
                }
            }
        }
    }

    // compute routing table for a given node by considering all entries
    // in the final Dijkstra table and recurring back to the source node
    // to find the closest next hop to it
    private void computeRoutingTable(Character source) {

        List<Character> destination = new ArrayList<>();
        List<Character> nextHop = new ArrayList<>();

        dijkstraTables.forEach(entry -> {
            destination.add(entry.getDestination());
            nextHop.add(getNextHop(source, entry));
        });

        RoutingTable routingTable = new RoutingTable(source, destination, nextHop);
        routingTables.add(routingTable);
    }

    // find the next hop closest to the source node
    private Character getNextHop(Character source, DijkstraEntry entry) {

        Character destination = entry.getDestination();

        if (entry.getPredecessor() == source)
            return destination;

        DijkstraEntry predecessorEntry = null;

        for (DijkstraEntry e : dijkstraTables)
            if (entry.getPredecessor() == e.getDestination())
                predecessorEntry = e;

        assert predecessorEntry != null;
        return getNextHop(source, predecessorEntry);
    }

    // find the cost of the path from the current node back to
    // the source node
    private int getCurrentPathCost(Character source, DijkstraEntry entry) {

        if (entry.getPredecessor() == source)
            return graph.getEdgeCost(source, entry.getDestination());

        DijkstraEntry predecessorEntry = null;

        for (DijkstraEntry e : dijkstraTables)
            if (entry.getPredecessor() == e.getDestination())
                predecessorEntry = e;

        if (predecessorEntry == null)
            return 0;
        return graph.getEdgeCost(entry.getPredecessor(), entry.getDestination()) +
                getCurrentPathCost(source, predecessorEntry);
    }

    // display content of the routing tables
    private void displayRoutingTables() {

        System.out.println("Dijkstra algorithm computed...\n\n" +
                "Results:\n");

        for (RoutingTable table : routingTables) {
            System.out.println(table);
        }
    }

}
