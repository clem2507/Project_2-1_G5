package Abalon.UI;

import java.awt.*;

/**
 * Class-list, stores values about marble only and has setters/getters to edit them
 */
public class Marble {

    private Color color;
    private int[] absPosition;
    private Notation position;

    // changed

    public Marble(Color color, Notation position, int[] absPosition) {
        this.color = color;
        this.position = position;
        this.absPosition = absPosition;
    }

    public boolean hasMoved(){
        return false;
    }

    public void setAbsPosition (int[] absPosition){
        this.absPosition = absPosition;
    }

    public void remove(){

    }

    public boolean isSelected(){
        return false;
    }

    public void select(){

    }
}
