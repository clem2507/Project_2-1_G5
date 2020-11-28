package Abalon.UI;

import Abalon.Main.Abalon;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class WinPage extends Application{

    private final double WIDTH = 1344;
    private final double HEIGHT = 756;
    Group pane = new Group();

    Abalon abalon;

    public WinPage(){

    }

    public void start(Stage primaryStage) throws Exception {

        Text title = new Text();

        //Set the title
        if(Abalon.getGameMode().equals("Human vs Human")){
            if(abalon.getCurrentPlayer() == 1){
                System.out.println("Player 2 won the game");
                title.setText("PLAYER 2 WON!");
            }else{
                System.out.println("Player 1 won the game");
                title.setText("PLAYER 1 WON!");
            }
        }
        else if(Abalon.getGameMode().equals("Alpha-Beta vs Human")){
            if(abalon.getCurrentPlayer() == 1){
                System.out.println("Player 1 won the game");
                title.setText("PLAYER 1 WON!");
            }else{
                System.out.println("MINIMAX won the game");
                title.setText("MINIMAX WON!");
            }
        }
        else if(Abalon.getGameMode().equals("MCTS vs Human")){
            if(abalon.getCurrentPlayer() == 1){
                System.out.println("Player 1 won the game");
                title.setText("PLAYER 1 WON!");
            }else{
                System.out.println("MCTS won the game");
                title.setText("MCTS WON!");
            }
        }
        else{ //AI vs AI
            if(abalon.getCurrentPlayer() == 1){
                System.out.println("MINIMAX won the game");
                title.setText("MINIMAX WON!");
            }else{
                System.out.println("MCTS won the game");
                title.setText("MCTS WON!");
            }
        }

        title.setTranslateX(460);
        title.setTranslateY(180);
        title.setFont(Font.font("Zorque", FontWeight.BOLD, FontPosture.REGULAR, 90));
        //Setting the color
        title.setFill(Color.BLACK);
        //Setting the Stroke
        pane.getChildren().add(title);

        Scene scene = new Scene(pane ,WIDTH, HEIGHT);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Abalone");
        primaryStage.setScene(scene);
        primaryStage.show();

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
