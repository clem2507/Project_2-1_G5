package Abalon.AI;

import java.util.*;

public class RuleBased {

    private int[][] boardState;
    private int[][] bestMove;
    private int currentPlayer;
    private GetPossibleMoves getPossibleMoves = new GetPossibleMoves();

    public RuleBased(int[][] boardState, int currentPlayer){

        this.boardState = boardState;
        this.currentPlayer = currentPlayer;
    }

    public void start(){
        int opponent;

        if (currentPlayer == 1){
            opponent = 2;
        }else{
            opponent = 1;
        }

        bestMove = new int[9][9];
        int oppMarbles = countOpponentMarbles(opponent, boardState);

        ArrayList<int[][]> tripleMovesEject = new ArrayList<>();
        ArrayList<int[][]> tripleMoves = getPossibleMoves.getTripleMarbleMoves(boardState, currentPlayer);

        for(int[][] move : tripleMoves){
            int oppMarblesAfterMove = countOpponentMarbles(opponent, move);
            if(oppMarblesAfterMove < oppMarbles){
                tripleMovesEject.add(move);
            }
        }
        int bests3 = tripleMovesEject.size();
        int triples = tripleMoves.size();

        if(bests3 != 0){ // not empty
            int rand = new Random().nextInt(bests3); //between 0 and bests
            bestMove = tripleMovesEject.get(rand);
        }else{
            ArrayList<int[][]> doubleMovesEject = new ArrayList<>();
            ArrayList<int[][]> doubleMoves = getPossibleMoves.getDoubleMarbleMoves(boardState, currentPlayer);

            for(int[][] move : doubleMoves){
                int oppMarblesAfterMove = countOpponentMarbles(opponent, move);
                if(oppMarblesAfterMove < oppMarbles){
                    doubleMovesEject.add(move);
                }
            }
            int bests2 = doubleMovesEject.size();

            if(bests2 != 0){ // not empty
                int rand = new Random().nextInt(bests2); //between 0 and bests
                bestMove = doubleMovesEject.get(rand);
            }else{
                int rand = new Random().nextInt(triples); //between 0 and triples
                bestMove = tripleMovesEject.get(rand);
            }
        }
    }

    private int countOpponentMarbles(int opponent, int[][] board) {
        int count = 0;

        for(int i=0; i < board.length; i++){
            for (int j=0; j < board[0].length; j++){
                if (board[i][j] == opponent){
                    count ++;
                }
            }
        }
        return count;
    }

    public int[][] getBestMove() {
        return bestMove;
    }
}
