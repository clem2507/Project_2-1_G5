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

            {0, 1, 1, 1, 1, -1, -1, -1, -1},
            {0, 0, 1, 1, 1,  0, -1, -1, -1},
            {0, 0, 0, 0, 0,  0,  0, -1, -1},
            {0, 0, 0, 0, 0,  0,  0,  0, -1},
            {0, 0, 0, 0, 0,  0,  0,  0,  0},
            {0, 0, 0, 0, 0,  0,  0,  0, -1},
            {0, 0, 0, 0, 0,  0,  0, -1, -1},
            {0, 0, 2, 2, 2,  0, -1, -1, -1},
            {0, 2, 2, 2, 2, -1, -1, -1, -1}

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

        EvaluationFunction evaluation = new EvaluationFunction(0, cellColor, rootCellColor);

        int[][] bestMove = rootCellColor;
        int currentPlayer = 1;
        int turn = 1;
        while (Math.abs(evaluation.countMarbles(1, bestMove) - evaluation.countMarbles(2, bestMove)) < 1) {

            GameTree gameTree = new GameTree();

            long b_gametreeTime = System.currentTimeMillis();

            if (turn == 1 && currentPlayer == 1) {
                gameTree.createTree(rootCellColor, currentPlayer, 3);

                System.out.println();
                System.out.println("nodes list size = " + gameTree.getNodes().size());
                System.out.println("pruned nodes = " + gameTree.getPrunedNodes());
                System.out.println();
            } else {
                if (currentPlayer == 1) {
                    gameTree.createTree(bestMove, currentPlayer, 3);

                    System.out.println();
                    System.out.println("nodes list size = " + gameTree.getNodes().size());
                    System.out.println("pruned nodes = " + gameTree.getPrunedNodes());
                    System.out.println();
                }
            }

            long e_gametreeTime = System.currentTimeMillis();
            double gametreeDuration = (e_gametreeTime - b_gametreeTime) / 1000f;

            if (currentPlayer == 1) {
                System.out.println("Game tree duration = " + gametreeDuration + " s");
            }

            System.out.println();
            System.out.println("currentPlayer = " + currentPlayer + ", turn = " + turn);

            if (currentPlayer == 1) {

                long b_minimaxTime = System.currentTimeMillis();

                AlphaBetaSearch algo = new AlphaBetaSearch(gameTree);
                algo.start(true);
                bestMove = algo.getBestMove();

                long e_minimaxTime = System.currentTimeMillis();
                double minimaxDuration = (e_minimaxTime - b_minimaxTime) / 1000f;

                System.out.println();
                System.out.println("Minimax time duration = " + minimaxDuration + " s");

                System.out.println();
                System.out.println("Total computation time (game tree + minimax) = " + (gametreeDuration + minimaxDuration) + " s");
            } else {

                long b_MCTStime = System.currentTimeMillis();

                MCTS monteCarlo = new MCTS(bestMove, currentPlayer);
                monteCarlo.start();
                bestMove = monteCarlo.getBestMove();

                long e_MCTStime = System.currentTimeMillis();
                double MCTSDuration = (e_MCTStime - b_MCTStime) / 1000f;

                System.out.println();
                System.out.println("MCTS time duration = " + MCTSDuration + " s");
            }

            if (currentPlayer == 1) {
                currentPlayer = 2;
            }
            else {
                currentPlayer = 1;
            }

            System.out.println();
            System.out.println("--------------------");

            turn++;
        }
        if (evaluation.countMarbles(1, bestMove) > evaluation.countMarbles(2, bestMove)) {
            System.out.println("AI 1 won the game (minimax)");
        }
        else {
            System.out.println("AI 2 won the game (mcts)");
        }


        /*
        long b_MCTStime = System.currentTimeMillis();

        MCTS monteCarlo = new MCTS(rootCellColor, 1);
        monteCarlo.start();

        long e_MCTStime = System.currentTimeMillis();
        double MCTSDuration = (e_MCTStime - b_MCTStime) / 1000f;

        System.out.println();
        System.out.println("MCTS time duration = " + MCTSDuration + " s");
        */
    }
}

