package Abalon.AI;

import java.util.*;

public class GameTree {
    /**
     * Creates the game tree data structure
     * each node represents a state
     * then the algorithms will take care of going through the tree and pick a move
     */

    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Node> previousGeneration = new ArrayList<>();
    private ArrayList<Node> currentGeneration = new ArrayList<>();
    private int counter = 1;
    private int generation = 3;

    private int count = 0;

    private static ArrayList<int[][]> list;

    public GameTree(){
        //create the tree, start with the initial board
    }

    //public ArrayList<int[][]> findPossibleMoves(int currentPlayer, int[][] currentBoardState){

         //ArrayList<int[][]> children = new ArrayList<>();
        //return children;
    //}


    public void createTree(int[][] currentRoot, int currentPlayer){
        Node root = new Node(currentRoot, 0);

        nodes.add(root);

        if (currentPlayer == 1) {
            currentPlayer = 2;
        } else {
            currentPlayer = 1;
        }

        createChildren(root, root.getBoardState(), currentPlayer);

        /*count = 0;

        while (count < previousGeneration.size()) {

            createChildren(previousGeneration.get(count), previousGeneration.get(count).getBoardState(), currentPlayer);

            count++;
        }*/


        while(counter < generation){

            if (currentPlayer == 1) {
                currentPlayer = 2;
            } else {
                currentPlayer = 1;
            }

            // System.out.println(previousGeneration);

            counter++;

            for(Node n : previousGeneration) {
                createChildren(n, n.getBoardState(), currentPlayer);
                // System.out.println(count);
                count++;
            }

            previousGeneration.clear();
            previousGeneration.addAll(currentGeneration);
        }

        System.out.println("nodes size = " + nodes.size());
    }


    public void createChildren(Node parent, int[][] currentBoardState, int currentPlayer){
        //List<int[][]> childrenStates = findPossibleMoves(currentPlayer, currentBoardState);

        ArrayList<int[][]> childrenStates = findPossibleMoves(60);

        // System.out.println("childrenStates = " + childrenStates);

        for(int[][] child : childrenStates){

            // System.out.println(Arrays.deepToString(root.getBoardState()));
            // EvaluationFunction evaluationFunction = new EvaluationFunction(currentPlayer, child, root.getBoardState());
            // double score = evaluationFunction.evaluate();
            double score = 10;
            Node node = new Node(child, score);

            nodes.add(node);

            Edge edge = new Edge(parent, node);
            edges.add(edge);

            if(counter == 1){
                previousGeneration.add(node);
            }else{
                currentGeneration.add(node);
            }
        }
   }

   public ArrayList<Node> getNodes() {
        return nodes;
   }


    public static ArrayList<int[][]> findPossibleMoves(int size) {

        Random r = new Random();

        list = new ArrayList<>();

        int[][] cellColor = new int[9][9];

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

    public List<int[][]> getList() {

        return list;
    }
}
