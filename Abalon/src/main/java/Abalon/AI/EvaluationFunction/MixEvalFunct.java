package Abalon.AI.EvaluationFunction;

//TODO add MixEvalFunct as option in the game settings

public class MixEvalFunct {

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

        int strategy = chooseEvalFunction();

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

    public int chooseEvalFunction() {
        int strategy;
        int neutralScore = 0;
        int offensiveScore = 0;
        int defensiveScore = 0;

        // TODO compute scores

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
