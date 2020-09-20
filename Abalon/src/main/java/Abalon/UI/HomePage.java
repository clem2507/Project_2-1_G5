package Abalon.UI;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class HomePage extends Application {

    private final double WIDTH = 1260;
    private final double HEIGHT = 700;
    Hexagon hexagon;

    @Override
    public void start(Stage primaryStage) {
        Group pane = new Group();

        //Creation of the title of the project
        Text title = new Text("ABALONE");
        title.setTranslateX(460);
        title.setTranslateY(200);
        title.setFont(Font.font("ayuthaya", FontWeight.BOLD, FontPosture.REGULAR, 90));
        //Setting the color
        title.setFill(Color.ORANGE);
        //Setting the Stroke
        pane.getChildren().add(title);

        //Creation of the text for the group number
        Text groupNumber = new Text("GROUP 5");
        groupNumber.setTranslateX(530);
        groupNumber.setTranslateY(500);
        groupNumber.setFont(Font.font("ayuthaya", FontWeight.BOLD, FontPosture.REGULAR, 50));
        //Setting the color
        groupNumber.setFill(Color.ORANGE);
        //Setting the Stroke
        pane.getChildren().add(groupNumber);

        //Creation of a button to access the game
        Button play = new Button();
        play.setText("Start");
        play.setTranslateX(570);
        play.setTranslateY(350);
        play.setTextFill(Color.ORANGE);
        play.setFont(Font.font("ayuthaya", FontWeight.BOLD, FontPosture.REGULAR, 30));
        pane.getChildren().add(play);

        //Button listener
        play.setOnAction(new EventHandler() {
            @Override
            public void handle(Event event) {
                hexagon = new Hexagon();
                System.out.println("Start Button clicked, game is gonna start!");
                hexagon.start(primaryStage);
            }
        });

        //Creation of a combo box to choose the game Mode
        ComboBox gameChoice = new ComboBox();
        gameChoice.getItems().addAll(
                "PLAYER VS PLAYER" ,
                "AI CHOICE 1",
                "AI CHOICE 2",
                "PRACTICE MODE");

        gameChoice.setTranslateX(560);
        gameChoice.setTranslateY(270);
        gameChoice.setPrefSize(150, 35);
        pane.getChildren().add(gameChoice);

        Scene scene = new Scene(pane ,WIDTH, HEIGHT);
        primaryStage.setTitle("Abalone");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}

