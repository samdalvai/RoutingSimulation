import java.util.Objects;

/**
 * Author: Samuel Dalvai
 * Student ID: 17682
 * Email: samdalvai@unibz.it
 * Course: Computer Networks 2020/2021
 * Assignment 3 (Distance vector algorithm)
 * <p>
 * Class that is used as a building block for RoutingTable class. Represents one entry
 * in the table consisting of destination router, next hop router and cost of the connection.
 */
public class RoutingEntry implements Comparable<Object> {

    private final Character destination;
    private Character nextHop;
    private int cost;

    public RoutingEntry(Character destination, Character nextHop, int cost) {
        this.destination = destination;
        this.nextHop = nextHop;
        this.cost = cost;
    }

    public Character getDestination() {
        return destination;
    }

    public Character getNextHop() {
        return nextHop;
    }

    public int getCost() {
        return cost;
    }

    public void setNextHop(Character nextHop) {
        this.nextHop = nextHop;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoutingEntry that = (RoutingEntry) o;
        return cost == that.cost &&
                Objects.equals(destination, that.destination) &&
                Objects.equals(nextHop, that.nextHop);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destination, nextHop, cost);
    }

    @Override
    public int compareTo(Object o) {
        return Character.compare(this.destination, ((RoutingEntry) o).destination);
    }
}
