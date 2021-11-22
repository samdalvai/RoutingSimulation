import java.util.*;

/**
 * Author: Samuel Dalvai
 * <p>
 * This class represents a Routing Table consisting of a router label and
 * a List of Routing Entries
 */
public class RoutingTable implements Comparable<Object> {

    private final char router;
    private final List<RoutingEntry> entries;

    public RoutingTable(char router) {
        this.router = router;
        this.entries = new ArrayList<>();
    }

    public char getRouter() {
        return router;
    }

    public List<RoutingEntry> getEntries() {
        return entries;
    }

    public void addRoutingEntry(RoutingEntry entry) {
        entries.add(entry);
        entries.sort(Comparator.naturalOrder());
    }

    public void updateRoutingEntry(Character destination, Character nextHop, int cost) throws Exception {
        int index = -1;

        for (RoutingEntry e : entries)
            if (e.getDestination() == destination) {
                index = entries.indexOf(e);
                break;
            }

        if (index == -1) // something went wrong
            throw new Exception("Destination not present in the Routing Table...");

        entries.get(index).setCost(cost);
        entries.get(index).setNextHop(nextHop);
    }

    // get the connection cost currently stored for a given destination
    public int getConnectionCost(Character destination) throws Exception {
        int cost = -1;

        for (RoutingEntry e : entries)
            if (e.getDestination() == destination) {
                cost = e.getCost();
                break;
            }

        if (cost == -1) // something went wrong
            throw new Exception("Destination not present in the Routing Table...");

        return cost;
    }

    // check whether this destination is known inside this Table
    public boolean knowsDestination(Character destination) {

        boolean output = false;

        for (RoutingEntry e : entries) {
            if (e.getDestination() == destination) {
                output = true;
                break;
            }
        }

        return output;
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("Router " + router + ":\n");

        output.append("Dest, Next hop, Cost\n");

        for (RoutingEntry e : entries) {

            output.append(e.getDestination()).append("     ");

            if (e.getNextHop() == e.getDestination() && e.getNextHop() == router)
                output.append("direct").append("    ");
            else
                output.append(e.getNextHop()).append("         ");

            if (e.getCost() == Integer.MAX_VALUE)
                output.append(" ");
            else
                output.append(e.getCost());

            output.append("\n");
        }

        return output.toString();
    }

    // method used to format output for printing to a file
    public String toFile() {
        StringBuilder output = new StringBuilder();

        for (RoutingEntry e : entries) {

            output.append(e.getDestination()).append(" ");

            if (e.getNextHop() == e.getDestination() && e.getNextHop() == router)
                output.append("direct");
            else
                output.append(e.getNextHop());

            output.append("\n");
        }

        return output.toString();
    }

    @Override
    public int compareTo(Object o) {
        return Character.compare(this.router, ((RoutingTable) o).router);

    }
}
