package Abalon.Main;

import java.util.ArrayList;

/**
 * This class tests the class Rules
 */
public class TestRules {

    public static void main(String[] args) {


        ArrayList<int[]> pushing = new ArrayList<int[]>();
        int[] firstMarble = {2,3};
        pushing.add(firstMarble);
        int[][] board = new int[][] {
                {1, 1, 1, 1, 1, -1, -1, -1, -1},
                {1, 1, 1, 1, 1,  1, -1, -1, -1},
                {0, 0, 1, 1, 1,  0,  0, -1, -1},
                {0, 0, 0, 0, 0,  0,  0,  0, -1},
                {0, 0, 0, 0, 0,  0,  0,  0,  0},
                {0, 0, 0, 0, 0,  0,  0,  0, -1},
                {0, 0, 2, 2, 2,  0,  0, -1, -1},
                {2, 2, 2, 2, 2,  2, -1, -1, -1},
                {2, 2, 2, 2, 2, -1, -1, -1, -1}
        };
        MoveDirection direction = MoveDirection.TOP_RIGHT;
        System.out.println(direction);
        int playerTurn = 1;
        System.out.println("check move: " + Rules.checkMove(pushing, direction, board, playerTurn));
        System.out.println("check location: " + Rules.checkSquareForLocation(pushing.get(0), direction, board)[0] + ", " + Rules.checkSquareForLocation(pushing.get(0), direction, board)[1]);
        System.out.println(Rules.checkSquareForColor(pushing.get(0), direction, board));


        ArrayList<int[]> pushing2 = new ArrayList<int[]>();
        int[] marble1 = new int[] {3,1};
        int[] marble2 = new int[] {4,1};
        int[] marble3 = new int[] {5,0};
        pushing2.add(marble1);
        pushing2.add(marble2);
        pushing2.add(marble3);
    }


}