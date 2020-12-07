package Abalon.AI;

import java.util.*;

public class RuleBased {

    private int[][] boardState;
    private int[][] bestMove;
    private int currentPlayer;
    private GetPossibleMoves getPossibleMoves = new GetPossibleMoves();

    private EvaluationFunction rootEvaluation;
    private double rootScore;

    private int depth;

    private int strategy;

    public RuleBased(int[][] boardState, int currentPlayer, int depth, int strategy){

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
        bestMove = new int[9][9];

        if (childrenSize >= 20)
        	bestMove = rootChildren.get(20);
        
        else
        	bestMove = rootChildren.get(ChildrenSize - 1);
        
        // Problem: which conditions to use ?
    }

    public int[][] getBestMove() {
        return bestMove;
    }
}
