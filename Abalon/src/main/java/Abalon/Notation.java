package Abalon;

import javafx.util.Pair;

public class Notation {
	private char raw;
	private int column;


	private static int[] columns_boundaries = new int[]{0, 5, 6, 7, 8, 9, 8, 7, 6, 5};

	public Notation(char raw, int column) throws IllegalArgumentException {
		if (isValid(raw, column)) {
			this.raw = raw;
			this.column = column;
		} else
			throw new IllegalArgumentException();
	}

	public boolean move(MoveDirection dir) {
		switch (dir) {
			case TOP_LEFT: raw++;
						   if ()
		}
	}

	public Pair<Character, Integer> getPair() {
		return new Pair<Character, Integer>(raw, column);
	}

	public static boolean isValid(char _raw, int _column) {	
		int id = (int)(_raw - 'A');
		if (id < 0 || id >= columns_boundaries.length)
			return false;
		if (_column <= 0 || columns_boundaries[id] < _column)
			return false;
		return true;
	}
}