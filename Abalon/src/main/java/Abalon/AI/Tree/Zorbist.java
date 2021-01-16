package Abalon.AI.Tree;

import java.security.SecureRandom;

public class Zorbist {

    private long player1Key;
    private long player2Key;

    private long[][] player1Board;
    private long[][] player2Board;

    public Zorbist() {

        player1Board = fillArray();
        player2Board = fillArray();

        player1Key = generateRandomNumber();
        player2Key = generateRandomNumber();
    }

    public long[][] fillArray() {

        long[][] board = new long[9][9];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                board[i][j] = generateRandomNumber();
            }
        }
        return board;
    }

    public long getZorbistKey(int currentPlayer, int[][] cellColor) {

        long returnKey = 0;

        for (int i = 0; i < cellColor.length; i++) {
            for (int j = 0; j < cellColor.length; j++) {
                if (cellColor[i][j] == 1) {
                    returnKey ^= player1Board[i][j];
                }
                if (cellColor[i][j] == 2) {
                    returnKey ^= player2Board[i][j];
                }
            }
        }
        if (currentPlayer == 1) {
            returnKey ^= player1Key;
        }
        else {
            returnKey ^= player2Key;
        }
        return returnKey;
    }

    public long generateRandomNumber() {

        SecureRandom random = new SecureRandom();
        return bitExtracted(random.nextLong(), 26, 1);
    }

    public long bitExtracted(long randomNumber, int bits, int p)
    {
        return (((1 << bits) - 1) & (randomNumber >> (p - 1)));
    }
}

