package Abalon.AI.RB;

import Abalon.AI.EvaluationFunction.NeutralEvalFunct;
import Abalon.AI.EvaluationFunction.OffensiveEvalFunct;
import Abalon.AI.Tree.GetPossibleMoves;

import java.util.*;

/**
 * This class defines the rule-based algorithm used by a bot to play the Abalon game
 */
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
            int rand = new Random().nextInt(sumitoMoves.size());
            bestMove = sumitoMoves.get(rand);

        }else{
            if(pushingMoves.size() != 0){ // not empty
                int rand = new Random().nextInt(pushingMoves.size());
                bestMove = pushingMoves.get(rand);

            }else{
                ArrayList<int[][]> tripleMoves = getPossibleMoves.getTripleMarbleMoves(boardState, currentPlayer, true);

                if(tripleMoves.size() == 0){
                    ArrayList<int[][]> doubleMoves = getPossibleMoves.getDoubleMarbleMoves(boardState, currentPlayer, true);

                    if(doubleMoves.size() != 0){
                        do{
                            bestMove = doubleMoves.get(new Random().nextInt(doubleMoves.size()));
                        } while(bestMove == previousMove && doubleMoves.size()!=1);
                    }else{
                        ArrayList<int[][]> singleMoves = getPossibleMoves.getSingleMarbleMoves(boardState, currentPlayer);
                        do{
                            bestMove = singleMoves.get(new Random().nextInt(singleMoves.size()));
                        } while(bestMove == previousMove);
                    }
                }else{
                    do{
                        bestMove = tripleMoves.get(new Random().nextInt(tripleMoves.size()));
                    } while(bestMove == previousMove && tripleMoves.size()!=1);
                }
            }
        }
        previousMove = bestMove;
    }

    /**
     * Computes the moves that displace an opponent's marble
     */
    public ArrayList<int[][]> getPushingMoves(ArrayList<int[][]> children, int[][] board, int opponentPlayer) {
        ArrayList<int[][]> pushings = new ArrayList<>();

        for (int[][] child : children) {
            if (!OffensiveEvalFunct.checkOpponentMarble(board, child, opponentPlayer)) {
                pushings.add(child);
            }
        }
        return pushings;
    }

    /**
     * Computes the moves that eject an opponent's marble
     */
    public ArrayList<int[][]> getSumitoMoves(ArrayList<int[][]> children, int[][] board, int opponentPlayer){
        ArrayList<int[][]> sumitos = new ArrayList<>();

        for(int[][] child : children){
            if(NeutralEvalFunct.countMarbles(opponentPlayer, board) > NeutralEvalFunct.countMarbles(opponentPlayer, child)){
                sumitos.add(child);
            }
        }
        return sumitos;
    }

    public int[][] getBestMove() {
        return bestMove;
    }
}
