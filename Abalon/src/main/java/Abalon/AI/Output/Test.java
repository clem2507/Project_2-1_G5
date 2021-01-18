package Abalon.AI.Output;

import Abalon.AI.AB.AlphaBetaSearch;
import Abalon.AI.EvaluationFunction.EvaluationFunction;
import Abalon.AI.EvaluationFunction.NeutralEvalFunct;
import Abalon.AI.MCTS.MCTS;
import Abalon.AI.Tree.GameTree;

public class Test {

    public static void printBoard(int[][] board) {

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board.length; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static boolean checkWinCheat(int[][] board) {

        int countP1 = 0;
        int countP2 = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 1) {
                    countP1++;
                }
                if (board[i][j] == 2) {
                    countP2++;
                }
            }
        }
        if (countP1 <= 8 || countP2 <= 8) {
            return true;
        } else {
            return false;
        }
    }

    public static int[][] rootCellColor = new int[][]{

            {2, 2, 2, 2, 2, -1, -1, -1, -1},
            {2, 2, 2, 2, 2, 2, -1, -1, -1},
            {0, 0, 2, 2, 2, 0, 0, -1, -1},
            {0, 0, 0, 0, 0, 0, 0, 0, -1},
            {0, 0, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0, -1},
            {0, 0, 1, 1, 1, 0, 0, -1, -1},
            {1, 1, 1, 1, 1, 1, -1, -1, -1},
            {1, 1, 1, 1, 1, -1, -1, -1, -1}

    };

    public static int[][] cellColor = new int[][]{

            {0, 0, 0, 0, 0, -1, -1, -1, -1},
            {0, 2, 2, 2, 0, 2, -1, -1, -1},
            {0, 2, 2, 2, 2, 0, 0, -1, -1},
            {0, 0, 2, 2, 2, 0, 0, 0, -1},
            {0, 0, 1, 2, 2, 1, 0, 0, 0},
            {0, 0, 1, 1, 0, 0, 0, 0, -1},
            {0, 1, 1, 1, 0, 1, 0, -1, -1},
            {0, 1, 1, 1, 0, 0, -1, -1, -1},
            {0, 1, 1, 0, 0, -1, -1, -1, -1}

    };

    public static void main(String[] args) {

//        abVSmcts_Simulation(5, "Output1.txt", 2, 10, 1, 1);
//        abVSmcts_Simulation(5, "Output2.txt", 2, 5, 1, 1);
//        abVSmcts_Simulation(5, "Output3.txt", 3, 5, 1, 1);
//        abVSmcts_Simulation(5, "Output4.txt", 3, 10, 1, 1);
//        abVSab_Simulation(5, "Output5.txt", 3, 2, 3);
//        mctsVSmcts_Simulation(5, "Output6.txt", 10, 2, 3);


        //MCTS monteCarlo = new MCTS(rootCellColor, 1, 4000, 3, 10, 1);
        //monteCarlo.start();
        //System.out.println();


        //mctsConfigurations();
        //testWeights(10, 3, 2);
        // -> strategy = 2, offensive eval function
        //testWeights(10, 3, 3);
        // -> strategy = 3, defensive eval function
        //testTimers(1, 100);
        //alphaBetaVSminimax(10);
        //testTranspositionTable(10);
        //testAIvsAI(1, 20, false);
        //testMoveOrdering(10);

//        int currentPlayer = 1;
//        int[][] bestBoard = rootCellColor;
//        while (!checkWinCheat(bestBoard)) {
//
//            long b_AlphaBetaTime = System.currentTimeMillis();
//
//            GameTree gameTree = new GameTree(1, false);
//            gameTree.createTree(bestBoard, currentPlayer, 3);
//
//            System.out.println("#nodes = " + gameTree.getNodes().size());
//            System.out.println();
//
//            AlphaBetaSearch ab = new AlphaBetaSearch(gameTree);
//            ab.start(true);
//            bestBoard = ab.getBestMove();
//
//            System.out.println("investigated nodes = " + ab.getInvestigatedNodes());
//            System.out.println("ABTS score = " + ab.getBestScore());
//
//            long e_AlphaBetaTime = System.currentTimeMillis();
//
//            long AlphaBetaDuration = (e_AlphaBetaTime - b_AlphaBetaTime);
//            System.out.println("AlphaBetaDuration = " + AlphaBetaDuration + " ms");
//
//            if (currentPlayer == 1) {
//                currentPlayer = 2;
//            }
//            else {
//                currentPlayer = 1;
//            }
//
//            System.out.println();
//            printBoard(ab.getBestMove());
//            System.out.println();
//        }
    }

    public static void testTimers(int sampleSize, int plays) {

        int counter = 0;
        OutputCSV out = new OutputCSV("testTimers2.txt", "timer, mean_score");
        int[] timers = {2000, 5000, 10000, 20000, 30000, 60000};
        for (int i = 0; i < timers.length; i++) {
            for (int j = 0; j < sampleSize; j++) {
                double meanScore = 0;
                int count = 0;
                int currentPlayer = 1;
                int[][] bestBoard = rootCellColor;
                while (count < plays) {
                    if (currentPlayer == 1) {
                        MCTS monteCarlo = new MCTS(bestBoard, currentPlayer, timers[i], 10, 1);
                        monteCarlo.start();
                        bestBoard = monteCarlo.getBestMove();
                        EvaluationFunction eval = new NeutralEvalFunct(currentPlayer, bestBoard, rootCellColor);
                        meanScore += eval.evaluate();
                        currentPlayer = 2;
                    } else {
                        GameTree gameTree = new GameTree(1, 1, false);
                        gameTree.createTree(bestBoard, currentPlayer, 3);
                        AlphaBetaSearch alphaBeta = new AlphaBetaSearch(gameTree);
                        alphaBeta.start(true);
                        bestBoard = alphaBeta.getBestMove();
                        currentPlayer = 1;
                    }
                    count++;
                }
                counter++;
                System.out.println();
                System.out.println(i);
                System.out.println();
                String[] data = {Integer.toString(timers[i] / 1000), Double.toString(meanScore / ((double) plays / 2))};
                if (counter == 1) {
                    out.writeResume(true, false, data);
                } else if (counter > 1 && counter < timers.length * sampleSize) {
                    out.writeResume(false, false, data);
                } else {
                    out.writeResume(false, true, data);
                }
            }
        }
    }

    public static void alphaBetaVSminimax(int sampleSize) {

        OutputCSV out = new OutputCSV("alphaBetaVSminimax.txt", "alphaBetaPruning, minimax, #investigated_nodes, time");
        int currentPlayer = 1;
        int[][] bestMove = cellColor;
        for (int i = 0; i < sampleSize; i++) {

            GameTree gameTree = new GameTree(1, 1, false);
            gameTree.createTree(bestMove, currentPlayer, 3);

            long b_AlphaBetaTime = System.currentTimeMillis();

            AlphaBetaSearch alphaBeta = new AlphaBetaSearch(gameTree);
            alphaBeta.start(true);

            long e_AlphaBetaTime = System.currentTimeMillis();
            long AlphaBetaDuration = (e_AlphaBetaTime - b_AlphaBetaTime);

            long b_MinimaxTime = System.currentTimeMillis();

            AlphaBetaSearch minimax = new AlphaBetaSearch(gameTree);
            minimax.start(false);

            long e_MinimaxTime = System.currentTimeMillis();
            long MinimaxDuration = (e_MinimaxTime - b_MinimaxTime);

            System.out.println();
            System.out.println("i = " + i);
            System.out.println();

            bestMove = alphaBeta.getBestMove();

            String[] data1 = {Boolean.toString(true), Boolean.toString(false), Integer.toString(alphaBeta.getInvestigatedNodes()), Long.toString(AlphaBetaDuration)};
            String[] data2 = {Boolean.toString(false), Boolean.toString(true), Integer.toString(minimax.getInvestigatedNodes()), Long.toString(MinimaxDuration)};
            if (i == 0) {
                out.writeResume(true, false, data1);
                out.writeResume(false, false, data2);
            } else if (i < sampleSize - 1) {
                out.writeResume(false, false, data1);
                out.writeResume(false, false, data2);
            } else {
                out.writeResume(false, false, data1);
                out.writeResume(false, true, data2);
            }

            if (currentPlayer == 1) {
                currentPlayer = 2;
            } else {
                currentPlayer = 1;
            }
        }
    }

    public static void testTranspositionTable(int sampleSize) {

        OutputCSV out = new OutputCSV("testTranspositionTable.txt", "transposition_table, #investigated_nodes, #time");
        int currentPlayer = 1;
        int[][] bestMove = cellColor;
        for (int i = 0; i < sampleSize; i++) {

            long b_transpositionTime = System.currentTimeMillis();

            GameTree transpo = new GameTree(1, 1, true);
            transpo.createTree(bestMove, currentPlayer, 3);
            int transpoNodes = transpo.getInvestigatedNodes();

            long e_transpositionTime = System.currentTimeMillis();
            long transpositionDuration = (e_transpositionTime - b_transpositionTime);

            long b_noTranspositionTime = System.currentTimeMillis();

            GameTree noTranspo = new GameTree(1, 1, false);
            noTranspo.createTree(bestMove, currentPlayer, 3);
            int noTranspoNodes = noTranspo.getInvestigatedNodes();

            long e_noTranspositionTime = System.currentTimeMillis();
            long noTranspositionDuration = (e_noTranspositionTime - b_noTranspositionTime);

            AlphaBetaSearch alphaBeta = new AlphaBetaSearch(transpo);
            alphaBeta.start(true);
            bestMove = alphaBeta.getBestMove();

            String[] data1 = {Boolean.toString(true), Integer.toString(transpoNodes), Long.toString(transpositionDuration)};
            String[] data2 = {Boolean.toString(false), Integer.toString(noTranspoNodes), Long.toString(noTranspositionDuration)};
            if (i == 0) {
                out.writeResume(true, false, data1);
                out.writeResume(false, false, data2);
            } else if (i < sampleSize - 1) {
                out.writeResume(false, false, data1);
                out.writeResume(false, false, data2);
            } else {
                out.writeResume(false, false, data1);
                out.writeResume(false, true, data2);
            }

            System.out.println("i = " + i);

            if (currentPlayer == 1) {
                currentPlayer = 2;
            } else {
                currentPlayer = 1;
            }
        }
    }

    public static void testAIvsAI(int strategy, int sampleSize, boolean fair) {

        OutputCSV out = new OutputCSV("testAIvsAInotFair2.txt", "ABTS_win, MCTS_win, ABTS_avg_time, MCTS_avg_time, ABTS_marbles, MCTS_marbles, #turn");
        for (int i = 0; i < sampleSize; i++) {
            float meanTimeABTS = 0;
            float meanTimeMCTS = 0;
            int turn = 0;
            int turnABTS = 0;
            int turnMCTS = 0;
            int currentPlayer = 1;
            int[][] bestBoard = rootCellColor;
            boolean ABTSwin = false;
            boolean MCTSwin = false;
            long b_ABTS;
            long e_ABTS;
            long ABTS_duration = 0;
            while (!checkWinCheat(bestBoard)) {
                if (currentPlayer == 1) {

                    b_ABTS = System.currentTimeMillis();

                    GameTree gameTree = new GameTree(strategy, 1, false);
                    gameTree.createTree(bestBoard, currentPlayer, 3);
                    AlphaBetaSearch ABTS = new AlphaBetaSearch(gameTree);
                    ABTS.start(true);
                    bestBoard = ABTS.getBestMove();

                    e_ABTS = System.currentTimeMillis();
                    ABTS_duration = (e_ABTS - b_ABTS);
                    meanTimeABTS += ABTS_duration;
                    turnABTS++;
                    currentPlayer = 2;
                } else {
                    MCTS monteCarlo;
                    if (fair) {
                        monteCarlo = new MCTS(bestBoard, currentPlayer, ABTS_duration, 10, 1);
                        meanTimeMCTS += ABTS_duration;
                    }
                    else {
                        monteCarlo = new MCTS(bestBoard, currentPlayer, 10000, 10, 1);
                        meanTimeMCTS += 10000;
                    }
                    monteCarlo.start();
                    bestBoard = monteCarlo.getBestMove();
                    turnMCTS++;
                    currentPlayer = 1;
                }
                turn++;
                //System.out.println("turn = " + turn);
            }
            System.out.println("i = " + i);
            System.out.println();
            if (currentPlayer == 1) {
                MCTSwin = true;
            } else {
                ABTSwin = true;
            }
            int ABTSmarbles = NeutralEvalFunct.countMarbles(1, bestBoard);
            int MCTSmarbles = NeutralEvalFunct.countMarbles(2, bestBoard);
            meanTimeABTS = (meanTimeABTS / (turnABTS)) / 1000f;
            meanTimeMCTS = (meanTimeMCTS / (turnMCTS)) / 1000f;
            String[] data = {Boolean.toString(ABTSwin), Boolean.toString(MCTSwin), Float.toString(meanTimeABTS), Float.toString(meanTimeMCTS), Integer.toString(ABTSmarbles), Integer.toString(MCTSmarbles), Double.toString(turn)};
            if (i == 0) {
                out.writeResume(true, false, data);
            } else if (i < sampleSize - 1) {
                out.writeResume(false, false, data);
            } else {
                out.writeResume(false, true, data);
            }
        }
    }

    public static void testMoveOrdering(int sampleSize) {

        OutputCSV out = new OutputCSV("testMoveOrdering2.txt", "best_ordering, simple_ordering, no_ordering, #investigated_nodes, #time");
        int currentPlayer = 1;
        int[][] bestMove = cellColor;
        AlphaBetaSearch ab;
        for (int i = 0; i < sampleSize; i++) {

            GameTree bestOrdering = new GameTree(1, 1, false);
            bestOrdering.createTree(bestMove, currentPlayer, 3);

            long b_bestMoveOrdering = System.currentTimeMillis();

            ab = new AlphaBetaSearch(bestOrdering);
            ab.start(true);
            int bestOrderingInvestigatedNodes = ab.getInvestigatedNodes();

            long e_bestMoveOrdering = System.currentTimeMillis();
            long bestMoveOrderingDuration = (e_bestMoveOrdering - b_bestMoveOrdering);


            GameTree simpleOrdering = new GameTree(1, 2, false);
            simpleOrdering.createTree(bestMove, currentPlayer, 3);

            long b_simpleMoveOrdering = System.currentTimeMillis();

            ab = new AlphaBetaSearch(simpleOrdering);
            ab.start(true);
            int simpleOrderingInvestigatedNodes = ab.getInvestigatedNodes();

            long e_simpleMoveOrdering = System.currentTimeMillis();
            long simpleMoveOrderingDuration = (e_simpleMoveOrdering - b_simpleMoveOrdering);


            GameTree noOrdering = new GameTree(1, 3, false);
            noOrdering.createTree(bestMove, currentPlayer, 3);

            long b_noMoveOrdering = System.currentTimeMillis();

            ab = new AlphaBetaSearch(noOrdering);
            ab.start(true);
            int noOrderingInvestigatedNodes = ab.getInvestigatedNodes();

            long e_noMoveOrdering = System.currentTimeMillis();
            long noMoveOrderingDuration = (e_noMoveOrdering - b_noMoveOrdering);


            MCTS mcts = new MCTS(bestMove, currentPlayer, 5000, 10, 1);
            mcts.start();
            bestMove = mcts.getBestMove();

            System.out.println("i = " + i);

            String[] data1 = {Boolean.toString(true), Boolean.toString(false), Boolean.toString(false), Integer.toString(bestOrderingInvestigatedNodes), Long.toString(bestMoveOrderingDuration)};
            String[] data2 = {Boolean.toString(false), Boolean.toString(true), Boolean.toString(false), Integer.toString(simpleOrderingInvestigatedNodes), Long.toString(simpleMoveOrderingDuration)};
            String[] data3 = {Boolean.toString(false), Boolean.toString(false), Boolean.toString(true), Integer.toString(noOrderingInvestigatedNodes), Long.toString(noMoveOrderingDuration)};
            if (i == 0) {
                out.writeResume(true, false, data1);
                out.writeResume(false, false, data2);
                out.writeResume(false, false, data3);
            } else if (i < sampleSize - 1) {
                out.writeResume(false, false, data1);
                out.writeResume(false, false, data2);
                out.writeResume(false, false, data3);
            } else {
                out.writeResume(false, false, data1);
                out.writeResume(false, false, data2);
                out.writeResume(false, true, data3);
            }

            if (currentPlayer == 1) {
                currentPlayer = 2;
            } else {
                currentPlayer = 1;
            }
        }
    }
}