package Abalon.Main;

import Abalon.UI.BoardUI;
import java.awt.EventQueue;
import javafx.application.Platform;

/**
 * Abalon class that serves to run a new game
 */
public class Abalon {

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
	private int victory = 0;

	/** 
	 * Returns a new object of class Abalon
	 * @param p1 class that serves to perform the move of a real person OR an AI
	 * @param p2 class that serves to perform the move of a real person OR an AI
	 */
	public Abalon(BoardUI board, Player p1, Player p2) {
		this.board = board;
		player[0] = p1;
		player[1] = p2;
	
		player[0].setBoard(board);
		player[1].setBoard(board);
		player[0].setTurn(1);
		player[1].setTurn(2);
	}

	/**
	 * Runs a new game
	 * All moves are performed consecutively in EventQueue
	 */
	public void runGame() {
		//playMove(false);

		System.out.println("kek");

		for (int i = 0; i < 100; i++) {
			Move mv = player[i & 1].collectMove();
			mv.board = board.getBoard();
			Rules checkRules = new Rules(mv);
			checkRules.move();

			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					board.drawAllCells();
				}
			});
		}
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