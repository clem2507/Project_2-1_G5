package Abalon.AI.EvaluationFunction;

public class DefensiveEvalFunct extends EvaluationFunction{

    /**
     -----------------------------------------------------

     HEURISTICS:

     v1 = the current player board center distance
     v2 = the current player marbles cohesion
     v4 = count of breaking opponent line patterns
     v6 = difference between root current player marble count and actual board count

     WEIGHTS:

     w1 = -10
     w2 = 10
     w4 = 150
     w6 = -10000

     E(s) = w1*v1 + w2*v2 + w4*v4 + w6*v6

     -----------------------------------------------------
     */

    private static int v1;
    private static int v2;
    private static int v4;
    private static int v6;

    private static double w1 = -10;
    private static double w2 = 10;
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
        double score;

        computeValues();
        score = w1*v1 + w2*v2 + w4*v4 + w6*v6;

        return score;
    }
}
