package Abalone.Main;

import Abalone.UI.BoardUI;

import java.lang.InterruptedException;

/** 
 * Player interface to be implemented by PlayerH and PlayerAI(TODO) that will be responsible for move performance: 
 * collecting input, checking validity and calling the shift in both backend and frontend
 */
public interface Player {
	Move collectMove() throws InterruptedException;
	void setTurn(int turn);
	void setBoard(BoardUI board);
}