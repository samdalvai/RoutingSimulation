/**
 * Author: Samuel Dalvai
 * Student ID: 17682
 * Email: samdalvai@unibz.it
 * Course: Computer Networks 2020/2021
 * Assignment 2 (Dijkstra algorithm)
 * <p>
 * Class that represents the results of the Dijkstra algorithm
 * as an entry containing the destination node, the predecessor node
 * and the cost to reach the destination
 */
public class DijkstraEntry {

    private final char destination;
    private int cost;
    private char predecessor;

    public DijkstraEntry(char destination, int cost, char predecessor) {
        this.destination = destination;
        this.cost = cost;
        this.predecessor = predecessor;
    }

    public char getDestination() {
        return destination;
    }

    public int getCost() {
        return cost;
    }

    public char getPredecessor() {
        return predecessor;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setPredecessor(char predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public String toString() {

        String output = "destination= " + destination;

        if (cost == Integer.MAX_VALUE)
            output += ", cost= *";
        else
            output += ", cost= " + cost;

        output += ", predecessor= " + predecessor;

        return output;
    }

}

