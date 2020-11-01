package Abalon.AI;

public class Edge  {
    private final String id;
    private final Node source;
    private final Node destination;

    public Edge(String id, Node source, Node destination) {
        this.id = id;
        this.source = source;
        this.destination = destination;
    }

    public String getId() {
        return id;
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
