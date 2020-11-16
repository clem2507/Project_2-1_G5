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


    public ArrayList<int[][]> getPossibleMoves(int[][] board, int playerTurn) {

        allPossibleMoves.clear();

        allPossibleMoves.addAll(getTripleMarbleMoves(board, playerTurn));

        allPossibleMoves.addAll(getDoubleMarbleMoves(board, playerTurn));

        allPossibleMoves.addAll(getSingleMarbleMoves(board, playerTurn));

        return allPossibleMoves; //this will be an arraylist containing move objects
    }

    public ArrayList<int[][]> getSingleMarbleMoves(int[][] board, int playerTurn) {


        ArrayList<int[][]> singleMarbleLocations = new ArrayList<>();
        for(int i = 0; i<board.length; i++) { // find every marble that belongs to the player and create a pushing array
            for(int j = 0; j < board[i].length; j++) {
                if(board[i][j] == playerTurn) {
                    int[][] pushing = new int[1][2];
                    pushing[0][0] = i;
                    pushing[0][1] = j;
                    singleMarbleLocations.add(pushing);
                }
            }
        }
        return possibleMovesGivenPushing(singleMarbleLocations, board, playerTurn);
    }

    public ArrayList<int[][]> getDoubleMarbleMoves(int[][] board, int playerTurn) {
        ArrayList<int[][]> combinationsOfMarbles = new ArrayList<>();
        Move justAMove = new Move();
        justAMove.pushing = new int[0][0];
        justAMove.board = board;
        Rules searchRules = new Rules(justAMove);


        for(int i = 0; i < board.length; i++) { // search for all combinations of two marbles the player can make
                                                /* NOTE: there are some missing directions. This is to prevent unnecessarily
                                                   finding vertically oriented combinations twice.
                                                */
            for(int j = 0; j < board[i].length; j++) {

                if(board[j][i] == playerTurn) {
                    int[] marbleLocation = new int[] {j,i};

                    if(searchRules.checkSquareForColor(marbleLocation, MoveDirection.BOTTOM_LEFT, board) == playerTurn) {
                        int[][] newCombination = new int[2][2];
                        newCombination[0] = marbleLocation;
                        newCombination[1] = searchRules.checkSquareForLocation(marbleLocation, MoveDirection.BOTTOM_LEFT, board);

                        combinationsOfMarbles.add(newCombination);

                    }

                    if(searchRules.checkSquareForColor(marbleLocation, MoveDirection.BOTTOM_RIGHT, board) == playerTurn) {
                        int[][] newCombination = new int[2][2];
                        newCombination[0] = marbleLocation;
                        newCombination[1] = searchRules.checkSquareForLocation(marbleLocation, MoveDirection.BOTTOM_RIGHT, board);

                        combinationsOfMarbles.add(newCombination);
                    }

                    if(searchRules.checkSquareForColor(marbleLocation, MoveDirection.RIGHT, board) == playerTurn) {
                        int[][] newCombination = new int[2][2];
                        newCombination[0] = marbleLocation;
                        newCombination[1] = searchRules.checkSquareForLocation(marbleLocation, MoveDirection.RIGHT, board);

                        combinationsOfMarbles.add(newCombination);

                    }

                }
            }
        }

        return possibleMovesGivenPushing(combinationsOfMarbles, board, playerTurn);
    }

    public ArrayList<int[][]> getTripleMarbleMoves(int[][] board, int playerTurn) {
        ArrayList<int[][]> combinationsOfMarbles = new ArrayList<>();
        Move justAMove = new Move();
        justAMove.pushing = new int[0][0];
        justAMove.board = board;
        Rules searchRules = new Rules(justAMove);


        for(int i = 0; i < board.length; i++) { // search for all combinations of three marbles the player can make
                                                /* NOTE: there are some missing directions. This is to prevent unnecessarily
                                                   finding vertically oriented combinations twice.
                                                */
            for(int j = 0; j < board[i].length; j++) {

                if(board[j][i] == playerTurn) {
                    int[] marbleLocation = new int[] {j,i};

                    if(searchRules.checkSquareForColor(marbleLocation, MoveDirection.BOTTOM_LEFT, board) == playerTurn) {
                        int[] secondMarbleLocation = searchRules.checkSquareForLocation(marbleLocation, MoveDirection.BOTTOM_LEFT, board);
                        if(searchRules.checkSquareForColor(secondMarbleLocation, MoveDirection.BOTTOM_LEFT, board) == playerTurn) {
                            int[][] newCombination = new int[3][2];
                            newCombination[0] = marbleLocation;
                            newCombination[1] = secondMarbleLocation;
                            newCombination[2] = searchRules.checkSquareForLocation(secondMarbleLocation, MoveDirection.BOTTOM_LEFT, board);

                            combinationsOfMarbles.add(newCombination);
                        }
                    }

                    if(searchRules.checkSquareForColor(marbleLocation, MoveDirection.BOTTOM_RIGHT, board) == playerTurn) {
                        int[] secondMarbleLocation = searchRules.checkSquareForLocation(marbleLocation, MoveDirection.BOTTOM_RIGHT, board);
                        if(searchRules.checkSquareForColor(secondMarbleLocation, MoveDirection.BOTTOM_RIGHT, board) == playerTurn) {
                            int[][] newCombination = new int[3][2];
                            newCombination[0] = marbleLocation;
                            newCombination[1] = secondMarbleLocation;
                            newCombination[2] = searchRules.checkSquareForLocation(secondMarbleLocation, MoveDirection.BOTTOM_RIGHT, board);

                            combinationsOfMarbles.add(newCombination);
                        }
                    }

                    if(searchRules.checkSquareForColor(marbleLocation, MoveDirection.RIGHT, board) == playerTurn) {
                        int[] secondMarbleLocation = searchRules.checkSquareForLocation(marbleLocation, MoveDirection.RIGHT, board);
                        if(searchRules.checkSquareForColor(secondMarbleLocation, MoveDirection.RIGHT, board) == playerTurn) {
                            int[][] newCombination = new int[3][2];
                            newCombination[0] = marbleLocation;
                            newCombination[1] = secondMarbleLocation;
                            newCombination[2] = searchRules.checkSquareForLocation(secondMarbleLocation, MoveDirection.RIGHT, board);

                            combinationsOfMarbles.add(newCombination);
                        }
                    }


                }
            }
        }
        return possibleMovesGivenPushing(combinationsOfMarbles, board, playerTurn);
    }

    public ArrayList<int[][]> possibleMovesGivenPushing(ArrayList<int[][]> possiblePushing, int[][] board, int playerTurn) {
        ArrayList<int[][]> possibleMovesGivenPushing = new ArrayList<>();
        for(int i = 0; i<possiblePushing.size(); i++) {
            Move mv = new Move(); //create a move object
            mv.pushing = possiblePushing.get(i);

            int[][] newBoard = new int[board.length][];
            for(int k = 0; k < board.length; k++) {
                newBoard[k] = board[k].clone();
            }
            mv.board = newBoard;
            mv.turn = playerTurn;

            mv.dir = MoveDirection.TOP_LEFT;
            rules = new Rules(mv);
            if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                rules.move(); //change the cloned board to the state after the move has been made
                possibleMovesGivenPushing.add(mv.board); //add this state to the array of possible moves

                newBoard = new int[board.length][]; //create a new copy of the current gamestate
                for(int k = 0; k < board.length; k++) {
                    newBoard[k] = board[k].clone();
                }
                mv.board = newBoard;
            }

            mv.dir = MoveDirection.LEFT;
            rules = new Rules(mv);
            //rules = new Rules(mv);
            if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                rules.move();
                possibleMovesGivenPushing.add(mv.board);

                newBoard = new int[board.length][]; //create a new copy of the current gamestate
                for(int k = 0; k < board.length; k++) {
                    newBoard[k] = board[k].clone();
                }
                mv.board = newBoard;
            }

            mv.dir = MoveDirection.BOTTOM_LEFT;
            rules = new Rules(mv);
            if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                rules.move();
                possibleMovesGivenPushing.add(mv.board);

                newBoard = new int[board.length][]; //create a new copy of the current gamestate
                for(int k = 0; k < board.length; k++) {
                    newBoard[k] = board[k].clone();
                }
                mv.board = newBoard;
            }

            mv.dir = MoveDirection.TOP_RIGHT;
            rules = new Rules(mv);
            if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                rules.move();
                possibleMovesGivenPushing.add(mv.board);

                newBoard = new int[board.length][]; //create a new copy of the current gamestate
                for(int k = 0; k < board.length; k++) {
                    newBoard[k] = board[k].clone();
                }
                mv.board = newBoard;
            }

            mv.dir = MoveDirection.RIGHT;
            rules = new Rules(mv);
            if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                rules.move();
                possibleMovesGivenPushing.add(mv.board);

                newBoard = new int[board.length][]; //create a new copy of the current gamestate
                for(int k = 0; k < board.length; k++) {
                    newBoard[k] = board[k].clone();
                }
                mv.board = newBoard;
            }

            mv.dir = MoveDirection.BOTTOM_RIGHT;
            rules = new Rules(mv);
            if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                rules.move();
                possibleMovesGivenPushing.add(mv.board);
            }
        }
        return possibleMovesGivenPushing;
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

    public List<Node> getChildren(Node v) { // String vertex
        // Returns all neighbours of a given vertex
        List<Node> children = new ArrayList<Node>();

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
}

