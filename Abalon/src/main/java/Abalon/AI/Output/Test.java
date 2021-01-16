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
        if (countP1 <= 11 || countP2 <= 11) {
            return true;
        }
        else {
            return false;
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

    public static int[][] cellColor = new int[][]{

            {0, 0, 0, 0, 2, -1, -1, -1, -1},
            {0, 2, 2, 2, 0,  2, -1, -1, -1},
            {0, 2, 2, 2, 2,  0,  0, -1, -1},
            {0, 0, 2, 2, 2,  0,  0,  0, -1},
            {0, 0, 1, 2, 2,  1,  0,  0,  0},
            {0, 0, 1, 1, 0,  0,  0,  0, -1},
            {0, 1, 1, 1, 0,  1,  0, -1, -1},
            {0, 1, 1, 1, 0,  0, -1, -1, -1},
            {1, 1, 1, 0, 0, -1, -1, -1, -1}

    };

    public static void main(String[]args) {

//        abVSmcts_Simulation(5, "Output1.txt", 2, 10, 1, 1);
//        abVSmcts_Simulation(5, "Output2.txt", 2, 5, 1, 1);
//        abVSmcts_Simulation(5, "Output3.txt", 3, 5, 1, 1);
//        abVSmcts_Simulation(5, "Output4.txt", 3, 10, 1, 1);
//        abVSab_Simulation(5, "Output5.txt", 3, 2, 3);
//        mctsVSmcts_Simulation(5, "Output6.txt", 10, 2, 3);

        //mctsVSmcts(5000, 1, 5, 6, 5000, 2, 5, 6);

        //MCTS monteCarlo = new MCTS(rootCellColor, 1, 10000, 30, 10, 1);
        //monteCarlo.start();
        //System.out.println();


        //mctsConfigurations();
        //testWeights(10, 3, 2);
        // -> strategy = 2, offensive eval function
        //testWeights(10, 3, 3);
        // -> strategy = 3, defensive eval function
        //      testTimers(5, 10);
        //alphaBetaVSminimax(10);
        //testTranspositionTable(10);
        testAIvsAI(1, 5);

//        int currentPlayer = 1;
//        int[][] bestBoard = cellColor;
//        while (!checkWinCheat(bestBoard)) {
//
//            GameTree gameTree = new GameTree(1, false);
//            gameTree.createTree(bestBoard, currentPlayer, 3);
//
//            System.out.println("#nodes = " + gameTree.getNodes().size());
//            System.out.println();
//
//            long b_AlphaBetaTime = System.currentTimeMillis();
//
//            AlphaBetaSearch ab = new AlphaBetaSearch(gameTree);
//            ab.start(true);
//            bestBoard = ab.getBestMove();
//
//            System.out.println("counter = " + ab.getInvestigatedNodes());
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
//        }
    }

    public static void mctsConfigurations() {

        int count = 0;
        OutputCSV out = new OutputCSV("MCTSvsMCTS.txt", "timer, sample_size, #plays, win_rate_1");
        for (int sampleSize = 10; sampleSize <= 50; sampleSize+=20) {
            for (int plays = 4; plays <= 20; plays+=8) {
                double winRate1 = 0;
                double winRate2 = 0;
                for (int i = 0; i < 5; i++) {
                    int currentPlayer = 1;
                    int[][] bestBoard = rootCellColor;
                    while (!checkWinCheat(bestBoard)) {
                        if (currentPlayer == 1) {
                            MCTS monteCarlo = new MCTS(bestBoard, currentPlayer, 10000, sampleSize, plays, 1);
                            monteCarlo.start();
                            bestBoard = monteCarlo.getBestMove();
                            currentPlayer = 2;
                        } else {
                            MCTS monteCarlo = new MCTS(bestBoard, currentPlayer, 10000, 30, 10, 1);
                            monteCarlo.start();
                            bestBoard = monteCarlo.getBestMove();
                            currentPlayer = 1;
                        }
                    }
                    if (currentPlayer == 1) {
                        winRate2++;
                    }
                    else {
                        winRate1++;
                    }
                }
                count++;
                String[] data = {"10", Integer.toString(sampleSize), Integer.toString(plays), Double.toString(winRate1/5)};
                if (count == 1) {
                    out.writeResume(true, false, data);
                }
                else if (count > 1 && count < 9) {
                    out.writeResume(false, false, data);
                }
                else {
                    out.writeResume(false, true, data);
                }
            }
        }
    }


    public static void testWeights(int plays, int sampleSize, int strategy) {

        OutputCSV out = new OutputCSV("testWeights.txt", "strategy, w1, w2, w5, w7, w8, mean_score");
        int count = 0;
        for (int w1 = -5; w1 < 0; w1++) {
            for (int w2 = 1; w2 < 5; w2++) {
                for (int w5 = 10000; w5 <= 10000; w5+=0) {
                    for (int w7 = 200; w7 <= 400; w7+=100) {
                        for (int w8 = 40; w8 <= 100; w8+=20) {
                            double meanScore = 0;
                            for (int i = 0; i < sampleSize; i++) {
                                int currentPlayer = 1;
                                int[][] bestBoard = rootCellColor;
                                for (int j = 0; j < plays; j++) {
                                    if (currentPlayer == 1) {
                                        MCTS monteCarlo = new MCTS(bestBoard, currentPlayer, 10000, 20, 10, strategy);
                                        monteCarlo.setWeights(w1, w2, w5, w7, w8);
                                        monteCarlo.start();
                                        bestBoard = monteCarlo.getBestMove();
                                        currentPlayer = 2;
                                    } else {
                                        MCTS monteCarlo = new MCTS(bestBoard, currentPlayer, 10000, 20, 10, 1);
                                        monteCarlo.start();
                                        bestBoard = monteCarlo.getBestMove();
                                        currentPlayer = 1;
                                    }
                                }
                                NeutralEvalFunct eval = new NeutralEvalFunct(1, bestBoard, rootCellColor);
                                double currentScore = eval.evaluate();
                                meanScore+=currentScore;
                            }
                            count++;
                            String[] data = {"Offensive", Double.toString(w1), Double.toString(w2), Double.toString(w5), Double.toString(w7), Double.toString(w8), Double.toString(meanScore/sampleSize)};
                            if (count == 1) {
                                out.writeResume(true, false, data);
                            }
                            else if (count > 1 && count < 300) {
                                out.writeResume(false, false, data);
                            }
                            else {
                                out.writeResume(false, true, data);
                            }
                            System.out.println();
                            System.out.println((300-count) + " simulations left");
                            System.out.println();
                        }
                    }
                }
            }
        }
    }

    public static void testTimers(int sampleSize, int plays) {

        int counter = 0;
        OutputCSV out = new OutputCSV("testTimers.txt", "timer_1, mean_score_1");
        int[] timers = {2000, 5000, 10000, 20000, 30000};
        for (int i = 0; i < timers.length; i++) {
            for (int j = 0; j < sampleSize; j++) {
                double meanScore = 0;
                int count = 0;
                int currentPlayer = 1;
                int[][] bestBoard = rootCellColor;
                while (count < plays) {
                    if (currentPlayer == 1) {
                        MCTS monteCarlo = new MCTS(bestBoard, currentPlayer, timers[i], 20, 10, 1);
                        monteCarlo.start();
                        bestBoard = monteCarlo.getBestMove();
                        EvaluationFunction eval = new NeutralEvalFunct(currentPlayer, bestBoard, rootCellColor);
                        meanScore += eval.evaluate();
                        currentPlayer = 2;
                    } else {
//                        MCTS monteCarlo = new MCTS(bestBoard, currentPlayer, timers[i+1], 30, 10, 1);
//                        monteCarlo.start();
//                        bestBoard = monteCarlo.getBestMove();
                        GameTree gameTree = new GameTree(1, true);
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
                } else if (counter > 1 && counter < timers.length*sampleSize) {
                    out.writeResume(false, false, data);
                } else {
                    out.writeResume(false, true, data);
                }
            }
        }
    }

    public static void alphaBetaVSminimax(int sampleSize) {

        OutputCSV out = new OutputCSV("alphaBetaVSminimax.txt", "alphaBetaPruning, minimax, #game_tree_nodes, #investigated_nodes, #time");
        int currentPlayer = 1;
        int[][] bestMove = rootCellColor;
        for (int i = 0; i < sampleSize; i++) {

            GameTree gameTree = new GameTree(1, true);
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

            bestMove = alphaBeta.getBestMove();

            String[] data1 = {Boolean.toString(true), Boolean.toString(false), Integer.toString(gameTree.getNodes().size()), Integer.toString(alphaBeta.getInvestigatedNodes()), Long.toString(AlphaBetaDuration)};
            String[] data2 = {Boolean.toString(false), Boolean.toString(true), Integer.toString(gameTree.getNodes().size()), Integer.toString(minimax.getInvestigatedNodes()), Long.toString(MinimaxDuration)};
            if (i == 0) {
                out.writeResume(true, false, data1);
                out.writeResume(false, false, data2);
            }
            else if (i < sampleSize-1) {
                out.writeResume(false, false, data1);
                out.writeResume(false, false, data2);
            }
            else {
                out.writeResume(false, false, data1);
                out.writeResume(false, true, data2);
            }

            if (currentPlayer == 1) {
                currentPlayer = 2;
            }
            else {
                currentPlayer = 1;
            }
        }
    }

    public static void testTranspositionTable(int sampleSize) {

        OutputCSV out = new OutputCSV("testTranspositionTable.txt", "transposition_table, #investigated_nodes, #time");
        int currentPlayer = 1;
        int[][] bestMove = rootCellColor;
        for (int i = 0; i < sampleSize; i++) {

            long b_transpositionTime = System.currentTimeMillis();

            GameTree transpo = new GameTree(1, true);
            transpo.createTree(bestMove, currentPlayer, 3);
            int transpoNodes = transpo.getInvestigatedNodes();

            long e_transpositionTime = System.currentTimeMillis();
            long transpositionDuration = (e_transpositionTime - b_transpositionTime);

            long b_noTranspositionTime = System.currentTimeMillis();

            GameTree noTranspo = new GameTree(1, false);
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
            }
            else if (i < sampleSize-1) {
                out.writeResume(false, false, data1);
                out.writeResume(false, false, data2);
            }
            else {
                out.writeResume(false, false, data1);
                out.writeResume(false, true, data2);
            }

            System.out.println("i = " + i);

            if (currentPlayer == 1) {
                currentPlayer = 2;
            }
            else {
                currentPlayer = 1;
            }
        }
    }

    public static void testAIvsAI(int strategy, int sampleSize) {

        OutputCSV out = new OutputCSV("testAIvsAI.txt", "ABTS_win, MCTS_win, ABTS_marbles, MCTS_marbles, avg_time, #turn, strategy");
        for (int i = 0; i < sampleSize; i++) {
            long meanTimeABTS = 0;
            long turn = 0;
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

                    GameTree gameTree = new GameTree(strategy, false);
                    gameTree.createTree(bestBoard, currentPlayer, 3);
                    AlphaBetaSearch ABTS = new AlphaBetaSearch(gameTree);
                    ABTS.start(true);
                    bestBoard = ABTS.getBestMove();

                    e_ABTS = System.currentTimeMillis();
                    ABTS_duration = (e_ABTS - b_ABTS);
                    //System.out.println("ABTS_duration = " + ABTS_duration);
                    meanTimeABTS += ABTS_duration;
                    currentPlayer = 2;
                }
                else {
                    MCTS monteCarlo = new MCTS(bestBoard, currentPlayer, (int) ABTS_duration, 20, 10, 1);
                    monteCarlo.start();
                    bestBoard = monteCarlo.getBestMove();
                    currentPlayer = 1;
                }
                turn++;
                //System.out.println("turn = " + turn);
            }
            //System.out.println("i = " + i);
            if (currentPlayer == 1) {
                MCTSwin = true;
            }
            else {
                ABTSwin = true;
            }
            int ABTSmarbles = NeutralEvalFunct.countMarbles(1, bestBoard);
            int MCTSmarbles = NeutralEvalFunct.countMarbles(2, bestBoard);
            meanTimeABTS = meanTimeABTS/(turn/2);
            String[] data = {Boolean.toString(ABTSwin), Boolean.toString(MCTSwin), Long.toString(meanTimeABTS), Integer.toString(ABTSmarbles), Integer.toString(MCTSmarbles), Double.toString(turn), Integer.toString(strategy)};
            if (i == 0) {
                out.writeResume(true, false, data);
            }
            else if (i < sampleSize-1) {
                out.writeResume(false, false, data);
            }
            else {
                out.writeResume(false, true, data);
            }
        }
    }

