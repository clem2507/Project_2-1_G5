
package Abalone.Main;

import Abalone.AI.AB.AlphaBetaSearch;
import Abalone.AI.Tree.GameTree;
import Abalone.AI.MCTS.MCTS;
import Abalone.AI.RB.RuleBased;
import Abalone.UI.BoardUI;

import Abalone.UI.Hexagon;
import Abalone.UI.HomePage;
import Abalone.UI.WinPage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.lang.InterruptedException;

import static javafx.scene.input.KeyCode.*;

/**
 * Abalone class that serves to run a new game
 */
public class Abalone {

	private static int currentPlayer = 1;

	private BoardUI board;
	private Player[] player = new Player[2];
	private boolean victory = false;

	public static int numberOfTurn = 0;
	public int strategy1;
	public int strategy2;
	public int timer;

	private static String gameMode;

	/**
	 * Returns a new object of class Abalone
	 * @param p1 class that serves to perform the move of a real person OR an AI
	 * @param p2 class that serves to perform the move of a real person OR an AI
	 */
	public Abalone(BoardUI board, Player p1, Player p2, String gameMode) {
		this.board = board;
		player[0] = p1;
		player[1] = p2;

		player[0].setBoard(board);
		player[1].setBoard(board);
		player[0].setTurn(1);
		player[1].setTurn(2);

		this.gameMode = gameMode;
	}

