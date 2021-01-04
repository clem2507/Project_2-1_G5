package Abalon.AI;

import Abalon.Main.Move;
import Abalon.Main.MoveDirection;
import Abalon.Main.Rules;

import java.util.*;

public class GameTree {

    GetPossibleMoves getPossibleMoves = new GetPossibleMoves();

    /**
     * Creates the game tree data structure
     * each node represents a state
     * then the algorithms will take care of going through the tree and pick a move
     */

    private ArrayList<int[][]> allPossibleMoves = new ArrayList<>();

    private Node root;

    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Node> previousGeneration = new ArrayList<>();
    private ArrayList<Node> currentGeneration = new ArrayList<>();

    private int generation;
    private int generationCounter = 1;

    private HashTable table;
    private int prunedNodes = 0;

    private int strategy;

    public GameTree(){

        //create the tree, start with the initial board
        table = new HashTable();
    }

    public void createTree(int[][] currentRoot, int currentPlayer, int depth, int strategy){

        this.generation = depth;
        this.strategy = strategy;

        root = new Node(currentRoot, 0);

        nodes.add(root);

        createChildren(root, root.getBoardState(), currentPlayer);

        while(generationCounter < generation){

            if (currentPlayer == 1) {
                currentPlayer = 2;
            } else {
                currentPlayer = 1;
            }

            generationCounter++;

            for(Node n : previousGeneration) {
                createChildren(n, n.getBoardState(), currentPlayer);
            }

            previousGeneration.clear();
            previousGeneration.addAll(currentGeneration);
        }
    }


    public void createChildren(Node parent, int[][] currentBoardState, int currentPlayer){

        ArrayList<int[][]> childrenStates = getPossibleMoves.getPossibleMoves(currentBoardState, currentPlayer);

        for(int[][] child : childrenStates){

            double score;

            if (table.checkInTable(currentPlayer, child)) {
                EvaluationFunction evaluationFunction = new EvaluationFunction(currentPlayer, child, root.getBoardState(), strategy);
                score = evaluationFunction.evaluate();
                table.addInTable(score, generationCounter);
            }
            else {
                if (generationCounter >= table.getTable()[table.index].getDepth()) {
                    score = table.getTable()[table.index].getScore();
                }
                else {
                    EvaluationFunction evaluationFunction = new EvaluationFunction(currentPlayer, child, root.getBoardState(), strategy);
                    score = evaluationFunction.evaluate();
                }
            }

            if(generationCounter == 1) {

                Node node = new Node(child, score);
                nodes.add(node);

                Edge edge = new Edge(parent, node);
                edges.add(edge);

                previousGeneration.add(node);
            }
            else {

                Node node = new Node(child, score);
                nodes.add(node);

                Edge edge = new Edge(parent, node);
                edges.add(edge);

                currentGeneration.add(node);
            }
        }
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

    public ArrayList<Node> getChildren(Node v) { // String vertex
        // Returns all neighbours of a given vertex
        ArrayList<Node> children = new ArrayList<Node>();

        for (Edge e : edges){
            if(e.getSource() == v) {
                children.add(e.getDestination());
            }
        }
        return children;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public int getPrunedNodes() {
        return prunedNodes;
    }

    public int getGeneration() {
        return generation;
    }

    public HashTable getTable() {
        return table;
    }
}


