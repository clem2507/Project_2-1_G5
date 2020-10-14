package Abalon.Main;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * for each move, an object of this class has to be instanciated in the game.Run() (or whatever runs the game) after the selection of the marbles and the direction of the move, which both have to be given as parameters of the constructor together with an integer storing the number of selected marbles, the current board, and the playerTurn
 * example:
 * < Rules move = new Rules(direction, selectedMarbles,numberSelected,currentBoard,playerTurn);
 * move.move(); > are lines similar to the ones that should be after the selection of marbles
 * */
public class Rules {

    private int[][] board;
    private int[][] new_board = new int[9][9];
    private int playerTurn;
    private MoveDirection direction; //stores the target direction of the current move
    private int[][] marblesSelected; //stores the coordinates of the selected marbles
    private ArrayList<int[]> marblesToBePushed = new ArrayList<>(); // Will store the coordinates of marbles that has to be pushed
    private int nbSelected; //keeps track of how many marbles have been selected
    private boolean pushingMove; // true if the move to be performed is a pushing move

    /**
     *  The constructor with parameters:
     *  @param moveTo the direction where to move the selected marbles
     *  @param selectedMarbles the selected marbles that need to move (stored in a list of marble's coordinates)
     *  @param selectedNb the number of marbles selected (1, 2 or 3)
     *  @param currentBoard the board which defines the positions of the marbles
     */
    public Rules(Move mv){
        playerTurn = mv.turn;
        direction= mv.dir;
        marblesSelected=mv.pushing;
        nbSelected=mv.pushing.length;
        board=mv.board;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                new_board[i][j] = board[i][j];
        }
    }

    public void move(){
        if (nbSelected == 1) {
            if (checkMove(marblesSelected,direction,board,playerTurn)) { 
                performMoveOne(marblesSelected[0],direction,board); 
                replaceBoard();
            }   
        } else if (nbSelected == 2) {
            if (checkMove(marblesSelected,direction,board,playerTurn)) { 
                performMoveTwo(marblesSelected[0],marblesSelected[1],direction,board); 
                replaceBoard();
            }
        } else if (nbSelected == 3) {
            if (checkMove(marblesSelected,direction,board,playerTurn)) { 
                performMoveThree(marblesSelected[0],marblesSelected[1], marblesSelected[2],direction,board); 
                replaceBoard();
            }
        }
    }

    public void replaceBoard() {
        for (int i = 0; i < 9; i++) 
            for (int j = 0; j < 9; j++) 
                board[i][j] = new_board[i][j];
    }

    /**
     checks whether or not a suggested move is legal.
     @param pushing An ArrayList that contains the marbles. MAKE SURE THAT THE CONNECTED MARBLES ARE PLACED IN ORDER.
     IE. index 0 is connected to index 1. index 1 is connected to index 2. index 3 is NOT connected to index 0.
     @param direction A direction of the MoveDirection enum
     @param board A 2D integer array that contains the colors of every position on the hexagon.
     1 = a marble that belongs to player 1.
     2 = a marble that belongs to player 2.
     0 = a position that does not contain a marble.
     -1 = a position in the array that is out of bounds.
     @param playerTurn an integer that checks if it is the turn of player 1 or player 2.
     @returns a boolean. True if the move is legal. False if it is not.
     */

    public boolean checkMove(int[][] pushing, MoveDirection direction, int[][] board,  int playerTurn) {

        switch(pushing.length) {

            case 1: System.out.println(checkSingleMarble(pushing[0], direction, board,playerTurn)); return checkSingleMarble(pushing[0], direction, board,playerTurn);

            case 2: System.out.println(checkTwoMarbles(pushing, direction, board, playerTurn)); return checkTwoMarbles(pushing, direction, board, playerTurn);

            case 3: System.out.println(checkThreeMarbles(pushing,direction,board,playerTurn)); return checkThreeMarbles(pushing,direction,board,playerTurn); //TODO: method to be implemented

        }
        return false;
    }
    private boolean checkSingleMarble(int[] marble, MoveDirection direction, int[][] board, int playerTurn) {
        if(checkSquareForColor(marble, direction, board) == 0) { return true; }
        return false;

    }

    /*private  boolean checkSingleMarble(int[] marble, MoveDirection direction, int[][] board, int playerTurn) {
        if(checkOutOfBounds(marble, direction, board)) { return false; }
        switch(direction) {

            case TOP_LEFT:

                if(marble[0] >= 5) {
                    switch(board[marble[0]-1][marble[1]]) {
                        case 0: return true;
                        case 1: return false;
                        case 2: return false;
                    }
                }
                else {
                    switch(board[marble[0]-1][marble[1]-1]) {
                        case 0: return true;
                        case 1: return false;
                        case 2: return false;
                    }
                }
            case LEFT:
                switch(board[marble[0]][marble[1]-1]) {
                    case 0: return true;
                    case 1: return false;
                    case 2: return false;
                }

            case BOTTOM_LEFT:
                if(marble[0] >= 4) {
                    switch(board[marble[0]+1][marble[1]-1]) {
                        case 0: return true;
                        case 1: return false;
                        case 2: return false;
                    }
                }
                else {
                    switch(board[marble[0]+1][marble[1]]) {
                        case 0: return true;
                        case 1: return false;
                        case 2: return false;
                    }
                }
            case BOTTOM_RIGHT:
                if(marble[0] >= 4) {
                    switch(board[marble[0]+1][marble[1]]) {
                        case 0: return true;
                        case 1: return false;
                        case 2: return false;
                    }
                }
                else {
                    switch(board[marble[0]+1][marble[1]+1]) {
                        case 0: return true;
                        case 1: return false;
                        case 2: return false;
                    }
                }
            case RIGHT:
                switch(board[marble[0]][marble[1]+1]) {
                    case 0: return true;
                    case 1: return false;
                    case 2: return false;
                }

            case TOP_RIGHT:
                if (marble[0] >= 5) {
                    switch(board[marble[0]-1][marble[1]+1]) {
                        case 0: return true;
                        case 1: return false;
                        case 2: return false;
                    }
                }
                else {
                    switch(board[marble[0]-1][marble[1]]) {
                        case 0: return true;
                        case 1: return false;
                        case 2: return false;
                    }
                }
        }
        return false;
    }*/

    private boolean checkTwoMarbles(int[][] pushing,  MoveDirection direction, int[][] board, int playerTurn) {

        if(checkSideWays(pushing[0], pushing[1], direction, board, playerTurn)) {
            //if the marbles move sideways they cannot push anything. Check if all marbles move to a free spot.
            if(checkEmptySpots(pushing[0], direction, board)  && checkEmptySpots(pushing[1], direction, board)) {
                return true;
            }
            return false;
        }
        int[] leadingMarble = findLeadingMarble(pushing, direction, board, playerTurn);
        //Check what is in the moving direction of the leading marble
        switch (checkSquareForColor(leadingMarble,direction,board)) {
            case -1: return false;  //move not valid if out of bounds
            case 0: return true;    //move valid if empty spot
            case 1:
                if(playerTurn == 1){ return false; } //NOT valid if trying to push own marble
                else {
                    int [] pushedMarbleLocation=checkSquareForLocation(leadingMarble,direction,board);
                    System.out.println("pushedMarbleLocation: " + pushedMarbleLocation[0] + " " + pushedMarbleLocation[1]);
                    //If spot behind pushedMarble is not empty, the move is not valid as two marbles can only push 1.
                    if(checkSquareForColor(pushedMarbleLocation,direction,board) != 0 && checkSquareForColor(pushedMarbleLocation,direction,board) != -1) {
                        return false;
                    }
                    else{
                        pushingMove = true;
                        marblesToBePushed.add(pushedMarbleLocation);
                        return true;
                    }
                }

            case 2:
                if(playerTurn == 2){ return false; }
                else {
                    int [] pushedMarbleLocation=checkSquareForLocation(leadingMarble,direction,board);
                    if(checkSquareForColor(pushedMarbleLocation,direction,board) != 0 && checkSquareForColor(pushedMarbleLocation,direction,board) != -1) //If spot behind pushedMarble is not empty, the move is not valid.
                        return false;
                    else{
                        pushingMove = true; // set the move as a pushing move
                        marblesToBePushed.add(pushedMarbleLocation); //Add the location of the To-Be-Pushed marble to the arraylist storing them
                    }
                }

        }
        return false;
    }

    public boolean checkThreeMarbles(int[][] pushing,  MoveDirection direction, int[][] board, int playerTurn){
        if(checkSideWays(pushing[0], pushing[1], pushing[2], direction, board, playerTurn)) {
            //if the marbles move sideways they cannot push anything. Check if all marbles move to a free spot.
            return checkEmptySpots(pushing[0], direction, board) && checkEmptySpots(pushing[1], direction, board) && checkEmptySpots(pushing[2], direction, board);
        }
        int[] leadingMarble = findLeadingMarble(pushing, direction, board, playerTurn);
        switch (checkSquareForColor(leadingMarble,direction,board)) {
            case -1: return false;  //move not valid if out of bounds
            case 0: return true;    //move valid if empty spot
            case 1:
                if(playerTurn == 1){ return false; } //NOT valid if trying to push own marble
                else {

                    int [] pushedMarbleLocation=checkSquareForLocation(leadingMarble,direction,board);
                    if(checkSquareForColor(pushedMarbleLocation,direction,board) == 2)
                        return false;

                    else if(checkSquareForColor(pushedMarbleLocation,direction,board) == 0){
                        pushingMove=true;
                        marblesToBePushed.add(pushedMarbleLocation);
                        return true;
                    }
                    else if(checkSquareForColor(pushedMarbleLocation,direction,board) == 1){

                        int[] pushedMarble2Location = checkSquareForLocation(pushedMarbleLocation,direction,board);
                        if(checkSquareForColor(pushedMarble2Location,direction,board) == 0){
                            pushingMove = true;
                            marblesToBePushed.add(pushedMarbleLocation);
                            marblesToBePushed.add(pushedMarble2Location);
                            return true;
                        }
                        else
                            return false;
                    }
                    else
                        return false;
                }


            case 2:
                if(playerTurn == 2){ return false; } //NOT valid if trying to push own marble
                else {
                    int [] pushedMarbleLocation=checkSquareForLocation(leadingMarble,direction,board);
                    if(checkSquareForColor(pushedMarbleLocation,direction,board) == 1)
                        return false;
                    else if(checkSquareForColor(pushedMarbleLocation,direction,board) == 0){
                        pushingMove=true;
                        marblesToBePushed.add(pushedMarbleLocation);
                        return true;
                    }
                    else if(checkSquareForColor(pushedMarbleLocation,direction,board) == 2){

                        int[] pushedMarble2Location = checkSquareForLocation(pushedMarbleLocation,direction,board);
                        if(checkSquareForColor(pushedMarble2Location,direction,board) == 0){
                            pushingMove = true;
                            marblesToBePushed.add(pushedMarbleLocation);
                            marblesToBePushed.add(pushedMarble2Location);
                            return true;
                        }
                        else
                            return false;
                    }

                }

        }
        return false;
    }

    /**
     checks what is behind a certain marble to be pushed
     @param marblePushed An array storing the coordinates of the marble going to be pushed
     @param direction A direction of the MoveDirection enum, Direction in which the selected marbles are going to push
     @returns an int. 1 if there is a marble of player 1, 2 if it's player 2 marble, etc.
     */
