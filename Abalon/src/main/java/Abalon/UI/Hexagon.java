package Abalon.UI;


import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Hexagon extends Application {

    private final double WIDTH = 1000;
    private final double HEIGHT = 700;

    // Hexagon should access Board to obtain Marbles positions, colors, etc 
    // Board is a backend-only class, while Hexagon is so far the only UI class in the game (thus, we can consider renaming it)

    @Override
    public void start(Stage primaryStage) {
        //Creating an object of the class Polygon
        Polygon hexagon = new Polygon();

        //Adding coordinates to the hexagon
        hexagon.getPoints().addAll(new Double[]{
                300.0, 50.0, //1
                700.0, 50.0, //1
                850.0, 350.0, //2
                700.0, 650.0, //3
                300.0, 650.0, //3
                150.0, 350.0, //2
        });

        hexagon.setFill(Color.BISQUE);

        //Creation of the different Text of the board
        Text text = new Text("Player 1 points: ");
        text.setX(50.0);
        text.setY(50.0);

        Group root = new Group(hexagon);

        Scene scene = new Scene(root ,WIDTH, HEIGHT);
        primaryStage.setTitle("Sample Application");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
