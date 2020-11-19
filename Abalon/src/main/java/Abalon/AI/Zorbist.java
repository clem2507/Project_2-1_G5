package Abalon.AI;

import java.security.SecureRandom;

public class Zorbist {

    public long[][] zArray = new long[2][61];
    public long player1Key;
    public long player2Key;

    public Zorbist() {

        fillArray();

        player1Key = generateRandomNumber();
        player2Key = generateRandomNumber();
    }

    public void fillArray() {

        for (int color = 0; color < 2; color++) {
            for (int fields = 0; fields < 61; fields++) {
                zArray[color][fields] = generateRandomNumber();
            }
        }
    }

    public long getZorbistHash(int nextPlayer, int[][] cellColor) {

        long returnKey = 0;
        int marbleIndex = 0;

        for (int i = 0; i < cellColor.length; i++) {
            for (int j = 0; j < cellColor[0].length; j++) {
                if (cellColor[i][j] >= 0) {
                    if (cellColor[i][j] == 1) {
                        returnKey ^= zArray[0][marbleIndex];
                    }
                    if (cellColor[i][j] == 2) {
                        returnKey ^= zArray[1][marbleIndex];
                    }
                    marbleIndex++;
                }
            }
        }
        if (nextPlayer == 1) {
            returnKey ^= player1Key;
        }
        else {
            returnKey ^= player2Key;
        }
        return returnKey;
    }

    public long generateRandomNumber() {

        SecureRandom random = new SecureRandom();
        return bitExtracted(random.nextLong(), 20, 1);
    }

    public long bitExtracted(long randomNumber, int bits, int p)
    {
        return (((1 << bits) - 1) & (randomNumber >> (p - 1)));
    }
}
