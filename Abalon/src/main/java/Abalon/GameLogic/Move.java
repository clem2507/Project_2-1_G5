package Abalon.GameLogic;
public class Move {

    //the codes of the three selected marbles and the square to move to
     Position firstMarble;
     Position secondMarble;
     Position thirdMarble;
     Position moveTo;
     boolean validMove;

    public void select(Position position, Hashtable<Position, Marble>) {
    /*TODO using a position and the hashtable, select the marble at that position if it exists.
      make sure to account for first, second and third selected marble.
      If a selection is illegal, reset selection. */

    }

    public void move(Hashtable<Position, Marble>) {
    //TODO check if the move is valid. If not, reset selection. Else execute move and end turn
    }

    public void resetSelect() {
    //TODO set MoveTo, first, second and third marble to null and start selecting again.
    }

    /*TODO you're gonna need methods for:
    1. moving one marble
    2. moving two marbles inline or broadside
    3. moving three marbles inline or broadside
    4. deleting a marble
    5. pushing one marble
    6. pushing two marbles
     */



}