//    public int checkSpotBehindPushedMarble(int[] marblePushed, MoveDirection direction, int[][] board, int playerTurn){
//        switch (direction){
//            case TOP_LEFT: return checkSquareForColor(marblePushed,MoveDirection.BOTTOM_RIGHT, board);
//
//            case TOP_RIGHT: return checkSquareForColor(marblePushed,MoveDirection.BOTTOM_LEFT,board);
//
//            case LEFT: return checkSquareForColor(marblePushed,MoveDirection.RIGHT,board);
//
//            case RIGHT: return checkSquareForColor(marblePushed,MoveDirection.LEFT,board);
//
//            case BOTTOM_LEFT: return checkSquareForColor(marblePushed,MoveDirection.TOP_RIGHT,board);
//
//            case BOTTOM_RIGHT: return checkSquareForColor(marblePushed,MoveDirection.TOP_LEFT,board);
//
//
//        }
//    }

//    public MoveDirection getOppositeDirection(MoveDirection) {
//        switch (direction) {
//            case TOP_LEFT:
//                return MoveDirection.BOTTOM_RIGHT;
//
//            case TOP_RIGHT:
//                return MoveDirection.BOTTOM_LEFT;
//
//            case LEFT:
//                return MoveDirection.RIGHT;
//
//            case RIGHT:
//                return ,MoveDirection.LEFT;
//
//            case BOTTOM_LEFT:
//                return MoveDirection.TOP_RIGHT;
//
//            case BOTTOM_RIGHT:
//                return MoveDirection.TOP_LEFT;
//
//        }
//    }



    /**
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
    private  boolean checkOutOfBounds(int[] marble, MoveDirection direction, int[][] board) {

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
                    if (marble[0] == 8) {
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
        return false;
    }

    /**
     returns the position in the array of colors where a move from a selected marble will end up.

     @param board A 2D integer array that contains the colors of every position on the hexagon.
     @param direction an enum that contains the direction of the suggested move.
     @param marble An integer array with length 2. The first int is the row the marble is at and the second int
     is the column the marble is at.
     @param playerTurn an integer that specifies what player is moving the marbles. 1 for player 1, 2 for player 2.

     @returns an integer array of size 2 containing the row and column the move will leave you at.
     */
    public int[] checkSquareForLocation(int[] marble, MoveDirection direction, int[][] board) {
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
                if(marble[0] >= 4) {
                    location = new int[] {marble[0]+1, marble[1]-1};
                    return location;
                }
                else {
                    location = new int[] {marble[0]+1, marble[1]};
                    return location;
                }

            case BOTTOM_RIGHT:
                if(marble[0] >= 4) {
                    location = new int[] {marble[0]+1, marble[1]};
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

    /**
     Returns the color of the square a marble has been suggested to mvoe to.

     @param board A 2D integer array that contains the colors of every position on the hexagon.
     @param direction an enum that contains the direction of the suggested move.
     @param marble An integer array with length 2. The first int is the row the marble is at and the second int
     is the column the marble is at.

     @returns an integer:
     1 = a marble that belongs to player 1.
     2 = a marble that belongs to player 2.
     0 = a position that does not contain a marble.
     -1 = a position in the array that is out of bounds.
     */
    public int checkSquareForColor(int[] marble, MoveDirection direction, int[][] board) {
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
                if(marble[0] >= 4) {
                    return board[marble[0]+1][marble[1]-1];
                }
                else {
                    return board[marble[0]+1][marble[1]];
                }

            case BOTTOM_RIGHT:
                if(marble[0] >= 4) {
                    return board[marble[0]+1][marble[1]];
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

    /**
     checks if a row of 2 or 3 marbles is moving sideways.
     @returns a boolean. True if the marbles are moving sideways. False if they are not.
      * @param marble 1 a marble in the selected row.
     * @param marble 2 a marble connected to marble 1 that is also in the selected row.
     * @param direction the direction the row is moving in.
     * @param board A 2D integer array that contains the colors of every position on the hexagon.
     * @param playerTurn
     */
    public  boolean checkSideWays(int[] marble1, int[] marble2, MoveDirection direction, int[][] board, int playerTurn) {
        int[] moveFromMarble1 = checkSquareForLocation(marble1, direction, board);
        int[] moveFromMarble2 = checkSquareForLocation(marble2, direction, board);
        if(Arrays.equals(moveFromMarble1, marble2) || Arrays.equals(moveFromMarble2, marble1)) { return false; }
        else
            return true;
    }

    public  boolean checkSideWays(int[] marble1, int[] marble2, int[] marble3, MoveDirection direction, int[][] board, int playerTurn) {
        int[] moveFromMarble1 = checkSquareForLocation(marble1, direction, board);
        int[] moveFromMarble2 = checkSquareForLocation(marble2, direction, board);
        int[] moveFromMarble3 = checkSquareForLocation(marble3,direction,board);
        if(Arrays.equals(moveFromMarble1, marble2) || Arrays.equals(moveFromMarble2, marble1) || Arrays.equals(moveFromMarble3, marble2) || Arrays.equals(moveFromMarble2, marble3) ) { return false; }
        return true;
    }

    /**
     checks if a move is permitted for a single marble.
     @param direction the direction the row is moving in.
     @param board A 2D integer array that contains the colors of every position on the hexagon.
     @param marble An integer array with length 2. The first int is the row the marble is at and the second int
     is the column the marble is at.
     @returns a boolean. True if the marble is moving to an empty spot. False if it is not.
     */
    private  boolean checkEmptySpots(int[]marble, MoveDirection direction, int[][] board) {
        //if a single marble does not move out of bounds and moves to an empty spot it is a legal move. return false in any other case
        if (checkOutOfBounds(marble, direction, board)) { return false; }
        if(checkSquareForColor(marble, direction, board) == 0) { return true; }
        return false;
    }

    /**
     searches for and returns the leading marble of the selected row.
     @param pushing a 2d array containing up to three 2 value rows with marble positions
     @param board A 2D integer array that contains the colors of every position on the hexagon.
     @param direction an enum that contains the direction of the suggested move.
     @param playerTurn an integer that specifies what player is moving the marbles. 1 for player 1, 2 for player 2.
     */
    public  int[] findLeadingMarble(int[][] pushing, MoveDirection direction, int[][] board, int playerTurn) {

        for(int i = 0; i<pushing.length; i++) {
            boolean equalFound = false;
            for(int j = 0; j<pushing.length; j++) {
                if(Arrays.equals(checkSquareForLocation(pushing[i],direction,board),pushing[j])){
                    equalFound = true;
                }
            }
            if(!equalFound) { System.out.println("leading marble: " + pushing[i][0] + " " + pushing[i][1]); return pushing[i];}
        }
        int[] problem = {-1,-1};
        return problem;
    }

    public void performMoveOne(int[] marble, MoveDirection direction, int[][] board) {
        int[] location = checkSquareForLocation(marble,direction,board);
        System.out.println(location[0] + " " + location[1]);
        new_board[marble[0]][marble[1]] = 0;
        moveMarble(marble,location, board);
    }

    public void performMoveTwo(int[] marble1, int[] marble2, MoveDirection direction, int[][] board) {
        new_board[marble1[0]][marble1[1]] = 0;
        new_board[marble2[0]][marble2[1]] = 0;
            
        if(!pushingMove) {
            System.out.println("heyo");
            int[] location1 = checkSquareForLocation(marble1,direction,board);
            moveMarble(marble1,location1,board);
            int[] location2=checkSquareForLocation(marble2,direction,board);
            moveMarble(marble2,location2,board);
        }
        else if(pushingMove) {
            int[] marblet = marblesToBePushed.get(0);
            new_board[marblet[0]][marblet[1]] = 0;
            int[] nLocation = checkSquareForLocation(marblet,direction, board);
            moveMarble(marblesToBePushed.get(0), nLocation, board);
            int[] location1 = checkSquareForLocation(marble1, direction, board);
            moveMarble(marble1, location1, board);
            int[] location2 = checkSquareForLocation(marble2, direction, board);
            moveMarble(marble2, location2, board);
        }
    }
    /** I used it because I thought I had to push/move the marbles in opposite directions but it is actually the same*/
//    /** Perform the push of the marble that has to be pushed*/
//    public void pushOneMarble(MoveDirection direction, int[][] board){
//        switch (direction){
//            case TOP_LEFT:
//                int[] nLocation = checkSquareForLocation(marblesToBePushed.get(0),MoveDirection.BOTTOM_RIGHT, board);
//                moveMarble(marblesToBePushed.get(0),nLocation,board);
//
//            case TOP_RIGHT:
//                int[] nLocation = checkSquareForLocation(marblesToBePushed.get(0),MoveDirection.BOTTOM_LEFT,board);
//                moveMarble(marblesToBePushed.get(0),nLocation,board);
//
//            case LEFT:
//                int[] nLocation = checkSquareForLocation(marblesToBePushed.get(0),MoveDirection.RIGHT,board);
//                moveMarble(marblesToBePushed.get(0),nLocation,board);
//
//            case RIGHT:
//                int[] nLocation = checkSquareForLocation(marblesToBePushed.get(0),MoveDirection.LEFT,board);
//                moveMarble(marblesToBePushed.get(0),nLocation,board);
//
//            case BOTTOM_LEFT:
//                int[] nLocation = checkSquareForLocation(marblesToBePushed.get(0),MoveDirection.TOP_RIGHT,board);
//                moveMarble(marblesToBePushed.get(0),nLocation,board);
//
//            case BOTTOM_RIGHT:
//                int[] nLocation = checkSquareForLocation(marblesToBePushed.get(0),MoveDirection.TOP_LEFT,board);
//                moveMarble(marblesToBePushed.get(0),nLocation,board);
//        }
//    }

    public void performMoveThree(int[] marble1, int[] marble2, int[] marble3, MoveDirection direction, int[][] board) {
        new_board[marble1[0]][marble1[1]] = 0;
        new_board[marble2[0]][marble2[1]] = 0;
        new_board[marble3[0]][marble3[1]] = 0;
        
        if(!pushingMove){
            int[] location1 = checkSquareForLocation(marble1,direction,board);
            moveMarble(marble1,location1,board);
            int[] location2=checkSquareForLocation(marble2,direction,board);
            moveMarble(marble2,location2,board);
            int[] location3 = checkSquareForLocation(marble3,direction,board);
            moveMarble(marble3,location3,board);
        }
        else if(pushingMove) {
            int[] marblet1 = marblesToBePushed.get(0);
            new_board[marblet1[0]][marblet1[1]] = 0;
            
            try { 
                int[] marblet2 = marblesToBePushed.get(1);
                new_board[marblet2[0]][marblet2[1]] = 0;
            } catch (Exception ignore) {

            }
            try {
                int[] nLocation2 = checkSquareForLocation(marblesToBePushed.get(1),direction, board);
                moveMarble(marblesToBePushed.get(1),nLocation2,board);
            } catch (Exception ignore) {
                
            }

            int[] nLocation = checkSquareForLocation(marblesToBePushed.get(0),direction, board);
            moveMarble(marblesToBePushed.get(0),nLocation,board);
            int[] location1 = checkSquareForLocation(marble1,direction,board);
            moveMarble(marble1,location1,board);
            int[] location2=checkSquareForLocation(marble2,direction,board);
            moveMarble(marble2,location2,board);
            int[] location3 = checkSquareForLocation(marble3,direction,board);
            moveMarble(marble3,location3,board);
        }
    }

    /** I used it because I thought I had to push/move the marbles in opposite directions but it is actually the same*/
//    public void pushTwoMarble(MoveDirection direction, int[][] board){
//        switch (direction){
//            case TOP_LEFT:
//                int[] nLocation = checkSquareForLocation(marblesToBePushed.get(0),MoveDirection.BOTTOM_RIGHT, board);
//                moveMarble(marblesToBePushed.get(0),nLocation,board);
//                int[] nLocation2 = checkSquareForLocation(marblesToBePushed.get(1),MoveDirection.BOTTOM_RIGHT, board);
//                moveMarble(marblesToBePushed.get(1),nLocation2,board);
//
//
//            case TOP_RIGHT:
//                int[] nLocation = checkSquareForLocation(marblesToBePushed.get(0),MoveDirection.BOTTOM_LEFT,board);
//                moveMarble(marblesToBePushed.get(0),nLocation,board);
//                int[] nLocation2 = checkSquareForLocation(marblesToBePushed.get(1),MoveDirection.BOTTOM_LEFT, board);
//                moveMarble(marblesToBePushed.get(1),nLocation2,board);
//
//
//            case LEFT:
//                int[] nLocation = checkSquareForLocation(marblesToBePushed.get(0),MoveDirection.RIGHT,board);
//                moveMarble(marblesToBePushed.get(0),nLocation,board);
//                int[] nLocation2 = checkSquareForLocation(marblesToBePushed.get(1),MoveDirection.RIGHT, board);
//                moveMarble(marblesToBePushed.get(1),nLocation2,board);
//
//            case RIGHT:
//                int[] nLocation = checkSquareForLocation(marblesToBePushed.get(0),MoveDirection.LEFT,board);
//                moveMarble(marblesToBePushed.get(0),nLocation,board);
//                int[] nLocation2 = checkSquareForLocation(marblesToBePushed.get(1),MoveDirection.LEFT, board);
//                moveMarble(marblesToBePushed.get(1),nLocation2,board);
//
//            case BOTTOM_LEFT:
//                int[] nLocation = checkSquareForLocation(marblesToBePushed.get(0),MoveDirection.TOP_RIGHT,board);
//                moveMarble(marblesToBePushed.get(0),nLocation,board);
//                int[] nLocation2 = checkSquareForLocation(marblesToBePushed.get(1),MoveDirection.TOP_RIGHT, board);
//                moveMarble(marblesToBePushed.get(1),nLocation2,board);
//
//            case BOTTOM_RIGHT:
//                int[] nLocation = checkSquareForLocation(marblesToBePushed.get(0),MoveDirection.TOP_LEFT,board);
//                moveMarble(marblesToBePushed.get(0),nLocation,board);
//                int[] nLocation2 = checkSquareForLocation(marblesToBePushed.get(1),MoveDirection.TOP_LEFT board);
//                moveMarble(marblesToBePushed.get(1),nLocation2,board);
//        }
//    }


    public void moveMarble(int[] marble, int[] location, int[][] board) {
        if(playerTurn == 1)
        {
            new_board[location[0]][location[1]] = 1; // Then replace value of target direction to player's number. So here 1 because it is player 1 turn
            //board[marble[0]][marble[1]] = 0; // Set the old marble position to zero because it becomes an empty zone
        }
        else if(playerTurn == 2)
        {
            new_board[location[0]][location[1]] = 2; // Then replace value of target direction to player's number. So here 2 because it is player 2 turn
            //board[marble[0]][marble[1]] = 0; // Set the old marble position to zero because it becomes an empty zone
        }
    }

}