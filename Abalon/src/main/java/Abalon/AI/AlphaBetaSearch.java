package Abalon.AI;

import Abalon.Main.Player;

import java.util.ArrayList;

public class AlphaBetaSearch {

    private GameTree tree;
    private int totalDepth;
    private ArrayList<Node> rootChildren;
    private ArrayList<Node> rootChildrenBoard = new ArrayList<>();
    private int[][] bestMove;
    private int index = 0;
    private int prunedCount = 0;


    public AlphaBetaSearch(GameTree gameTree) {

        this.tree = gameTree;
        this.totalDepth = gameTree.getGeneration();
    }

    public void start(boolean alphaBeta) {

        rootChildren = tree.getChildren(tree.getNodes().get(0));

        double bestScore;
        if (alphaBeta) {
            bestScore = ab_minimax(tree.getNodes().get(0), totalDepth, true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        }
        else {
            bestScore = minimax(tree.getNodes().get(0), totalDepth, true);
        }
        System.out.println();
        System.out.println("pruned branch count alpha-beta = " + prunedCount);
        System.out.println();
        System.out.println("best score = " + bestScore);
        System.out.println("best move = ");

        for (Node n : rootChildrenBoard) {
            if (n.getScore() == bestScore) {
                bestMove = n.getBoardState();
            }
        }
        Test.printBoard(bestMove);

        // TODO : need to perform the best move on the board
    }

    // minimax algorithm without alpha-beta technique
    public double minimax(Node position, int depth, boolean maximizingPlayer) {

        ArrayList<Node> children = tree.getChildren(position);

        if (depth == 0) {
            return position.getScore();
        }

        if (maximizingPlayer) {
            double maxEvaluation = Double.NEGATIVE_INFINITY;
            for (Node child : children) {
                double evaluation = minimax(child, depth-1, false);
                maxEvaluation = Double.max(maxEvaluation, evaluation);
            }
            return maxEvaluation;
        }
        else {
            double minEvaluation = Double.POSITIVE_INFINITY;
            for (Node child : children) {
                double evaluation = minimax(child, depth-1, true);
                minEvaluation = Double.min(minEvaluation, evaluation);
            }
            if (depth == totalDepth-1) {
                Node node = new Node(rootChildren.get(index).getBoardState(), minEvaluation);
                rootChildrenBoard.add(node);
                index++;
            }
            return minEvaluation;
        }
    }

    // minimax algorithm with alpha-beta pruning implemented
    public double ab_minimax(Node position, int depth, boolean maximizingPlayer, double alpha, double beta) {

        ArrayList<Node> children = tree.getChildren(position);

        if (depth == 0) {
            return position.getScore();
        }

        if (maximizingPlayer) {
            double maxEvaluation = Double.NEGATIVE_INFINITY;
            for (Node child : children) {
                double evaluation = ab_minimax(child, depth-1, false, alpha, beta);
                maxEvaluation = Double.max(maxEvaluation, evaluation);
                alpha = Math.max(alpha, evaluation);
                if (beta <= alpha) {
                    prunedCount++;
                    break;
                }
            }
            return maxEvaluation;
        }
        else {
            double minEvaluation = Double.POSITIVE_INFINITY;
            for (Node child : children) {
                double evaluation = ab_minimax(child, depth-1, true, alpha, beta);
                minEvaluation = Double.min(minEvaluation, evaluation);
                beta = Math.min(beta, evaluation);
                if (beta <= alpha) {
                    prunedCount++;
                    break;
                }
            }
            if (depth == totalDepth-1) {
                Node node = new Node(rootChildren.get(index).getBoardState(), minEvaluation);
                rootChildrenBoard.add(node);
                index++;
            }
            return minEvaluation;
        }
    }

    public int[][] getBestMove() {
        return bestMove;
    }
}

