package Abalon.UI;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Hexagon extends Application {

    private final double WIDTH = 1400;
    private final double HEIGHT = 750;


    @Override
    public void start(Stage primaryStage) {
        //Creating an object of the class Polygon
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
        Group root = new Group(hexagon);
        Board board = new Board();
        root.getChildren().addAll(Board.circles);
        //root.getChildren().addAll(Board.scoreCircles);

        Scene scene = new Scene(root ,WIDTH, HEIGHT);
        primaryStage.setTitle("Sample Application");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
