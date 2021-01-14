package Abalon.AI.RB;

import Abalon.AI.EvaluationFunction.NeutralEvalFunct;
import Abalon.AI.EvaluationFunction.OffensiveEvalFunct;
import Abalon.AI.Tree.GetPossibleMoves;

import java.util.*;

public class RuleBased {

    private int[][] boardState;
    private int[][] bestMove = new int[9][9];
    private int currentPlayer;
    private GetPossibleMoves getPossibleMoves = new GetPossibleMoves();
    private int[][] previousMove;

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

        ArrayList<int[][]> children = getPossibleMoves.getPossibleMoves(boardState, currentPlayer);
        ArrayList<int[][]> sumitoMoves = getSumitoMoves(children, boardState, opponent); //ejecting moves
        ArrayList<int[][]> pushingMoves = getPushingMoves(children, boardState, opponent);

        if(sumitoMoves.size() != 0){ // not empty
            System.out.println("ejecting moves found!");

            int rand = new Random().nextInt(sumitoMoves.size());
            bestMove = sumitoMoves.get(rand);

        }else{
            System.out.println("no sumito/ejecting moves yet");

            if(pushingMoves.size() != 0){ // not empty
                System.out.println("pushing moves found!");

                int rand = new Random().nextInt(pushingMoves.size());
                bestMove = pushingMoves.get(rand);

            }else{
                System.out.println("no pushing moves nor ejecting moves >> pick random triple move!");

                ArrayList<int[][]> tripleMoves = getPossibleMoves.getTripleMarbleMoves(boardState, currentPlayer);

                do{
                    bestMove = tripleMoves.get(new Random().nextInt(tripleMoves.size()));
                } while(bestMove == previousMove);
            }
        }
        previousMove = bestMove;
    }


    public ArrayList<int[][]> getPushingMoves(ArrayList<int[][]> children, int[][] board, int opponentPlayer) {
        ArrayList<int[][]> pushings = new ArrayList<>();

        for (int[][] child : children) {
            if (!OffensiveEvalFunct.checkOpponentMarble(board, child, opponentPlayer)) {
                pushings.add(child);
                System.out.println("pushings ++");
            }
        }
        return pushings;
    }

    public ArrayList<int[][]> getSumitoMoves(ArrayList<int[][]> children, int[][] board, int opponentPlayer){
        ArrayList<int[][]> sumitos = new ArrayList<>();

        for(int[][] child : children){
            if(NeutralEvalFunct.countMarbles(opponentPlayer, board) > NeutralEvalFunct.countMarbles(opponentPlayer, child)){
                sumitos.add(child);
                System.out.println("sumito ++");
            }
        }
        return sumitos;
    }

    public int[][] getBestMove() {
        return bestMove;
    }
}
