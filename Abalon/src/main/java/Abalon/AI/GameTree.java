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

    public GameTree(){
        //create the tree, start with the initial board

        table = new HashTable();
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

        // ArrayList<int[][]> childrenStates = findRandomPossibleMoves(60);

        ArrayList<int[][]> childrenStates = getPossibleMoves.getPossibleMoves(currentBoardState, currentPlayer);

        for(int[][] child : childrenStates){

            EvaluationFunction evaluationFunction = new EvaluationFunction(currentPlayer, child, root.getBoardState());
            double score = evaluationFunction.evaluate();

            if(generationCounter == 1) {

                Node node = new Node(child, score);
                nodes.add(node);

                Edge edge = new Edge(parent, node);
                edges.add(edge);

                table.checkInTable(currentPlayer, child, score);

                previousGeneration.add(node);
            }
            else {
                if(table.checkInTable(currentPlayer, child, score)) {

                    Node node = new Node(child, score);
                    nodes.add(node);

                    Edge edge = new Edge(parent, node);
                    edges.add(edge);

                    currentGeneration.add(node);
                }
                else {
                    prunedNodes++;
                }
            }
        }
    }

    /*
    public ArrayList<int[][]> findRandomPossibleMoves(int size) {

        ArrayList<int[][]> list = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            list.add(createRandomArray());
        }
        return list;
    }

    public int[][] createRandomArray() {

        Random r = new Random();

        int[][] randomCellColor = new int[9][9];

        int[][] cellColor = new int[][] {

                {1, 1, 1, 1, 1, -1, -1, -1, -1},
                {1, 1, 1, 1, 1,  1, -1, -1, -1},
                {0, 0, 1, 1, 1,  0,  0, -1, -1},
                {0, 0, 0, 0, 0,  0,  0,  0, -1},
                {0, 0, 0, 0, 0,  0,  0,  0,  0},
                {0, 0, 0, 0, 0,  0,  0,  0, -1},
                {0, 0, 2, 2, 2,  0,  0, -1, -1},
                {2, 2, 2, 2, 2,  2, -1, -1, -1},
                {2, 2, 2, 2, 2, -1, -1, -1, -1}

        };

        for (int j = 0; j < cellColor.length; j++) {
            for (int k = 0; k < cellColor.length; k++) {
                if (cellColor[j][k] == 0 || cellColor[j][k] == 1 || cellColor[j][k] == 2) {
                    int randomNum = r.nextInt((2) + 1);
                    randomCellColor[j][k] = randomNum;
                }
                else {
                    randomCellColor[j][k] = -1;
                }
            }
        }
        return randomCellColor;
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
}


