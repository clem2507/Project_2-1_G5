package Abalone.AI.EvaluationFunction;

import Abalone.AI.Tree.GetPossibleMoves;

import java.util.ArrayList;

public class OffensiveEvalFunct extends EvaluationFunction {

    /**
     -----------------------------------------------------

     HEURISTICS:

     v1bis = the current player board center distance
     v2bis = the current player marbles cohesion
     v5 = difference between root opponent player marble count and actual board count
     v7 = total count of sumito positions
     v8 = total count of pushing positions

     WEIGHTS:

     w1 = -2
     w2 = 3
     w5 = 10000
     w7 = 300
     w8 = 70

     E(s) = w1*v1bis + w2*v2bis + w5*v5 + w7*v7 + w8*v8

     -----------------------------------------------------
     */

    private ArrayList<int[][]> children;

    private int v1bis;
    private int v2bis;
    private int v5;
    private int v7;
    private int v8;

    private double w1 = -2;
    private double w2 = 3;
    private double w5 = 10000;
    private double w7 = 300;
    private double w8 = 70;

    public OffensiveEvalFunct(int currentPlayer, int[][] cellColor, int[][] rootCellColor) {
        super(currentPlayer, cellColor, rootCellColor);
    }

    public void computeValues() {

        //v1bis
        int ownMarblesCenterDistance;

        //v2bis
        int ownMarblesNeighbour;

        // v5
        int currentStateOpponentMarblesCount;
        int rootOpponentMarblesCount;

        // v7
        int currentPlayerSumitos;

        // v8
        int currentPlayerPushing;

        if (currentPlayer == 1) {
            ownMarblesCenterDistance = NeutralEvalFunct.centerDistance(currentPlayer, cellColor);

            ownMarblesNeighbour = NeutralEvalFunct.marblesNeighbourhood(currentPlayer, cellColor);

            currentStateOpponentMarblesCount = NeutralEvalFunct.countMarbles(currentPlayer+1, cellColor);
            rootOpponentMarblesCount = NeutralEvalFunct.countMarbles(currentPlayer+1, rootCellColor);

            children = getPossiblePushingMoves(cellColor, currentPlayer);

            currentPlayerSumitos = countSumitoPositions(children, cellColor, currentPlayer+1);

            currentPlayerPushing = countPushingPositions(children, cellColor, currentPlayer+1);
        }
        else {
            ownMarblesCenterDistance = NeutralEvalFunct.centerDistance(currentPlayer, cellColor);

            ownMarblesNeighbour = NeutralEvalFunct.marblesNeighbourhood(currentPlayer, cellColor);

            currentStateOpponentMarblesCount = NeutralEvalFunct.countMarbles(currentPlayer-1, cellColor);
            rootOpponentMarblesCount = NeutralEvalFunct.countMarbles(currentPlayer-1, rootCellColor);

            children = getPossiblePushingMoves(cellColor, currentPlayer);

            currentPlayerSumitos = countSumitoPositions(children, cellColor, currentPlayer-1);

            currentPlayerPushing = countPushingPositions(children, cellColor, currentPlayer-1);
        }

        v1bis = ownMarblesCenterDistance;
        v2bis = ownMarblesNeighbour;
        v5 = rootOpponentMarblesCount - currentStateOpponentMarblesCount;
        v7 = currentPlayerSumitos;
        v8 = currentPlayerPushing;
    }

    public ArrayList<int[][]> getPossiblePushingMoves(int[][] board, int currentPlayer) {

        GetPossibleMoves obj = new GetPossibleMoves();
        ArrayList<int[][]> doubles = obj.getDoubleMarbleMoves(board, currentPlayer, false);
        ArrayList<int[][]> triples = obj.getTripleMarbleMoves(board, currentPlayer, false);

        doubles.addAll(triples);

        return doubles;
    }

    /**
     * all positions where current player can push out
     * =
     * all positions where opponent player can be pushed out
     */
    public static int countSumitoPositions(ArrayList<int[][]> children, int[][] board, int opponentPlayer){

        int count = 0;
        for(int[][] child : children){
            if(NeutralEvalFunct.countMarbles(opponentPlayer, board) > NeutralEvalFunct.countMarbles(opponentPlayer, child)){
                count++;
            }
        }
        return count;
    }

    public int countPushingPositions(ArrayList<int[][]> children, int[][] board, int opponentPlayer) {

        int count = 0;
        for (int[][] child : children) {
            if (!checkOpponentMarble(board, child, opponentPlayer)) {
                count++;
            }
        }
        return count;
    }

    public static boolean checkOpponentMarble(int[][] board, int[][] currentBoard, int opponentPlayer) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                if (board[i][j] == opponentPlayer && currentBoard[i][j] != opponentPlayer) {
                    return false;
                }
            }
        }
        return true;
    }

    public double evaluate() {
        computeValues();
        return w1 * v1bis + w2 * v2bis + w5 * v5 + w7 * v7 + w8 * v8;
    }
    
    public void setWeights(double w1_, double w2_, double w3_, double w4_, double w5_) {
        w1 = w1_;
        w2 = w2_;
        w5 = w3_;
        w7 = w4_;
        w8 = w5_;
    }

    @Override
    public void setUnifiedWeights(double[] weights) {
        setWeights(weights[0], weights[1], weights[2], weights[3], weights[4]);
    }
}

