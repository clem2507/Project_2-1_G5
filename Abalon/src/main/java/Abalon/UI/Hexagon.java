package Abalon.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Hexagon extends Application {

    private final double WIDTH = 1400;
    private final double HEIGHT = 750;


    @Override
    public void start(Stage primaryStage) {
        //Creating an object of Board, which construct a board
        Board board = new Board();

        //Creation of a Border pane to make our scene easier to construct
        BorderPane pane = new BorderPane();
        pane.setCenter(board.hexagon);
        pane.getChildren().addAll(Board.circles);

        Scene scene = new Scene(pane ,WIDTH, HEIGHT);
        primaryStage.setTitle("Abalone");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}
