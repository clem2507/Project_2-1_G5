package Abalon.AI;

import java.util.ArrayList;
import java.util.List;

public class Tree {
    public final List<Node> nodes;
    public final List<Edge> edges;

    public Tree(List<Node> vertexes, List<Edge> edges) {
        this.nodes = vertexes;
        this.edges = edges;
    }

    public List<Node> getVertexes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public boolean adjacent(Node x, Node y)	{
        // Returns true when there’s an edge from x to y

        for(Edge e : edges){
            if (e.getSource() == x){
                if(e.getDestination() == y){
                    return true;
                }
            }else if (e.getSource() == y){
                if(e.getDestination() == x) {
                    return true;
                }
            }
        }
        return false;
    }

    public List<Node> getNeighbours(Node v) { // String vertex
        // Returns all neighbours of a given vertex
        List<Node> neighbours = new ArrayList<Node>();

        for (Edge e : edges){
            if(e.getSource() == v){
                neighbours.add(e.getDestination());
            }else if(e.getDestination() == v){
                neighbours.add(e.getSource());
            }
        }
        return neighbours;
    }


}
