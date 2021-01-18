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
    private int investigatedNodes = 0;
    public double bestScore;

    public AlphaBetaSearch(GameTree gameTree) {

        this.tree = gameTree;
        this.totalDepth = gameTree.getGeneration();
    }

    public void start(boolean alphaBeta) {

        rootChildren = tree.getChildren(tree.getNodes().get(0));

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

        ArrayList<Node> children;

        if (depth == 0) {
            return position.getScore();
        }
        else {
            children = tree.getChildren(position);
        }

        if (maximizingPlayer) {
            double maxEvaluation = Double.NEGATIVE_INFINITY;
            for (Node child : children) {
                double evaluation = minimax(child, depth-1, false);
                maxEvaluation = Double.max(maxEvaluation, evaluation);
                investigatedNodes++;
            }
            return maxEvaluation;
        }
        else {
            double minEvaluation = Double.POSITIVE_INFINITY;
            for (Node child : children) {
                double evaluation = minimax(child, depth-1, true);
                minEvaluation = Double.min(minEvaluation, evaluation);
                investigatedNodes++;
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

        ArrayList<Node> children;

        if (depth == 0) {
            return position.getScore();
        }
        else {
            children = tree.getChildren(position);
        }

        if (maximizingPlayer) {
            double maxEvaluation = Double.NEGATIVE_INFINITY;
            for (Node child : children) {
                double evaluation = ab_minimax(child, depth-1, false, alpha, beta);
                maxEvaluation = Double.max(maxEvaluation, evaluation);
                alpha = Math.max(alpha, evaluation);
                if (beta <= alpha) {
                    break;
                }
                investigatedNodes++;
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
                    break;
                }
                investigatedNodes++;
            }
            if (depth == totalDepth-1) {
                Node node = new Node(rootChildren.get(index).getBoardState(), minEvaluation);
                rootChildrenBoard.add(node);
                index++;
            }
            return minEvaluation;
        }
    }

    public int getInvestigatedNodes() {
        return investigatedNodes;
    }

    public int[][] getBestMove() {
        return bestMove;
    }

    public double getBestScore() {
        return bestScore;
    }
}
