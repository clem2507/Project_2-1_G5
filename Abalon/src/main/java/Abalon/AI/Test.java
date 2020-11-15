package Abalon.AI;

import java.util.*;

public class Test {

    public static int[][] cellColor = new int[][]{

            {1, 1, 1, 0, 1, -1, -1, -1, -1},
            {1, 1, 1, 1, 1,  1, -1, -1, -1},
            {0, 0, 1, 1, 1,  0,  0, -1, -1},
            {0, 0, 0, 1, 0,  0,  0,  0, -1},
            {0, 0, 0, 2, 0,  0,  0,  0,  0},
            {0, 0, 0, 2, 0,  0,  0,  0, -1},
            {0, 0, 2, 0, 2,  0,  0, -1, -1},
            {2, 2, 2, 0, 2,  2, -1, -1, -1},
            {2, 2, 2, 2, 2, -1, -1, -1, -1}

    };

    public static int[][] rootCellColor = new int[][]{

            {1, 1, 1, 1, 1, -1, -1, -1, -1},
            {1, 1, 1, 1, 1,  1, -1, -1, -1},
            {0, 0, 1, 1, 1,  0,  0, -1, -1},
            {0, 0, 0, 0, 0,  0,  0,  0, -1},
            {0, 0, 0, 0, 0,  0,  0,  0,  0},
            {0, 0, 0, 0, 0,  0,  0,  0, -1},
            {0, 0, 2, 2, 2,  0,  0, -1, -1},
            {2, 2, 2, 2, 2,  2, -1, -1, -1},
            {2, 2, 2, 2, 2, -1, -1, -1, -1}

    };

    public static void main(String[]args) {

        // Instantiation of the hash table
        // HashTable table = new HashTable();
        // If there is an empty space in the table for a specific move, put true in the right index and continue the search
        //if (table.checkInTable(1, cellColor)) {
          //  table.put(1, cellColor);
        //}
        //else {
            // node.setScore = table.getScore()
        //}

        /*
        Random r = new Random();

        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < cellColor.length; j++) {
                for (int k = 0; k < cellColor.length; k++) {
                    if (cellColor[j][k] == 0 || cellColor[j][k] == 1 || cellColor[j][k] == 2) {
                        int randomNum = r.nextInt((2) + 1);
                        cellColor[j][k] = randomNum;
                    }
                }
            }
            EvaluationFunction evaluationFunction = new EvaluationFunction(1, cellColor, rootCellColor);
            double score = evaluationFunction.evaluate();

            System.out.println();

            for (int j = 0; j < cellColor.length; j++) {
                for (int k = 0; k < cellColor.length; k++) {
                    System.out.print(cellColor[j][k] + " ");
                }
                System.out.println();
            }

            System.out.println();
            System.out.println("score " + i + ": " + score);
            System.out.println("-------");
        }
        */

        GameTree gameTree = new GameTree();

        long b_time = System.currentTimeMillis();
        gameTree.createTree(rootCellColor, 1);
        long e_time = System.currentTimeMillis();

        System.out.println("nodes list size = " + gameTree.getNodes().size());
        System.out.println("Compilation time: " + (e_time-b_time));
    }
}
