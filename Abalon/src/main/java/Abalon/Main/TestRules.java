package Abalon.Main;

import java.util.ArrayList;

/**
 * This class tests the class Rules
 */
public class TestRules {

    public static void main(String[] args) {


        int[][] pushing = new int[1][2]; // It consist of a 2D array containing the coordinates of the different selected marbles (in this case we select 1 marble)
        int[] firstMarble = {2,3};
        pushing[0] = firstMarble;
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

        //Rules testMove = new Rules(direction, pushing,1,board,playerTurn); // Example of the use of the constructor



        //System.out.println("check move: " + testMove.checkMove(pushing, direction, board, playerTurn));
        //System.out.println("check location: " + testMove.checkSquareForLocation(pushing[0], direction, board)[0] + ", " + testMove.checkSquareForLocation(pushing[0], direction, board)[1]);
        //System.out.println(testMove.checkSquareForColor(pushing[0], direction, board));

        /*
        ArrayList<int[]> pushing2 = new ArrayList<int[]>();
        int[] marble1 = new int[] {3,1};
        int[] marble2 = new int[] {4,1};
        int[] marble3 = new int[] {5,0};
        pushing2.add(marble1);
        pushing2.add(marble2);
        pushing2.add(marble3); */
    }
}