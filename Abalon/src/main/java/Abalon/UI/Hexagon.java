package Abalon.UI;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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

/*
==================================================================================================================
                                                   TODO
==================================================================================================================

    1) I already created separate Board in backend. Its gonna be pretty messy if we keep things in one place, 
    so I suggest splitting it
    2) Use Notation. It will store absolute positions of marbles. You can set coordinates manually 
    if the marble is outside of the board or just pass notation and Notation will calculate absolute
    coordinates authomatically.   
    3) BoardUI should be responsible for UI only. I'll move all backend-related stuff from there myself soon.
    4) Marbles is just a list-like class. It contains only data about marbles and getters/setters for them, 
    I dont think wee need anything else to be done there
==================================================================================================================
*/

public class Hexagon extends Application {

    private final double WIDTH = 1270;
    private final double HEIGHT = 700;

    // Hexagon should access Board to obtain Marbles positions, color, etc 
    // Board is a backend-only class, while Hexagon is so far the only UI class in the game (thus, we can consider renaming it)

    @Override
    public void start(Stage primaryStage) {

        //Creation of a Border pane to make our scene easier to construct
        BorderPane pane = new BorderPane();

        Image background = new Image("Abalon/game-background.jpg");
        ImageView view = new ImageView(background);
        pane.getChildren().addAll(view);

        //Creating an object of Board, which construct a board
        BoardUI board = new BoardUI();
        pane.setCenter(board.hexagon);
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                if (board.circles[i][j] != null)
                    pane.getChildren().add(board.circles[i][j]);
        }

        for (int i = 0; i < 6; i++)
            pane.getChildren().add(board.scoredCircles[i][0]);

        Text player1 = new Text("Player 1");
        player1.setTranslateX(110);
        player1.setTranslateY(170);
        player1.setFont(Font.font("Monospaced", FontWeight.BOLD, FontPosture.REGULAR, 26));
        //Setting the color
        player1.setFill(Color.BLACK);
        //Setting the Stroke
        player1.setStrokeWidth(2);
        pane.getChildren().add(player1);

        Text player2 = new Text("Player 2");
        player2.setTranslateX(1070);
        player2.setTranslateY(170);
        player2.setFont(Font.font("Monospaced", FontWeight.BOLD, FontPosture.REGULAR, 26));
        //Setting the color
        player2.setFill(Color.BLACK);
        //Setting the Stroke
        player2.setStrokeWidth(2);
        pane.getChildren().add(player2);

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Abalone");
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
