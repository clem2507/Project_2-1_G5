package Abalone.AI.EvaluationFunction;

public class NeutralEvalFunct extends EvaluationFunction{

    /**
     ==================================================================================================================

     Evaluation function is important to be able to give a correct score for a given game board.
     It depends on a lot of different parameters (in this case 6).
     The one we can use could be this one:

     E(s) = (SUM (wi*vi)) - w6*v6
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

    // w6 = 50*w5 because useless to push out an opponent marble if opponent can also push afterwards
    private static double[] modusN1 = {3, 2, 6, 1.8, 0, 0};
    private static double[] modusN2 = {3.3, 2, 6, 1.8, 35, 1750};
    private static double[] modusN3 = {2.9, 2, 15, 3, 4, 200};
    private static double[] modusN4 = {2.9, 2, 15, 3, 15, 750};
    private static double[] modusN5 = {2.8, 2.3, 25, 3, 15, 75};
    private static double[] modusN6 = {2.8, 2.1, 25, 3, 25, 750};
    private static double[] modusN7 = {2.7, 2.3, 25, 3, 30, 1500};
    private static double[] modusN8 = {2.4, 2.3, 25, 3, 35, 1750};
    private static double[] modusN9 = {2.2, 2.3, 25, 3, 40, 2000};
    // w5 is huge because bot can win the game
    private static double[] modusWin = {1, 1, 1, 1, 100000, 1};


    public NeutralEvalFunct(int currentPlayer, int[][] cellColor, int[][] rootCellColor) {
        super(currentPlayer, cellColor, rootCellColor);
    }

    public void computeValues() {

        // v1
        int ownMarblesCenterDistance;
        int opponentMarblesCenterDistance;

        // v2
        int ownMarblesNeighbour;
        int opponentMarblesNeighbour;

        // v3
        int ownBreakStrongGroupCount;
        int opponentBreakStrongGroupCount;

        // v4
        int ownStrengthenGroupCount;
        int opponentStrengthenGroupCount;

        // v5
        int currentStateOpponentMarblesCount;
        int rootOpponentMarblesCount;

        // v6
        int currentStateOwnMarblesCount = countMarbles(currentPlayer, cellColor);
        int rootOwnMarblesCount = countMarbles(currentPlayer, rootCellColor);

        if (currentPlayer == 1) {
            ownMarblesCenterDistance = centerDistance(currentPlayer, cellColor);
            opponentMarblesCenterDistance = centerDistance(currentPlayer+1, cellColor);

            ownMarblesNeighbour = marblesNeighbourhood(currentPlayer, cellColor);
            opponentMarblesNeighbour = marblesNeighbourhood(currentPlayer+1, cellColor);

            ownBreakStrongGroupCount = breakStrongGroupPattern(currentPlayer, cellColor);
            opponentBreakStrongGroupCount = breakStrongGroupPattern(currentPlayer+1, cellColor);

            ownStrengthenGroupCount = strengthenGroupPattern(currentPlayer, cellColor);
            opponentStrengthenGroupCount = strengthenGroupPattern(currentPlayer+1, cellColor);

            currentStateOpponentMarblesCount = countMarbles(currentPlayer+1, cellColor);
            rootOpponentMarblesCount = countMarbles(currentPlayer+1, rootCellColor);
        }
        else {
            ownMarblesCenterDistance = centerDistance(currentPlayer, cellColor);
            opponentMarblesCenterDistance = centerDistance(currentPlayer-1, cellColor);

            ownMarblesNeighbour = marblesNeighbourhood(currentPlayer, cellColor);
            opponentMarblesNeighbour = marblesNeighbourhood(currentPlayer-1, cellColor);

            ownBreakStrongGroupCount = breakStrongGroupPattern(currentPlayer, cellColor);
            opponentBreakStrongGroupCount = breakStrongGroupPattern(currentPlayer-1, cellColor);

            ownStrengthenGroupCount = strengthenGroupPattern(currentPlayer, cellColor);
            opponentStrengthenGroupCount = strengthenGroupPattern(currentPlayer-1, cellColor);

            currentStateOpponentMarblesCount = countMarbles(currentPlayer-1, cellColor);
            rootOpponentMarblesCount = countMarbles(currentPlayer-1, rootCellColor);
        }

        v1 = opponentMarblesCenterDistance - ownMarblesCenterDistance;
        v2 = ownMarblesNeighbour - opponentMarblesNeighbour;
        v3 = ownBreakStrongGroupCount - opponentBreakStrongGroupCount;
        v4 = ownStrengthenGroupCount - opponentStrengthenGroupCount;
        v5 = rootOpponentMarblesCount - currentStateOpponentMarblesCount;
        v6 = rootOwnMarblesCount - currentStateOwnMarblesCount;
    }

    public void computeWeights(double v1, double v2) {

        if (isWin(cellColor)) {
            changeModus(modusWin);
        }
        else {
            if (v1 < 0) {
                changeModus(modusN1);
            } else if (v1 < 5) {
                changeModus(modusN2);
            } else if (v1 >= 5) {
                if (v2 >= 0 && v2 < 4) {
                    changeModus(modusN3);
                }
                if (v2 >= 4 && v2 < 10) {
                    changeModus(modusN4);
                }
                if (v2 >= 10 && v2 < 16) {
                    changeModus(modusN5);
                }
                if (v2 >= 16 && v2 < 22) {
                    changeModus(modusN6);
                }
                if (v2 >= 22 && v2 < 28) {
                    changeModus(modusN7);
                }
                if (v2 >= 28 && v2 < 34) {
                    changeModus(modusN8);
                }
                if (v2 >= 34) {
                    changeModus(modusN9);
                }
            }
        }
    }

    @Override
    public double evaluate() {

        computeValues();
        computeWeights(v1, v2);
        return (w1 * v1 + w2 * v2 + w3 * v3 + w4 * v4 + w5 * v5) - w6 * v6;
    }

    @Override
    public void setUnifiedWeights(double[] weights) {
        changeModus(weights);
    }

    public void changeModus(double[] modus) {

        w1 = modus[0];
        w2 = modus[1];
        w3 = modus[2];
        w4 = modus[3];
        w5 = modus[4];
        w6 = modus[5];
    }

    public static int centerDistance(int currentPlayer, int[][] cellColor) {

        int distance = 0;
        for (int i = 0; i < cellColor.length; i++) {
            for (int j = 0; j < cellColor.length; j++) {
                if (cellColor[i][j] == currentPlayer) {
                    if (i == 4) {
                        distance += Math.abs(4-j);
                    }
                    else if (j == 4) {
                        distance += Math.abs(4-i);
                    }
                    else if (i <= 3 && j <= 3) {
                        distance += 4 - Math.min(i,j);
                    }
                    else if (i >= 5 && j <= 3) {
                        distance += 4 - Math.min(Math.abs(i-8), j);
                    }
                    else if ((i == 2 && j == 6) || (i == 6 && j == 6) || (i == 3 && j == 7) || (i == 5 && j == 7)) {
                        distance += 4;
                    }
                    else if ((i == 3 && j == 6) || (i == 5 && j == 6)) {
                        distance += 3;
                    }
                    else if (i <= 3 && j == 5) {
                        distance += (4 - i) + 1;
                    }
                    else if (i >= 5 && j == 5) {
                        distance += (i - 4) + 1;
                    }
                }
            }
        }
        return distance;
    }

    public static int marblesNeighbourhood(int currentPlayer, int[][] cellColor) {

        int neighbourhood = 0;
        for (int i = 0; i < cellColor.length; i++) {
            for (int j = 0; j < cellColor.length; j++) {
                if (cellColor[i][j] == currentPlayer) {
                    neighbourhood += countNeighbour(i, j, currentPlayer, cellColor);
                }
            }
        }
        return neighbourhood;
    }

    public static int countNeighbour(int i, int j, int currentPlayer, int[][] cellColor) {

        int count = 0;
        if (i == 0 && j == 0) {
            if (cellColor[i][j+1] == currentPlayer) {
                count++;
            }
            if (cellColor[i+1][j+1] == currentPlayer) {
                count++;
            }
            if (cellColor[i+1][j] == currentPlayer) {
                count++;
            }
        }
        else if (i == 4 && j == 8) {
            if (cellColor[i][j-1] == currentPlayer) {
                count++;
            }
            if (cellColor[i-1][j-1] == currentPlayer) {
                count++;
            }
            if (cellColor[i+1][j-1] == currentPlayer) {
                count++;
            }
        }
        else if (i == 8 && j == 0) {
            if (cellColor[i-1][j] == currentPlayer) {
                count++;
            }
            if (cellColor[i-1][j+1] == currentPlayer) {
                count++;
            }
            if (cellColor[i][j+1] == currentPlayer) {
                count++;
            }
        }
        else if (i < 1 && j >= 1) {
            if (cellColor[i][j+1] == currentPlayer) {
                count++;
            }
            if (cellColor[i+1][j] == currentPlayer) {
                count++;
            }
            if (cellColor[i+1][j+1] == currentPlayer) {
                count++;
            }
            if (cellColor[i][j-1] == currentPlayer) {
                count++;
            }
        }
        else if (i >= 1 && j < 1) {
            if (cellColor[i-1][j] == currentPlayer) {
                count++;
            }
            if (cellColor[i][j+1] == currentPlayer) {
                count++;
            }
            if (cellColor[i+1][j] == currentPlayer) {
                count++;
            }
        }
        else if (i >= 8 && j >= 1) {
            if (cellColor[i][j-1] == currentPlayer) {
                count++;
            }
            if (cellColor[i-1][j] == currentPlayer) {
                count++;
            }
            if (cellColor[i-1][j+1] == currentPlayer) {
                count++;
            }
            if (cellColor[i][j+1] == currentPlayer) {
                count++;
            }
        }
        else if (i >= 1 && j >= 1) {
            if (i <= 3) {
                if (cellColor[i][j-1] == currentPlayer) {
                    count++;
                }
                if (cellColor[i-1][j-1] == currentPlayer) {
                    count++;
                }
                if (cellColor[i-1][j] == currentPlayer) {
                    count++;
                }
                if (cellColor[i][j+1] == currentPlayer) {
                    count++;
                }
                if (cellColor[i+1][j] == currentPlayer) {
                    count++;
                }
                if (cellColor[i+1][j+1] == currentPlayer) {
                    count++;
                }
            }
            if (i == 4) {
                if (cellColor[i][j-1] == currentPlayer) {
                    count++;
                }
                if (cellColor[i-1][j-1] == currentPlayer) {
                    count++;
                }
                if (cellColor[i-1][j] == currentPlayer) {
                    count++;
                }
                if (cellColor[i][j+1] == currentPlayer) {
                    count++;
                }
                if (cellColor[i+1][j] == currentPlayer) {
                    count++;
                }
                if (cellColor[i+1][j-1] == currentPlayer) {
                    count++;
                }
            }
            if (i >= 5) {
                if (cellColor[i][j-1] == currentPlayer) {
                    count++;
                }
                if (cellColor[i+1][j-1] == currentPlayer) {
                    count++;
                }
                if (cellColor[i-1][j] == currentPlayer) {
                    count++;
                }
                if (cellColor[i][j+1] == currentPlayer) {
                    count++;
                }
                if (cellColor[i+1][j] == currentPlayer) {
                    count++;
                }
                if (cellColor[i-1][j+1] == currentPlayer) {
                    count++;
                }
            }
        }
        return count;
    }

    public static int breakStrongGroupPattern(int currentPlayer, int[][] cellColor) {

        int opponentPlayer;
        if (currentPlayer == 1) {
            opponentPlayer = 2;
        }
        else {
            opponentPlayer = 1;
        }
        int count = 0;
        for (int i = 0; i < cellColor.length; i++) {
            for (int j = 1; j < cellColor.length-1; j++) {
                if (cellColor[i][j] == currentPlayer) {
                    if (i == 0 || i == 8) {
                        if ((cellColor[i][j-1] == opponentPlayer) && (cellColor[i][j+1] == opponentPlayer)) {
                            count++;
                        }
                    }
                    if (i != 4) {
                        if (i >= 1 && i <= 7) {
                            if (i <= 3) {
                                if ((cellColor[i - 1][j - 1] == opponentPlayer) && (cellColor[i + 1][j + 1]) == opponentPlayer) {
                                    count++;
                                }
                            }
                            if (i >= 5) {
                                if ((cellColor[i - 1][j + 1] == opponentPlayer) && (cellColor[i + 1][j - 1]) == opponentPlayer) {
                                    count++;
                                }
                            }
                            if ((cellColor[i - 1][j] == opponentPlayer) && (cellColor[i + 1][j]) == opponentPlayer) {
                                count++;
                            }
                            if ((cellColor[i][j - 1] == opponentPlayer) && (cellColor[i][j + 1]) == opponentPlayer) {
                                count++;
                            }
                        }
                    }
                    else {
                        if ((cellColor[i-1][j] == opponentPlayer) && (cellColor[i+1][j-1]) == opponentPlayer) {
                            count++;
                        }
                        if ((cellColor[i][j-1] == opponentPlayer) && (cellColor[i][j+1]) == opponentPlayer) {
                            count++;
                        }
                        if ((cellColor[i-1][j-1] == opponentPlayer) && (cellColor[i+1][j]) == opponentPlayer) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
    }

    public static int strengthenGroupPattern(int currentPlayer, int[][] cellColor) {

        int opponentPlayer;
        if (currentPlayer == 1) {
            opponentPlayer = 2;
        }
        else {
            opponentPlayer = 1;
        }
        int count = 0;
        for (int i = 0; i < cellColor.length; i++) {
            for (int j = 1; j < cellColor.length-1; j++) {
                if (cellColor[i][j] == currentPlayer) {
                    if (i == 0 || i == 8) {
                        if (((cellColor[i][j-1] == opponentPlayer) && (cellColor[i][j+1] == currentPlayer))
                                || ((cellColor[i][j-1] == currentPlayer) && (cellColor[i][j+1] == opponentPlayer))) {
                            count++;
                        }
                    }
                    if (i != 4) {
                        if (i >= 1 && i <= 7) {
                            if (i <= 3) {
                                if (((cellColor[i-1][j-1] == opponentPlayer) && (cellColor[i+1][j+1]) == currentPlayer)
                                        || ((cellColor[i-1][j-1] == opponentPlayer) && (cellColor[i+1][j+1]) == opponentPlayer)) {
                                    count++;
                                }
                            }
                            if (i >= 5) {
                                if (((cellColor[i-1][j+1] == currentPlayer) && (cellColor[i+1][j-1]) == opponentPlayer)
                                        || ((cellColor[i-1][j+1] == opponentPlayer) && (cellColor[i+1][j-1]) == currentPlayer)) {
                                    count++;
                                }
                            }
                            if (((cellColor[i-1][j] == currentPlayer) && (cellColor[i+1][j]) == opponentPlayer)
                                    || ((cellColor[i-1][j] == opponentPlayer) && (cellColor[i+1][j]) == currentPlayer)) {
                                count++;
                            }
                            if (((cellColor[i][j-1] == currentPlayer) && (cellColor[i][j+1]) == opponentPlayer)
                                    || ((cellColor[i][j-1] == opponentPlayer) && (cellColor[i][j+1]) == currentPlayer)) {
                                count++;
                            }
                        }
                    }
                    else {
                        if (((cellColor[i-1][j] == currentPlayer) && (cellColor[i+1][j-1]) == opponentPlayer)
                                || ((cellColor[i-1][j] == opponentPlayer) && (cellColor[i+1][j-1]) == currentPlayer)) {
                            count++;
                        }
                        if (((cellColor[i][j-1] == currentPlayer) && (cellColor[i][j+1]) == opponentPlayer)
                                || ((cellColor[i][j-1] == opponentPlayer) && (cellColor[i][j+1]) == currentPlayer)) {
                            count++;
                        }
                        if (((cellColor[i-1][j-1] == currentPlayer) && (cellColor[i+1][j]) == opponentPlayer)
                                || ((cellColor[i-1][j-1] == opponentPlayer) && (cellColor[i+1][j]) == currentPlayer)) {
                            count++;
                        }
                    }
                }
            }
        }
        return count;
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

    public static boolean isWin(int[][] cellColor) {

        if (currentPlayer == 1) {
            if (countMarbles(currentPlayer+1, cellColor) <= 8) {
                return true;
            }
        }
        else {
            if (countMarbles(currentPlayer-1, cellColor) <= 8) {
                return true;
            }
        }
        return false;
    }
}


