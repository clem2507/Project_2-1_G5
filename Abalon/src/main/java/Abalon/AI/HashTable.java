package Abalon.AI;

public class HashTable {

    public int index;
    public int arraySize;
    public Entry[] table;
    public Zorbist key;

    public HashTable() {

        key = new Zorbist();
        arraySize = (int) Math.pow(2, 26);
        table = new Entry[arraySize];
        for (int i = 0; i < table.length; i++) {
            table[i] = new Entry(0, 0);
        }
    }

    public boolean checkInTable(int currentPlayer, int[][] cellColor) {

        this.index = (int) key.getZorbistKey(currentPlayer, cellColor);

        if (table[index].getScore() == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public void addInTable(double score, int depth) {

        table[index].setScore(score);
        table[index].setDepth(depth);
    }

    public Entry[] getTable() {
        return table;
    }
}


