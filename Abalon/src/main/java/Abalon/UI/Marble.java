package Abalon.UI;

import java.awt.*;

/**
 * Class-list, stores values about marble only and has setters/getters to edit them
 */
public class Marble {

    private Color color;
    private int absPositionX, absPositionY;
    private Notation notation;
    private boolean isSelected;
    private int outside = 0;

    // changed array to 2 variables, for the following reason:
    // java is passed-by value, the value that is passed for objects are references to their positions
    // in java static arrays are objects as well
    // so if we store coordinates in array there's more risk to accidentially edit it it from another place 
    // if we store it in primitive types we wont risk 
    public Marble(Color color, Notation notation, int absPositionX, int absPositionY) {
        this.color = color;
        this.position = position;
        this.absPositionX = absPositionX;
        this.absPositionY = absPositionY;
        this.isSelected = false;
    }

    public Marble(Marble other) {
        this.color = other.getColor();
        this.notation = other.getNotation();
        this.absPositionX = other.getX();
        this.absPositionY = other.getY();
        this.isSelected = other.isSelected();
    }

    public void setAbsPosition (int absPositionX, int absPositionY) {
        this.absPositionX = absPositionX;
        this.absPositionY = absPositionY;
    }

    public void remove(){

    }

    public boolean isSelected(){
        return false;
    }

    public void select(){

    }
}
