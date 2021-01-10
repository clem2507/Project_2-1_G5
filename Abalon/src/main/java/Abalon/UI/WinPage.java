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

    Abalon abalon;

    static MediaPlayer mediaPlayerWin;
    public static final Color VERYLIGHTBLUE = new Color(51,204,255);

    public WinPage(){

    }

    public void start(Stage primaryStage) throws Exception {

        JFrame frame = new JFrame("Abalone");
        java.awt.Font f1 = new java.awt.Font("Zorque", 10, 40);
        java.awt.Font f2 = new java.awt.Font("Zorque", 10, 20);

        String txt;
        Color c;
        if(abalon.getCurrentPlayer() == 1){
            System.out.println(Hexagon.player2 + " won the game");
            txt = Hexagon.player2.toUpperCase() + " WON!";
            c = Color.BLUE;
        }else{
            System.out.println(Hexagon.player1 + " won the game");
            txt = Hexagon.player1.toUpperCase() + " WON!";
            c = VERYLIGHTBLUE;
        }

        JLabel message = new JLabel(txt, SwingConstants.CENTER);
        message.setBounds(100, 100, 400, 50);
        message.setFont(f1);
        message.setForeground(c);

        // TODO add timer


        JLabel turn = new JLabel("The game took "+ Abalon.numberOfTurn +" turns.", SwingConstants.CENTER);
        turn.setFont(f2);
        turn.setBounds(100, 100, 400, 200);

        // add happy gif
        ImageIcon gif = new ImageIcon("./res/giphy.gif");
        JLabel happyGif = new JLabel(gif);
        happyGif.setBounds(150, 200, 300, 400); // You can use your own values
        frame.getContentPane().add(happyGif);

        // add firework
        ImageIcon fire = new ImageIcon("./res/fireworks.gif");
        JLabel fireGif = new JLabel(fire);
        fireGif.setBounds(100, 200, 400, 600); // You can use your own values
        frame.getContentPane().add(fireGif);

        winMusic();

        frame.getContentPane().add(message);
        frame.getContentPane().add(turn);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(600,600);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


    /**
     * Add of music in the game
     */
    public static void winMusic(){
        Hexagon.mediaPlayer.stop();
        String musicFile = "./res/win.mp3";
        Media mediaWin = new Media(Paths.get(musicFile).toUri().toString());
        mediaPlayerWin = new MediaPlayer(mediaWin);
        mediaPlayerWin.play();
    }

}
