package Abalon.AI;

public class Test {

    public static int[][] cellColor = new int[][]{

            {1, 1, 1, 1, 1, -1, -1, -1, -1},
            {0, 1, 1, 1, 1,  0, -1, -1, -1},
            {0, 0, 1, 0, 1,  0,  0, -1, -1},
            {1, 0, 0, 0, 0,  0,  0,  0, -1},
            {0, 2, 0, 0, 0,  2,  1,  0,  0},
            {0, 1, 0, 0, 0,  0,  2,  0, -1},
            {0, 0, 2, 0, 2,  0,  0, -1, -1},
            {0, 0, 2, 0, 2,  0, -1, -1, -1},
            {0, 2, 2, 2, 2, -1, -1, -1, -1}

    };

    public static void main(String[]args) {

        // Instantiation of the hash table
        HashTable table = new HashTable();
        // If there is an empty space in the table for a specific move, put true in the right index and continue the search
        if (table.checkInTable(1, cellColor)) {
            table.put(1, cellColor);
        }
        else {
            // Else, backtrack the search because the move has already been visited
        }

        EvaluationFunction evaluation = new EvaluationFunction();
        int score = evaluation.evaluate(1, cellColor, 100, 5, 1, 1, 1, 1, 1);
        System.out.println("score = " + score);
    }
}
