package Abalon.UI;

import javafx.application.Application;
import javafx.event.EventHandler;
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
import javafx.scene.control.TextField;

import java.io.File;
import javafx.embed.swing.SwingFXUtils;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class HomePage extends Application {

    private final double WIDTH = 1344;
    private final double HEIGHT = 756;
    public static ComboBox gameChoice = new ComboBox();
    public static ComboBox evaluationChoice1 = new ComboBox();
    public static ComboBox evaluationChoice2 = new ComboBox();

    public static TextField field1;
    public static TextField field2;

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

        //Creation of a combo box to choose the game Mode
        gameChoice.getItems().addAll(
                "Human vs Human" ,
                "Alpha-Beta vs Human",
                "MCTS vs Human",
                "Rule-Based vs Human",
                "Alpha-Beta vs MCTS");

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

        // create combo boxes to choose which evaluation function to use
        evaluationChoice1.getItems().addAll(
                "Neutral evaluation function" ,
                "Offensive evaluation function",
                "Defensive evaluation function",
                "All evaluation functions");
        evaluationChoice1.getSelectionModel().selectFirst();
        evaluationChoice1.setTranslateX(270);
        evaluationChoice1.setTranslateY(350);
        evaluationChoice1.setPrefSize(230, 35);
        evaluationChoice2.getItems().addAll(
                "Neutral evaluation function" ,
                "Offensive evaluation function",
                "Defensive evaluation function",
                "All evaluation functions");
        evaluationChoice2.getSelectionModel().selectFirst();
        evaluationChoice2.setTranslateX(800);
        evaluationChoice2.setTranslateY(350);
        evaluationChoice2.setPrefSize(230, 35);
        //pane.getChildren().add(evaluationChoice2);


        //Button listener
        play.setOnAction((EventHandler) event -> {
            if (gameChoice.getValue().equals("Human vs Human")) {
                hexagon = new Hexagon();
                hexagon.start(primaryStage);
            }
            else if (gameChoice.getValue().equals("Alpha-Beta vs Human")){
                hexagon = new Hexagon();
                hexagon.start(primaryStage);
            }
            else if (gameChoice.getValue().equals("MCTS vs Human")){
                hexagon = new Hexagon();
                hexagon.start(primaryStage);
            }
            else if (gameChoice.getValue().equals("Rule-Based vs Human")){
                hexagon = new Hexagon();
                hexagon.start(primaryStage);
            }
            else if (gameChoice.getValue().equals("Alpha-Beta vs MCTS")){
                hexagon = new Hexagon();
                hexagon.start(primaryStage);
            }
            else {
                System.out.println("Please select a valid game mode!");
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
                            "Each player waits for his turn to play. \n When it's its turn the player has three options: \n" +
                            " moving 1, 2 or 3 marbles at the same \n time. Knowing that to displace opponent's \n marble," +
                            "he needs to push them with more \n than the number of marbles that are going to \n be displaced. " +
                            "A marble is ejected when it gets \n pushed out of the board. " +
                            "To move \n the marbles, the user first needs to select \n the marbles to move and then press:" +
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

        //Set the Player's name
        Text player1Text = new Text("Player 1's Name: ");
        player1Text.setTranslateX(270);
        player1Text.setTranslateY(270);
        player1Text.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //Setting the color
        player1Text.setFill(Color.BLACK);
        pane.getChildren().addAll(player1Text);

        field1 = new TextField();
        field1.setTranslateX(270);
        field1.setTranslateY(300);
        field1.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 20));
        pane.getChildren().addAll(field1);

        Text player2Text = new Text("Player 2's Name: ");
        player2Text.setTranslateX(800);
        player2Text.setTranslateY(270);
        player2Text.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //Setting the color
        player2Text.setFill(Color.BLACK);
        pane.getChildren().addAll(player2Text);

        field2 = new TextField();
        field2.setTranslateX(800);
        field2.setTranslateY(300);
        field2.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 20));
        pane.getChildren().addAll(field2);

        gameChoice.setOnAction(e -> {
            if(gameChoice.getValue().equals("Alpha-Beta vs Human")){
                field2.setText("Alpha Beta");
                pane.getChildren().add(evaluationChoice2);
            }
            else if(gameChoice.getValue().equals("MCTS vs Human")){
                field2.setText("MCTS");
                pane.getChildren().add(evaluationChoice2);
            }
            else if(gameChoice.getValue().equals("Rule-Based vs Human")){
                field2.setText("Rule-Based");
            }
            else if(gameChoice.getValue().equals("Alpha-Beta vs MCTS")){
                field2.setText("MCTS");
                field1.setText("Alpha Beta");
                pane.getChildren().add(evaluationChoice1);
                pane.getChildren().add(evaluationChoice2);
            }
        });

        Scene scene = new Scene(pane ,WIDTH, HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Abalone");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent t) {
                KeyCode key = t.getCode();
                if (key == KeyCode.ESCAPE){
                    primaryStage.close();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch();
    }
}