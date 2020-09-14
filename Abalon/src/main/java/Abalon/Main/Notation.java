package Abalon.Main;

import javafx.util.Pair;

public class Notation {
	private char raw;
	private int column;

	private static int[] columnsLeftBoundary  = new int[]{1, 1, 1, 1, 1, 2, 3, 4, 5};
	private static int[] columnsRightBoundary = new int[]{5, 6, 7, 8, 9, 9, 9, 9, 9};
	private static int numberOfColumns = 10;

	public Notation(char raw, int column) throws IllegalArgumentException {
		if (isValid(raw, column)) {
			this.raw = raw;
			this.column = column;
		} else
			throw new IllegalArgumentException();
	}
	
	public boolean move(MoveDirection dir) {
		/*switch (dir) {
			case TOP_LEFT: raw++;
			               if (raw > 
						   
		}
		return true;*/
	}

	public Pair<Character, Integer> getPair() {
		return new Pair<Character, Integer>(raw, column);
	}

	public static boolean isValid(char _raw, int _column) {	
		int id = (int)(_raw - 'A');
		if (id < 0 || id >= columnsLeftBoundary.length)
			return false;
		if (_column < columnsLeftBoundary[id] || columnsRightBoundary[id] < _column)
			return false;
		return true;
	}
}