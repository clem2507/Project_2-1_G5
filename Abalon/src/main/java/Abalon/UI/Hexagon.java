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

import java.io.File; 
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import Abalon.Main.Abalon;
import Abalon.Main.Player;
import Abalon.Main.PlayerH;

/**
 * TODO Add marbles to the board and create Home page
 * Home Page: Aaron
 * Marbles: Aaron + Adèle 
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

        try {
            BufferedImage buffer = ImageIO.read(new File("./res/grey2.jpg"));
            Image background = SwingFXUtils.toFXImage(buffer, null);
            ImageView view = new ImageView(background);
            pane.getChildren().addAll(view);
        } catch (Exception e) {
            System.out.println("file not found");
            System.exit(0);
        }

        //Creating an object of Board, which construct a board
        BoardUI board = new BoardUI();
        pane.setCenter(board.hexagon);
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++)
                if (board.circles[i][j] != null)
                    pane.getChildren().add(board.circles[i][j]);
        }

        for (int i = 0; i < 6; i++){
            pane.getChildren().add(board.scoredCircles[i][0]);
            pane.getChildren().add(board.scoredCircles[i][1]);
        }


        Text player1 = new Text("Player 1");
        player1.setTranslateX(100);
        player1.setTranslateY(170);
        player1.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 26));
        //Setting the color
        player1.setFill(Color.BLACK);
        //Setting the Stroke
        player1.setStrokeWidth(2);
        pane.getChildren().add(player1);

        Text player2 = new Text("Player 2");
        player2.setTranslateX(1036);
        player2.setTranslateY(170);
        player2.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 26));
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
    
        Player p1 = new PlayerH();
        Player p2 = new PlayerH();

        Thread gameThread = new Thread(new Runnable() {

            @Override
            public void run() {
                Abalon game = new Abalon(board, p1, p2);
            game.runGame();
            }
        });
        gameThread.setDaemon(false);
        gameThread.start();
    }
}
