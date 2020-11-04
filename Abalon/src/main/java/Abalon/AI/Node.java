package Abalon.AI;
import java.util.ArrayList;
import java.util.List;

public class Node{

    private double score; //represent the score of a node, defined by the evaluation function
    private int[][] boardState; //2D array of type cell colors, represent the state of the board

    private List<Node> children = new ArrayList<>();
    private Node parent = null;
    private Node root = null;

    public Node(int[][] boardState, int score) {
        //get the values of the state and its score with their getters or not?
        this.boardState = getBoardState();
        this.score = getScore();
    }

    public double getScore() { //get the score by the evaluation function
//        EvaluationFunction evaluationFunction = new EvaluationFunction(1, cellColor, rootCellColor);
//        score = evaluationFunction.evaluate();
        return score;
    }

    public int[][] getBoardState() {
        return boardState;
    }

    public Node getRoot() {
        return this.root;
    }

//    public List<Node> setChildren(){
//        //set children with possible moves (use Mathias' method)
//    }


//    public List<Node> getChildren() {
            //get all possible moves from a board state, computed by Mathias' method
//    }

}
