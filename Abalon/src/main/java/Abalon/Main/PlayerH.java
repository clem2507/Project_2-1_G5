package Abalon.Main;

import Abalon.UI.BoardUI;

import java.util.Scanner;

public class PlayerH implements Player {
	private BoardUI board;
	public int turn;

	public PlayerH() {
		
	}

	@Override
	public void setTurn(int turn) {
		this.turn = turn;
	}

	@Override
	public void setBoard(BoardUI board) {
		this.board = board;
	}

	@Override
	public Move collectMove() {
		Scanner scr = new Scanner(System.in);
		int numb = scr.nextInt();
		int[][] a = new int[numb][2];

		for (int i = 0; i < numb; i++) {
			int x = scr.nextInt();
			int y = scr.nextInt();
			a[i][0] = x;
			a[i][1] = y;
		}

		Move ans = new Move();
		ans.pushing = a;
		ans.turn = turn;

		int dir = scr.nextInt();
		switch (dir) {
			case 1: ans.dir = MoveDirection.TOP_LEFT; break;
			case 2: ans.dir = MoveDirection.LEFT; break;
			case 3: ans.dir = MoveDirection.BOTTOM_LEFT; break;
			case 4: ans.dir = MoveDirection.TOP_RIGHT; break;
			case 5: ans.dir = MoveDirection.RIGHT; break;
			case 6: ans.dir = MoveDirection.BOTTOM_RIGHT; break;
		}

		return ans;
	}

	public static void main(String[] args) {
		PlayerH obj = new PlayerH();
		Move mv = obj.collectMove();
		System.out.println(mv);
	}
}