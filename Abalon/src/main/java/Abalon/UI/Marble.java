package Abalon.UI;

import java.awt.*;

/**
 * Class-list, stores values about marble only and has setters/getters to edit them
 */
public class Marble {
    private Color color; // sure to use this color type?
    private Notation notation; 
    private boolean selected; 
    private int outsideFlag = 0; // specifies whether the ball is on the board, or to the left/to the right (in one of triangles); 

    // NOW IM GONNA MOVE ABSOLUTE POSITION TO NOTATION

    public Marble(Color color, Notation notation) {
        this.color = color;
        this.position = position;
        this.absPositionX = absPositionX;
        this.absPositionY = absPositionY;
        this.isSelected = false;
        this.outsideFlag = false;
    }

    /** 
     * Copy constructor
     * @param other other Marble object to be copied
     */
    public Marble(Marble other) {
        this.color = other.getColor();
        this.notation = other.getNotation();
        this.absPositionX = other.getX();
        this.absPositionY = other.getY();
        this.isSelected = other.isSelected();
        this.outsideFlag = other.getOutsideFlag();
    }

    /** 
     * Sets the color
     * @param color color to be set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /** 
     * Returns the color
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /** 
     * Sets position of the marble according to notation
     * @param notation notation
     */
    public void setNotation(Notation notation) {
        this.notation = notation;
    }

    /** 
     * Returns position of the marble according to notation
     * @return position
     */
    public Notation getNotation() {
        return notation;
    }

    /** 
     * Sets the marble to be selected by the player for the current move if boolean flag is true
     * @param selected boolean flag
     */
    public void setSelect(boolean selected){
        this.selected = selected;
    }

    /** 
     * Returns the state of the marble: selected or not for the current move
     * @return true if selected, false otherwise
     */
    public boolean isSelected(){
        return selected;
    }

    /** 
     * Sets the integer flag describing whether the marble is on the board or outside in one of triangles
     * @param outsideFlag integer flag
     */
    public void moveOutside(int outsideFlag){
        this.outsideFlag = outsideFlag;
    }

    /** 
     * Returns the state of the marble: outside of the board or in one side of triangles
     * @return integer flag
     */
    public int getOutside() {
        return outsideFlag;
    }
}
