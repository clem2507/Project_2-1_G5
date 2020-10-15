package Abalon.UI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;   
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File; 
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.util.Collections;

public class HomePage extends Application {

    private final double WIDTH = 1344;
    private final double HEIGHT = 756;
    Hexagon hexagon;

    @Override
    public void start(Stage primaryStage) {
        Group pane = new Group();

        try {
            BufferedImage buffer = ImageIO.read(new File("./res/grey.jpg"));
            Image background = SwingFXUtils.toFXImage(buffer, null);
            ImageView view = new ImageView(background);
            pane.getChildren().addAll(view);
        } catch (Exception e) {
            System.out.println("file not found");
            System.exit(0);
        }

        //Creation of the title of the project
        Text title = new Text("ABALONE");
        title.setTranslateX(460);
        title.setTranslateY(180);
        title.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 90));
        //Setting the color
        title.setFill(Color.BLACK);
        //Setting the Stroke
        pane.getChildren().add(title);

        // System.out.println(javafx.scene.text.Font.getFamilies());

        //Creation of a combo box to choose the game Mode
        ComboBox gameChoice = new ComboBox();
        gameChoice.getItems().addAll(
                "PLAYER VS PLAYER" ,
                "AI CHOICE 1",
                "AI CHOICE 2",
                "PRACTICE MODE");

        gameChoice.getSelectionModel().selectFirst();
        gameChoice.setTranslateX(548);
        gameChoice.setTranslateY(250);
        gameChoice.setPrefSize(180, 35);
        pane.getChildren().add(gameChoice);

        //Creation of a button to access the game
        Button play = new Button();
        play.setText("Start");
        play.setTranslateX(570);
        play.setTranslateY(330);
        play.setTextFill(Color.BLACK);
        play.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 35));
        pane.getChildren().add(play);

        //Button listener
        play.setOnAction((EventHandler) event -> {
            if (gameChoice.getValue().equals("PLAYER VS PLAYER")) {
                hexagon = new Hexagon();
                System.out.println("Start Button clicked, game is gonna start!");
                hexagon.start(primaryStage);
            }
            else {
                System.out.println("Please select PLAYER VS PLAYER game mode!");
            }
        });

        Button rules = new Button();
        rules.setText("Rules");
        rules.setTranslateX(570);
        rules.setTranslateY(440);
        rules.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 13));
        rules.setOnAction(
                event -> {
                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(primaryStage);
                    VBox dialogVbox = new VBox(20);
                    Text text = new Text("The goal of the game is to eject 6 marbles of the \n opponent. " +
                            "Each player waits for his turn to play. \n When it is its turn the player has three options: \n" +
                            " moving 1, 2 or 3 marbles at the same \n time. Knowing that to displace opponent's \n marble," +
                            "he needs to push them with more \n than the number of marbles that are going to \n be displaced." +
                            "Thus, he can displace maximum \n two of the opponents' marbles because he can \n only move maximum" +
                            "three marbles at the \n same time. A marble is ejected when it \n gets pushed out of the board." +
                            "To \n move the marbles, the user first needs to select \n the marbles to move and then press:" +
                            "\n- Q to go TOP_LEFT \n" +
                            "- A to go LEFT\n" +
                            "- D to go RIGHT \n" +
                            "- Z to go BOTTOM_LEFT \n" +
                            "- C to go BOTTOM_RIGHT \n" +
                            "- E to go TOP_RIGHT " +
                            "\nThen press enter to confirm the move.");
                    text.setFont(Font.font("Arial", 13));
                    dialogVbox.getChildren().add(text);
                    Scene dialogScene = new Scene(dialogVbox, 280, 400);
                    dialog.setScene(dialogScene);
                    dialog.setResizable(false);
                    dialog.show();
                });
        pane.getChildren().addAll(rules);

        Button contact = new Button();
        contact.setText("Contact");
        contact.setTranslateX(642);
        contact.setTranslateY(440);
        contact.setFont(Font.font("Times New Roman", FontWeight.BOLD, FontPosture.REGULAR, 13));
        contact.setOnAction(
                event -> {
                    final Stage dialog = new Stage();
                    dialog.initModality(Modality.APPLICATION_MODAL);
                    dialog.initOwner(primaryStage);
                    Text text = new Text(
                            "\n \tGroup 5:\n \n" +
                                    "\tDetry Clément\n" +
                                    "\tImparato Adèle\n" +
                                    "\tPodevyn Loris\n" +
                                    "\tPoliakov Ivan\n" +
                                    "\tSchapira Aaron\n" +
                                    "\tThirion Guillaume\n" +
                                    "\tYap Mathias"

                    );
                    text.setFont(Font.font("Arial", 13));
                    VBox dialogVbox = new VBox(20);
                    dialogVbox.getChildren().add(text);
                    Scene dialogScene = new Scene(dialogVbox, 150, 180);
                    dialog.setScene(dialogScene);
                    dialog.setResizable(false);
                    dialog.show();
                });
        pane.getChildren().addAll(contact);

        Scene scene = new Scene(pane ,WIDTH, HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Abalone");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}