package Abalon.AI.EvaluationFunction;

//TODO add MixEvalFunct as option in the game settings

import Abalon.AI.Tree.GetPossibleMoves;

public class MixEvalFunct {

    public OffensiveEvalFunct offensiveEvalFunct;

    private static int currentPlayer;
    private static int[][] cellColor; //current board (node of the game tree)
    private static int[][] rootCellColor; //root of current game tree

    public MixEvalFunct(int currentPlayer, int[][] cellColor, int[][] rootCellColor) {

        MixEvalFunct.currentPlayer = currentPlayer;
        MixEvalFunct.cellColor = cellColor;
        MixEvalFunct.rootCellColor = rootCellColor;
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
        int neutralScore = 0;
        int offensiveScore = 0;
        int defensiveScore = 0;

        // TODO compute scores
        /*
        Features that we want to use:
        */
        GetPossibleMoves obj = new GetPossibleMoves();
        int ownMarblesCenterDistance = NeutralEvalFunct.centerDistance(currentPlayer, cellColor);
        int ownMarblesNeighbours = NeutralEvalFunct.marblesNeighbourhood(currentPlayer, cellColor);
        int diffBetweenPlayersMarbles;
        int numberSumitos;

        int opponentPlayer;

        if(currentPlayer == 1){
            opponentPlayer = 2;
           diffBetweenPlayersMarbles = NeutralEvalFunct.countMarbles(currentPlayer, cellColor) -  NeutralEvalFunct.countMarbles(opponentPlayer, cellColor);
           numberSumitos = offensiveEvalFunct.countSumitoPositions(obj.getPossibleMoves(cellColor, currentPlayer), cellColor, opponentPlayer);
        }
        else{
            opponentPlayer = 1;
            diffBetweenPlayersMarbles = NeutralEvalFunct.countMarbles(currentPlayer, cellColor) -  NeutralEvalFunct.countMarbles(opponentPlayer, cellColor);
            numberSumitos = offensiveEvalFunct.countSumitoPositions(obj.getPossibleMoves(cellColor, currentPlayer), cellColor, opponentPlayer);
        }

        //Defensive
        if(ownMarblesCenterDistance < tresholdDefense){
            //Defensive
            strategy = 3;
        }
        else{
            //Neutral
            strategy = 1;
        }



        strategy = pickMax(neutralScore, offensiveScore, defensiveScore);

        return strategy;
    }

    private int pickMax(int neutralScore, int offensiveScore, int defensiveScore) {
        int strategy = 1; //neutral by default

        if(neutralScore > offensiveScore && neutralScore > defensiveScore){ //neural absolutely bigger
            strategy = 1; //neutral
        }else if(offensiveScore > neutralScore && offensiveScore > defensiveScore){ //offensive absolutely bigger
            strategy = 2; //offensive
        }else if(defensiveScore > offensiveScore && defensiveScore > neutralScore){ //defensive absolutely bigger
            strategy = 3; //defensive
        }else if(neutralScore > offensiveScore && offensiveScore == defensiveScore){ //neutral bigger and def = off
            strategy = 1;
        }else if(offensiveScore > neutralScore && neutralScore == defensiveScore){ //offensive bigger and def = neut
            strategy = 2;
        }else if(defensiveScore > neutralScore && neutralScore == offensiveScore){ //defensive bigger and off = neut
            strategy = 3;
        }

        return strategy;
    }
}
