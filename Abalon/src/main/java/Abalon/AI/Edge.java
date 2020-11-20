package Abalon.AI;

public class Edge  {
    private final Node source;
    private final Node destination;

    public Edge(Node source, Node destination) {
        this.source = source;
        this.destination = destination;
    }

    public Node getDestination() {
        return destination;
    }

    public Node getSource() {
        return source;
    }

    @Override
    public String toString() {
        return source + " " + destination;
    }


}

