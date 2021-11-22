import java.util.Objects;

/**
 * Author: Samuel Dalvai
 * <p>
 * Class that represents a connection between two nodes. Building block of Graph class.
 */
public class Connection implements Comparable<Object> {

    private final Character nodeA;
    private final Character nodeB;
    private final int cost;

    public Connection(Character nodeA, Character nodeB, int cost) throws Exception {
        if (nodeA == nodeB && cost > 0)
            throw new Exception("A node cannot be connected to itself with cost greater than 0..");

        this.nodeA = nodeA;
        this.nodeB = nodeB;
        this.cost = cost;
    }

    public Character getNodeA() {
        return nodeA;
    }

    public Character getNodeB() {
        return nodeB;
    }

    public int getCost() {
        return cost;
    }

    // check if node a has a connection to b or the opposite (undirected edges)
    public boolean isConnected(Character a, Character b) {
        return a == nodeA && b == nodeB || a == nodeB && b == nodeA;
    }

    // check if node a appears in this connection
    public boolean isConnected(Character a) {
        return a == nodeA || a == nodeB;
    }

    public Character getConnectedNode(Character a) {
        Character output;

        if (a == nodeA)
            output = nodeB;
        else
            output = nodeA;

        return output;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Connection c = (Connection) o;
        return (cost == c.cost &&
                Objects.equals(nodeA, c.nodeA) &&
                Objects.equals(nodeB, c.nodeB) ||
                (cost == c.cost) &&
                        Objects.equals(nodeA, c.nodeB) &&
                        Objects.equals(nodeB, c.nodeA));
    }

    @Override
    public int hashCode() {
        return Objects.hash(nodeA, nodeB, cost);
    }

    @Override
    public String toString() {
        return "Connection {" + nodeA +
                " -> " + nodeB +
                " cost = " + cost +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        return Character.compare(this.nodeA, ((Connection) o).nodeA);
    }

}
