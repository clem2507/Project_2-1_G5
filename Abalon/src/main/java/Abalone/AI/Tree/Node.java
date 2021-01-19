package Abalone.AI.Tree;

public class Node{

    private double score; //represent the score of a node, defined by the evaluation function
    private int[][] boardState; //2D array of type cell colors, represent the state of the board
    private int totalSimulation;
    private double totalScore;

    //    private List<Node> children = new ArrayList<>();
    private Node parent = null;
    private Node root = null;

    public Node(int[][] boardState, double score) {
        this.boardState = boardState;
        this.score = score;
    }

    // constructor used for the MCTS tree
    public Node(int[][] boardState, int totalSimulation, double totalScore) {
        this.boardState = boardState;
        this.totalSimulation = totalSimulation;
        this.totalScore = totalScore;
    }

    public double getScore() { //get the score by the evaluation function
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public int[][] getBoardState() {
        return boardState;
    }

    public Node getRoot() {
        return root;
    }

    public int getTotalSimulation() {
        return totalSimulation;
    }

    public void setTotalSimulation(int totalSimulation) {
        this.totalSimulation = totalSimulation;
    }

    public double getTotalScore() {
        return totalScore;
    }

    public void setTotalWin(double totalScore) {
        this.totalScore = totalScore;
    }
}

