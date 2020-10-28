package Abalon.AI;

public class EvaluationFunction {

    /**
     ==================================================================================================================

     Evaluation function is important to be able to give a correct score for a given game board.
     It depends on a lot of different parameters (in this case 6).
     The one we can use could be this one:

     E(s) = SUM (wi*vi) - w6*v6
     -> where all the wi are scalar factors that are used to measure the significance of each parameter
     in the result of the function.

     v1 = center distance: computes by taking the difference between distances to the center of both player
     v2 = cohesion strategy: calculates by taking the difference between the number of neighbouring teammates
     of both player
     v3 = break-strong-group: found by summing the recognition patterns where a player marble is in between
     two opposite opponent marbles of both player and taking the difference.
     v4 = strengthen-group: similar to v3 but with patterns that have a player marble in between a player and
     an opponent marble. Once again the difference between the values for both players is calculated.
     v5 = number-of-opponent-marbles: computes the difference between the amount of opponent marbles on the board at
     the beginning of the search and the current board state amount of marbles.
     v6 = number-of-own-marbles: similar to v5 but dealing with the current player marbles.

     Weights have already been calculated and evolves as the game progresses (according to the paper I have read).

     ==================================================================================================================
     */

    private static int currentPlayer;
    private static int[][] cellColor;

    private static int v1;
    private static int v2;
    private static int v3;
    private static int v4;
    private static int v5;
    private static int v6;

    private static double w1;
    private static double w2;
    private static double w3;
    private static double w4;
    private static double w5;
    private static double w6;

    private static double[] modus1 = {3, 2, 6, 1.8, 0};
    private static double[] modus2 = {3.3, 2, 6, 1.8, 35};
    private static double[] modus3 = {2.9, 2, 15, 3, 4};
    private static double[] modus4 = {2.9, 2, 15, 3, 15};
    private static double[] modus5 = {2.8, 2.3, 25, 3, 15};
    private static double[] modus6 = {2.8, 2.1, 25, 3, 25};
    private static double[] modus7 = {2.7, 2.3, 25, 3, 30};
    private static double[] modus8 = {2.4, 2.3, 25, 3, 35};
    private static double[] modus9 = {2.2, 2.3, 25, 3, 40};

    public EvaluationFunction(int currentPlayer, int[][] cellColor) {

        EvaluationFunction.currentPlayer = currentPlayer;
        EvaluationFunction.cellColor = cellColor;
    }

    public static void computeValues() {

        // v1
        v1 = 0;
        // TODO need to find a way to compute it

        // v2
        v2 = 0;
        // TODO need to find a way to compute it

        // v3
        v3 = 0;
        // TODO need to find a way to compute it

        // v4
        v4 = 0;
        // TODO need to find a way to compute it

        // v5
        v5 = 0;
        // TODO need to find a way to finish it
        if (currentPlayer == 1) {
            int currentStateOpponentMarblesCount = countMarbles(currentPlayer+1, cellColor);
            // int rootOpponentMarblesCount = countMarble(currentPlayer+1, rootCellColor);
            // v5 = rootOpponentMarblesCount - currentStateOpponentMarblesCount;
        }
        else {
            int currentStateOpponentMarblesCount = countMarbles(currentPlayer-1, cellColor);
            // int rootOpponentMarblesCount = countMarble(currentPlayer-1, rootCellColor);
            // v5 = rootOpponentMarblesCount - currentStateOpponentMarblesCount;
        }

        // v6
        v6 = 0;
        // TODO need to find a way to finish it
        int currentStateOwnMarblesCount = countMarbles(currentPlayer, cellColor);
        // int rootOwnMarblesCount = countMarble(currentPlayer, rootCellColor);
        // v6 = rootOwnMarblesCount - currentStateOwnMarblesCount;
    }

    public static void computeWeights(double v1, double v2) {

        if (v1 < 0) {
            changeModus(modus1);
        }
        else if (v1 < 5) {
            changeModus(modus2);
        }
        else if (v1 >= 5) {
            if (v2 >= 0 && v2 < 4) {
                changeModus(modus3);
            }
            if (v2 >= 4 && v2 < 10) {
                changeModus(modus4);
            }
            if (v2 >= 10 && v2 < 16) {
                changeModus(modus5);
            }
            if (v2 >= 16 && v2 < 22) {
                changeModus(modus6);
            }
            if (v2 >= 22 && v2 < 28) {
                changeModus(modus7);
            }
            if (v2 >= 28 && v2 < 34) {
                changeModus(modus8);
            }
            if (v2 >= 34) {
                changeModus(modus9);
            }
        }
    }

    public double evaluate() {

        computeValues();
        computeWeights(v1, v2);
        return (w1*v1 + w2*v2 + w3*v3 + w4*v4 + w5*v5) - w6*v6;
    }

    public static int countMarbles(int currentPlayer, int[][] cellColor) {

        int count = 0;
        for (int i = 0; i < cellColor.length; i++) {
            for (int j = 0; j < cellColor[0].length; j++) {
                if (cellColor[i][j] == currentPlayer) {
                    count++;
                }
            }
        }
        return count;
    }

    public static void changeModus(double[] modus) {

        w1 = modus[0];
        w2 = modus[1];
        w3 = modus[2];
        w4 = modus[3];
        w5 = modus[4];
        w6 = 50*w5;
    }
}
