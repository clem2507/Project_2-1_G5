package Abalon.UI;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

import static javafx.scene.paint.Color.*;

public class BoardUI {

    private int[][] cellColor;
    public Circle[][] circles;
    public Polygon hexagon;

    private final double RADIUS =30;
    public BoardUI(){
        createCircles();
        createColors();
        drawAllCells();
        hexagon = createHexagon();
    }
    
    public static Polygon createHexagon(){
        Polygon hexagon = new Polygon();

        //Adding coordinates to the hexagon
        hexagon.getPoints().addAll(new Double[]{
                285.0, 12.0, //1
                685.0, 12.0, //1
                840.0, 350.0, //2
                685.0, 688.0, //3
                285.0, 688.0, //3
                140.0, 350.0, //2
        });
        hexagon.setFill(Color.ORANGE);

        return hexagon;
    }

    /*
        Create all circles of the board + define their position
    */
    private void createCircles() {
        circles = new Circle[9][9];
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                circles[i][j] = null;

        int nc = 5; // nc: number of circles
        double x_coord = 485;
        double y_coord = 62;

        for (int i = 0; i < 9; i++) { // 9 loops for the 9 levels of the hexagon

            for (int j = 0; j < nc; j++) 
            {
                Circle circle = new Circle(RADIUS);
                circle.setCenterX(x_coord);
                circle.setCenterY(y_coord);
                circles[i][j] = circle;
                x_coord += RADIUS * 2 + 15;
            }
            // update the number of circles per level
            if (i < 4) { // less than 9 holes at that level
                nc = nc + 1; // increase number of holes
            } else {
                nc = nc - 1; // diminish number of holes
            }

            // update y_coord
                y_coord += RADIUS * 2 + 12;

            //update x_coord
            if (i == 0 || i == 6) {
                x_coord = 455;
            } else if (i == 1 || i == 5) {
                x_coord = 415;
            } else if (i == 2 || i == 4) {
                x_coord = 380;
            } else if (i == 3) {
                x_coord = 340;
            } else { // i==7
                x_coord = 485;
            }
        }
    }

    /*
        define which circle is empty (0), red (1) or black (2)
        red is for player 1
        black is for player 2
        -1 is for "out of the board"
    */

    private void createColors() {
        cellColor = new int[][]{
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
    }

    private void drawAllCells() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                drawCell(i, j);
            }
        }
    }

    private void drawCell(int i, int j) {
        Color c = null;
        switch (cellColor[i][j]) {
            case 0:  c = BISQUE;       break;
            case 1:  c = MEDIUMBLUE;   break;
            case 2:  c = LIGHTSKYBLUE; break; 
            default: break;
        }
        if (c != null)
            circles[i][j].setFill(c);
    }
}
