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

    private int[] values;
    public static ArrayList<Circle> circles;
    public static ArrayList<Circle> circlesPlayer1;
    public static ArrayList<Circle> circlesPlayer2;
    public Polygon hexagon;
    public Text player1;
    public Text player2;

    private final double RADIUS =30;

    public BoardUI(){
        values = createValues();
        circles = createCircles();
        updateColorCircles();
        hexagon = createHexagon();
        player1 = new Text("test");
    }

    public static Polygon createHexagon(){
        Polygon hexagon = new Polygon();

        //Adding coordinates to the hexagon
        hexagon.getPoints().addAll(new Double[]{
                300.0, 12.0, //1
                700.0, 12.0, //1
                850.0, 350.0, //2
                700.0, 688.0, //3
                300.0, 688.0, //3
                150.0, 350.0, //2
        });
        hexagon.setFill(Color.ORANGE);

        return hexagon;
    }

 /*
    Create all circles of the board + define their position
  */
    private ArrayList<Circle> createCircles() {
        ArrayList<Circle> circles = new ArrayList<Circle>();

        int nc = 5; // nc: number of circles
        double x_coord = 485;
        double y_coord = 62;

        for (int i=0; i<9; i++){ // 9 loops for the 9 levels of the hexagon

            for(int j=0; j<nc; j++){
                Circle circle = new Circle(RADIUS);
                circle.setCenterX(x_coord);
                circle.setCenterY(y_coord);
                circles.add(circle);
                x_coord += RADIUS*2 + 15;
            }
            // update the number of circles per level
            if(i<4){ // less than 9 holes at that level
                nc = nc+1; // increase number of holes
            }else{
                nc = nc-1; // diminish number of holes
            }

            // update y_coord
            if(i==0 | i==1 || i==2 || i==3 || i==6){
                y_coord += RADIUS*2+10;
            }else{
                y_coord += RADIUS*2+15;
            }

            //update x_coord
            if(i==0 || i==6){
                x_coord = 455;
            }else if(i==1 || i==5){
                x_coord = 415;
            }else if(i==2 || i==4){
                x_coord = 380;
            }else if(i==3){
                x_coord = 340;
            }else{ // i==7
                x_coord = 485;
            }
        }

        /*
        //Creation of the circles for the score FOR PLAYER 1
        Circle c1 = createCircleForScore(25, 150,250, Color.BISQUE);
        Circle c2 = createCircleForScore(25, 125,300, Color.BISQUE);
        Circle c3 = createCircleForScore(25, 175,300, Color.BISQUE);
        Circle c4 = createCircleForScore(25, 100,350, Color.BISQUE);
        Circle c5 = createCircleForScore(25, 150,350, Color.BISQUE);
        Circle c6 = createCircleForScore(25, 200,350, Color.BISQUE);

        circlesPlayer1.add(c1);
        circlesPlayer1.add(c2);
        circlesPlayer1.add(c3);
        circlesPlayer1.add(c4);
        circlesPlayer1.add(c5);
        circlesPlayer1.add(c6);

        //Creation of the circles for the score FOR PLAYER 2
        Circle c11 = createCircleForScore(25, 1130,250, Color.BISQUE);
        Circle c21 = createCircleForScore(25, 1105,300, Color.BISQUE);
        Circle c31 = createCircleForScore(25, 1155,300, Color.BISQUE);
        Circle c41 = createCircleForScore(25, 1080,350, Color.BISQUE);
        Circle c51 = createCircleForScore(25, 1130,350, Color.BISQUE);
        Circle c61 = createCircleForScore(25, 1180,350, Color.BISQUE);

        circlesPlayer2.add(c11);
        circlesPlayer2.add(c21);
        circlesPlayer2.add(c31);
        circlesPlayer2.add(c41);
        circlesPlayer2.add(c51);
        circlesPlayer2.add(c61);

         */

        return circles;
    }

    //Method to create a circle
    public Circle createCircleForScore(double radius, double posX, double posY, Color color){
        Circle circle = new Circle(radius);
        circle.setFill(color);
        circle.setCenterX(posX);
        circle.setCenterY(posY);

        return circle;
    }

 /*
    define which circle is empty (0), red (1) or black (2)
    red is for player 1
    black is for player 2
  */
    private int[] createValues() {
        int[] values = {
                1,1,1,1,1,
                1,1,1,1,1,1,
                0,0,1,1,1,0,0,
                0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,0,
                0,0,0,0,0,0,0,0,
                0,0,2,2,2,0,0,
                2,2,2,2,2,2,
                2,2,2,2,2
        };

        return values;
    }

    private void updateColorCircles() {
        Color c = null;
        // define color
        for(int i=0;i<values.length;i++){
            if(values[i] ==0){
                c = BISQUE;
            }else if(values[i] ==1){
                c = RED;
            }else if(values[i] ==2){
                c = BLACK;
            }

            // fill circle with the correct color
            circles.get(i).setFill(c);
        }
    }

}
