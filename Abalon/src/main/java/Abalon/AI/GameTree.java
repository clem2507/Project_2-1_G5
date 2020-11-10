package Abalon.AI;

import java.util.*;

public class GameTree {
    /**
     * Creates the game tree data structure
     * each node represents a state
     * then the algorithms will take care of going through the tree and pick a move
     */

    private Node root;

    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Node> previousGeneration = new ArrayList<>();
    private ArrayList<Node> currentGeneration = new ArrayList<>();
    private final int generation = 3;
    private int counter = 1;

    private static ArrayList<int[][]> list;

    public GameTree(){
        //create the tree, start with the initial board
    }


    public void createTree(int[][] currentRoot, int currentPlayer){
        root = new Node(currentRoot, 0);

        nodes.add(root);

        if (currentPlayer == 1) {
            currentPlayer = 2;
        }
        else {
            currentPlayer = 1;
        }

        createChildren(root, root.getBoardState(), currentPlayer);

        while(counter < generation){

            if (currentPlayer == 1) {
                currentPlayer = 2;
            } else {
                currentPlayer = 1;
            }

            counter++;

            for(Node n : previousGeneration) {
                createChildren(n, n.getBoardState(), currentPlayer);
            }

            previousGeneration.clear();
            previousGeneration.addAll(currentGeneration);
        }
    }


    public void createChildren(Node parent, int[][] currentBoardState, int currentPlayer){

        // ArrayList<int[][]> childrenStates = findPossibleMoves(currentPlayer, currentBoardState);

        ArrayList<int[][]> childrenStates = findRandomPossibleMoves(60);

        for(int[][] child : childrenStates){

            EvaluationFunction evaluationFunction = new EvaluationFunction(currentPlayer, child, root.getBoardState());
            double score = evaluationFunction.evaluate();

            Node node = new Node(child, score);
            nodes.add(node);

            Edge edge = new Edge(parent, node);
            edges.add(edge);

            if(counter == 1) {
                previousGeneration.add(node);
            }
            else {
                currentGeneration.add(node);
            }
        }
   }


    public static ArrayList<int[][]> findRandomPossibleMoves(int size) {

        Random r = new Random();

        list = new ArrayList<>();

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

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < cellColor.length; j++) {
                for (int k = 0; k < cellColor.length; k++) {
                    if (cellColor[j][k] == 0 || cellColor[j][k] == 1 || cellColor[j][k] == 2) {
                        int randomNum = r.nextInt((2) + 1);
                        cellColor[j][k] = randomNum;
                    }
                }
            }
            list.add(cellColor);
        }
        return list;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }
}
