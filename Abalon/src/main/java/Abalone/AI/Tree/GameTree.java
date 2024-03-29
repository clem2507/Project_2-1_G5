package Abalone.AI.Tree;

import Abalone.AI.EvaluationFunction.*;

import java.util.*;

/**
 * Creates the game tree data structure
 * each node represents a state
 * then the algorithms will take care of going through the tree and pick a move
 */
public class GameTree {

    private GetPossibleMoves getPossibleMoves = new GetPossibleMoves();

    private Node root;

    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Node> previousGeneration = new ArrayList<>();
    private ArrayList<Node> currentGeneration = new ArrayList<>();

    private int generation;
    private int generationCounter = 1;

    private HashTable table;
    private int investigatedNodes = 0;

    private int strategy;
    private boolean transpositionTable;
    // moveOrdering = 1, best ordering
    // moveOrdering = 2, normal ordering
    // moveOrdering = 3, random ordering
    private int moveOrdering;

    public GameTree(int strategy, int moveOrdering, boolean transpositionTable){

        this.strategy = strategy;
        this.moveOrdering = moveOrdering;
        this.transpositionTable = transpositionTable;
        if (transpositionTable) {
            table = new HashTable();
        }
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
    }


    public void createChildren(Node parent, int[][] currentBoardState, int currentPlayer){

        ArrayList<int[][]> childrenStates;
        if (moveOrdering == 1) {
            childrenStates = getPossibleMoves.getPossibleMoves(currentBoardState, currentPlayer);
        }
        else if (moveOrdering == 2) {
            childrenStates = getPossibleMoves.getPossibleMovesPreviousOrdering(currentBoardState, currentPlayer);
        }
        else {
            childrenStates = getPossibleMoves.getPossibleMovesNoOrdering(currentBoardState, currentPlayer);
        }

        EvaluationFunction eval;

        for(int[][] child : childrenStates){

            double score = 0;

            if (transpositionTable) {
                if (table.checkInTable(currentPlayer, child)) {
                    if (strategy == 1) {
                        eval = new NeutralEvalFunct(currentPlayer, child, root.getBoardState());
                        score = eval.evaluate();
                    } else if (strategy == 2) {
                        eval = new OffensiveEvalFunct(currentPlayer, child, root.getBoardState());
                        score = eval.evaluate();
                    } else if (strategy == 3) {
                        eval = new DefensiveEvalFunct(currentPlayer, child, root.getBoardState());
                        score = eval.evaluate();
                    } else if (strategy == 4) {
                        eval = new MixEvalFunct(currentPlayer, child, root.getBoardState());
                        score = eval.evaluate();
                    }
                    investigatedNodes++;
                    table.addInTable(score, generationCounter);
                } else {
                    if (generationCounter >= table.getTable()[table.index].getDepth()) {
                        score = table.getTable()[table.index].getScore();
                    } else {
                        if (strategy == 1) {
                            eval = new NeutralEvalFunct(currentPlayer, child, root.getBoardState());
                            score = eval.evaluate();
                        } else if (strategy == 2) {
                            eval = new OffensiveEvalFunct(currentPlayer, child, root.getBoardState());
                            score = eval.evaluate();
                        } else if (strategy == 3) {
                            eval = new DefensiveEvalFunct(currentPlayer, child, root.getBoardState());
                            score = eval.evaluate();
                        } else if (strategy == 4) {
                            eval = new MixEvalFunct(currentPlayer, child, root.getBoardState());
                            score = eval.evaluate();
                        }
                        investigatedNodes++;
                    }
                }
            }
            else {
                if (strategy == 1) {
                    eval = new NeutralEvalFunct(currentPlayer, child, root.getBoardState());
                    score = eval.evaluate();
                } else if (strategy == 2) {
                    eval = new OffensiveEvalFunct(currentPlayer, child, root.getBoardState());
                    score = eval.evaluate();
                } else if (strategy == 3) {
                    eval = new DefensiveEvalFunct(currentPlayer, child, root.getBoardState());
                    score = eval.evaluate();
                } else if (strategy == 4) {
                    eval = new MixEvalFunct(currentPlayer, child, root.getBoardState());
                    score = eval.evaluate();
                }
                investigatedNodes++;
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
        List<Node> neighbours = new ArrayList<>();

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
        ArrayList<Node> children = new ArrayList<>();

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

    public int getInvestigatedNodes() {
        return investigatedNodes;
    }

    public int getGeneration() {
        return generation;
    }
}

