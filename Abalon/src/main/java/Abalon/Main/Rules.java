public class Rules {
    public static boolean checkMove(List<int[][]> pushing, List<int[][]> pushed, int[][] board, MoveDirection direction) {
        switch(board.length) {
            case "1":
                checkOutOfBounds(pushing[0][]);


                if (pushing.length == 0 && ) { return true; }


        }
    }

    private static boolean checkOutOfBounds(int[] marble, MoveDirection direction, int[][] board) {
        switch(direction) {
            case TOP_LEFT:
                int row = marble[0] - 1;
                int column = marble[1] - 1;
                if (board[row][column] == -1) { return true; }
                else { return false; }

        }
    }
}