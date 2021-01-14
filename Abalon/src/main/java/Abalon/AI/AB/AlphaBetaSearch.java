package Abalon.AI.AB;

import Abalon.AI.Tree.GameTree;
import Abalon.AI.Tree.Node;

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
        System.out.println("Number of nodes = " + tree.getNodes().size());
        this.totalDepth = gameTree.getGeneration();
    }

    public void start(boolean alphaBeta) {

        rootChildren = GameTree.getChildren(tree.getNodes().get(0));

        double bestScore;
        if (alphaBeta) {
            bestScore = ab_minimax(tree.getNodes().get(0), totalDepth, true, Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);
        }
        else {
            bestScore = minimax(tree.getNodes().get(0), totalDepth, true);
        }

        for (Node n : rootChildrenBoard) {
            if (n.getScore() == bestScore) {
                bestMove = n.getBoardState();
            }
        }
    }

    // minimax algorithm without alpha-beta pruning
    public double minimax(Node position, int depth, boolean maximizingPlayer) {

        if (depth == 0) {
            return position.getScore();
        }
        
        if (maximizingPlayer) {
            ArrayList<Node> children = GameTree.getChildren(position);
            double maxEvaluation = Double.NEGATIVE_INFINITY;
            for (Node child : children) {
                double evaluation = minimax(child, depth-1, false);
                maxEvaluation = Double.max(maxEvaluation, evaluation);
            }
            return maxEvaluation;
        }
        else {
            ArrayList<Node> children = GameTree.getChildren(position);
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
