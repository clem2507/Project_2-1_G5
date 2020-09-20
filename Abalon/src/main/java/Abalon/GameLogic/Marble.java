package Abalon.GameLogic;
public class Marble {
    Position position;
    int player;

    /*
    Constructor class for the marble object
    @param player an integer that contains either 1 or 2 depending on which player it belongs to
    @param Position an object that contains the x and the y coordinate of where the marble is at
     */
    public Marble(int player, Position position){
        this.player = player;
        this.position = position;
    }
    /*
        @return the player
     */
    public int getPlayer(){
        return player;
    }
}