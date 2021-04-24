package maxSumGame;

import java.awt.print.Printable;
import java.util.Random;

public class GameBoard {

	private Random rnd = new Random();
	private int[] cards = new int[7];
	private int size;
	private int leftFlag; // sign for border of the last left card
	private int rightFlag; // sign for border of the last right card
	private int[][] optionTable;

	public GameBoard() {
		resetBoard();
		size = 7;
		leftFlag = 0;
		rightFlag = 6;
		this.optionTable = makeTable(cards, size);
	}

	public void resetBoard() {
		for (int i = 0; i < cards.length; i++) {
			cards[i] = rnd.nextInt(3) + 1;
		}
	}

	public int getSize() {
		return size;
	}

	public void printBoard() {
		for (int i = leftFlag; i <= rightFlag; i++) {
			System.out.print(cards[i] + " ");
		}
		System.out.println("");
	}

	public int[] getCards() {
		return cards;
	}

	public int getLeftFlag() {
		return leftFlag;
	}

	public void setLeftFlag(int leftFlag) {
		this.leftFlag = leftFlag;
	}

	public int getRightFlag() {
		return rightFlag;
	}

	public void setRightFlag(int rightFlag) {
		this.rightFlag = rightFlag;
	}

	public int[][] getOptionTable() {
		return optionTable;
	}

	// boolean for: true=player chose right, false=player chose left
	public void setBoardAfterTurn(boolean select) {
		if (select) {
			rightFlag--;
		} else {
			leftFlag++;
		}
		size--;
	}

	private int[][] makeTable(int arr[], int size) {

		int table[][] = new int[size][size];
		int row, left, right, x, y, z;

		for (row = 0; row < size; ++row) {
			for (left = 0, right = row; right < size; ++left, ++right) {

				x = ((left + 2) <= right) ? table[left + 2][right] : 0;
				y = ((left + 1) <= (right - 1)) ? table[left + 1][right - 1] : 0;
				z = (left <= (right - 2)) ? table[left][right - 2] : 0;

				table[left][right] = Math.max(arr[left] + Math.min(x, y), arr[right] + Math.min(y, z));

			}
		}

		return table;
	}
}
