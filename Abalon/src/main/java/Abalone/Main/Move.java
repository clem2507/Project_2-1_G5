package Abalone.Main;

import java.util.ArrayList;

public class Move {
	public MoveDirection dir;
	public int[][] pushing;
	public int[][] board;
	public int turn;

	@Override
	public String toString() {
		ArrayList<ArrayList<Integer> > intList = new ArrayList<ArrayList<Integer> >();
		for (int i = 0; i < pushing.length; i++) {
			intList.add(new ArrayList<>());
			for (int x : pushing[i]) {
				intList.get(i).add(x);
			}
		}

		return "[" + dir.name() + " " + intList.toString() + " " + turn + "]";
	}
}