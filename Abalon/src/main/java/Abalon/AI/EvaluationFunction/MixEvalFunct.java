package Abalon.AI.EvaluationFunction;

import Abalon.AI.Tree.GetPossibleMoves;

/**
 * This class aims to use all three evaluation functions (Neutral, Offensive & Defensive) to form
 * the Mix Evaluation Function. The Mix Evaluation Function adapts itself to the situation so that it
 * uses the proper evaluation function at the proper time.
 * Because it wasn't efficient enough, we've decided no to use this new evaluation function.
 */

public class MixEvalFunct extends EvaluationFunction{

    public MixEvalFunct(int currentPlayer, int[][] cellColor, int[][] rootCellColor) {
        super(currentPlayer, cellColor, rootCellColor);
    }

    public double evaluate() {
        double returnValue = 0;

        int strategy = chooseEvalFunction(25);

        if(strategy == 1){
            EvaluationFunction neutral = new NeutralEvalFunct(currentPlayer, cellColor, rootCellColor);
            returnValue = neutral.evaluate();
        }else if(strategy == 2){
            EvaluationFunction offensive = new OffensiveEvalFunct(currentPlayer, cellColor, rootCellColor);
            returnValue = offensive.evaluate();
        }else if(strategy == 3){
            EvaluationFunction defensive = new DefensiveEvalFunct(currentPlayer, cellColor, rootCellColor);
            returnValue = defensive.evaluate();
        }

        return returnValue;
    }

    public int chooseEvalFunction(int tresholdDefense) {
        int strategy;

        GetPossibleMoves obj = new GetPossibleMoves();

        int ownMarblesCenterDistance = NeutralEvalFunct.centerDistance(currentPlayer, cellColor);
        int ownMarblesNeighbours = NeutralEvalFunct.marblesNeighbourhood(currentPlayer, cellColor);
        int diffBetweenPlayersMarbles;
        int numberSumitos;

        int opponentPlayer;

        if(currentPlayer == 1){
            opponentPlayer = 2;
            diffBetweenPlayersMarbles = NeutralEvalFunct.countMarbles(currentPlayer, cellColor) -  NeutralEvalFunct.countMarbles(opponentPlayer, cellColor);
            numberSumitos = OffensiveEvalFunct.countSumitoPositions(obj.getPossibleMoves(cellColor, currentPlayer), cellColor, opponentPlayer);
        }
        else{
            opponentPlayer = 1;
            diffBetweenPlayersMarbles = NeutralEvalFunct.countMarbles(currentPlayer, cellColor) -  NeutralEvalFunct.countMarbles(opponentPlayer, cellColor);
            numberSumitos = OffensiveEvalFunct.countSumitoPositions(obj.getPossibleMoves(cellColor, currentPlayer), cellColor, opponentPlayer);
        }

        if(ownMarblesCenterDistance < tresholdDefense || ownMarblesNeighbours < 40){
            //Defensive
            strategy = 3;
        }
        else if(numberSumitos > 0){
            //Defensive
            strategy = 3;
        }
        else if(diffBetweenPlayersMarbles > 0){
            //Offensive
            strategy = 2;
        }
        else{
            //Neutral
            strategy = 1;
        }

        return strategy;
    }
}
