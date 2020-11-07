package Abalon.AI;

import java.util.ArrayList;
import java.util.List;

public class GameTree {
    /**
     * Creates the game tree data structure
     * each node represents a state
     * then the algorithms will take care of going through the tree and pick a move
     */

    private List<Edge> edges;
    private List<Node> nodes;
    private int counter = 0;
    private int generation = 3;

    public GameTree(){
        //create the tree, start with the initial board
    }

    public ArrayList<int[][]> findPossibleMoves(int currentPlayer, int[][] currentBoardState){

         ArrayList<int[][]> children = new ArrayList<>();
        return children;

    }


    public void createTree(int[][] currentRoot, int currentPlayer){
        Node root = new Node(currentRoot, 0);

        nodes.add(root);

        createChildren(root, root.getBoardState(), currentPlayer);
    }


    public void createChildren(Node root, int[][] currentBoardState, int currentPlayer){

        if(currentPlayer == 1){
            currentPlayer = 2;
        }

        List<int[][]> childrenStates = findPossibleMoves(currentPlayer, currentBoardState);

        for(int[][] child : childrenStates){

            EvaluationFunction evaluationFunction = new EvaluationFunction(currentPlayer, child, root.getBoardState());
            double score = evaluationFunction.evaluate();
            Node node = new Node(child, score);

            nodes.add(node);

            Edge edge = new Edge(root, node);
            edges.add(edge);


            while(counter<generation){
                if(counter%2 == 0){
                    currentPlayer = 1;
                }
                else{
                    currentPlayer = 2;
                }

                createChildren(root, child, currentPlayer);
                counter++;
            }

            if(counter == generation){
                counter = 0;
            }
        }
   }



}
