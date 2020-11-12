package Abalon.AI;
import java.util.ArrayList;
import java.util.List;

public class Node{

    private double score; //represent the score of a node, defined by the evaluation function
    private int[][] boardState; //2D array of type cell colors, represent the state of the board

//    private List<Node> children = new ArrayList<>();
    private Node parent = null;
    private Node root = null;

    public Node(int[][] boardState, double score) {
        this.boardState = boardState;
        this.score = score;
    }

    public double getScore() { //get the score by the evaluation function
        return score;
    }

    public int[][] getBoardState() {
        return boardState;
    }

    public Node getRoot() {
        return root;
    }
//  ???????
//    public List<Node> setChildren(){
//
//    }
//
//
//    public List<Node> getChildren() {
//        return children;
//    }

}
