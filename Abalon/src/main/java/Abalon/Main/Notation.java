package Abalon.Main;

import Abalon.Main.MoveDirection;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;

import javafx.util.Pair;

/** 
 * Notation class representing notation for marbles positions
 */
public class Notation {
	private char row;
	private int column;
	private int absPositionX, absPositionY;

	private static int[] columnsLeftBoundary  = new int[]{1, 1, 1, 1, 1, 2, 3, 4, 5};
	private static int[] columnsRightBoundary = new int[]{5, 6, 7, 8, 9, 9, 9, 9, 9};
	private static int numberOfColumns = 9;
	private static int numberOfRows = 9;

	private int[][][] absoluteCoordinates = new int[9][9][2];

	/**
	 * Returns a new object of class Notation
	 * @param row first symbol in notation
	 * @param column second symbol in notation
	 * @throws IllegalArgumentException throws an exception if the passed notation is not valid 
	 */
	public Notation(char row, int column) throws IllegalArgumentException {
		if (isValid(row, column)) {
			this.row = row;
			this.column = column;
			
			try {		
				readAbsoluteCoordinates();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("File with coordinates not found");
				System.exit(0);
			}

			updateAbsolutePosition();
		} else
			throw new IllegalArgumentException();
	}
	
	/** 
	 * Copy constructor
	 * @param other object of class Notation to be copied 
	 */
	public Notation(Notation other) {
		row = other.row;
		column = other.column;
		updateAbsolutePosition();
	}

	private void readAbsoluteCoordinates() throws IOException, FileNotFoundException {
		File file = new File("./res/marbles_board_coordinates.txt");
		BufferedReader inp = new BufferedReader(new FileReader(file));
		String cur = "";
		int counter = 0;
		while ((cur = inp.readLine()) != null) {
			cur.replace("[", " ").replace("]", " ");
			String[] substrings = cur.split("\\s+");
			
			int n = substrings.length;
			for (int i = 0; i < n; i += 2) {
				absoluteCoordinates[counter][i / 2][0] = Integer.parseInt(substrings[i]);
				absoluteCoordinates[counter][i / 2][1] = Integer.parseInt(substrings[i + 1]);
			}
			counter++;
		}
	}

	/**
	 * Shift current notation by 1 in one of 6 directions (see MoveDirection)
	 * @param dir variable of type MoveDirection
	 * @param IllegalArgumentException throws an exception if the passed direction is not valid 
	 * @return true if the respective marble is still within the board, false otherwise
	 */
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
		if (!isValid(row, column))
			return false;
		updateAbsolutePosition();
		return true;
	}

	/**
	 * Return current notation in a form of Pair<Character, Integer> 
	 * @return current notation 
	 */
	public Pair<Character, Integer> getPair() {
		return new Pair<Character, Integer>(row, column);
	}

	/**
	 * Checks whether the passed notation is valid
	 * @param _row first symbol of notation
	 * @param _column second symbol of notation
	 * @return true is notation is valid, false otherwise
	 */
	public static boolean isValid(char _row, int _column) {	
		int id = (int)(_row - 'A');
		if (id < 0 || id >= numberOfRows)
			return false;
		if (_column < columnsLeftBoundary[id] || columnsRightBoundary[id] < _column)
			return false;
		return true;
	}

	private void updateAbsolutePosition() {
		int id1 = (int)(row - 'A');
		int id2 = column - columnsLeftBoundary[id1];
		absPositionX = absoluteCoordinates[id1][id2][0];
		absPositionY = absoluteCoordinates[id1][id2][1];
	}

	/** 
     * Sets the absolute position of the marble center on the stage
     * @param absPositionX X
     * @param absPositionY Y
     */
    public void setAbsPosition (int absPositionX, int absPositionY) {
        this.absPositionX = absPositionX;
        this.absPositionY = absPositionY;
    }

    /** 
     * Returns absolute X of the marble center on the stage
     * @return X coordinate
     */
    public int getX() {
        return absPositionX;
    }

    /** 
     * Returns absolute Y of the marble center on the stage
     * @return Y coordinate
     */
    public int getY() {
        return absPositionY;
    }

	@Override 
	/**
	 * Returns current notation in a form of string
	 * @return current notation 
	 */
	public String toString() {
		return "" + row + column;
	}
}