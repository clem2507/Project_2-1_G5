package Abalon.AI;

public class EvaluationFunction {

/**
    ==================================================================================================================

    Evaluation function is important to be able to give a correct score for a given game board.
    It depends on a lot of different parameters (in our case 7).
    The one we can use could be this one:

    E(S) = w1*v1 + w2*v2 + w3*v3 + w4*v4 + w5*v5 + w6*v6 + w7*v7
    -> where all the w_i are scalar factors that are used to measure the significance of each parameter
    in the result of the function (later we will be able to optimise them using an NN).

    v1 = win or loss: if the current state is a win it returns 1, if it is a loss -1, otherwise 0 (probably
    need to add a considering weight to this parameter given that the win is the best position that can achieve a
    player and loss the worst).
    v2 = lost marbles: it contains the difference between the current player amount of marbles on the board
    and the second player.
    v3 = center distance: it sums all the distances of the current player's marbles from the center of the board.
    The more the player's marbles are in the centre of the game, the better the position of the player is.
    The worst value for that parameter could be 56, which means that we will subtract 56 with the actual center
    distance marbles.
    v4 = own-marbles grouping: sum all the own-marbles neighbourhood together. It is a considerable force to group
    marbles in Abalone. The best value is 58.
    v5 = opposite-marbles grouping: sum all the the opposite-marbles neighbourhood. Then we need to subtract 58 with
    this value.
    v6 = attacking positions: it sums all the own sumito moves that can be performed on the current board.
    v7 = defending positions: it sums all the opponent sumito moves that can be done on the current board. In this
    case, we need to subtract 19 (which is the best score) with that sum.

    ==================================================================================================================
*/


    public int evaluate(int currentPlayer, int[][] cellColor, int w1, int w2, int w3, int w4, int w5, int w6, int w7) {

        int score;

        // v1
        int v1 = 0;
        if (currentPlayer == 1) {
            if (countMarbles(currentPlayer+1, cellColor) < 9) {
                v1 = 1;
            }
            if (countMarbles(currentPlayer, cellColor) < 9) {
                v1 = -1;
            }
        }
        else {
            if (countMarbles(currentPlayer-1, cellColor) < 9) {
                v1 = 1;
            }
            if (countMarbles(currentPlayer, cellColor) < 9) {
                v1 = -1;
            }
        }

        // v2
        int v2;
        if (currentPlayer == 1) {
            v2 = countMarbles(currentPlayer, cellColor) - countMarbles(currentPlayer+1, cellColor);
        }
        else {
            v2 = countMarbles(currentPlayer, cellColor) - countMarbles(currentPlayer-1, cellColor);
        }

        // v3
        int v3 = 0;
        // TODO need to find a way to compute it

        // v4
        int v4 = 0;
        // TODO need to find a way to compute it

        // v5
        int v5 = 0;
        // TODO need to find a way to compute it

        // v6
        int v6 = 0;
        // TODO need to find a way to compute it

        // v7
        int v7 = 0;
        // TODO need to find a way to compute it

        score = w1*v1 + w2*v2 + w3*v3 + w4*v4 + w5*v5 + w6*v6 + w7*v7;
        return score;
    }

    public int countMarbles(int currentPlayer, int[][] cellColor) {

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
}
