import java.util.ArrayList;
public class GameTree {

    private Rules rules;
    private ArrayList<int[][]> allPossibleMoves = new ArrayList<>();


    public ArrayList<int[][]> getPossibleMoves(int[][] board, int playerTurn) {
        //TODO take the board and create move objects for every possible move the player can make


        allPossibleMoves.addAll(getSingleMarbleMoves(board, playerTurn));



        return allPossibleMoves; //this will be an arraylist containing move objects
    }

    public ArrayList<int[][]> getSingleMarbleMoves(int[][] board, int playerTurn) {
        ArrayList<int[][]> singleMarbleMoves = new ArrayList<>();

        for(int i = 0; i<board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == playerTurn) { //search the entire array for marbles belonging to player 1
                    Move mv = new Move(); //create a move object

                    mv.pushing = new int[3][2]; //add the first marble to pushing for the move
                    mv.pushing[0][0] = j;       //QUESTION: in what form does pushing usually come? Is this valid for the rules class?
                    mv.pushing[0][1] = i;

                    mv.board = board.clone(); //clone the board as it is as a new object that can be altered
                    mv.turn = playerTurn;

                    mv.dir = MoveDirection.TOP_LEFT;
                    rules = new Rules(mv);
                    if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                        rules.move(); //change the cloned board to the state after the move has been made
                        singleMarbleMoves.add(mv.board); //add this state to the array of possible moves
                    }

                    mv.dir = MoveDirection.LEFT;
                    mv.board = board.clone(); //create a new object from the game state before the move
                    //rules = new Rules(mv);
                    if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                        rules.move();
                        singleMarbleMoves.add(mv.board);
                    }

                    mv.dir = MoveDirection.BOTTOM_LEFT;
                    mv.board = board.clone();
                    if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                        rules.move();
                        singleMarbleMoves.add(mv.board);
                    }

                    mv.dir = MoveDirection.TOP_RIGHT;
                    mv.board = board.clone();
                    if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                        rules.move();
                        singleMarbleMoves.add(mv.board);
                    }

                    mv.dir = MoveDirection.RIGHT;
                    mv.board = board.clone();
                    if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                        rules.move();
                        singleMarbleMoves.add(mv.board);
                    }

                    mv.dir = MoveDirection.BOTTOM_RIGHT;
                    mv.board = board.clone();
                    if (rules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)) {
                        rules.move();
                        singleMarbleMoves.add(mv.board);
                    }
                }

            }
        }
        return singleMarbleMoves;
    }

    public ArrayList<int[][]> doubleMarblemMoves(int[][] board, int playerTurn) {
        ArrayList<int[][]> doubleMarbleMoves = new ArrayList<>();
        return doubleMarbleMoves;
        //TODO find all combinations of two marbles then use the same operations as singleMarbleMoves
    }

    public ArrayList<int[][]> tripleMarbleMoves(int[][] board, int playerTurn) {
        ArrayList<int[][]> tripleMarbleMoves = new ArrayList<>();
        return tripleMarbleMoves;
        //TODO find all combinations of three marbles then use the same method operations as singleMarbleMoves
    }
}

