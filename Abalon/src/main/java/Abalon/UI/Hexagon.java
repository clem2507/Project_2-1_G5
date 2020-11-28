package Abalon.UI;

import Abalon.Main.Abalon;
import Abalon.Main.Player;
import Abalon.Main.PlayerH;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Paths;

public class Hexagon extends Application {

    private final double WIDTH = 1270;
    private final double HEIGHT = 700;

    //Creation of a Border pane to make our scene easier to construct
    public static BorderPane pane = new BorderPane();

    public static Text whosePlaying;
    public static Text winText;
    public static Scene accessableScene;
    public static Stage primaryStage;

    // Hexagon should access Board to obtain Marbles positions, color, etc
    // Board is a backend-only class, while Hexagon is so far the only UI class in the game (thus, we can consider renaming it)

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        music();

        try {
            BufferedImage buffer = ImageIO.read(new File("Abalon/res/grey2.jpg"));
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


        Text player1 = null;
        Text player2 = null;
        Text gameMode = null;

        if(HomePage.gameChoice.getValue().equals("Human vs Human")) {
            player1 = new Text("Human 1");
            player2 = new Text("Human 2");
            gameMode = new Text((String) HomePage.gameChoice.getValue());
        }
        else if(HomePage.gameChoice.getValue().equals("Alpha-Beta vs Human")){
            player1 = new Text("Alpha-Beta");
            player2 = new Text("Human");
            gameMode = new Text((String) HomePage.gameChoice.getValue());
        }
        else if(HomePage.gameChoice.getValue().equals("MCTS vs Human")){
            player1 = new Text("MCTS");
            player2 = new Text("Human");
            gameMode = new Text((String) HomePage.gameChoice.getValue());
        }
        else if(HomePage.gameChoice.getValue().equals("Alpha-Beta vs MCTS")){
            player1 = new Text("Alpha-Beta");
            player2 = new Text("MCTS");
            gameMode = new Text((String) HomePage.gameChoice.getValue());
        }

        player1.setTranslateX(100);
        player1.setTranslateY(170);
        player1.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 26));
        //Setting the color
        player1.setFill(Color.MEDIUMBLUE);
        //Setting the Stroke
        player1.setStrokeWidth(2);
        pane.getChildren().add(player1);

        player2.setTranslateX(1036);
        player2.setTranslateY(170);
        player2.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 26));
        //Setting the color
        player2.setFill(Color.LIGHTSKYBLUE);
        //Setting the Stroke
        player2.setStrokeWidth(2);
        pane.getChildren().add(player2);

        gameMode.setTranslateX(485);
        gameMode.setTranslateY(70);
        gameMode.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 30));
        //Setting the color
        gameMode.setFill(Color.BLACK);
        //Setting the Stroke
        gameMode.setStrokeWidth(2);
        pane.getChildren().add(gameMode);

        // sketch buttons to know how to play the game
        if (!HomePage.gameChoice.getValue().equals("Alpha-Beta vs MCTS")) {
            sketchHowToPlay();
        }

        //displays message to indicate whose player is playing
        whosePlaying = new Text (displayCurrentPlayer(1));
        whosePlaying.setX(485);
        whosePlaying.setY(640);
        whosePlaying.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
        whosePlaying.setFill(Color.BLACK);
        whosePlaying.setStrokeWidth(2);
        pane.getChildren().add(whosePlaying);

        winText = new Text ("");
        winText.setX(900);
        winText.setY(600);
        winText.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 25));
        winText.setFill(Color.RED);
        winText.setStrokeWidth(2);
        pane.getChildren().add(winText);

        Scene scene = new Scene(pane, WIDTH, HEIGHT);
        this.primaryStage.setResizable(false);
        this.primaryStage.setTitle("Abalone");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();
                if (key == KeyCode.ESCAPE){
                    primaryStage.close();
                }
            }
        });

        accessableScene = scene;

        Player p1 = new PlayerH();
        Player p2 = new PlayerH();


