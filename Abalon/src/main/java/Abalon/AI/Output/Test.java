package Abalon.AI.Output;

import Abalon.AI.EvaluationFunction.NeutralEvalFunct;
import Abalon.AI.MCTS.MCTS;
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

//        abVSmcts_Simulation(5, "Output1.txt", 2, 10, 1, 1);
//        abVSmcts_Simulation(5, "Output2.txt", 2, 5, 1, 1);
//        abVSmcts_Simulation(5, "Output3.txt", 3, 5, 1, 1);
//        abVSmcts_Simulation(5, "Output4.txt", 3, 10, 1, 1);
//        abVSab_Simulation(5, "Output5.txt", 3, 2, 3);
//        mctsVSmcts_Simulation(5, "Output6.txt", 10, 2, 3);

        //mctsVSmcts(5000, 1, 5, 6, 5000, 2, 5, 6);

        //MCTS monteCarlo = new MCTS(rootCellColor, 1, 10000, 1, 20, 6);
        //monteCarlo.start();
        //System.out.println();
    }


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


