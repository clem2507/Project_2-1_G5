package Abalon.GameLogic;
public class Position {
    char xPos;
    int yPos;

    /*
        a constructor class for the Position object
        @param xPos the x coordinate of this position
        @param yPos the y coordinate of this position
     */
    public Position(char yPos, int xPos){
        this.xPos = xPos;
        this.yPos = yPos;
    }
    /*
        @return the y coordinate of this position
     */
    public char getyPos() {
        return yPos;
    }
    /*
        @return the x coordinate of this position
     */
    public int getxPos() {
        return xPos;
    }
}
