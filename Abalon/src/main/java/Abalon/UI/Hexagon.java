package Abalon.UI;

import Abalon.Main.Abalon;
import Abalon.Main.Player;
import Abalon.Main.PlayerH;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
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

    private BoardUI board;
    public static Text whosePlaying;
    public static Text winText;
    public static Text turnText;
    public static Scene accessableScene;
    public static Stage primaryStage;

    public static String player1 = null;
    public static String player2 = null;

    public static ImageView winImage;
    public static Image gif;

    // Hexagon should access Board to obtain Marbles positions, color, etc
    // Board is a backend-only class, while Hexagon is so far the only UI class in the game (thus, we can consider renaming it)

    public BoardUI getBoardUI() {
        return board;
    }  

    @Override
    public void start(Stage primaryStage) {

        this.primaryStage = primaryStage;

        music();

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
        board = new BoardUI();
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

        Text gameMode = null;

        if(HomePage.gameChoice.getValue().equals("Human vs Human")) {
            if(HomePage.field1.getText().length() != 0){
                player1 = HomePage.field1.getText();
            }else{
                player1 = "Human1"; // default name
            }

            if(HomePage.field2.getText().length() != 0){
                player2 = HomePage.field2.getText();
            }else{
                player2 = "Human2"; // default name
            }
            gameMode = new Text((String) HomePage.gameChoice.getValue());
        }
        else if(HomePage.gameChoice.getValue().equals("Alpha-Beta vs Human")){
            if(HomePage.field1.getText().length() != 0){
                player1 = HomePage.field1.getText();
            }else{
                player1 = "Human"; // default name
            }
            player2 =  HomePage.field2.getText();
            gameMode = new Text((String) HomePage.gameChoice.getValue());
        }
        else if(HomePage.gameChoice.getValue().equals("MCTS vs Human")){
            if(HomePage.field1.getText().length() != 0){
                player1 = HomePage.field1.getText();
            }else{
                player1 = "Human"; // default name
            }
            player2 = HomePage.field2.getText();
            gameMode = new Text((String) HomePage.gameChoice.getValue());
        }
        else if(HomePage.gameChoice.getValue().equals("Rule-Based vs Human")){
            if(HomePage.field1.getText().length() != 0){
                player1 = HomePage.field1.getText();
            }else{
                player1 = "Human"; // default name
            }
            player2 = HomePage.field2.getText();
            gameMode = new Text((String) HomePage.gameChoice.getValue());
        }
        else if(HomePage.gameChoice.getValue().equals("Alpha-Beta vs MCTS")){
            player1 = HomePage.field1.getText();
            player2 = HomePage.field2.getText();
            gameMode = new Text((String) HomePage.gameChoice.getValue());
        }

        Text pl1 = new Text(100, 170, player1);
        pl1.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 26));
        pl1.setFill(Color.LIGHTSKYBLUE);
        pl1.setStrokeWidth(2);
        pane.getChildren().add(pl1);

        Text pl2 = new Text(1036, 170, player2);
        pl2.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 26));
        pl2.setFill(Color.MEDIUMBLUE);
        pl2.setStrokeWidth(2);
        pane.getChildren().add(pl2);

        gameMode.setTranslateX(485);
        gameMode.setTranslateY(70);
        gameMode.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 30));
        //Setting the color
        gameMode.setFill(Color.BLACK);
        //Setting the Stroke
        gameMode.setStrokeWidth(2);
        pane.getChildren().add(gameMode);

        //Add of the button to pause and resume the music

        Text music = new Text("Music: ");
        music.setTranslateX(100);
        music.setTranslateY(100);
        music.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 26));
        music.setFill(Color.BLACK);
        pane.getChildren().add(music);

        Pane musicPane = new Pane();

        Button pauseButton = new Button();
        pauseButton.setText("Pause");
        pauseButton.setTextFill(Color.BLACK);
        pauseButton.setMaxWidth(300);
        pauseButton.setTranslateX(200);
        pauseButton.setTranslateY(75);
        pauseButton.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 20));

        pauseButton.setOnAction(
                event -> {
                    if(pauseButton.getText().equals("Pause")){
                        mediaPlayer.pause();
                        pauseButton.setText("Play");
                    }
                   else{
                       mediaPlayer.play();
                       pauseButton.setText("Pause");
                    }
                });

        musicPane.getChildren().add(pauseButton);

        pane.getChildren().add(musicPane);

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

        turnText = new Text ("Turn number 0");
        turnText.setX(485);
        turnText.setY(670);
        turnText.setFont(Font.font("Arial", FontWeight.BOLD, FontPosture.REGULAR, 20));
        turnText.setFill(Color.BLACK);
        turnText.setStrokeWidth(2);
        pane.getChildren().add(turnText);

        winText = new Text ("");
        winText.setX(900);
        winText.setY(600);
        winText.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 30));
        winText.setFill(Color.DARKORANGE);
        winText.setStrokeWidth(2);
        pane.getChildren().add(winText);

        winImage = new ImageView();
        gif = new Image(new File(".Abalon/res/giphy.gif").toURI().toString());
        //selectedImage.setImage(gif);
        winImage.setX(1000);
        winImage.setY(600);
        winImage.setFitHeight(100);
        winImage.setFitWidth(100);
        pane.getChildren().addAll(winImage);


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
                else if(HomePage.gameChoice.getValue().equals("Rule-Based vs Human")){
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
                direction = new Text("TOP_LEFT");}
            else if(i==1){ // E Key
                    letter = new Text("E");
                    direction = new Text("TOP_RIGHT");
            }else if(i==2){ // A key
                letter = new Text("A");
                direction = new Text("LEFT");
            }else if(i==3){ // D key
                letter = new Text("D");
                direction = new Text("RIGHT");
            }else if(i==4){ // Z Key
                letter = new Text("Z");
                direction = new Text("BOTTOM_LEFT");
            }else if(i==5){ // C key
                letter = new Text("C");
                direction = new Text("BOTTOM_RIGHT");
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
            currentPlayer = player1;
        }else if(player == 2){
            currentPlayer = player2;
        }

        return currentPlayer;
    }

    static MediaPlayer mediaPlayer;
    public static void music(){
        //Add of music in the game
        String musicFile = "./res/wii.mp3";
        Media media = new Media(Paths.get(musicFile).toUri().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.play();
    }


    static MediaPlayer mediaPlayerWin;

    public static void winMusic(){
        mediaPlayer.stop();
        //Add of music in the game
        String musicFile = "./res/win.mp3";
        Media mediaWin = new Media(Paths.get(musicFile).toUri().toString());
        mediaPlayerWin = new MediaPlayer(mediaWin);
        mediaPlayerWin.play();
        long time = System.currentTimeMillis();
        while ((System.currentTimeMillis() - time) < 6600) {
            // just a timer that waits the end of the win music
        }
        System.exit(0);
    }
}

