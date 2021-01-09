package Abalon.AI.EvaluationFunction;

import Abalon.Main.Abalon;

public class DefensiveEvalFunct {

    /*

    Heuristics that are good to use:

    // Marbles grouped together
    // Marbles centered on the board
    // -> Important to avoid the opponent being centered grouped before us
    // As few as possible lost marbles compared to the opponent

    // -> v1, v2, v4, v6 to use from neutral strategy

    // E(s) = w1*v1 + w2*v2 + w4*v4 + w5*v5

     */
    private static int currentPlayer;
    private static int[][] cellColor;
    private static int[][] rootCellColor;

    private static int v1;
    private static int v2;
    private static int v4;
    private static int v6;

    private static double w1 = -2;
    private static double w2 = 2;
    private static double w4 = 100;
    private static double w6 = 70;


    public DefensiveEvalFunct(int currentPlayer, int[][] cellColor, int[][] rootCellColor) {
        DefensiveEvalFunct.currentPlayer = currentPlayer;
        DefensiveEvalFunct.cellColor = cellColor;
        DefensiveEvalFunct.rootCellColor = rootCellColor;
    }

    public void computeValues(){

        //v1
        int ownMarblesCenterDistance;
        int opponentMarblesCenterDistance;

        // v2
        int ownMarblesNeighbour;
        int opponentMarblesNeighbour;

        // v4
        int ownStrengthenGroupCount;
        int opponentStrengthenGroupCount;

        // v6
        int currentStateOwnMarblesCount = NeutralEvalFunct.countMarbles(currentPlayer, cellColor);
        int rootOwnMarblesCount = NeutralEvalFunct.countMarbles(currentPlayer, rootCellColor);

        if (currentPlayer == 1) {
            ownMarblesCenterDistance = NeutralEvalFunct.centerDistance(currentPlayer, cellColor);
            opponentMarblesCenterDistance = NeutralEvalFunct.centerDistance(currentPlayer+1, cellColor);

            ownMarblesNeighbour = NeutralEvalFunct.marblesNeighbourhood(currentPlayer, cellColor);
            opponentMarblesNeighbour = NeutralEvalFunct.marblesNeighbourhood(currentPlayer+1, cellColor);

            ownStrengthenGroupCount = NeutralEvalFunct.strengthenGroupPattern(currentPlayer, cellColor);
            opponentStrengthenGroupCount = NeutralEvalFunct.strengthenGroupPattern(currentPlayer+1, cellColor);

        }
        else {
            ownMarblesCenterDistance = NeutralEvalFunct.centerDistance(currentPlayer, cellColor);
            opponentMarblesCenterDistance = NeutralEvalFunct.centerDistance(currentPlayer-1, cellColor);

            ownMarblesNeighbour = NeutralEvalFunct.marblesNeighbourhood(currentPlayer, cellColor);
            opponentMarblesNeighbour = NeutralEvalFunct.marblesNeighbourhood(currentPlayer-1, cellColor);

            ownStrengthenGroupCount = NeutralEvalFunct.strengthenGroupPattern(currentPlayer, cellColor);
            opponentStrengthenGroupCount = NeutralEvalFunct.strengthenGroupPattern(currentPlayer-1, cellColor);

        }

        v1 = opponentMarblesCenterDistance - ownMarblesCenterDistance;
        v2 = ownMarblesNeighbour - opponentMarblesNeighbour;
        v4 = ownStrengthenGroupCount - opponentStrengthenGroupCount;
        v6 = rootOwnMarblesCount - currentStateOwnMarblesCount;

    }

    public double evaluate(){

        int turn = Abalon.numberOfTurn;
        double score = 0;

        if(turn <= 4){
            NeutralEvalFunct neut = new NeutralEvalFunct(currentPlayer, cellColor, rootCellColor);
            score = neut.evaluate();
        }else{
            computeValues();
            score = (w1 * v1 + w2 * v2 + w4 * v4) - w6 * v6;
        }

        return score;
    }
}
