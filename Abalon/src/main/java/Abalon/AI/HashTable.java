package Abalon.AI;

public class HashTable {

    public int arraySize;
    public boolean[] table;
    public Zorbist key;

    public HashTable() {

        key = new Zorbist();
        arraySize = (int) Math.pow(2, 20);
        table = new boolean[arraySize];
    }

    public boolean checkInTable(int nextPlayer, int[][] cellColor) {

        int index = (int) key.getZorbistHash(nextPlayer, cellColor);

        if (!table[index]) {
            return true;
        }
        else {
            return false;
        }
    }

    public void put(int nextPlayer, int[][] cellColor) {

        int index = (int) key.getZorbistHash(nextPlayer, cellColor);
        table[index] = true;
    }

    public boolean[] getTable() {

        return table;
    }
}
