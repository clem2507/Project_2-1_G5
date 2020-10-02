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
import javafx.stage.Stage;

import java.util.Collections;


public class HomePage extends Application {

    private final double WIDTH = 1344;
    private final double HEIGHT = 756;
    Hexagon hexagon;

    @Override
    public void start(Stage primaryStage) {
        Group pane = new Group();

        Image background = new Image("Abalon/background.jpg");
        ImageView view = new ImageView(background);
        pane.getChildren().addAll(view);

        //Creation of the title of the project
        Text title = new Text("ABALONE");
        title.setTranslateX(460);
        title.setTranslateY(180);
        title.setFont(Font.font("ayuthaya", FontWeight.BOLD, FontPosture.REGULAR, 90));
        //Setting the color
        title.setFill(Color.BLACK);
        //Setting the Stroke
        pane.getChildren().add(title);

        //Creation of the text for the group number
        Text groupNumber = new Text("GROUP 5");
        groupNumber.setTranslateX(530);
        groupNumber.setTranslateY(480);
        groupNumber.setFont(Font.font("ayuthaya", FontWeight.BOLD, FontPosture.REGULAR, 50));
        //Setting the color
        groupNumber.setFill(Color.BLACK);
        //Setting the Stroke
        pane.getChildren().add(groupNumber);

        //Creation of a combo box to choose the game Mode
        ComboBox gameChoice = new ComboBox();
        gameChoice.getItems().addAll(
                "PLAYER VS PLAYER" ,
                "AI CHOICE 1",
                "AI CHOICE 2",
                "PRACTICE MODE");

        gameChoice.getSelectionModel().selectFirst();
        gameChoice.setTranslateX(560);
        gameChoice.setTranslateY(250);
        gameChoice.setPrefSize(150, 35);
        pane.getChildren().add(gameChoice);

        //Creation of a button to access the game
        Button play = new Button();
        play.setText("Start");
        play.setTranslateX(570);
        play.setTranslateY(330);
        play.setTextFill(Color.BLACK);
        play.setFont(Font.font("ayuthaya", FontWeight.BOLD, FontPosture.REGULAR, 30));
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

