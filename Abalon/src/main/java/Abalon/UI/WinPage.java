package Abalon.UI;

import Abalon.Main.Abalon;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.*;

import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class WinPage extends Application{

    Group pane = new Group();

    Abalon abalon;

    static MediaPlayer mediaPlayerWin;

    public WinPage(){

    }

    public void start(Stage primaryStage) throws Exception {

        JFrame frame = new JFrame("Abalone");
        String txt;

        if(abalon.getCurrentPlayer() == 1){
            System.out.println(Hexagon.player2 + " won the game");
            txt = Hexagon.player2.toUpperCase() + " WON!";
        }else{
            System.out.println(Hexagon.player1 + " won the game");
            txt = Hexagon.player1.toUpperCase() + " WON!";
        }

        JLabel message = new JLabel(txt);
        message.setBounds(100, 100, 1000, 1000);
        //message.setFont(new Font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 90));
        message.setFont(message.getFont ().deriveFont (37.0f));

        // TODO add timer


        // TODO add turn
        JLabel turn = new JLabel("The game took "+ Abalon.numberOfTurn +" turns.");
        message.setBounds(220, 70, 100, 50);
        message.setFont(message.getFont ().deriveFont (20.0f));

        // TODO add gif
        /*ImageView winImage = new ImageView();
        Image gif = new Image(new File("./res/giphy.gif").toURI().toString());
        winImage.setX(100);
        winImage.setY(100);
        winImage.setFitHeight(100);
        winImage.setFitWidth(100);
        frame.add(winImage);*/

        /*try {
            BufferedImage img = ImageIO.read(new File("./res/giphy.gif"));
            ImageIcon icon = new ImageIcon(img);
            JLabel jlabel = new JLabel(icon, JLabel.CENTER);
            frame.getContentPane().add(jlabel);
            frame.validate();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        // TODO add music
        winMusic();

        frame.add(turn);
        frame.add(message);
        //frame.getContentPane().add(message);
        frame.setLayout(null);
        //frame.pack();
        frame.setSize(600,600);
        frame.setLocationRelativeTo(null); //TODO center frame
        frame.setVisible(true);


    }

    /*public static void main(String[] args) {
        launch(args);
    }*/

    /**
     * Add of music in the game
     */
    public static void winMusic(){
        Hexagon.mediaPlayer.stop();
        String musicFile = "./res/win.mp3";
        Media mediaWin = new Media(Paths.get(musicFile).toUri().toString());
        mediaPlayerWin = new MediaPlayer(mediaWin);
        mediaPlayerWin.play();

        /*long time = System.currentTimeMillis();
        while ((System.currentTimeMillis() - time) < 6600) {
            // just a timer that waits the end of the win music
        }
        System.exit(0);*/
    }

    public static void exitHexagon(){

    }
}
