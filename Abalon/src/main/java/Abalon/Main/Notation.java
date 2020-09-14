package Abalon.Main;

import javafx.util.Pair;

public class Notation {
	private char row;
	private int column;

	private static int[] columnsLeftBoundary  = new int[]{1, 1, 1, 1, 1, 2, 3, 4, 5};
	private static int[] columnsRightBoundary = new int[]{5, 6, 7, 8, 9, 9, 9, 9, 9};
	private static int numberOfColumns = 9;
	private static int numberOfRows = 9;

	public Notation(char row, int column) throws IllegalArgumentException {
		if (isValid(row, column)) {
			this.row = row;
			this.column = column;
		} else
			throw new IllegalArgumentException();
	}
	
	public boolean move(MoveDirection dir) throws IllegalArgumentException {
		switch (dir) {
			case TOP_LEFT:     row++;           break;
			case LEFT:                column--; break;
			case BOTTOM_LEFT:  row--; column--; break;
			case TOP_RIGHT:    row++; column++; break;
			case RIGHT:               column++; break;
			case BOTTOM_RIGHT: row--;           break;
			default: throw new IllegalArgumentException();
		}
		if (isValid(row, column))
			return false;
		return true;
	}

	public Pair<Character, Integer> getPair() {
		return new Pair<Character, Integer>(row, column);
	}

	public static boolean isValid(char _row, int _column) {	
		int id = (int)(_row - 'A');
		if (id < 0 || id >= numberOfRows)
			return false;
		if (_column < columnsLeftBoundary[id] || columnsRightBoundary[id] < _column)
			return false;
		return true;
	}

	
}