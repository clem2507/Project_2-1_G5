package Abalon.UI;

import Abalon.Main.Abalon;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;
import javafx.stage.Stage;

public class WinPage extends Application{

    private final double WIDTH = 1344;
    private final double HEIGHT = 756;
    Group pane = new Group();

    Abalon abalon;

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
        message.setBounds(220, 120, 300, 100);
        //message.setFont(new Font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 90));
        message.setFont(message.getFont ().deriveFont (37.0f));

        // TODO add timer


        // TODO add turn
        JLabel turn = new JLabel("The game took "+ String.valueOf(Abalon.numberOfTurn) +" turns.");
        message.setBounds(220, 70, 100, 50);
        message.setFont(message.getFont ().deriveFont (20.0f));

        // TODO add gif
        /*ImageView winImage = new ImageView();
        Image gif = new Image(new File(".Abalon/res/giphy.gif").toURI().toString());
        winImage.setX(1000);
        winImage.setY(600);
        winImage.setFitHeight(100);
        winImage.setFitWidth(100);*/

        /*try {
            BufferedImage img = ImageIO.read(new File("./res/giphy.gif"));
            ImageIcon icon = new ImageIcon(img);
            JLabel jlabel = new JLabel(icon, JLabel.CENTER);
            frame.getContentPane().add(jlabel);
            frame.validate();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO add music
        Hexagon.winMusic();*/

        frame.add(turn);
        frame.add(message);
        //frame.getContentPane().add(message);
        frame.setLayout(null);
        //frame.pack();
        frame.setSize(600,600);
        frame.setVisible(true);


    }

    public static void main(String[] args) {
        launch(args);
    }

    public double getHEIGHT() {
        return HEIGHT;
    }

    public double getWIDTH() {
        return WIDTH;
    }

    public Group getPane() {
        return pane;
    }
}
