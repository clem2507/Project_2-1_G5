package Abalon.Main;

import Abalon.UI.BoardUI;

/** 
 * Player interface to be implemented by PlayerH and PlayerAI(TODO) that will be responsible for move performance: 
 * collecting input, checking validity and calling the shift in both backend and frontend
 */
public interface Player {
	Move collectMove();
	void setTurn(int turn);
	void setBoard(BoardUI board);
}