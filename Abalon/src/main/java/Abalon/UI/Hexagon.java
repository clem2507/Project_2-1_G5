package Abalon.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * TODO Add marbles to the board and create Home page
 * Home Page: Aaron
 * Marbles: Aaron + Adèle 
 */


public class Hexagon extends Application {

    private final double WIDTH = 1400;
    private final double HEIGHT = 750;

    // Hexagon should access Board to obtain Marbles positions, color, etc 
    // Board is a backend-only class, while Hexagon is so far the only UI class in the game (thus, we can consider renaming it)

    @Override
    public void start(Stage primaryStage) {
        //Creating an object of Board, which construct a board
        BoardUI board = new BoardUI();

        //Creation of a Border pane to make our scene easier to construct
        BorderPane pane = new BorderPane();
        pane.setCenter(board.hexagon);
        pane.getChildren().addAll(board.circles);
        
        Text player1 = new Text("Player 1");
        player1.setTranslateX(110);
        player1.setTranslateY(200);
        player1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //Setting the color
        player1.setFill(Color.ORANGE);
        //Setting the Stroke
        player1.setStrokeWidth(2);
        pane.getChildren().add(player1);

        Text player2 = new Text("Player 2");
        player2.setTranslateX(1090);
        player2.setTranslateY(200);
        player2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //Setting the color
        player2.setFill(Color.ORANGE);
        //Setting the Stroke
        player2.setStrokeWidth(2);
        pane.getChildren().add(player2);

        Scene scene = new Scene(pane ,WIDTH, HEIGHT);
        primaryStage.setTitle("Abalone");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }

    public void create(Stage primaryStage){
        //Creating an object of Board, which construct a board
        BoardUI board = new BoardUI();

        //Creation of a Border pane to make our scene easier to construct
        BorderPane pane = new BorderPane();
        pane.setCenter(board.hexagon);
        pane.getChildren().addAll(board.circles);

        Text player1 = new Text("Player 1");
        player1.setTranslateX(110);
        player1.setTranslateY(200);
        player1.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //Setting the color
        player1.setFill(Color.ORANGE);
        //Setting the Stroke
        player1.setStrokeWidth(2);
        pane.getChildren().add(player1);

        Text player2 = new Text("Player 2");
        player2.setTranslateX(1090);
        player2.setTranslateY(200);
        player2.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //Setting the color
        player2.setFill(Color.ORANGE);
        //Setting the Stroke
        player2.setStrokeWidth(2);
        pane.getChildren().add(player2);

        Scene scene = new Scene(pane ,WIDTH, HEIGHT);
        primaryStage.setTitle("Abalone");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
