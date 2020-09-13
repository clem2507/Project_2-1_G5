package Abalon.UI;

import java.awt.*;

public class Marble {

    private Color color;
    private int[] position;

    public Marble(Color color, int[] position) {
        this.color = color;
        this.position = position;

    }

    public boolean hasMoved(){
        return false;
    }

    public void editPosition (int[] newPosition){
        this.position = newPosition;
    }

    public void remove(){

    }

    public boolean isSelected(){
        return false;
    }

    public void select(){

    }
}