//        else if (HomePage.gameChoice.getValue().equals("Alpha-Beta vs Human")){
//            System.out.println("Choice of game: Alpha-Beta vs Human");
//            p1 = new PlayerH();
//
//
//            //player 2 a.k.a AI
//            GameTree gameTree = new GameTree();
//            AlphaBetaSearch algo = new AlphaBetaSearch(gameTree);
//            algo.start(true);
//            bestMove = algo.getBestMove();
//
//        }
//        else if (HomePage.gameChoice.getValue().equals("MCTS vs Human")){
//            System.out.println("Choice of game: MCTS vs Human");
//
//            //player 2 a.k.a AI
//            //MCTS monteCarlo = new MCTS(bestMove, currentPlayer);
//            //monteCarlo.start();
//            //bestMove = monteCarlo.getBestMove();
//
//
//        }
//        else if (HomePage.gameChoice.getValue().equals("Alpha-Beta vs MCTS")){
//            System.out.println("Choice of game: Alpha-Beta vs MCTS");
//
//            GameTree gameTree = new GameTree();
//            AlphaBetaSearch algo = new AlphaBetaSearch(gameTree);
//            algo.start(true);
//            bestMove = algo.getBestMove();
//
//        }

        Thread gameThread = new Thread(new Runnable() {

            @Override
            public void run() {
                if(HomePage.gameChoice.getValue().equals("Human vs Human")) {
                    Abalon game = new Abalon(board, p1, p2, (String) HomePage.gameChoice.getValue());
                    game.runGame();
                }
                else if(HomePage.gameChoice.getValue().equals("Alpha-Beta vs Human")){
                    Abalon game = new Abalon(board, p1, p2, (String) HomePage.gameChoice.getValue());
                    game.runGame();
                }
                else if(HomePage.gameChoice.getValue().equals("MCTS vs Human")){
                    Abalon game = new Abalon(board, p1, p2, (String) HomePage.gameChoice.getValue());
                    game.runGame();
                }
                else if(HomePage.gameChoice.getValue().equals("Alpha-Beta vs MCTS")){
                    Abalon game = new Abalon(board, p1, p2, (String) HomePage.gameChoice.getValue());
                    game.runGame();
                }
            }
        });
        gameThread.setDaemon(false);
        gameThread.start();
    }

    private void sketchHowToPlay() {

        int y = 40; // interval

        for(int i = 0; i < 6; i++){
            Rectangle rect = new Rectangle(30,30,Color.GREY);
            rect.setArcHeight(8);
            rect.setArcWidth(8);
            rect.relocate(70,400+(i*y));

            Text letter = null;
            Text direction = null;
            if(i==0){ // Q key
                letter = new Text("Q");
                direction = new Text("TOP_LEFT");
            }else if(i==1){ // A key
                letter = new Text("A");
                direction = new Text("LEFT");
            }else if(i==2){ // D key
                letter = new Text("D");
                direction = new Text("RIGHT");
            }else if(i==3){ // Z Key
                letter = new Text("Z");
                direction = new Text("BOTTOM_LEFT");
            }else if(i==4){ // C key
                letter = new Text("C");
                direction = new Text("BOTTOM_RIGHT");
            }else if(i==5){ // E Key
                letter = new Text("E");
                direction = new Text("TOP_RIGHT");
            }

            letter.setX(78);
            letter.setY(420+(i*y));
            letter.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
            letter.setFill(Color.BLACK);
            letter.setStrokeWidth(2);

            direction.setX(120);
            direction.setY(420+(i*y));
            direction.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
            direction.setFill(Color.BLACK);
            direction.setStrokeWidth(2);

            pane.getChildren().addAll(rect, letter, direction);
        }

        // press ENTER
        Text enter = new Text ("Press Enter to validate move.");
        enter.setX(80);
        enter.setY(420+(6*y));
        enter.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
        enter.setFill(Color.BLACK);
        enter.setStrokeWidth(2);
        pane.getChildren().add(enter);
    }

    public static String displayCurrentPlayer(int player) {
        String currentPlayer = null;

        if(player == 1){
            if(HomePage.gameChoice.getValue().equals("Human vs Human")) {
                currentPlayer = "Human 1";
            }
            else if(HomePage.gameChoice.getValue().equals("Alpha-Beta vs Human")){
                currentPlayer = "Human";
            }
            else if(HomePage.gameChoice.getValue().equals("MCTS vs Human")){
                currentPlayer = "Human";
            }
            else if(HomePage.gameChoice.getValue().equals("Alpha-Beta vs MCTS")){
                currentPlayer = "Alpha-Beta";
            }
        }else if(player == 2){
            if(HomePage.gameChoice.getValue().equals("Human vs Human")) {
                currentPlayer = "Human 2";
            }
            else if(HomePage.gameChoice.getValue().equals("Alpha-Beta vs Human")){
                currentPlayer = "Alpha-Beta";
            }
            else if(HomePage.gameChoice.getValue().equals("MCTS vs Human")){
                currentPlayer = "MCTS";
            }
            else if(HomePage.gameChoice.getValue().equals("Alpha-Beta vs MCTS")){
                currentPlayer = "MCTS";
            }
        }

//        whosePlaying = new Text ("It is "+ currentPlayer +"'s turn to play.");
//        whosePlaying.setX(485);
//        whosePlaying.setY(640);
//        whosePlaying.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
//        whosePlaying.setFill(Color.BLACK);
//        whosePlaying.setStrokeWidth(2);

        return currentPlayer;
    }

    MediaPlayer mediaPlayer;
    public void music(){
        //Add of music in the game
        String musicFile = "Abalon/res/wii.mp3";
        Media media = new Media(Paths.get(musicFile).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();

       /* Media mp3MusicFile= new Media(new File(musicFile).toURI().toString());
        MediaPlayer player = new MediaPlayer(mp3MusicFile);
        player.play();

        */

    }
}