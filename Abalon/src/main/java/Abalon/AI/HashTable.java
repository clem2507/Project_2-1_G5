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

    public boolean checkInTable(int currentPlayer, int[][] cellColor) {

        int index = (int) key.getZorbistHash(currentPlayer, cellColor);

        if (table[index] == 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public void put(int currentPlayer, int[][] cellColor) {

        EvaluationFunction evaluationFunction = new EvaluationFunction(currentPlayer, cellColor);

        int index = (int) key.getZorbistHash(currentPlayer, cellColor);
        table[index] = evaluationFunction.evaluate();
    }

    public double getScore(int currentPlayer, int[][] cellColor) {

        int index = (int) key.getZorbistHash(currentPlayer, cellColor);
        return table[index];
    }

    public double[] getTable() {

        return table;
    }
}
