package Abalon.UI;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
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
import java.io.IOException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import javax.swing.*;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;

public class HomePage extends Application {

    private final double WIDTH = 1344;
    private final double HEIGHT = 756;
    public static ComboBox gameChoice = new ComboBox();
    public static ComboBox evaluationChoice1 = new ComboBox();
    public static ComboBox evaluationChoice2 = new ComboBox();
    public static ComboBox mctsDifficulty = new ComboBox();

    public static TextField field1;
    public static TextField field2;

    public static Button avatar1;
    public static Button avatar2;
    public static Button avatar3;
    public static Button avatar4;
    public static Button avatar5;
    public static Button avatar6;
    public static int avatarChoice1;
    public static int avatarChoice2;

    public static Image arrow1;
    public static Image arrow2;
    public static ImageView arrow1View;
    public static ImageView arrow2View;

    public static Group pane = new Group();

    Hexagon hexagon;

    @Override
    public void start(Stage primaryStage) {

        setBackground();
        displayTitle();
        addGameChoice();

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

        setPlayersNames();

        addAvatarButtons();

        gameChoice.setOnAction(e -> {

            field1.setText("Human");
            field2.setText("Human");

            pane.getChildren().removeAll(avatar1, avatar2, avatar3);
            pane.getChildren().removeAll(avatar4, avatar5, avatar6);
            pane.getChildren().remove(evaluationChoice2);
            pane.getChildren().remove(evaluationChoice1);
            pane.getChildren().remove(mctsDifficulty);
            pane.getChildren().addAll(avatar1, avatar2, avatar3);
            pane.getChildren().addAll(avatar4, avatar5, avatar6);


            if(gameChoice.getValue().equals("Alpha-Beta vs Human")){
                field1.setText("Human");
                field2.setText("Alpha Beta");
                pane.getChildren().remove(evaluationChoice2);
                pane.getChildren().remove(evaluationChoice1);
                pane.getChildren().remove(mctsDifficulty);
                pane.getChildren().add(evaluationChoice2);
                pane.getChildren().removeAll(avatar4, avatar5, avatar6);

            }
            else if(gameChoice.getValue().equals("MCTS vs Human")){
                field1.setText("Human");
                field2.setText("MCTS");
                pane.getChildren().remove(evaluationChoice2);
                pane.getChildren().remove(evaluationChoice1);
                pane.getChildren().remove(mctsDifficulty);
                pane.getChildren().removeAll(avatar4, avatar5, avatar6);

                pane.getChildren().add(evaluationChoice2);
                pane.getChildren().add(mctsDifficulty);
            }
            else if(gameChoice.getValue().equals("Rule-Based vs Human")){
                field1.setText("Human");
                field2.setText("Rule-Based");
                pane.getChildren().remove(evaluationChoice1);
                pane.getChildren().remove(evaluationChoice2);
                pane.getChildren().remove(mctsDifficulty);
                pane.getChildren().removeAll(avatar4, avatar5, avatar6);

            }
            else if(gameChoice.getValue().equals("Alpha-Beta vs MCTS")){
                field2.setText("MCTS");
                field1.setText("Alpha Beta");

                pane.getChildren().remove(evaluationChoice1);
                pane.getChildren().remove(evaluationChoice2);
                pane.getChildren().remove(mctsDifficulty);

                pane.getChildren().add(evaluationChoice1);
                pane.getChildren().add(evaluationChoice2);
                pane.getChildren().add(mctsDifficulty);

                pane.getChildren().removeAll(avatar1, avatar2, avatar3);
                pane.getChildren().removeAll(avatar4, avatar5, avatar6);
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

    /**
     * Set the Player's name
     */
    private void setPlayersNames() {
        Text player1Text = new Text("Player 1's Name: ");
        player1Text.setTranslateX(270);
        player1Text.setTranslateY(270);
        player1Text.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 20));
        //Setting the color
        player1Text.setFill(Color.BLACK);
        pane.getChildren().addAll(player1Text);

        field1 = new TextField("Human");
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

        field2 = new TextField("Human");
        field2.setTranslateX(800);
        field2.setTranslateY(300);
        field2.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 20));
        pane.getChildren().addAll(field2);
    }

    /**
     * Creation of a combo box to choose the game Mode
     */
    private void addGameChoice() {
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

        mctsDifficulty.getItems().addAll("Easy", "Neutral", "Hard");
        mctsDifficulty.getSelectionModel().selectFirst();
        mctsDifficulty.setTranslateX(800);
        mctsDifficulty.setTranslateY(400);
        mctsDifficulty.setPrefSize(230, 35);
    }

