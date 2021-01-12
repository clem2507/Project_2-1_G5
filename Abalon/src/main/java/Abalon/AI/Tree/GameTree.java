package Abalon.AI.Tree;

import Abalon.AI.EvaluationFunction.DefensiveEvalFunct;
import Abalon.AI.EvaluationFunction.NeutralEvalFunct;
import Abalon.AI.EvaluationFunction.OffensiveEvalFunct;

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

    private static ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Node> previousGeneration = new ArrayList<>();
    private ArrayList<Node> currentGeneration = new ArrayList<>();

    private int generation;
    private int generationCounter = 1;

    private HashTable table;
    private int prunedNodes = 0;

    private int strategy;

    public GameTree(int strategy){

        //create the tree, start with the initial board
        table = new HashTable();
        this.strategy = strategy;
    }

    public void createTree(int[][] currentRoot, int currentPlayer, int depth){

        this.generation = depth;

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

        //for (Node node : nodes) {
            // change the current player
            //evaluateNodes(node, currentPlayer);
        //}
    }


    public void createChildren(Node parent, int[][] currentBoardState, int currentPlayer){

        ArrayList<int[][]> childrenStates = getPossibleMoves.getPossibleMoves(currentBoardState, currentPlayer);

        NeutralEvalFunct neutral;
        OffensiveEvalFunct offensive;
        DefensiveEvalFunct defensive;

        for(int[][] child : childrenStates){

            double score = 0;

            if (table.checkInTable(currentPlayer, child)) {
                if (strategy == 1) {
                    neutral = new NeutralEvalFunct(currentPlayer, child, root.getBoardState());
                    score = neutral.evaluate();
                }
                else if (strategy == 2) {
                    offensive = new OffensiveEvalFunct(currentPlayer, child, root.getBoardState());
                    score = offensive.evaluate();
                }
                else if (strategy == 3){
                    defensive = new DefensiveEvalFunct(currentPlayer, child, root.getBoardState());
                    score = defensive.evaluate();
                }
                table.addInTable(score, generationCounter);
            }
            else {
                if (generationCounter >= table.getTable()[table.index].getDepth()) {
                    score = table.getTable()[table.index].getScore();
                }
                else {
                    if (strategy == 1) {
                        neutral = new NeutralEvalFunct(currentPlayer, child, root.getBoardState());
                        score = neutral.evaluate();
                    }
                    else if (strategy == 2) {
                        offensive = new OffensiveEvalFunct(currentPlayer, child, root.getBoardState());
                        score = offensive.evaluate();
                    }
                    else {
                        defensive = new DefensiveEvalFunct(currentPlayer, child, root.getBoardState());
                        score = defensive.evaluate();
                    }
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

    /*
    public void evaluateNodes(Node parent, int currentPlayer) {

        NeutralEvalFunct neutral;
        OffensiveEvalFunct offensive;
        DefensiveEvalFunct defensive;
        if (strategy == 1) {
            neutral = new NeutralEvalFunct(currentPlayer, parent.getBoardState(), root.getBoardState());
            parent.setScore(neutral.evaluate());
        }
        else if (strategy == 2) {

            offensive = new OffensiveEvalFunct(parent, currentPlayer, parent.getBoardState(), root.getBoardState());
            parent.setScore(offensive.evaluate());
        }
        else {
            defensive = new DefensiveEvalFunct(currentPlayer, parent.getBoardState(), root.getBoardState());
            parent.setScore(defensive.evaluate());
        }
    }
    */

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

    public static ArrayList<Node> getChildren(Node v) { // String vertex
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


