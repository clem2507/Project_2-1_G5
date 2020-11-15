package Abalon.AI;

import Abalon.Main.Move;
import Abalon.Main.MoveDirection;
import Abalon.Main.Rules;

import java.util.*;

public class GameTree {
    /**
     * Creates the game tree data structure
     * each node represents a state
     * then the algorithms will take care of going through the tree and pick a move
     */

    private Rules rules;
    private ArrayList<int[][]> allPossibleMoves = new ArrayList<>();

    private Node root;

    private ArrayList<Edge> edges = new ArrayList<>();
    private ArrayList<Node> nodes = new ArrayList<>();
    private ArrayList<Node> previousGeneration = new ArrayList<>();
    private ArrayList<Node> currentGeneration = new ArrayList<>();
    private final int generation = 3;
    private int generationCounter = 1;

    private HashTable table;

    public GameTree(){
        //create the tree, start with the initial board

        table = new HashTable();
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

        ArrayList<int[][]> childrenStates = getPossibleMoves(currentBoardState, currentPlayer);

        for(int[][] child : childrenStates){

            EvaluationFunction evaluationFunction = new EvaluationFunction(currentPlayer, child, root.getBoardState());
            double score = evaluationFunction.evaluate();

            Node node = new Node(child, score);
            nodes.add(node);

            Edge edge = new Edge(parent, node);
            edges.add(edge);

            if(generationCounter == 1) {
                previousGeneration.add(node);
            }
            else {
                if(table.checkInTable(currentPlayer, child, score)) {
                    currentGeneration.add(node);
                }
            }
        }
    }


    public ArrayList<int[][]> getPossibleMoves(int[][] board, int playerTurn) {
        //TODO take the board and create move objects for every possible move the player can make


        allPossibleMoves.addAll(getSingleMarbleMoves(board, playerTurn));



        return allPossibleMoves; //this will be an arraylist containing move objects
    }

    public ArrayList<int[][]> getSingleMarbleMoves(int[][] board, int playerTurn) {
        ArrayList<int[][]> singleMarbleMoves = new ArrayList<>();

        for(int i = 0; i<board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == playerTurn) { //search the entire array for marbles belonging to player 1
                    Move mv = new Move(); //create a move object

                    mv.pushing = new int[3][2]; //add the first marble to pushing for the move
                    mv.pushing[0][0] = j;       //QUESTION: in what form does pushing usually come? Is this valid for the rules class?
                    mv.pushing[0][1] = i;

                    mv.board = board.clone(); //clone the board as it is as a new object that can be altered
                    mv.turn = playerTurn;

                    mv.dir = MoveDirection.TOP_LEFT;
                    rules = new Rules(mv);
                    if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                        rules.move(); //change the cloned board to the state after the move has been made
                        singleMarbleMoves.add(mv.board); //add this state to the array of possible moves
                    }

                    mv.dir = MoveDirection.LEFT;
                    mv.board = board.clone(); //create a new object from the game state before the move
                    //rules = new Rules(mv);
                    if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                        rules.move();
                        singleMarbleMoves.add(mv.board);
                    }

                    mv.dir = MoveDirection.BOTTOM_LEFT;
                    mv.board = board.clone();
                    if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                        rules.move();
                        singleMarbleMoves.add(mv.board);
                    }

                    mv.dir = MoveDirection.TOP_RIGHT;
                    mv.board = board.clone();
                    if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                        rules.move();
                        singleMarbleMoves.add(mv.board);
                    }

                    mv.dir = MoveDirection.RIGHT;
                    mv.board = board.clone();
                    if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                        rules.move();
                        singleMarbleMoves.add(mv.board);
                    }

                    mv.dir = MoveDirection.BOTTOM_RIGHT;
                    mv.board = board.clone();
                    if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                        rules.move();
                        singleMarbleMoves.add(mv.board);
                    }
                }

            }
        }
        return singleMarbleMoves;
    }

    public ArrayList<int[][]> doubleMarblemMoves(int[][] board, int playerTurn) {
        ArrayList<int[][]> doubleMarbleMoves = new ArrayList<>();
        return doubleMarbleMoves;
        //TODO find all combinations of two marbles then use the same operations as singleMarbleMoves
    }

    public ArrayList<int[][]> tripleMarbleMoves(int[][] board, int playerTurn) {
        ArrayList<int[][]> tripleMarbleMoves = new ArrayList<>();
        return tripleMarbleMoves;
        //TODO find all combinations of three marbles then use the same method operations as singleMarbleMoves
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

    public ArrayList<Node> getNodes() {
        return nodes;
    }
}

