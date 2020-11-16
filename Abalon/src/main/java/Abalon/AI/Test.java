package Abalon.AI;

import java.util.ArrayList;
import java.util.Arrays;

public class Test {

    public static void printBoard(int[][] board) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] cellColor = new int[][]{

            {1, 1, 1, 0, 1, -1, -1, -1, -1},
            {1, 0, 1, 1, 0,  1, -1, -1, -1},
            {0, 0, 1, 1, 1,  0,  0, -1, -1},
            {0, 1, 0, 1, 0,  1,  0,  0, -1},
            {0, 0, 0, 2, 0,  2,  0,  0,  1},
            {2, 0, 0, 2, 0,  0,  0,  0, -1},
            {0, 0, 2, 0, 2,  0,  0, -1, -1},
            {0, 2, 2, 0, 2,  0, -1, -1, -1},
            {2, 2, 0, 2, 2, -1, -1, -1, -1}

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

        gameTree.createTree(rootCellColor, 1, 3);

        long e_time = System.currentTimeMillis();
        double duration = (e_time-b_time)/1000f;

        System.out.println("nodes list size = " + gameTree.getNodes().size());
        System.out.println("pruned nodes = " + gameTree.getPrunedNodes());
        System.out.println();
        System.out.println("compilation time = " + duration + " s");

        ArrayList<Node> nodes = gameTree.getNodes();
        ArrayList<Edge> edges = gameTree.getEdges();

        System.out.println();
        System.out.println("source");
        printBoard(edges.get(0).getSource().getBoardState());
        System.out.println();
        System.out.println("destination");
        printBoard(edges.get(0).getDestination().getBoardState());
        System.out.println();
        System.out.println(gameTree.adjacent(nodes.get(0), nodes.get(44)));
        System.out.println();
        System.out.println(gameTree.getChildren(nodes.get(168)).size());
    }
}

