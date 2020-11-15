package Abalon.AI;

public class HashTable {

    public int arraySize;
    public double[] table;
    public Zorbist key;

    public HashTable() {

        key = new Zorbist();
        arraySize = (int) Math.pow(2, 20);
        table = new double[arraySize];
    }

    public boolean checkInTable(int currentPlayer, int[][] cellColor, double score) {

        int index = (int) key.getZorbistHash(currentPlayer, cellColor);

        if (table[index] == 0) {
            table[index] = score;
            return true;
        }
        else {
            return false;
        }
    }

    public double[] getTable() {

        return table;
    }
}

