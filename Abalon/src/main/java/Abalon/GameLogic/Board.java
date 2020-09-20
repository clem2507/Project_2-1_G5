package Abalon.GameLogic;
import java.util.HashTable;
public class Board {
    private Hashtable<Position, Marble> marblePositions;

    /*
        a constructor for the Board class.
        creates a hashtable and fills it up with marble objects that are stored with their positions as keys.
     */
    public Board() {
        marblePositions = new Hashtable<Position, Marble>();
        setStandardBoard(marblePositions);
    }
    /*
        initializes 14 marbles in a traditional position on each side and stores them in a Hashtable.
     */
    public void setStandardBoard(Hashtable<Position, Marble> marblePositions) {

        //create and store marbles for player 2
        for (int i = 1; i < 6; i++) {
            Position pos = new Position('A', i);
            Marble marble = new Marble(2, pos);
            marblePositions.put(pos, marble);
        }
        for (int i = 1; i < 7; i++) {
            Position pos = new Position('B', i);
            Marble marble = new Marble(2, pos);
            marblePositions.put(pos, marble);
        }
        for (int i = 3; i < 6; i++) {
            Position pos = new Position('C', i);
            Marble marble = new Marble(2, pos);
            marblePositions.put(pos, marble);
        }
        //create and store marbles for player 1
        for (int i = 1; i < 6; i++) {
            Position pos = new Position('I', i);
            Marble marble = new Marble(1, pos);
            marblePositions.put(pos, marble);
        }
        for (int i = 1; i < 7; i++) {
            Position pos = new Position('H', i);
            Marble marble = new Marble(1, pos);
            marblePositions.put(pos, marble);
        }
        for (int i = 3; i < 6; i++) {
            Position pos = new Position('G', i);
            Marble marble = new Marble(1, pos);
            marblePositions.put(pos, marble);
        }
    }
    public Marble marbleAtPosition(Position pos) {
        return marblePositions.get(pos);
    }
}