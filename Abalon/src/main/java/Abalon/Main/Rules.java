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



    /*
        checks whether or not a suggested move is legal.
        @param pushing A 2d array that contains the positions of the selected marbles
        @param direction A direction of the MoveDirection enum
        @param playerTurn an integer that checks if it is the turn of player 1 or player 2.
    */
    public static boolean checkMove(int[][] pushing, MoveDirection direction, int[][] board,  int playerTurn) {

        switch(pushing.length) {

            case 1: return checkSingleMarble(pushing[0], direction, board);


            case 2: return checkTwoMarbles(pushing, direction, board, playerTurn);

        }
        return false;
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

    private static boolean checkDoubleMarble(int[] marble1, int[] marble2, MoveDirection direction, int[][] board, int playerTurn) {

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
                if (marble[0] >= 5) {
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

    /*
        returns the position in the array of colors where a move from a selected marble will end up.

        @param board A 2D integer array that contains the colors of every position on the hexagon.
        @param direction an enum that contains the direction of the suggested move.
        @param marble An integer array with length 2. The first int is the row the marble is at and the second int
               is the column the marble is at.
        @param playerTurn an integer that specifies what player is moving the marbles. 1 for player 1, 2 for player 2.

       @returns an integer of size 2 containing the row and column the move will leave you at.
        */
    public static int[] checkSquareForLocation(int[] marble, MoveDirection direction, int[][] board) {
        int[] location;
        if (checkOutOfBounds(marble, direction, board)) {
            location = new int[] {-1, -1};
            return location;
        }
        switch(direction) {
            case TOP_LEFT:
                if(marble[0] >= 5 ) {
                    location = new int[] {marble[0]-1, marble[1]};
                    return location;
                }
                else {
                    location = new int[] {marble[0]-1, marble[1]-1};
                    return location;
                }

            case LEFT:
                location = new int[] {marble[0], marble[1]-1};
                return location;

            case BOTTOM_LEFT:
                if(marble[0] >= 5) {
                    location = new int[] {marble[0]+1, marble[1]-1};
                    return location;
                }
                else {
                    location = new int[] {marble[0]+1, marble[1]};
                    return location;
                }

            case BOTTOM_RIGHT:
                if(marble[0] >= 5) {
                    location = new int[] {marble[0]+1, marble[1]-1};
                    return location;
                }
                else {
                    location = new int[] {marble[0]+1, marble[1]+1};
                    return location;
                }
            case RIGHT:
                location = new int[] {marble[0], marble[1]+1};
                return location;

            case TOP_RIGHT:
                if (marble[0] >= 5) {
                    location = new int[] {marble[0]-1, marble[1]+1};
                    return location;
                }
                else {
                    location = new int[] {marble[0]-1, marble[1]};
                    return location;
                }
        }
        location = new int[] {-1, -1};
        return location;
    }

    /*
        returns the color of the square a marble has been suggested to mvoe to.

        @param board A 2D integer array that contains the colors of every position on the hexagon.
        @param direction an enum that contains the direction of the suggested move.
        @param marble An integer array with length 2. The first int is the row the marble is at and the second int
               is the column the marble is at.
        @param playerTurn an integer that specifies what player is moving the marbles. 1 for player 1, 2 for player 2.

       @returns an integer:
                            1 = a marble that belongs to player 1.
                            2 = a marble that belongs to player 2.
                            0 = a position that does not contain a marble.
                           -1 = a position in the array that is out of bounds.
        */
    public static int checkSquareForColor(int[] marble, MoveDirection direction, int[][] board) {
        if (checkOutOfBounds(marble, direction, board)) { System.out.println("out of bounds"); return -1; }
        switch(direction) {

            case TOP_LEFT:
                if(marble[0] >= 5 ) {
                    return board[marble[0]-1][marble[1]];
                }
                else {
                    return board[marble[0]-1][marble[1]-1];
                }

            case LEFT:
                return board[marble[0]][marble[1]-1];

            case BOTTOM_LEFT:
                if(marble[0] >= 5) {
                    return board[marble[0]+1][marble[1]-1];
                }
                else {
                    return board[marble[0]+1][marble[1]];
                }

            case BOTTOM_RIGHT:
                if(marble[0] >= 5) {
                    return board[marble[0]+1][marble[1]-1];
                }
                else {
                    return board[marble[0]+1][marble[1]+1];
                }
            case RIGHT:
                return board[marble[0]][marble[1]+1];

            case TOP_RIGHT:
                if (marble[0] >= 5) {
                    return board[marble[0]-1][marble[1]+1];
                }
                else {
                    return board[marble[0]-1][marble[1]];
                }
        }
        return -1;
    }

    private static boolean checkTwoMarbles(int[][] pushing,  MoveDirection direction, int[][] board, int playerTurn) {

        if(checkSideways(pushing[0], pushing[1], direction, board, playerTurn)) {
            //if the marbles move sideways they cannot push anything. Check if all marbles move to a free spot.
            if(checkSingleMarble(pushing[0], direction, board)  && checkSingleMarble(pushing[1], direction, board)) {
                return true;
            }
            return false;
        }
        int[] leadingMarble = findLeadingMarble(pushing, direction, board, playerTurn);
        //TODO check the color of the square starting at the leading marble. Then see if you can push a single marble from there.
        //
    }

    /*
        checks if a row of 2 or 3 marbles is moving sideways.
        @param marble 1 a marble in the selected row.
        @param marble 2 a marble connected to marble 1 that is also in the selected row.
        @param direction the direction the row is moving in.
        @param board A 2D integer array that contains the colors of every position on the hexagon.

        @returns a boolean. True if the marbles are moving sideways. False if they are not.
    */
    public static boolean checkSideWays(int[] marble1, int[] marble2, MoveDirection direction, int[][] board) {
        int[] moveFromMarble1 = checkSquareForLocation(marble1, direction, board);
        int[] moveFromMarble2 = checkSquareForLocation(marble2, direction, board);
        if(Arrays.equals(moveFromMarble1, marble2) || Arrays.equals(moveFromMarble2, marble1)) { return false; }
        return true;
    }

    /*
        checks if a move is permitted for a single marble.
        @param direction the direction the row is moving in.
        @param board A 2D integer array that contains the colors of every position on the hexagon.
        @param marble An integer array with length 2. The first int is the row the marble is at and the second int
                      is the column the marble is at.
        @returns a boolean. True if the marble is moving to an empty spot. False if it is not.
    */
    private static boolean checkSingleMarble(int[]marble, MoveDirection direction, int[][] board) {
        //if a single marble does not move out of bounds and moves to an empty spot it is a legal move. return false in any other case
        if (checkOutOfBounds(marble, direction, board)) { return false; }
        if(checkSquareForColor(marble, direction, board) == 0) { return true; }
        return false;
    }

    /*
        searches for and returns the leading marble of the selected row.
        @param pushing a 2d array containing up to three 2 value rows with marble positions
        @param board A 2D integer array that contains the colors of every position on the hexagon.
        @param direction an enum that contains the direction of the suggested move.
        @param playerTurn an integer that specifies what player is moving the marbles. 1 for player 1, 2 for player 2.
    */
    public static int[] findLeadingMarble(int[][] pushing, MoveDirection direction, int[][] board, int playerTurn) {

        for(int i = 0; i<pushing.length; i++) {
            boolean equalFound = false;
            for(int j = 0; j<pushing.length; j++) {
                if(Arrays.equals(checkSquareForLocation(pushing[i],direction,board),pushing[j])){
                    equalFound = true;
                }
            }
            if(!equalFound) { return pushing[i];}
        }
        int[] problem = {-1,-1};
        return problem;
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