package Abalon.GameLogic;
public class Position {
    int xPos;
    char yPos;

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

    //Overriding equals() method to compare two Position objects
    @Override
    public boolean equals(Object o) {
        //System.out.println("equals called");
        if (o == this) {
            //System.out.println("just equal");
            return true;
        }
        if (!(o instanceof Position)) {
            //System.out.println("not an instance of");
            return false;
        }

        Position pos = (Position) o;

        if(pos.getxPos()==this.xPos && pos.getyPos()==this.yPos) {
            System.out.println("equal by value");
            return true;
        }
        else {
            System.out.println("not equal by value");
            return false;
        }
    }
}
