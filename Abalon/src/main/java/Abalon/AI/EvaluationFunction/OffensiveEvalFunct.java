package Abalon.AI.EvaluationFunction;

import Abalon.AI.Tree.GetPossibleMoves;
import Abalon.Main.Abalon;

import java.util.ArrayList;

public class OffensiveEvalFunct {

    /**

    Heuristics that are good to use:

     // add the number of pushing positions as an heuristics
     // as well as the number of push out positions

    // Possible sumito positions
    // -> Try to have as much as possible sumito positions compared to the opponent
    // As much as possible lost marbles for to the opponent

    // -> v4, v5 to use from neutral strategy and sumito positions heuristic to introduce (let's call it v7)

    // E(s) = w4*v4 + w5*v5 + w7*v7

     -----------------------------------------------------

     RECAP:

     Don't forget to let the neutral strategy play few turns at the beginning to get closer to the center

     HEURISTICS:

     v1bis = the current player board center distance
     v2bis = the current player marbles cohesion
     v5 = difference between root opponent player marble count and actual board count
     v7 = total count of sumito positions
     v8 = total count of pushing positions

     Wait for Mathias to implement v7 and v8

     WEIGHTS:

     w1bis = 5
     w2bis = 3
     w5 = 500
     w7 = 100
     w8 = 40

     -----------------------------------------------------

     */

    private static int currentPlayer;
    private static int[][] cellColor;
    private static int[][] rootCellColor;

    private static int v1bis;
    private static int v2bis;
    //private static int v4;
    private static int v5;
    private static int v7;

    private static double w1 = -2;
    private static double w2 = 2;
    //private static double w4 = 70;
    private static double w5 = 100;
    private static double w7 = 90;

    public OffensiveEvalFunct(int currentPlayer, int[][] cellColor, int[][] rootCellColor) {

        OffensiveEvalFunct.currentPlayer = currentPlayer;
        OffensiveEvalFunct.cellColor = cellColor;
        OffensiveEvalFunct.rootCellColor = rootCellColor;
    }

    public void computeValues() {

        //v1bis
        int ownMarblesCenterDistance;

        //v2bis
        int ownMarblesNeighbour;

        // v4
        //int ownStrengthenGroupCount;
        //int opponentStrengthenGroupCount;

        // v5
        int currentStateOpponentMarblesCount;
        int rootOpponentMarblesCount;

        // v7
        int currentPlayerSumitos;
        int opponentPlayerSumitos;

        if (currentPlayer == 1) {
            ownMarblesCenterDistance = NeutralEvalFunct.centerDistance(currentPlayer, cellColor);

            ownMarblesNeighbour = NeutralEvalFunct.marblesNeighbourhood(currentPlayer, cellColor);

            //ownStrengthenGroupCount = NeutralEvalFunct.strengthenGroupPattern(currentPlayer, cellColor);
            //opponentStrengthenGroupCount = NeutralEvalFunct.strengthenGroupPattern(currentPlayer+1, cellColor);

            currentStateOpponentMarblesCount = NeutralEvalFunct.countMarbles(currentPlayer+1, cellColor);
            rootOpponentMarblesCount = NeutralEvalFunct.countMarbles(currentPlayer+1, rootCellColor);

            currentPlayerSumitos = countSumitoPositions(cellColor, currentPlayer, currentPlayer+1);
            opponentPlayerSumitos = countSumitoPositions(cellColor, currentPlayer+1, currentPlayer);
        }
        else {
            ownMarblesCenterDistance = NeutralEvalFunct.centerDistance(currentPlayer, cellColor);

            ownMarblesNeighbour = NeutralEvalFunct.marblesNeighbourhood(currentPlayer, cellColor);

            //ownStrengthenGroupCount = NeutralEvalFunct.strengthenGroupPattern(currentPlayer, cellColor);
            //opponentStrengthenGroupCount = NeutralEvalFunct.strengthenGroupPattern(currentPlayer-1, cellColor);

            currentStateOpponentMarblesCount = NeutralEvalFunct.countMarbles(currentPlayer-1, cellColor);
            rootOpponentMarblesCount = NeutralEvalFunct.countMarbles(currentPlayer-1, rootCellColor);

            currentPlayerSumitos = countSumitoPositions(cellColor, currentPlayer, currentPlayer-1);
            opponentPlayerSumitos = countSumitoPositions(cellColor, currentPlayer-1, currentPlayer);
        }

        v1bis = ownMarblesCenterDistance;
        v2bis = ownMarblesNeighbour;
        //v4 = ownStrengthenGroupCount - opponentStrengthenGroupCount;
        v5 = rootOpponentMarblesCount - currentStateOpponentMarblesCount;
        v7 = currentPlayerSumitos - opponentPlayerSumitos;
    }

    /**
     * all positions where current player can push out
     * =
     * all positions where opponent player can be pushed out
     */
    public int countSumitoPositions(int[][] board, int currentPlayer, int opponentPlayer){
        int sumito = 0;

        GetPossibleMoves obj = new GetPossibleMoves();
        //ArrayList<int[][]> pushMoves = obj.possibleMovesGivenPushing();
        ArrayList<int[][]> moves = obj.getDoubleMarbleMoves(board, currentPlayer);
        ArrayList<int[][]> triples = obj.getTripleMarbleMoves(board, currentPlayer);
        for(int[][] triple : triples){
            moves.add(triple);
        }

        int initialOppMarbles = NeutralEvalFunct.countMarbles(opponentPlayer,board);

        for(int[][] move: moves){
            int oppMarbles = NeutralEvalFunct.countMarbles(opponentPlayer, move);
            if(oppMarbles < initialOppMarbles){
                sumito++;
            }
        }

        System.out.println("there are "+sumito+" sumitos");
        return sumito;
    }

    public double evaluate() {
        int turn = Abalon.numberOfTurn;
        double score = 0;

        if(turn <= 4){
            NeutralEvalFunct neut = new NeutralEvalFunct(currentPlayer, cellColor, rootCellColor);
            score = neut.evaluate();
        }else{
            computeValues();
            //score = w1*v1bis + w2*v2bis + w4*v4 + w5*v5 + w7*v7;
            score = w1*v1bis + w2*v2bis + w5*v5 + w7*v7;
        }

        return score;

    }
}
