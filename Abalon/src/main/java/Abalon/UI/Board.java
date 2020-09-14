package Abalon.UI;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.ArrayList;

public class Board {

    private int[] values;
    public static ArrayList<Circle> circles;
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
        double x_coord = 335;
        double y_coord = 62;
        int index=0;

        for(int i=0; i<5;i++){
            Circle circle = new Circle(RADIUS);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circle.setFill(Color.BISQUE);
            circles.add(circle);

            x_coord += RADIUS*2 + 15;
            index++;
        }
        System.out.println(x_coord);
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
        y_coord += RADIUS*2+10;
        x_coord = 245;
        for(int i=0; i<8;i++){
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
        x_coord = 335;
        for(int i=0; i<5;i++){
            Circle circle = new Circle(RADIUS);
            circle.setFill(Color.BISQUE);
            circle.setCenterX(x_coord);
            circle.setCenterY(y_coord);
            circles.add(circle);

            x_coord += RADIUS * 2 + 15;
        }
        y_coord += RADIUS*2+10;


        return circles;
    }

 /*
    define which circle is empty (0), red (1) or blue (2)
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
