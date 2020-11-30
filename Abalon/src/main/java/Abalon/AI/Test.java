package Abalon.AI;

import Abalon.UI.BoardUI;

public class Test {

    public static void printBoard(int[][] board) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static int[][] rootCellColor = new int[][]{

            {2, 2, 2, 2, 2, -1, -1, -1, -1},
            {2, 2, 2, 2, 2,  2, -1, -1, -1},
            {0, 0, 2, 2, 2,  0,  0, -1, -1},
            {0, 0, 0, 0, 0,  0,  0,  0, -1},
            {0, 0, 0, 0, 0,  0,  0,  0,  0},
            {0, 0, 0, 0, 0,  0,  0,  0, -1},
            {0, 0, 1, 1, 1,  0,  0, -1, -1},
            {1, 1, 1, 1, 1,  1, -1, -1, -1},
            {1, 1, 1, 1, 1, -1, -1, -1, -1}

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

        // These two lines are used to store data in files (it takes a while to compute)
        abVSmcts_Simulation(100, "Output1.txt", 3, 10);
        abVSmcts_Simulation(100, "Output2.txt", 2, 5);
    }

    public static void abVSmcts_Simulation(int numSimulation, String fileName, int gtDepth, int mctsDepth) {

        OutputCSV out = new OutputCSV(numSimulation, fileName);
        for (int i = 0; i < numSimulation; i++) {

            int[][] bestMove = rootCellColor;
            int currentPlayer = 1;
            int turn = 1;
            int ab_turn = 0;
            int mcts_turn = 0;
            float ab_avg_time = 0;
            float mcts_avg_time = 0;
            String winner;

            while (!BoardUI.isVictorious(bestMove)) {

                GameTree gameTree = new GameTree();

                long b_gametreeTime = System.currentTimeMillis();

                if (turn == 1 && currentPlayer == 1) {
                    gameTree.createTree(rootCellColor, currentPlayer, gtDepth);
                } else {
                    if (currentPlayer == 1) {
                        gameTree.createTree(bestMove, currentPlayer, gtDepth);
                    }
                }

                long e_gametreeTime = System.currentTimeMillis();
                double gametreeDuration = (e_gametreeTime - b_gametreeTime);

                if (currentPlayer == 1) {

                    long b_minimaxTime = System.currentTimeMillis();

                    AlphaBetaSearch algo = new AlphaBetaSearch(gameTree);
                    algo.start(true);
                    bestMove = algo.getBestMove();

                    long e_minimaxTime = System.currentTimeMillis();
                    double minimaxDuration = (e_minimaxTime - b_minimaxTime);

                    ab_avg_time += (minimaxDuration + gametreeDuration);
                    ab_turn++;

                } else {
                    long b_MCTStime = System.currentTimeMillis();

                    MCTS monteCarlo = new MCTS(bestMove, currentPlayer, mctsDepth);
                    monteCarlo.start();
                    bestMove = monteCarlo.getBestMove();

                    long e_MCTStime = System.currentTimeMillis();
                    double MCTSDuration = (e_MCTStime - b_MCTStime);

                    mcts_avg_time += MCTSDuration;
                    mcts_turn++;
                }

                if (currentPlayer == 1) {
                    currentPlayer = 2;
                } else {
                    currentPlayer = 1;
                }

                turn++;
                // System.out.println("turn = " + turn);
            }

            if (currentPlayer == 1) {
                winner = "MCTS";
            }
            else {
                winner = "Alpha-Beta";
            }

            ab_avg_time = (ab_avg_time/ab_turn)/1000f;
            mcts_avg_time = (mcts_avg_time/mcts_turn)/1000f;

            out.set(i, ab_avg_time, mcts_avg_time, turn, winner);
        }
        out.writeResume();
    }
}

