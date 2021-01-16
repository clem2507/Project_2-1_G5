package Abalon.UI;

import Abalon.Main.Abalon;
import javafx.application.Application;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.awt.*;
import java.nio.file.Paths;
import javax.swing.*;
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

        JLabel turn = new JLabel("The game took "+ Abalon.numberOfTurn +" turns.", SwingConstants.CENTER);
        turn.setFont(f2);
        turn.setBounds(100, 100, 400, 200);

        // add happy gif
        ImageIcon gif = new ImageIcon("Abalon/res/aaron.gif");
        JLabel happyGif = new JLabel(gif);
        happyGif.setBounds(50, 150, 500, 500); // You can use your own values
        frame.getContentPane().add(happyGif);

        // add firework
        ImageIcon fire = new ImageIcon("Abalon/res/fireworks.gif");
        JLabel fireGif = new JLabel(fire);
        fireGif.setBounds(100, 200, 400, 600); // You can use your own values
        frame.getContentPane().add(fireGif);

        winMusic();

        frame.getContentPane().add(message);
        frame.getContentPane().add(turn);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setLayout(null);
        frame.setSize(600,650);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }


    /**
     * Add of music in the game
     */
    public static void winMusic(){
        Hexagon.mediaPlayer.stop();
        String musicFile = "Abalon/res/win.mp3";
        Media mediaWin = new Media(Paths.get(musicFile).toUri().toString());
        mediaPlayerWin = new MediaPlayer(mediaWin);
        mediaPlayerWin.play();
    }

}
