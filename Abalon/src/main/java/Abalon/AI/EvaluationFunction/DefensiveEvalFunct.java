package Abalon.AI.EvaluationFunction;

import Abalon.Main.Abalon;

public class DefensiveEvalFunct extends EvaluationFunction{

    /**

    Heuristics that are good to use:

    // Marbles grouped together
    // Marbles centered on the board
    // -> Important to avoid the opponent being centered grouped before us
    // As few as possible lost marbles compared to the opponent

    // -> v1, v2, v4, v6 to use from neutral strategy

    // E(s) = w1*v1 + w2*v2 + w4*v4 + w5*v5

     -----------------------------------------------------

     RECAP:

     Don't forget to let the neutral strategy play few turns at the beginning to get closer to the center

     HEURISTICS:

     v1 = the current player board center distance
     v2 = the current player marbles cohesion
     v4 = count of breaking opponent line patterns
     v6 = difference between root current player marble count and actual board count

     WEIGHTS:

     w1 = 3
     w2 = 2
     w4 = 3
     w6 = 500

     -----------------------------------------------------
     */

    private static int v1;
    private static int v2;
    private static int v4;
    private static int v6;

    private static double w1 = -5;
    private static double w2 = 5;
    private static double w4 = 150;
    private static double w6 = -10000;


    public DefensiveEvalFunct(int currentPlayer, int[][] cellColor, int[][] rootCellColor) {
        super(currentPlayer, cellColor, rootCellColor);
    }

    public void computeValues(){

        //v1
        int ownMarblesCenterDistance;
        //int opponentMarblesCenterDistance;

        // v2
        int ownMarblesNeighbour;
        //int opponentMarblesNeighbour;

        // v4
        int ownStrengthenGroupCount;
        //int opponentStrengthenGroupCount;

        // v6
        int currentStateOwnMarblesCount = NeutralEvalFunct.countMarbles(currentPlayer, cellColor);
        int rootOwnMarblesCount = NeutralEvalFunct.countMarbles(currentPlayer, rootCellColor);

        if (currentPlayer == 1) {
            ownMarblesCenterDistance = NeutralEvalFunct.centerDistance(currentPlayer, cellColor);
            //opponentMarblesCenterDistance = NeutralEvalFunct.centerDistance(currentPlayer+1, cellColor);

            ownMarblesNeighbour = NeutralEvalFunct.marblesNeighbourhood(currentPlayer, cellColor);
            //opponentMarblesNeighbour = NeutralEvalFunct.marblesNeighbourhood(currentPlayer+1, cellColor);

            ownStrengthenGroupCount = NeutralEvalFunct.strengthenGroupPattern(currentPlayer, cellColor);
            //opponentStrengthenGroupCount = NeutralEvalFunct.strengthenGroupPattern(currentPlayer+1, cellColor);
        }
        else {
            ownMarblesCenterDistance = NeutralEvalFunct.centerDistance(currentPlayer, cellColor);
            //opponentMarblesCenterDistance = NeutralEvalFunct.centerDistance(currentPlayer-1, cellColor);

            ownMarblesNeighbour = NeutralEvalFunct.marblesNeighbourhood(currentPlayer, cellColor);
            //opponentMarblesNeighbour = NeutralEvalFunct.marblesNeighbourhood(currentPlayer-1, cellColor);

            ownStrengthenGroupCount = NeutralEvalFunct.strengthenGroupPattern(currentPlayer, cellColor);
            //opponentStrengthenGroupCount = NeutralEvalFunct.strengthenGroupPattern(currentPlayer-1, cellColor);
        }

        //v1 = opponentMarblesCenterDistance - ownMarblesCenterDistance;
        v1 = ownMarblesCenterDistance;
        //v2 = ownMarblesNeighbour - opponentMarblesNeighbour;
        v2 = ownMarblesNeighbour;
        //v4 = ownStrengthenGroupCount - opponentStrengthenGroupCount;
        v4 = ownStrengthenGroupCount;
        v6 = rootOwnMarblesCount - currentStateOwnMarblesCount;

    }

    @Override
    public double evaluate(){

        //int turn = Abalon.numberOfTurn;
        double score = 0;

        //if(turn <= 4){
            //EvaluationFunction neut = new NeutralEvalFunct(currentPlayer, cellColor, rootCellColor);
            //score = neut.evaluate();
        //}else{
            computeValues();
            score = w1*v1 + w2*v2 + w4*v4 + w6*v6;
        //}

        return score;
    }
}