    /**
     * Add of the title of the project
     */
    private void displayTitle() {
        Text title = new Text("ABALONE");
        title.setTranslateX(460);
        title.setTranslateY(180);
        title.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 90));
        //Setting the color
        title.setFill(Color.BLACK);
        //Setting the Stroke
        pane.getChildren().add(title);
    }

    private void setBackground() {
        try {
            BufferedImage buffer = ImageIO.read(new File("./res/grey.jpg"));
            Image background = SwingFXUtils.toFXImage(buffer, null);
            ImageView view = new ImageView(background);
            pane.getChildren().addAll(view);
        } catch (Exception e) {
            System.out.println("file not found");
            System.exit(0);
        }
    }

    private void addAvatarButtons() {
        arrow1 = new Image(Paths.get("./res/arrow.png").toUri().toString());
        arrow2 = new Image(Paths.get("./res/arrow.png").toUri().toString());

        arrow1View = new ImageView(arrow1);
        arrow2View = new ImageView(arrow2);

        arrow1View.setFitHeight(20);
        arrow1View.setPreserveRatio(true);
        arrow1View.setTranslateX(360);
        arrow1View.setTranslateY(400);
        pane.getChildren().add(arrow1View);

        arrow2View.setFitHeight(20);
        arrow2View.setPreserveRatio(true);
        arrow2View.setTranslateX(890);
        arrow2View.setTranslateY(400);
        pane.getChildren().add(arrow2View);

        String pathAvatar1 = "./res/ghost.png";
        String pathAvatar2 = "./res/mario.png";
        String pathAvatar3 = "./res/muscle.png";

        avatar1 = new Button();
        Image avatarImage1 = new Image(Paths.get(pathAvatar1).toUri().toString());
        ImageView avatarView1 = new ImageView(avatarImage1);
        avatarView1.setFitHeight(40);
        avatarView1.setPreserveRatio(true);
        avatar1.setTranslateX(345);
        avatar1.setTranslateY(350);
        avatar1.setPrefSize(40, 40);
        avatar1.setGraphic(avatarView1);

        avatar2 = new Button();
        Image avatarImage2 = new Image(Paths.get(pathAvatar2).toUri().toString());
        ImageView avatarView2 = new ImageView(avatarImage2);
        avatarView2.setFitHeight(40);
        avatarView2.setPreserveRatio(true);
        avatar2.setTranslateX(280);
        avatar2.setTranslateY(350);
        avatar2.setPrefSize(40, 40);
        avatar2.setGraphic(avatarView2);

        avatar3 = new Button();
        Image avatarImage3 = new Image(Paths.get(pathAvatar3).toUri().toString());
        ImageView avatarView3 = new ImageView(avatarImage3);
        avatarView3.setFitHeight(40);
        avatarView3.setPreserveRatio(true);
        avatar3.setTranslateX(410);
        avatar3.setTranslateY(350);
        avatar3.setPrefSize(40, 40);
        avatar3.setGraphic(avatarView3);

        //Pane paneAvatars = new Pane();
        //paneAvatars.setPrefWidth(300);
        pane.getChildren().addAll(avatar1,avatar2,avatar3);
        //pane.getChildren().add(paneAvatars);

        avatar1.setOnAction((EventHandler) e-> {
            avatarChoice1 =1;
            arrow1View.setTranslateX(360);
        });
        avatar2.setOnAction((EventHandler) e-> {
            avatarChoice1 = 2;
            arrow1View.setTranslateX(295);
        });
        avatar3.setOnAction((EventHandler) e-> {
            avatarChoice1 = 3;
            arrow1View.setTranslateX(425);
        });

        String pathAvatar4 = "./res/ghost.png";
        String pathAvatar5 = "./res/mario.png";
        String pathAvatar6 = "./res/muscle.png";

        avatar4 = new Button();
        Image avatarImage4 = new Image(Paths.get(pathAvatar4).toUri().toString());
        ImageView avatarView4 = new ImageView(avatarImage4);
        avatarView4.setFitHeight(40);
        avatarView4.setPreserveRatio(true);
        avatar4.setTranslateX(875);
        avatar4.setTranslateY(350);
        avatar4.setPrefSize(40, 40);
        avatar4.setGraphic(avatarView4);

        avatar5 = new Button();
        Image avatarImage5 = new Image(Paths.get(pathAvatar5).toUri().toString());
        ImageView avatarView5 = new ImageView(avatarImage5);
        avatarView5.setFitHeight(40);
        avatarView5.setPreserveRatio(true);
        avatar5.setTranslateX(810);
        avatar5.setTranslateY(350);
        avatar5.setPrefSize(40, 40);
        avatar5.setGraphic(avatarView5);

        avatar6 = new Button();
        Image avatarImage6 = new Image(Paths.get(pathAvatar6).toUri().toString());
        ImageView avatarView6= new ImageView(avatarImage6);
        avatarView6.setFitHeight(40);
        avatarView6.setPreserveRatio(true);
        avatar6.setTranslateX(940);
        avatar6.setTranslateY(350);
        avatar6.setPrefSize(40, 40);
        avatar6.setGraphic(avatarView6);

        //Pane paneAvatarsBis = new Pane();
        //paneAvatarsBis.prefWidth(400);
        pane.getChildren().addAll(avatar4,avatar5,avatar6);
        //pane.getChildren().add(paneAvatarsBis);

        avatar4.setOnAction((EventHandler) e-> {
            avatarChoice2 = 1;
            arrow2View.setTranslateX(890);
        });

        avatar5.setOnAction((EventHandler) e-> {
            avatarChoice2 = 2;
            arrow2View.setTranslateX(825);
        });

        avatar6.setOnAction((EventHandler) e-> {
            avatarChoice2 = 3;
            arrow2View.setTranslateX(955);
        });


    }

    public static void main(String[] args) {
        launch();
    }
}