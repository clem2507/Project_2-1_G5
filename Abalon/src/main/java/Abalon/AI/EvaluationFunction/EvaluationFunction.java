package Abalon.AI.EvaluationFunction;

public abstract class EvaluationFunction {

    public static int currentPlayer;
    public static int[][] cellColor;
    public static int[][] rootCellColor;

    public EvaluationFunction(int currentPlayer, int[][] cellColor, int[][] rootCellColor) {

        this.currentPlayer = currentPlayer;
        this.cellColor = cellColor;
        this.rootCellColor = rootCellColor;
    }

    public abstract double evaluate();

    public int getCurrentPlayer(){
        return currentPlayer;
    }

    public int[][] getCellColor(){
        return cellColor;
    }

    public int[][] getRootCellColor(){
        return rootCellColor;
    }

}