/*

    public static void mctsVSmcts(int timer1, int eval1, int sampleSize1, int numOfPlays1, int timer2, int eval2, int sampleSize2, int numOfPlays2) {

        int turn = 0;
        int currentPlayer = 1;
        int[][] bestBoard = rootCellColor;
        while (!BoardUI.isVictorious(bestBoard)) {
            if (currentPlayer == 1) {
                MCTS monteCarlo = new MCTS(bestBoard, currentPlayer, timer1, eval1, sampleSize1, numOfPlays1);
                monteCarlo.start();
                bestBoard = monteCarlo.getBestMove();
                //System.out.println();
                currentPlayer = 2;
            }
            else {
                MCTS monteCarlo = new MCTS(bestBoard, currentPlayer, timer2, eval2, sampleSize2, numOfPlays2);
                monteCarlo.start();
                bestBoard = monteCarlo.getBestMove();
                //System.out.println();
                currentPlayer = 1;
            }
            System.out.println("turn = " + turn);
            turn++;
        }
        if (currentPlayer == 1) {
            System.out.println("MCTS 2 won the game in " + turn + " turns");
            System.out.println("timer1 = " + timer1);
            System.out.println("eval1 = " + eval1);
            System.out.println("sampleSize1 = " + sampleSize1);
            System.out.println("numOfPlays1 = " + numOfPlays1);
            System.out.println("timer2 = " + timer2);
            System.out.println("eval2 = " + eval2);
            System.out.println("sampleSize2 = " + sampleSize2);
            System.out.println("numOfPlays2 = " + numOfPlays2);
            System.out.println();
        }
        else {
            System.out.println("MCTS 1 won the game in " + turn + " turns");
            System.out.println("timer1 = " + timer1);
            System.out.println("eval1 = " + eval1);
            System.out.println("sampleSize1 = " + sampleSize1);
            System.out.println("numOfPlays1 = " + numOfPlays1);
            System.out.println("timer2 = " + timer2);
            System.out.println("eval2 = " + eval2);
            System.out.println("sampleSize2 = " + sampleSize2);
            System.out.println("numOfPlays2 = " + numOfPlays2);
            System.out.println();
        }
    }
*/


    /*public static void abVSab_Simulation(int numSimulation, String fileName, int gtDepth, int gtStrategy1, int gtStrategy2) {

        OutputCSV out = new OutputCSV(numSimulation, fileName, 2);
        for (int i = 0; i < numSimulation; i++) {

            int[][] bestMove = rootCellColor;
            int currentPlayer = 1;
            int turn = 1;
            int ab_turn1 = 0;
            int ab_turn2 = 0;
            float ab_avg_time1 = 0;
            float ab_avg_time2 = 0;
            boolean checkFirstRemoved = false;
            String firstMarble = "";
            String winningStrategy;
            String winner;

            while (!BoardUI.isVictorious(bestMove)) {

                if (turn > 1000) {
                    break;
                }

                GameTree gameTree = new GameTree();

                long b_gametreeTime = System.currentTimeMillis();

                if (turn == 1) {
                    gameTree.createTree(rootCellColor, currentPlayer, gtDepth, gtStrategy1);
                } else {
                    if (currentPlayer == 1) {
                        gameTree.createTree(bestMove, currentPlayer, gtDepth, gtStrategy1);
                    }
                    else {
                        gameTree.createTree(bestMove, currentPlayer, gtDepth, gtStrategy2);
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

                    ab_avg_time1 += (minimaxDuration + gametreeDuration);
                    ab_turn1++;

                } else {
                    long b_minimaxTime = System.currentTimeMillis();

                    AlphaBetaSearch algo = new AlphaBetaSearch(gameTree);
                    algo.start(true);
                    bestMove = algo.getBestMove();

                    long e_minimaxTime = System.currentTimeMillis();
                    double minimaxDuration = (e_minimaxTime - b_minimaxTime);

                    ab_avg_time2 += (minimaxDuration + gametreeDuration);
                    ab_turn2++;
                }

                if (!checkFirstRemoved) {
                    if (Math.abs((NeutralEvalFunct.countMarbles(1, bestMove)-NeutralEvalFunct.countMarbles(2, bestMove))) > 0) {
                        if (currentPlayer == 1) {
                            firstMarble = "Alpha-Beta";
                        }
                        else {
                            firstMarble = "Alpha-Beta";
                        }
                        checkFirstRemoved = true;
                    }
                }

                if (currentPlayer == 1) {
                    currentPlayer = 2;
                } else {
                    currentPlayer = 1;
                }

                turn++;
            }

            if (currentPlayer == 1) {
                winner = "Alpha-Beta";
                if (gtStrategy1==1) {
                    winningStrategy = "Neutral";
                }
                else if (gtStrategy1==2) {
                    winningStrategy = "Offensive";
                }
                else {
                    winningStrategy = "Defensive";
                }
            }
            else {
                winner = "Alpha-Beta";
                if (gtStrategy2==1) {
                    winningStrategy = "Neutral";
                }
                else if (gtStrategy2==2) {
                    winningStrategy = "Offensive";
                }
                else {
                    winningStrategy = "Defensive";
                }
            }

            ab_avg_time1 = (ab_avg_time1/ab_turn1)/1000f;
            ab_avg_time2 = (ab_avg_time2/ab_turn2)/1000f;

            out.add(i, ab_avg_time1, ab_avg_time2, 0, turn, firstMarble, winner, winningStrategy);
            System.out.println("i = " + i);
        }
        out.writeResume();
    }

    public static void abVSmcts_Simulation(int numSimulation, String fileName, int gtDepth, int mctsDepth, int gtStrategy, int mctsStrategy) {

        OutputCSV out = new OutputCSV(numSimulation, fileName, 1);
        for (int i = 0; i < numSimulation; i++) {

            int[][] bestMove = rootCellColor;
            int currentPlayer = 1;
            int turn = 1;
            int ab_turn = 0;
            int mcts_turn = 0;
            double gt_nodes_avg = 0;
            float ab_avg_time = 0;
            float mcts_avg_time = 0;
            boolean checkFirstRemoved = false;
            String firstMarble = "";
            String winningStrategy;
            String winner;

            while (!BoardUI.isVictorious(bestMove)) {
                if (turn > 1000) {
                    break;
                }

                GameTree gameTree = new GameTree();

                long b_gametreeTime = System.currentTimeMillis();

                if (turn == 1 && currentPlayer == 1) {
                    gameTree.createTree(rootCellColor, currentPlayer, gtDepth, gtStrategy);
                    gt_nodes_avg += gameTree.getNodes().size();
                } else {
                    if (currentPlayer == 1) {
                        gameTree.createTree(bestMove, currentPlayer, gtDepth, gtStrategy);
                        gt_nodes_avg += gameTree.getNodes().size();
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

                    MCTS monteCarlo = new MCTS(bestMove, currentPlayer);
                    monteCarlo.start();
                    bestMove = monteCarlo.getBestMove();

                    long e_MCTStime = System.currentTimeMillis();
                    double MCTSDuration = (e_MCTStime - b_MCTStime);

                    mcts_avg_time += MCTSDuration;
                    mcts_turn++;
                }

                if (!checkFirstRemoved) {
                    if (Math.abs((NeutralEvalFunct.countMarbles(1, bestMove)-NeutralEvalFunct.countMarbles(2, bestMove))) > 0) {
                        if (currentPlayer == 1) {
                            firstMarble = "Alpha-Beta";
                        }
                        else {
                            firstMarble = "MCTS";
                        }
                        checkFirstRemoved = true;
                    }
                }

                if (currentPlayer == 1) {
                    currentPlayer = 2;
                } else {
                    currentPlayer = 1;
                }

                turn++;
            }

            if (currentPlayer == 1) {
                winner = "MCTS";
                if (mctsStrategy==1) {
                    winningStrategy = "Neutral";
                }
                else if (mctsStrategy==2) {
                    winningStrategy = "Offensive";
                }
                else {
                    winningStrategy = "Defensive";
                }
            }
            else {
                winner = "Alpha-Beta";
                if (gtStrategy==1) {
                    winningStrategy = "Neutral";
                }
                else if (gtStrategy==2) {
                    winningStrategy = "Offensive";
                }
                else {
                    winningStrategy = "Defensive";
                }
            }

            gt_nodes_avg = gt_nodes_avg/ab_turn;

            ab_avg_time = (ab_avg_time/ab_turn)/1000f;
            mcts_avg_time = (mcts_avg_time/mcts_turn)/1000f;

            out.add(i, ab_avg_time, mcts_avg_time, gt_nodes_avg, turn, firstMarble, winner, winningStrategy);
            System.out.println("i = " + i);
        }
        out.writeResume();
    }

    public static void mctsVSmcts_Simulation(int numSimulation, String fileName, int mctsDepth, int mctsStrategy1, int mctsStrategy2) {

        OutputCSV out = new OutputCSV(numSimulation, fileName, 3);
        for (int i = 0; i < numSimulation; i++) {

            int[][] bestMove = rootCellColor;
            int currentPlayer = 1;
            int turn = 1;
            int mcts_turn1 = 0;
            int mcts_turn2 = 0;
            float mcts_avg_time1 = 0;
            float mcts_avg_time2 = 0;
            boolean checkFirstRemoved = false;
            String firstMarble = "";
            String winningStrategy;
            String winner;

            while (!BoardUI.isVictorious(bestMove)) {

                if (turn > 1000) {
                    break;
                }

                if (currentPlayer == 1) {

                    long b_MCTStime = System.currentTimeMillis();

                    MCTS monteCarlo = new MCTS(bestMove, currentPlayer);
                    monteCarlo.start();
                    bestMove = monteCarlo.getBestMove();

                    long e_MCTStime = System.currentTimeMillis();
                    double MCTSDuration = (e_MCTStime - b_MCTStime);

                    mcts_avg_time1 += MCTSDuration;
                    mcts_turn1++;

                } else {
                    long b_MCTStime = System.currentTimeMillis();

                    MCTS monteCarlo = new MCTS(bestMove, currentPlayer);
                    monteCarlo.start();
                    bestMove = monteCarlo.getBestMove();

                    long e_MCTStime = System.currentTimeMillis();
                    double MCTSDuration = (e_MCTStime - b_MCTStime);

                    mcts_avg_time2 += MCTSDuration;
                    mcts_turn2++;
                }

                if (!checkFirstRemoved) {
                    if (Math.abs((NeutralEvalFunct.countMarbles(1, bestMove)-NeutralEvalFunct.countMarbles(2, bestMove))) > 0) {
                        if (currentPlayer == 1) {
                            firstMarble = "MCTS";
                        }
                        else {
                            firstMarble = "MCTS";
                        }
                        checkFirstRemoved = true;
                    }
                }

                if (currentPlayer == 1) {
                    currentPlayer = 2;
                } else {
                    currentPlayer = 1;
                }

                turn++;
            }

            if (currentPlayer == 1) {
                winner = "MCTS";
                if (mctsStrategy1==1) {
                    winningStrategy = "Neutral";
                }
                else if (mctsStrategy1==2) {
                    winningStrategy = "Offensive";
                }
                else {
                    winningStrategy = "Defensive";
                }
            }
            else {
                winner = "MCTS";
                if (mctsStrategy2==1) {
                    winningStrategy = "Neutral";
                }
                else if (mctsStrategy2==2) {
                    winningStrategy = "Offensive";
                }
                else {
                    winningStrategy = "Defensive";
                }
            }

            mcts_avg_time1 = (mcts_avg_time1/mcts_turn1)/1000f;
            mcts_avg_time2 = (mcts_avg_time2/mcts_turn2)/1000f;

            out.add(i, mcts_avg_time1, mcts_avg_time2, 0, turn, firstMarble, winner, winningStrategy);
            System.out.println("i = " + i);
        }
        out.writeResume();
    }*/
}



