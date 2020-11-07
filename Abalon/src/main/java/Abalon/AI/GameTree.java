package Abalon.AI;

import java.util.List;

public class GameTree {
    /**
     * Creates the game tree data structure
     * each node represents a state
     * then the algorithms will take care of going through the tree and pick a move
     */

    private List<Edge> edges;
    private List<Node> nodes;

    public GameTree(){

        int[][] rootBoardState = new int[][]{

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

        //create the tree, start with the initial board
    }
}
