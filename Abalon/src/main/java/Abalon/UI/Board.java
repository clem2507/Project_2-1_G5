package Abalon.UI;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class Board {

    private int[] values;
    public static ArrayList<Circle> circles;
    public static ArrayList<Text> texts;

    private final double RADIUS =30;

    public Board(){
        values = createValues();
        circles = createCircles();
    }

 /*
    Create all circles of the board + define their position
  */
    private ArrayList<Circle> createCircles() {
        ArrayList<Circle> circles = new ArrayList<Circle>();

        // 9 loops to create all circles (define position)
        // 1 loop per line of circles in the board
        double x_coord = 335;
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
        x_coord = 305;

        for(int i=0; i<6;i++){
            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.BISQUE);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;
        }
        y_coord += RADIUS*2+10;
        x_coord = 275;

        for(int i=0; i<7;i++){
            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.BISQUE);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;

        }
        y_coord += RADIUS*2+10;
        x_coord = 245;


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
        x_coord = 205;

        for(int i=0; i<9;i++){
            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.BISQUE);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;
        }
        y_coord += RADIUS*2+15;
        x_coord = 245;
        for(int i=0; i<8;i++){
            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.BISQUE);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;
        }
        y_coord += RADIUS*2+15;
        x_coord = 275;

        for(int i=0; i<7;i++){
            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.BISQUE);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;
        }
        y_coord += RADIUS*2+10;
        x_coord = 305;

        for(int i=0; i<6;i++){
            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.BISQUE);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;
        }
        y_coord += RADIUS*2+15;
        x_coord = 335;

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
        Circle c1 = createCircleForScore(25, 1100,150, Color.BISQUE);
        Circle c2 = createCircleForScore(25, 1075,200, Color.BISQUE);
        Circle c3 = createCircleForScore(25, 1125,200, Color.BISQUE);
        Circle c4 = createCircleForScore(25, 1050,250, Color.BISQUE);
        Circle c5 = createCircleForScore(25, 1100,250, Color.BISQUE);
        Circle c6 = createCircleForScore(25, 1150,250, Color.BISQUE);

        circles.add(c1);
        circles.add(c2);
        circles.add(c3);
        circles.add(c4);
        circles.add(c5);
        circles.add(c6);

        //Creation of the circles for the score FOR PLAYER 2
        Circle c11 = createCircleForScore(25, 1100,500, Color.BISQUE);
        Circle c21 = createCircleForScore(25, 1075,550, Color.BISQUE);
        Circle c31 = createCircleForScore(25, 1125,550, Color.BISQUE);
        Circle c41 = createCircleForScore(25, 1050,600, Color.BISQUE);
        Circle c51 = createCircleForScore(25, 1100,600, Color.BISQUE);
        Circle c61 = createCircleForScore(25, 1150,600, Color.BISQUE);

        circles.add(c11);
        circles.add(c21);
        circles.add(c31);
        circles.add(c41);
        circles.add(c51);
        circles.add(c61);

        //Creation of the text in the scene
        Text player1 = scoreText("Player 1", 1200 ,100, 20);
        Text player2 = scoreText("Player 2", 1200 ,700, 20);

//        texts.add(player1);
//        texts.add(player2);

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

    public Text scoreText(String text, double posX, double posY, double size){
        Text player = new Text(text);
        player.setX(posX);
        player.setY(posY);
        player.setFont(Font.font(size));
        return player;
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
