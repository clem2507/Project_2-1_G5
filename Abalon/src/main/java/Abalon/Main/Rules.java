public class Rules {


    private int[][] board;
    private int playerTurn;
    private MoveDirection direction; //stores the target direction of the current move
    private ArrayList<int[]> marblesSelected = new ArrayList<>(); //stores the coordinates of the selected marbles
    private static int nbSelected; //keeps track of how many marbles have been selected

    public Rules(MoveDirection moveTo, ArrayList<int[]> selectedMarbles, int selectedNb, int[][] currentBoard){
        playerTurn = 1;
        direction=moveTo;
        marblesSelected=selectedMarbles;
        nbSelected=selectedNb;
        this.board=currentBoard;
    }

    public void move(){
        if(nbSelected == 1) {
            if(checkMove(null,null,board)) //TODO checkMove still needs to be correctly implemented to check the different types of moves ( direction and nb of marbles)
            { performMoveOne(marblesSelected.get(0),direction,board); }
        }
        else if(nbSelected == 2){
            if(checkMove(null,null,board))
            { performMoveTwo(marblesSelected.get(0),marblesSelected.get(1),direction,board); }
        }
        else if(nbSelected == 3){
            if (checkMove(null,null,board))
            { performMoveThree(marblesSelected.get(0),marblesSelected.get(1), marblesSelected.get(2),direction,board); }
        }
    }



    public static boolean checkMove(List<int[][]> pushing, List<int[][]> pushed, int[][] board, MoveDirection direction, int playerTurn) {

            switch(pushing.length) {

                case 1:
                    if (checkOutOfBounds(pushing[0][], direction, board)) { return false; }
                    return checkSingleMarble(pushing[0][], direction, board, playerTurn);

        }
    }
    private static boolean checkSingleMarble(int[] marble, MoveDirection direction, int[][] board, int playerTurn) {

        if(checkOutOfBounds(marble, direction, board)) { return false; }
        switch(direction) {

            case TOP_LEFT:

                if(marble[0] >= 5) {
                    switch(board[marble[0]-1][marble[1]]) {
                        case 0: return true;
                        case 1: return (playerTurn == 2);
                        case 2: return (playerTurn == 1);
                    }
                }
                else {
                    switch(board[marble[0]-1][marble[1]-1]) {
                        case 0: return true;
                        case 1: return (playerTurn == 2);
                        case 2: return (playerTurn == 1);
                    }
                }
            case LEFT:
                switch(board[marble[0]][marble[1]-1]) {
                    case 0: return true;
                    case 1: return (playerTurn == 2);
                    case 2: return (playerTurn == 1);
                }

            case BOTTOM_LEFT:
                if(marble[0] >= 5) {
                    switch(board[marble[0]+1][marble[1]-1]) {
                        case 0: return true;
                        case 1: return (playerTurn == 2);
                        case 2: return (playerTurn == 1);
                    }
                }
                else {
                    switch(board[marble[0]+1][marble[1]]) {
                        case 0: return true;
                        case 1: return (playerTurn == 2);
                        case 2: return (playerTurn == 1);
                    }
                }
            case BOTTOM_RIGHT:
                if(marble[0] >= 5) {
                    switch(board[marble[0]+1][marble[1]-1]) {
                        case 0: return true;
                        case 1: return (playerTurn == 2);
                        case 2: return (playerTurn == 1);
                    }
                }
                else {
                    switch(board[marble[0]+1][marble[1]+1]) {
                        case 0: return true;
                        case 1: return (playerTurn == 2);
                        case 2: return (playerTurn == 1);
                    }
                }
            case RIGHT:
                switch(board[marble[0]][marble[1]+1]) {
                    case 0: return true;
                    case 1: return (playerTurn == 2);
                    case 2: return (playerTurn == 1);
                }

            case TOP_RIGHT:
                if (marble[0] >= 5) {
                    switch(board[marble[0]-1][marble[1]+1]) {
                        case 0: return true;
                        case 1: return (playerTurn == 2);
                        case 2: return (playerTurn == 1);
                    }
                }
                else {
                    switch(board[marble[0]-1][marble[1]]) {
                        case 0: return true;
                        case 1: return (playerTurn == 2);
                        case 2: return (playerTurn == 1);
                    }
                }
        }
        return false;
    }
}


    /*
        checks if a suggested move will leave a marble out of bounds

        @param board A 2D integer array that contains the colors of every position on the hexagon.
               1 = a marble that belongs to player 1.
               2 = a marble that belongs to player 2.
               0 = a position that does not contain a marble.
               -1 = a position in the array that is out of bounds.

        @param direction an enum that contains the direction of the suggested move.
        @param marble An integer array with length 2. The first int is the row the marble is at and the second int
               is the column the marble is at.

       @returns a boolean. True if the move will leave the marble out of bounds, false if it will not.
     */
    private static boolean checkOutOfBounds(int[] marble, MoveDirection direction, int[][] board) {

        switch (direction) {

            case TOP_LEFT:
                //check if a marble is on the top half.
                if (marble[0] <= 5) {
                    //check if the marble is on the far left.
                    if (marble[1] == 0) {
                        return true;
                    }
                } else {
                    return false;
                }

            case LEFT:
                //check if a marble is on the far left.
                if (marble[1] == 0) {
                    return true;
                } else {
                    return false;
                }

            case BOTTOM_LEFT:
                //check if a marble is on the bottom half.
                if (marble[0] <= 5) {
                    //check if the marble is on the far left.
                    if (marble[1] == 0) {
                        return true;
                    }
                } else {
                    return false;
                }

            case TOP_RIGHT:
                //check if a marble is on the top half.
                if (marble[0] <= 5) {
                    //check if that marble is at the absolute top
                    if (marble[0] == 0) {
                        return true;
                    }
                    //check if the marble is on the far right.
                    int rowAbove = marble[0] - 1;
                    if (board[rowAbove][marble[1]] == -1) {
                        return true;
                    }
                } else {
                    return false;
                }


            case RIGHT:
                //check if the marble is on the far right.
                if (board[marble[0]][marble[1] + 1] == -1) {
                    return true;
                } else {
                    return false;
                }

            case BOTTOM_RIGHT:
                //check if a marble is on the bottom half.
                if (marble[0] >= 5) {
                    //check if that marble is at the absolute bottom.
                    if (marble[0] == 9) {
                        return true;
                    }
                    //check if the marble is on the far right.
                    int rowBelow = marble[0] + 1;
                    if (board[rowBelow][marble[1]] == -1) {
                        return true;
                    }
                } else {
                    return false;
                }
        }
        return true;
    }

    public void performMoveOne(int[] marble, MoveDirection direction, int[][] board)
    { moveMarble(marble, direction, board); }
    public void performMoveTwo(int[] marble1, int[] marble2, MoveDirection direction, int[][] board) {
        //TODO Check if the move is in the direction of the selected marbles, if it's not, then the move is a sideway movement --> particular case)
        /*
        if(checkSidewayMovement){ moveSideways(...);}
        else if(target direction is empty){
        */
        moveMarble(marble2,direction,board); /** QUESTION: What happens if marble1 is in top part of board but marble2 in bottom? Is this valid? maybe need to implement a specific method to move 2 and move 3*/
        /*
        else if(target direction is occupied by an opponent marble){pushOpponentOne(...)}
        */
    }
    public void performMoveThree(int[] marble1, int[] marble2, int[] marble3, MoveDirection direction, int[][] board) {
        //TODO Check if the move is in the direction of the selected marbles, if it's not, then the move is a sideway movement --> particular case)
        /*
        if(checkSidewayMovement){ moveSideways(...);}
        else if(target direction is empty){
        */
        moveMarble(marble3,direction,board); /** QUESTION: Same question but with 3 marbles..*/
        //else if(target direction is occupied by an opponent marble){pushOpponentTwo(...)}
    }


    public void moveMarble(int[] marble, MoveDirection direction, int[][] board) {
        switch (direction)
        {
            case TOP_LEFT:
                //Check if bottom or top part of the board
                // Then replace value of target direction to player's number
                if (marble[0] >= 5)
                {
                    if(playerTurn == 1)
                    {
                        board[marble[0]-1][marble[1]] = 1; // Then replace value of target direction to player's number. So here 1 because it is player 1 turn
                        board[marble[0]][marble[1]] = 0; // Set the old marble position to zero because it becomes an empty zone
                    }
                    else if(playerTurn == 2)
                    {
                        board[marble[0]-1][marble[1]] = 2; // Then replace value of target direction to player's number. So here 2 because it is player 2 turn
                        board[marble[0]][marble[1]] = 0; // Set the old marble position to zero because it becomes an empty zone
                    }
                }
                else
                {
                    if(playerTurn == 1)
                    {
                        board[marble[0]-1][marble[1]-1] = 1; // Then replace value of target direction to player's number. So here 1 because it is player 1 turn
                        board[marble[0]][marble[1]] = 0; // Set the old marble position to zero because it becomes an empty zone
                    }
                    else if(playerTurn == 2)
                    {
                        board[marble[0]-1][marble[1]-1] = 2; // Then replace value of target direction to player's number. So here 2 because it is player 2 turn
                        board[marble[0]][marble[1]] = 0; // Set the old marble position to zero because it becomes an empty zone
                    }
                }
            case TOP_RIGHT:
                if (marble[0] >= 5)
                {
                    if(playerTurn == 1)
                    {
                        board[marble[0]-1][marble[1]+1] = 1;
                        board[marble[0]][marble[1]] = 0;
                    }
                    else if(playerTurn == 2)
                    {
                        board[marble[0]-1][marble[1]+1] = 2;
                        board[marble[0]][marble[1]] = 0;
                    }
                }
                else
                {
                    if(playerTurn == 1)
                    {
                        board[marble[0]-1][marble[1]] = 1;
                        board[marble[0]][marble[1]] = 0;
                    }
                    else if(playerTurn == 2)
                    {
                        board[marble[0]-1][marble[1]] = 2;
                        board[marble[0]][marble[1]] = 0;
                    }
                }
            case BOTTOM_RIGHT:
                if (marble[0] >= 5)
                {
                    if(playerTurn == 1)
                    {
                        board[marble[0]+1][marble[1]] = 1;
                        board[marble[0]][marble[1]] = 0;
                    }
                    else if(playerTurn == 2)
                    {
                        board[marble[0]+1][marble[1]] = 2;
                        board[marble[0]][marble[1]] = 0;
                    }
                }
                else
                {
                    if(playerTurn == 1)
                    {
                        board[marble[0]+1][marble[1]+1] = 1;
                        board[marble[0]][marble[1]] = 0;
                    }
                    else if(playerTurn == 2)
                    {
                        board[marble[0]+1][marble[1]+1] = 2;
                        board[marble[0]][marble[1]] = 0;
                    }
                }
            case BOTTOM_LEFT:
                if (marble[0] >= 5)
                {
                    if(playerTurn==1)
                    {
                        board[marble[0]+1][marble[1]-1] = 1;
                        board[marble[0]][marble[1]] = 0;
                    }
                    else if(playerTurn==2)
                    {
                        board[marble[0]+1][marble[1]-1] = 2;
                        board[marble[0]][marble[1]] = 0;
                    }
                }
                else
                {
                    if(playerTurn == 1)
                    {
                        board[marble[0]+1][marble[1]] = 1;
                        board[marble[0]][marble[1]] = 0;
                    }
                    else if(playerTurn == 2)
                    {
                        board[marble[0]+1][marble[1]] = 2;
                        board[marble[0]][marble[1]] = 0;
                    }
                }
            case RIGHT:
                if(playerTurn == 1)
                {
                    board[marble[0]][marble[1]+1] = 1;
                    board[marble[0]][marble[1]] = 0;
                }
                else if(playerTurn == 2)
                {
                    board[marble[0]][marble[1]+1] = 2;
                    board[marble[0]][marble[1]] = 0;
                }
            case LEFT:
                if(playerTurn == 1)
                {
                    board[marble[0]][marble[1]-1] = 1;
                    board[marble[0]][marble[1]] = 0;
                }
                else if(playerTurn == 2)
                {
                    board[marble[0]][marble[1]-1] = 2;
                    board[marble[0]][marble[1]] = 0;
                };
        }
    }

}