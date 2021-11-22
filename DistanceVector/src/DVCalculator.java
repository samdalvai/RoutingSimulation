import java.util.*;

/**
 * Author: Samuel Dalvai
 * <p>
 * This class contains methods that combine RoutingTable objects and a Graph object
 * in order to compute the Distance Vector Algorithm for the computation of routing
 * tables.
 */
public class DVCalculator {

    private final String topologyFile;
    private final String messagesFile;
    private List<Character> messageOrder;
    private Graph graph;
    private Set<Character> nodes;
    private final Map<Character, RoutingTable> routingTableMap;

    public DVCalculator(String topologyFile, String messagesFile) {
        this.topologyFile = topologyFile;
        this.messagesFile = messagesFile;
        this.nodes = new TreeSet<>();
        this.routingTableMap = new TreeMap<>();
    }

    public void run() throws Exception {
        initGraph();
        initMessageOrder();
        initRoutingTableMap();
        computeRoutingTables();
        writeRoutingTables();
        System.out.println("Quitting program...");
    }

    // initialize the graph representing the connections between nodes
    private void initGraph() throws Exception {
        this.graph = FileUtilities.readGraph(topologyFile);
        this.nodes = graph.getAllNodes();

        if (nodes.size() == 0)
            throw new Exception("Error, no connection specified int the topology");

        System.out.println(topologyFile + " file loaded...\n");
    }

    // initialize the list representing the order in which the messages are sent
    private void initMessageOrder() throws Exception {
        this.messageOrder = FileUtilities.readMessages(messagesFile);

        if (!nodes.containsAll(messageOrder))
            throw new Exception("Error, node in " + messagesFile + " not present in the graph");

        System.out.println(messagesFile + " file loaded...\n");
    }

    // initialize the Routing Table of each node present in the graph
    // with the nodes that it knows and the cost to reach them
    private void initRoutingTableMap() {
        System.out.println("Initializing Routing tables...\n");

        nodes.forEach(source -> {
            RoutingTable table = new RoutingTable(source);
            Set<Character> connections = graph.getConnectedNodes(source);

            for (Character n : connections)
                table.addRoutingEntry(new RoutingEntry(n, n, graph.getConnectionCost(source, n)));

            // add the connection from the source node to itself
            table.addRoutingEntry(new RoutingEntry(source, source, 0));
            routingTableMap.put(source, table);
        });

        System.out.println("Tables initialized:\n");
        displayRoutingTables();
    }

    // compute the Routing tables by getting the Distance Vector from each node
    // in the order specified in the messageOrder List, then share this information
    // with the neighbouring nodes.
    private void computeRoutingTables() throws Exception {
        System.out.println("Computing Routing tables...\n");
        if (messageOrder.size() == 0)
            System.out.println("Nothing to compute...\n");

        for (Character source : messageOrder) {
            Set<Character> connected = graph.getConnectedNodes(source);
            Set<RoutingEntry> dv = getDistanceVector(source);

            System.out.println("Router " + source + " sending distance vector to: " + connected);
            displayDistanceVector(dv);

            for (Character neighbor : connected)
                updateRoutingTable(source, neighbor, dv);

            System.out.println("\nRouting tables after the update:\n");
            displayRoutingTables();
        }

    }

    // get distance vector from router
    private Set<RoutingEntry> getDistanceVector(Character router) {
        Set<RoutingEntry> distanceVector = new TreeSet<>();

        routingTableMap.get(router).getEntries().forEach(e -> {
            if (e.getCost() != 0 && e.getCost() != Integer.MAX_VALUE)
                distanceVector.add(e);
        });

        return distanceVector;
    }

    // If the current router/node does not know about the existence of a node contained
    // in the Distance vector, add it to the Routing Table, otherwise, if the destination
    // is already present, update the cost if a new "cheaper" path has been found.
    private void updateRoutingTable(Character sendingRouter, Character currentRouter, Set<RoutingEntry> distanceVector) throws Exception {
        int costToReach = graph.getConnectionCost(sendingRouter, currentRouter);

        for (RoutingEntry v : distanceVector) {
            int partialcost = costToReach + v.getCost();

            // add to table if not previously known destination
            if (!routingTableMap.get(currentRouter).knowsDestination(v.getDestination()))
                routingTableMap.get(currentRouter)
                        .addRoutingEntry(new RoutingEntry(v.getDestination(), sendingRouter, partialcost));

            // otherwise check if there is a better path and update accordingly
            if (partialcost < routingTableMap.get(currentRouter).getConnectionCost(v.getDestination()))
                routingTableMap.get(currentRouter).updateRoutingEntry(v.getDestination(), sendingRouter, partialcost);
        }

    }

    private void writeRoutingTables() {
        for (Map.Entry<Character, RoutingTable> entry : routingTableMap.entrySet())
            FileUtilities.writeRoutingTable(entry.getValue());
        System.out.println("Routing tables written to files...\n");
    }

    private void displayRoutingTables() {
        for (Map.Entry<Character, RoutingTable> entry : routingTableMap.entrySet())
            System.out.println(entry.getValue());

    }

    private void displayDistanceVector(Set<RoutingEntry> dv) {
        System.out.print("The distance vector: ");

        int index = 0;

        for (RoutingEntry e : dv) {
            if (index < dv.size() - 1)
                System.out.print("(" + e.getDestination() + "," + e.getCost() + "),");
            else
                System.out.print("(" + e.getDestination() + "," + e.getCost() + ")");
            index++;
        }

        System.out.println();
    }

}
