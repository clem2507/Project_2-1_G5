package Abalon.AI;

import java.util.*;

public class MCTS {

    private int[][] boardState;
    private int[][] bestMove;
    private int currentPlayer;
    private GetPossibleMoves getPossibleMoves = new GetPossibleMoves();

    private EvaluationFunction rootEvaluation;
    private double rootScore;

    private int depth;

    private int strategy;

    public MCTS(int[][] boardState, int currentPlayer, int depth, int strategy){

        this.boardState = boardState;
        this.currentPlayer = currentPlayer;
        this.rootEvaluation = new EvaluationFunction(currentPlayer, boardState, boardState, strategy);
        this.rootScore = rootEvaluation.evaluate();
        this.depth = depth;
        this.strategy = strategy;
    }

    public void start(){

        ArrayList<int[][]> rootChildren = getPossibleMoves.getPossibleMoves(boardState, currentPlayer);

        int childrenSize = rootChildren.size();
        int bestIndex = 0;
        int maxScore = Integer.MIN_VALUE;
        bestMove = new int[9][9];

        for(int i = 0; i < childrenSize; i++) {
            rootChildren = getPossibleMoves.getPossibleMoves(boardState, currentPlayer);
            int score = simulation(rootChildren.get(i));
            if (maxScore < score) {
                maxScore = score;
                bestIndex = i;
            }
        }
        rootChildren = getPossibleMoves.getPossibleMoves(boardState, currentPlayer);
        bestMove = rootChildren.get(bestIndex);

//        System.out.println();
//        System.out.println("maxScore = " + maxScore);
//        System.out.println();
//        System.out.println("best move = ");
//        Test.printBoard(bestMove);
    }

    public int simulation(int[][] currentBoard){

        int simulationScore = 0;

        for (int i = 0; i < 100; i++) {
            long b_time = System.currentTimeMillis();
            int actualPlayer = currentPlayer;
            int[][] actualBoard = currentBoard;
            int countMoves = 0;
            // while ((System.currentTimeMillis() - b_time) < 50) {
            while (countMoves <= depth) {
                ArrayList<int[][]> children = getPossibleMoves.getPossibleMoves(actualBoard, actualPlayer);

                int max = children.size();
                int randomIndex = (int)(Math.random() * ((max)));

                if (children.size() > 0) {
                    actualBoard = children.get(randomIndex);
                }

                if (actualPlayer == 1) {
                    actualPlayer = 2;
                }
                else {
                    actualPlayer = 1;
                }
                countMoves++;
            }

            EvaluationFunction evaluation = new EvaluationFunction(currentPlayer, actualBoard, boardState, strategy);

            if (evaluation.evaluate() >= rootScore) {
                simulationScore++;
            }
            else {
                simulationScore--;
            }
        }
        // System.out.println("simulationScore = " + simulationScore);
        return simulationScore;
    }

    public int[][] getBestMove() {
        return bestMove;
    }
}
