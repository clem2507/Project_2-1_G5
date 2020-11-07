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
        this.boardState = boardState;
        this.score = score;
    }

    public double getScore(int currentPlayer, int[][] currentBoardState) { //get the score by the evaluation function
        EvaluationFunction evaluationFunction = new EvaluationFunction(currentPlayer, currentBoardState, GameTree.rootBoardState);
        score = evaluationFunction.evaluate();
        return score;
    }

    public int[][] getBoardState() {
        return boardState;
    }

    public Node getRoot() {
        return root;
    }

//    public List<Node> setChildren(){
//        //set children with possible moves (use Mathias' method)
//    }


//    public List<Node> getChildren() {
            //get all possible moves from a board state, computed by Mathias' method
//    }

}
