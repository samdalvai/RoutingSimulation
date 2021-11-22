import java.util.Collections;
import java.util.List;

/**
 * Author: Samuel Dalvai
 * <p>
 * Class that represents a routing table as a table
 * containing the name of the router and the list
 * of destination nodes with the next hop
 */
public class RoutingTable implements Comparable<Object> {

    private final char router;
    private final List<Character> destination;
    private final List<Character> nextHop;

    public RoutingTable(char router, List<Character> destination, List<Character> nextHop) {
        this.router = router;
        this.destination = destination;
        this.nextHop = nextHop;
    }

    public char getRouter() {
        return router;
    }

    // sort the routing table in case the name of the nodes
    // is not ordered (for readability)
    public void sort() {

        for (int i = 0; i < destination.size() - 1; i++)
            for (int j = i + 1; j < destination.size(); j++)
                if (destination.get(i) > destination.get(j)) {
                    Collections.swap(destination, i, j);
                    Collections.swap(nextHop, i, j);
                }
    }

    @Override
    public String toString() {
        StringBuilder output = new StringBuilder("Router " + router + ":\n" +
                "Dest, Next hop\n");

        for (int i = 0; i < destination.size(); i++) {
            output.append(destination.get(i)).append("     ");
            if (nextHop.get(i) == router)
                output.append("direct\n");
            else
                output.append(nextHop.get(i)).append("\n");
        }

        return output.toString();
    }

    // method used to format output for printing to a file
    public String toFile() {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < destination.size(); i++) {
            output.append(destination.get(i)).append(" ");
            if (nextHop.get(i) == router)
                output.append("direct\n");
            else
                output.append(nextHop.get(i)).append("\n");
        }
        return output.toString();
    }

    @Override
    public int compareTo(Object o) {
        return Character.compare(this.router, ((RoutingTable) o).router);

    }
}
