public class Rules {
    public static boolean checkMove(List<int[][]> pushing, List<int[][]> pushed, int[][] board, MoveDirection direction) {
        switch(board.length) {
            case "1":
                checkOutOfBounds(pushing[0][]);


                if (pushing.length == 0 && ) { return true; }


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
    }
}