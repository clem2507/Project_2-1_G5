package Abalon.AI.Tree;

import Abalon.Main.Move;
import Abalon.Main.MoveDirection;
import Abalon.Main.Rules;
import java.util.Collections;
import java.util.ArrayList;

public class GetPossibleMoves {

    private Rules rules;
    private ArrayList<int[][]> allPossibleMoves = new ArrayList<>();
    private ArrayList<int[][]> capturing = new ArrayList<>();
    private ArrayList<int[][]> attacking = new ArrayList<>();
    private ArrayList<int[][]> threeMarblesMoving = new ArrayList<>();
    private ArrayList<int[][]> twoMarblesMoving = new ArrayList<>();
    private ArrayList<int[][]> oneMarbleMoving = new ArrayList<>();

    public ArrayList<int[][]> getPossibleMoves(int[][] board, int playerTurn) {
        //clear all arrays for new possible moves in a position
        allPossibleMoves.clear();
        capturing.clear();
        attacking.clear();
        threeMarblesMoving.clear();
        twoMarblesMoving.clear();
        oneMarbleMoving.clear();

        //three marbles capturing
        threeMarblesMoving.addAll(getTripleMarbleMoves(board, playerTurn, true));

        twoMarblesMoving.addAll(getDoubleMarbleMoves(board, playerTurn, true));

        oneMarbleMoving.addAll(getSingleMarbleMoves(board, playerTurn));

        /*move ordering:
            3 marbles, capturing
            2 marbles, capturing
            3 marbles, attacking
            2 marbles, attacking
            3 marbles, moving
            2 marbles, moving
            1 marble, moving
         */

        allPossibleMoves.addAll(capturing); //capturing is already ordered by 3 marbles first then 2 marbles second.
        allPossibleMoves.addAll(attacking); //attacking is already ordered by 3 marbles first then 2 marbles second.
        allPossibleMoves.addAll(threeMarblesMoving);
        allPossibleMoves.addAll(twoMarblesMoving);
        allPossibleMoves.addAll(oneMarbleMoving);

        return allPossibleMoves; //this will be an arraylist containing move objects
    }
    //returns a list with the ordering we were using in phase 2
    public ArrayList<int[][]> getPossibleMovesPreviousOrdering(int[][] board, int playerTurn) {
        allPossibleMoves.clear();

        allPossibleMoves.addAll(getTripleMarbleMoves(board, playerTurn, false));
        allPossibleMoves.addAll(getDoubleMarbleMoves(board, playerTurn, false));
        allPossibleMoves.addAll(getSingleMarbleMoves(board, playerTurn));

        return allPossibleMoves;

    }
    //shuffles the list so that we can compare with no ordering at all
    public ArrayList<int[][]> getPossibleMovesNoOrdering(int[][] board, int playerTurn) {
        allPossibleMoves.clear();

        allPossibleMoves.addAll(getTripleMarbleMoves(board, playerTurn, false));
        allPossibleMoves.addAll(getDoubleMarbleMoves(board, playerTurn, false));
        allPossibleMoves.addAll(getSingleMarbleMoves(board, playerTurn));

        Collections.shuffle(allPossibleMoves); //shuffle the list as it is currently ordered between 3, 2, and 1 marbles

        return allPossibleMoves;
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
        return possibleMovesGivenPushing(singleMarbleLocations, board, playerTurn, false);
    }

    public ArrayList<int[][]> getDoubleMarbleMoves(int[][] board, int playerTurn, boolean ordering) {
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

        return possibleMovesGivenPushing(combinationsOfMarbles, board, playerTurn, ordering);
    }

    public ArrayList<int[][]> getTripleMarbleMoves(int[][] board, int playerTurn, boolean ordering) {
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
        return possibleMovesGivenPushing(combinationsOfMarbles, board, playerTurn, ordering);
    }