	public void runGame() {
		//playMove(false);

		if (gameMode.equals("Human vs Human")) {
			for (int i = 0; !victory; i++) {
				int p;
				checkExitTheGame();
				Hexagon.whosePlaying.setText("It is " + Hexagon.displayCurrentPlayer(currentPlayer).toUpperCase() + "'s turn to play.");
				try {
					Move mv = player[i & 1].collectMove();
					mv.turn = currentPlayer;
					mv.board = board.getBoard();
					Rules checkRules = new Rules(mv);
					if(checkRules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)){
						checkRules.move();
						if (currentPlayer == 1) {
							p = 2;
						}
						else {
							p = 1;
						}
					}
					else{
						p = currentPlayer;
						numberOfTurn--;
					}

					if (currentPlayer == 1) {
						currentPlayer = p;
					}
					else {
						currentPlayer = p;
					}
					board.drawAllCells();

				} catch (InterruptedException e) {
					System.out.println("concurrency problem, aborting...");
					System.exit(0);
				}
				numberOfTurn++;
				Hexagon.turnText.setText("Turn number " + numberOfTurn);
				victory = BoardUI.isVictorious(board.getBoard());
			}
		}
		else if (gameMode.equals("Alpha-Beta vs Human")) {

			if(HomePage.evaluationChoice2.getValue().equals("Neutral evaluation function")){
				strategy2 = 1;
			}else if(HomePage.evaluationChoice2.getValue().equals("Offensive evaluation function")) {
				strategy2 = 2;
			}else if(HomePage.evaluationChoice2.getValue().equals("Defensive evaluation function")){
				strategy2 = 3;
			}else if(HomePage.evaluationChoice2.getValue().equals("All evaluation functions")){
				strategy2 = 4;
			}

			int index = 0;
			while (!victory) {
				checkExitTheGame();
				Hexagon.whosePlaying.setText("It is " + Hexagon.displayCurrentPlayer(currentPlayer).toUpperCase() + "'s turn to play.");
				if (currentPlayer == 1) {
					int p = 2;
					try {
						Move mv = player[index & 1].collectMove();
						mv.board = board.getBoard();
						Rules checkRules = new Rules(mv);

						if(checkRules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)){
							checkRules.move();
						}
						else{
							p = 1;
							numberOfTurn--;
						}

					} catch (InterruptedException e) {
						System.out.println("concurrency problem, aborting...");
						System.exit(0);
					}
					currentPlayer = p;
					index += 2;
				}
				else {
					GameTree gameTree = new GameTree(strategy2, 1, true);
					gameTree.createTree(board.getBoard(), currentPlayer, 3);
					AlphaBetaSearch algo = new AlphaBetaSearch(gameTree);
					algo.start(true);
					board.setBoard(algo.getBestMove());
					currentPlayer = 1;
				}
				numberOfTurn++;
				Hexagon.turnText.setText("Turn number " + numberOfTurn);
				board.drawAllCells();
				victory = BoardUI.isVictorious(board.getBoard());
			}
		}
		else if (gameMode.equals("MCTS vs Human")) {

			if(HomePage.evaluationChoice2.getValue().equals("Neutral evaluation function")){
				strategy2 = 1;
			}else if(HomePage.evaluationChoice2.getValue().equals("Offensive evaluation function")) {
				strategy2 = 2;
			}else if(HomePage.evaluationChoice2.getValue().equals("Defensive evaluation function")){
				strategy2 = 3;
			}else if(HomePage.evaluationChoice2.getValue().equals("All evaluation functions")){
				strategy2 = 4;
			}

			if(HomePage.mctsDifficulty.getValue().equals("Easy")){
				timer = 2000;
			}
			else if(HomePage.mctsDifficulty.getValue().equals("Neutral")){
				timer = 5000;
			}
			else if(HomePage.mctsDifficulty.getValue().equals("Hard")){
				timer = 10000;
			}

			int index = 0;
			while (!victory) {
				checkExitTheGame();
				Hexagon.whosePlaying.setText("It is " + Hexagon.displayCurrentPlayer(currentPlayer).toUpperCase() + "'s turn to play.");
				if (currentPlayer == 1) {
					int p = 2;
					try {
						Move mv = player[index & 1].collectMove();
						mv.board = board.getBoard();
						Rules checkRules = new Rules(mv);

						if(checkRules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)){
							checkRules.move();
						}
						else{
							p = 1;
							numberOfTurn--;
						}

					} catch (InterruptedException e) {
						System.out.println("concurrency problem, aborting...");
						System.exit(0);
					}
					currentPlayer = p;
					index += 2;
				}
				else {
					MCTS monteCarlo = new MCTS(board.getBoard(), currentPlayer,timer,10, strategy2);
					monteCarlo.start();
					board.setBoard(monteCarlo.getBestMove());
					currentPlayer = 1;
				}
				numberOfTurn++;
				Hexagon.turnText.setText("Turn number " + numberOfTurn);
				board.drawAllCells();
				victory = BoardUI.isVictorious(board.getBoard());
			}
		}
		else if (gameMode.equals("Rule-Based vs Human")) {
			int index = 0;
			while (!victory) {
				checkExitTheGame();
				Hexagon.whosePlaying.setText("It is " + Hexagon.displayCurrentPlayer(currentPlayer).toUpperCase() + "'s turn to play.");
				if (currentPlayer == 1) {
					int p = 2;
					try {
						Move mv = player[index & 1].collectMove();
						mv.board = board.getBoard();
						Rules checkRules = new Rules(mv);

						if(checkRules.checkMove(mv.pushing, mv.dir, mv.board, mv.turn)){
							checkRules.move();
						}
						else{
							p = 1;
							numberOfTurn--;
						}
					} catch (InterruptedException e) {
						System.out.println("concurrency problem, aborting...");
						System.exit(0);
					}
					currentPlayer = p;
					index += 2;
				}
				else {
					RuleBased ruleBased = new RuleBased(board.getBoard(), currentPlayer);
					ruleBased.start();
					try {
						Thread.sleep(1000); // delay to have time to see bot playing
					} catch (InterruptedException ie) {
						Thread.currentThread().interrupt();
					}
					board.setBoard(ruleBased.getBestMove());
					currentPlayer = 1;
				}
				numberOfTurn++;
				Hexagon.turnText.setText("Turn number " + numberOfTurn);
				board.drawAllCells();
				victory = BoardUI.isVictorious(board.getBoard());
			}
		}
		else if (gameMode.equals("Alpha-Beta vs MCTS")) {

			if(HomePage.evaluationChoice1.getValue().equals("Neutral evaluation function")){
				strategy1 = 1;
			}else if(HomePage.evaluationChoice1.getValue().equals("Offensive evaluation function")) {
				strategy1 = 2;
			}else if(HomePage.evaluationChoice1.getValue().equals("Defensive evaluation function")){
				strategy1 = 3;
			}else if(HomePage.evaluationChoice2.getValue().equals("All evaluation functions")){
				strategy2 = 4;
			}

			if(HomePage.evaluationChoice2.getValue().equals("Neutral evaluation function")){
				strategy2 = 1;
			}else if(HomePage.evaluationChoice2.getValue().equals("Offensive evaluation function")) {
				strategy2 = 2;
			}else if(HomePage.evaluationChoice2.getValue().equals("Defensive evaluation function")){
				strategy2 = 3;
			}else if(HomePage.evaluationChoice2.getValue().equals("All evaluation functions")){
				strategy2 = 4;
			}

			currentPlayer = 1;
			long start = 0;
			long end = 0;
			while (!victory) {
				checkExitTheGame();
				Hexagon.whosePlaying.setText("It is " + Hexagon.displayCurrentPlayer(currentPlayer).toUpperCase() + "'s turn to play.");
				if (currentPlayer == 1) {
					start = System.currentTimeMillis();
					GameTree gameTree = new GameTree(1, 1, false);
					gameTree.createTree(board.getBoard(), currentPlayer, 3);
					AlphaBetaSearch algo = new AlphaBetaSearch(gameTree);
					algo.start(true);
					board.setBoard(algo.getBestMove());
					end = System.currentTimeMillis();
					currentPlayer = 2;
				}
				else {
					MCTS monteCarlo;
					if ((int) (end-start) < 2000) {
						monteCarlo = new MCTS(board.getBoard(), currentPlayer, 2000, 10, strategy2);
					}
					else {
						monteCarlo = new MCTS(board.getBoard(), currentPlayer, (int) (end-start),10, strategy2);
					}
					monteCarlo.start();
					board.setBoard(monteCarlo.getBestMove());
					currentPlayer = 1;
				}
				numberOfTurn++;
				Hexagon.turnText.setText("Turn number " + numberOfTurn);
				board.drawAllCells();
				victory = BoardUI.isVictorious(board.getBoard());
			}
		}
		System.out.println("total numberOfTurn = " + numberOfTurn);
		if (currentPlayer == 1) {
			WinPage winpage = new WinPage();
			try {
				winpage.start(Hexagon.primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			WinPage winpage = new WinPage();
			try {
				winpage.start(Hexagon.primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void checkExitTheGame(){

		EventHandler keyHandler  = (EventHandler<KeyEvent>) e -> {
			if (e.getCode() == ESCAPE) {
				System.exit(0);
			}
		};

		Thread inpThread = new Thread(() -> Hexagon.accessableScene.addEventHandler(KeyEvent.KEY_PRESSED, keyHandler));
		inpThread.start();
	}

	public static String getGameMode() {
		return gameMode;
	}

	public static int getCurrentPlayer() {
		return currentPlayer;
	}

}


