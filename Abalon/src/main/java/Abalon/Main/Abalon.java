package Abalon.Main;

import Abalon.Main.Board;
import java.awt.EventQueue;

/**
 * Abalon class that serves to run a new game
 */
public class Abalon {

	/**
	 * Current way to run the project, to be replaced 
	 */
	public static void main(String[] args) {
		Player p1 = new PlayerH();
		Player p2 = new PlayerH();
		Abalon obj = new Abalon(p1, p2);
	}

	private Board board;
	private Player[] player = new Player[2]; 
	private int victory = 0;

	/** 
	 * Returns a new object of class Abalon
	 * @param p1 class that serves to perform the move of a real person OR an AI
	 * @param p2 class that serves to perform the move of a real person OR an AI
	 */
	public Abalon(Player p1, Player p2) {
		board = new Board();
		player[0] = p1;
		player[1] = p2;
	
		player[0].setBoard(board);
		player[1].setBoard(board);
	}

	/**
	 * Runs a new game
	 * All moves are performed consecutively in EventQueue
	 */
	public void runGame() {
		playMove(false);
	}

	private void playMove(boolean flag) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				int id = (flag ? 1 : 0);
				player[id].performMove();
				if (victory == 0) 
					playMove(flag ^ false);
			}
		});
	}
}