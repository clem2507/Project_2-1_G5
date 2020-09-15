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

public class Board {

    private int[] values;
    public static ArrayList<Circle> circles;
    public Polygon hexagon;
    public Text player1;
    public Text player2;

    private final double RADIUS =30;

    public Board(){
        values = createValues();
        circles = createCircles();
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

        // 9 loops to create all circles (define position)
        // 1 loop per line of circles in the board
        double x_coord = 485;
        double y_coord = 62;
        int index=5;

        for(int i=0; i<5;i++){
            Circle circle = new Circle(RADIUS);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circle.setFill(Color.BISQUE);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;
        }
        y_coord += RADIUS*2+10;
        x_coord = 455;

        for(int i=0; i<6;i++){
            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.BISQUE);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;
        }
        y_coord += RADIUS*2+10;
        x_coord = 415;

        for(int i=0; i<7;i++){
            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.BISQUE);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;

        }
        y_coord += RADIUS*2+10;
        x_coord = 380;


        for(int i=0; i<8;i++){
            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.BISQUE);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;
            index++;
        }
        y_coord += RADIUS*2+10;
        x_coord = 340;

        for(int i=0; i<9;i++){
            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.BISQUE);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;
        }
        y_coord += RADIUS*2+15;
        x_coord = 380;
        for(int i=0; i<8;i++){
            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.BISQUE);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;
        }
        y_coord += RADIUS*2+15;
        x_coord = 415;

        for(int i=0; i<7;i++){
            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.BISQUE);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;
        }
        y_coord += RADIUS*2+10;
        x_coord = 455;

        for(int i=0; i<6;i++){
            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.BISQUE);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;
        }
        y_coord += RADIUS*2+15;
        x_coord = 485;

        for(int i=0; i<5;i++){
            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.BISQUE);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;
        }
        y_coord += RADIUS*2+10;

        //Creation of the circles for the score FOR PLAYER 1
        Circle c1 = createCircleForScore(25, 150,250, Color.BISQUE);
        Circle c2 = createCircleForScore(25, 125,300, Color.BISQUE);
        Circle c3 = createCircleForScore(25, 175,300, Color.BISQUE);
        Circle c4 = createCircleForScore(25, 100,350, Color.BISQUE);
        Circle c5 = createCircleForScore(25, 150,350, Color.BISQUE);
        Circle c6 = createCircleForScore(25, 200,350, Color.BISQUE);

        circles.add(c1);
        circles.add(c2);
        circles.add(c3);
        circles.add(c4);
        circles.add(c5);
        circles.add(c6);

        //Creation of the circles for the score FOR PLAYER 2
        Circle c11 = createCircleForScore(25, 1130,250, Color.BISQUE);
        Circle c21 = createCircleForScore(25, 1105,300, Color.BISQUE);
        Circle c31 = createCircleForScore(25, 1155,300, Color.BISQUE);
        Circle c41 = createCircleForScore(25, 1080,350, Color.BISQUE);
        Circle c51 = createCircleForScore(25, 1130,350, Color.BISQUE);
        Circle c61 = createCircleForScore(25, 1180,350, Color.BISQUE);

        circles.add(c11);
        circles.add(c21);
        circles.add(c31);
        circles.add(c41);
        circles.add(c51);
        circles.add(c61);

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

}
