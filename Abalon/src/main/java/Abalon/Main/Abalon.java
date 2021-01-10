
package Abalon.Main;

import Abalon.AI.AB.AlphaBetaSearch;
import Abalon.AI.Tree.GameTree;
import Abalon.AI.MCTS.MCTS;
import Abalon.AI.RB.RuleBased;
import Abalon.UI.BoardUI;

import Abalon.UI.Hexagon;
import Abalon.UI.WinPage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

import java.lang.InterruptedException;

import static javafx.scene.input.KeyCode.*;

/**
 * Abalon class that serves to run a new game
 */
public class Abalon {

	private static int currentPlayer = 1;

	private BoardUI board;
	private Player[] player = new Player[2];
	private boolean victory = false;

	public static int numberOfTurn = 0;

	private static String gameMode;

	WinPage winPage;

	/**
	 * Returns a new object of class Abalon
	 * @param p1 class that serves to perform the move of a real person OR an AI
	 * @param p2 class that serves to perform the move of a real person OR an AI
	 */
	public Abalon(BoardUI board, Player p1, Player p2, String gameMode) {
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
				checkExitTheGame();
				Hexagon.whosePlaying.setText("It is " + Hexagon.displayCurrentPlayer(currentPlayer) + "'s turn to play.");
				try {
					Move mv = player[i & 1].collectMove();
					mv.board = board.getBoard();
					Rules checkRules = new Rules(mv);
					checkRules.move();
					board.drawAllCells();
					if (currentPlayer == 1) {
						currentPlayer = 2;
					}
					else {
						currentPlayer = 1;
					}
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
			int index = 0;
			while (!victory) {
				checkExitTheGame();
				Hexagon.whosePlaying.setText("It is " + Hexagon.displayCurrentPlayer(currentPlayer) + "'s turn to play.");
				if (currentPlayer == 1) {
					try {
						Move mv = player[index & 1].collectMove();
						mv.board = board.getBoard();
						Rules checkRules = new Rules(mv);
						checkRules.move();
					} catch (InterruptedException e) {
						System.out.println("concurrency problem, aborting...");
						System.exit(0);
					}
					currentPlayer = 2;
					index += 2;
				}
				else {
					GameTree gameTree = new GameTree();
					gameTree.createTree(board.getBoard(), currentPlayer, 3, 1);
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
			int index = 0;
			while (!victory) {
				checkExitTheGame();
				Hexagon.whosePlaying.setText("It is " + Hexagon.displayCurrentPlayer(currentPlayer) + "'s turn to play.");
				if (currentPlayer == 1) {
					try {
						Move mv = player[index & 1].collectMove();
						mv.board = board.getBoard();
						Rules checkRules = new Rules(mv);
						checkRules.move();
					} catch (InterruptedException e) {
						System.out.println("concurrency problem, aborting...");
						System.exit(0);
					}
					currentPlayer = 2;
					index += 2;
				}
				else {
					MCTS monteCarlo = new MCTS(board.getBoard(), currentPlayer,0,0,0,0);
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
				Hexagon.whosePlaying.setText("It is " + Hexagon.displayCurrentPlayer(currentPlayer) + "'s turn to play.");
				if (currentPlayer == 1) {
					try {
						Move mv = player[index & 1].collectMove();
						mv.board = board.getBoard();
						Rules checkRules = new Rules(mv);
						checkRules.move();
					} catch (InterruptedException e) {
						System.out.println("concurrency problem, aborting...");
						System.exit(0);
					}
					currentPlayer = 2;
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
			currentPlayer = 1;
			while (!victory) {
				checkExitTheGame();
				Hexagon.whosePlaying.setText("It is " + Hexagon.displayCurrentPlayer(currentPlayer) + "'s turn to play.");
				if (currentPlayer == 1) {
					GameTree gameTree = new GameTree();
					gameTree.createTree(board.getBoard(), currentPlayer, 3, 1);
					AlphaBetaSearch algo = new AlphaBetaSearch(gameTree);
					algo.start(true);
					board.setBoard(algo.getBestMove());
					currentPlayer = 2;
				}
				else {
					MCTS monteCarlo = new MCTS(board.getBoard(), currentPlayer,0,0,0,0);
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

			/*Hexagon.winText.setText(Hexagon.displayCurrentPlayer(currentPlayer+1) + " won the game!");
			Hexagon.winImage.setImage(Hexagon.gif);
			Hexagon.winMusic();*/
		} else {
			WinPage winpage = new WinPage();
			try {
				winpage.start(Hexagon.primaryStage);
			} catch (Exception e) {
				e.printStackTrace();
			}

			/*Hexagon.winText.setText(Hexagon.displayCurrentPlayer(currentPlayer-1) + " won the game!");
			Hexagon.winImage.setImage(Hexagon.gif);
			Hexagon.winMusic();*/
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