    public ArrayList<int[][]> possibleMovesGivenPushing(ArrayList<int[][]> possiblePushing, int[][] board, int playerTurn, boolean ordering) {
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

                if(ordering && checkIfScoringMove(mv.board, board, playerTurn)) { //check if the move pushes an opponent's marble off the board. if it does, add it to the capture move list.
                    capturing.add(mv.board);
                }
                else if(ordering && checkIfPushingMove(mv.board, board, playerTurn)) { //check if the move pushes an opponent's marble at all. if it does, add it to the attacking move list
                    attacking.add(mv.board);
                }
                else { // if the move is neither capturing nor attacking, add it to the regular moves.
                    possibleMovesGivenPushing.add(mv.board);
                }

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

                if(ordering && checkIfScoringMove(mv.board, board, playerTurn)) { //check if the move pushes an opponent's marble off the board. if it does, add it to the capture move list.
                    capturing.add(mv.board);
                }
                else if(ordering && checkIfPushingMove(mv.board, board, playerTurn)) { //check if the move pushes an opponent's marble at all. if it does, add it to the attacking move list
                    attacking.add(mv.board);
                }
                else { // if the move is neither capturing nor attacking, add it to the regular moves.
                    possibleMovesGivenPushing.add(mv.board);
                }

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

                if(ordering && checkIfScoringMove(mv.board, board, playerTurn)) { //check if the move pushes an opponent's marble off the board. if it does, add it to the capture move list.
                    capturing.add(mv.board);
                }
                else if(ordering && checkIfPushingMove(mv.board, board, playerTurn)) { //check if the move pushes an opponent's marble at all. if it does, add it to the attacking move list
                    attacking.add(mv.board);
                }
                else { // if the move is neither capturing nor attacking, add it to the regular moves.
                    possibleMovesGivenPushing.add(mv.board);
                }

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

                if(ordering && checkIfScoringMove(mv.board, board, playerTurn)) { //check if the move pushes an opponent's marble off the board. if it does, add it to the capture move list.
                    capturing.add(mv.board);
                }
                else if(ordering && checkIfPushingMove(mv.board, board, playerTurn)) { //check if the move pushes an opponent's marble at all. if it does, add it to the attacking move list
                    attacking.add(mv.board);
                }
                else { // if the move is neither capturing nor attacking, add it to the regular moves.
                    possibleMovesGivenPushing.add(mv.board);
                }

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

                if(ordering && checkIfScoringMove(mv.board, board, playerTurn)) { //check if the move pushes an opponent's marble off the board. if it does, add it to the capture move list.
                    capturing.add(mv.board);
                }
                else if(ordering && checkIfPushingMove(mv.board, board, playerTurn)) { //check if the move pushes an opponent's marble at all. if it does, add it to the attacking move list
                    attacking.add(mv.board);
                }
                else { // if the move is neither capturing nor attacking, add it to the regular moves.
                    possibleMovesGivenPushing.add(mv.board);
                }

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

                if(ordering && checkIfScoringMove(mv.board, board, playerTurn)) { //check if the move pushes an opponent's marble off the board. if it does, add it to the capture move list.
                    capturing.add(mv.board);
                }
                else if(ordering && checkIfPushingMove(mv.board, board, playerTurn)) { //check if the move pushes an opponent's marble at all. if it does, add it to the attacking move list
                    attacking.add(mv.board);
                }
                else { // if the move is neither capturing nor attacking, add it to the regular moves.
                    possibleMovesGivenPushing.add(mv.board);
                }
            }
        }
        return possibleMovesGivenPushing;
    }
    /*checks if any of the opposing marbles moved. if they did it will return true and the move is a move that pushes*/
    public boolean checkIfPushingMove(int[][] board, int[][] currentBoard, int playerTurn) {

        int opponentPlayer;

        if(playerTurn == 1) {
            opponentPlayer = 2;
        } else {
            opponentPlayer = 1;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == opponentPlayer && currentBoard[i][j] != opponentPlayer) {
                    return true;
                }
            }
        }
        return false;
    }
    /*checks if the count of opposing marbles is the same before the turn as after the turn. If it's not, it will return true and the move scores*/
    public boolean checkIfScoringMove(int[][] board, int[][] currentBoard, int playerTurn) {

        int opponentPlayer;

        if(playerTurn == 1) {
            opponentPlayer = 2;
        } else {
            opponentPlayer = 1;
        }

        int boardCount = 0;
        int currentBoardCount = 0;

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == opponentPlayer) {
                    boardCount++;
                }
                if (currentBoard[i][j] == opponentPlayer) {
                    currentBoardCount++;
                }
            }
        }
        if(currentBoardCount == boardCount) {
            return false;
        }
        return true;
    }
}


