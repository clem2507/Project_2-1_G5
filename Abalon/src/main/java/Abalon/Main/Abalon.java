package Abalon.Main;

import Abalon.AI.AlphaBetaSearch;
import Abalon.AI.GameTree;
import Abalon.AI.MCTS;
import Abalon.AI.Test;
import Abalon.UI.BoardUI;
import java.awt.EventQueue;
import javafx.application.Platform;

import java.lang.InterruptedException;

/**
 * Abalon class that serves to run a new game
 */
public class Abalon {

	private int currentPlayer;

	/**
	 * Current way to run the project, to be replaced 
	 */
	public static void main(String[] args) {
		//Player p1 = new PlayerH();
		//Player p2 = new PlayerH();
		//Abalon obj = new Abalon(p1, p2);
	}

	private BoardUI board;
	private Player[] player = new Player[2]; 
	private boolean victory = false;

	private static String gameMode;

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
				try {
					Move mv = player[i & 1].collectMove();
					mv.board = board.getBoard();
					Rules checkRules = new Rules(mv);
					checkRules.move();
				} catch (InterruptedException e) {
					System.out.println("concurrency problem, aborting...");
					System.exit(0);
				}
			}

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					board.drawAllCells();
				}
			});

			victory = board.isVictorious();
		}
		else if (gameMode.equals("Alpha-Beta vs MCTS")) {
			currentPlayer = 1;
			while (!victory) {
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
			}

			victory = board.isVictorious();
		}
		if (victory) {
			if (currentPlayer == 1) {
				System.out.println("MCTS won the game!");
			}
			else {
				System.out.println("Minimax won the game");
			}
			System.exit(0);
		}
	}

	public static String getGameMode() {
		return gameMode;
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