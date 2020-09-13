package Abalon;

public class Notation {
	private char raw;
	private int column;

	public Notation(char raw, int column) {

	}

	public Pair<Char, Integer> getPair(char raw, int column) {
		return new Pair<Char, Integer>(raw, column);
	}

	public static boolean isValid(char raw, int column) {
		
	}
}