
package Abalon.Main;

import Abalon.AI.AlphaBetaSearch;
import Abalon.AI.GameTree;
import Abalon.AI.MCTS;
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

	private int numberOfTurn = 0;

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
				victory = board.isVictorious(board.getBoard());
				numberOfTurn++;
				Hexagon.turnText.setText("Turn number " + numberOfTurn);
			}
			if (currentPlayer == 1) {
				Hexagon.winText.setText(Hexagon.displayCurrentPlayer(currentPlayer+1) + " won the game!");
				Hexagon.winImage.setImage(Hexagon.gif);
			} else {
				Hexagon.winText.setText(Hexagon.displayCurrentPlayer(currentPlayer-1) + " won the game!");
				Hexagon.winImage.setImage(Hexagon.gif);
			}
			// new page with the winner
			//System.exit(0);

			// Do we need this ?
			/*
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					board.drawAllCells();
				}
			});
			 */
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
					gameTree.createTree(board.getBoard(), currentPlayer, 3);
					AlphaBetaSearch algo = new AlphaBetaSearch(gameTree);
					algo.start(true);
					board.setBoard(algo.getBestMove());
					currentPlayer = 1;
				}
				board.drawAllCells();
				victory = board.isVictorious(board.getBoard());
				numberOfTurn++;
				Hexagon.turnText.setText("Turn number " + numberOfTurn);
			}
			if (currentPlayer == 1) {
				Hexagon.winText.setText(Hexagon.displayCurrentPlayer(currentPlayer+1) + " won the game!");
				Hexagon.winImage.setImage(Hexagon.gif);
			} else {
				Hexagon.winText.setText(Hexagon.displayCurrentPlayer(currentPlayer-1) + " won the game!");
				Hexagon.winImage.setImage(Hexagon.gif);
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
					MCTS monteCarlo = new MCTS(board.getBoard(), currentPlayer);
					monteCarlo.start();
					board.setBoard(monteCarlo.getBestMove());
					currentPlayer = 1;
				}
				board.drawAllCells();
				victory = board.isVictorious(board.getBoard());
				numberOfTurn++;
				Hexagon.turnText.setText("Turn number " + numberOfTurn);
			}
			if (currentPlayer == 1) {
				Hexagon.winText.setText(Hexagon.displayCurrentPlayer(currentPlayer+1) + " won the game!");
				Hexagon.winImage.setImage(Hexagon.gif);
			} else {
				Hexagon.winText.setText(Hexagon.displayCurrentPlayer(currentPlayer-1) + " won the game!");
				Hexagon.winImage.setImage(Hexagon.gif);
			}
		}
		else if (gameMode.equals("Alpha-Beta vs MCTS")) {
			currentPlayer = 1;
			while (!victory) {
				checkExitTheGame();
				Hexagon.whosePlaying.setText("It is " + Hexagon.displayCurrentPlayer(currentPlayer) + "'s turn to play.");
				if (currentPlayer == 1) {
					GameTree gameTree = new GameTree();
					gameTree.createTree(board.getBoard(), currentPlayer, 3);
					AlphaBetaSearch algo = new AlphaBetaSearch(gameTree);
					algo.start(true);
					board.setBoard(algo.getBestMove());
					currentPlayer = 2;
				}
				else {
					MCTS monteCarlo = new MCTS(board.getBoard(), currentPlayer);
					monteCarlo.start();
					board.setBoard(monteCarlo.getBestMove());
					currentPlayer = 1;
				}
				board.drawAllCells();
				victory = board.isVictorious(board.getBoard());
				numberOfTurn++;
				Hexagon.turnText.setText("Turn number " + numberOfTurn);
			}
			if (currentPlayer == 1) {
				Hexagon.winText.setText(Hexagon.displayCurrentPlayer(currentPlayer+1) + " won the game!");
				Hexagon.winImage.setImage(Hexagon.gif);
			} else {
				Hexagon.winText.setText(Hexagon.displayCurrentPlayer(currentPlayer-1) + " won the game!");
				Hexagon.winImage.setImage(Hexagon.gif);
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

	/*private void playMove(boolean flag) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				int id = (flag ? 1 : 0);
				player[id].performMove();
				if (victory == 0)
					playMove(flag ^ false);
			}
		});
	}*/
}

