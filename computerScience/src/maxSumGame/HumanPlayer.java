package maxSumGame;

import java.util.Scanner;

public class HumanPlayer extends Player {

	private static Scanner sc = new Scanner(System.in);

	public HumanPlayer(GameBoard board) {
		super(board);
	}

	public void turn(int left, int right) {
		Direction bestChoice = optimalChoice(left, right);
		int userChoice = -1;
		int newScore = 0;

		if (left == right) {
			System.out.println("Your best choise is selected automaticaly (only one number left in the array)");
			newScore = this.score + board.getCards()[right];
		} else {

			if (bestChoice == Direction.RIGHT) {
				System.out.println("Your best choice is: " + right);
			} else {
				System.out.println("Your best choice is: " + left);
			}

			do {
				System.out.print("Please enter your choice: (" + left + " or " + right + "):");
				userChoice = sc.nextInt();
			} while (userChoice != right && userChoice != left);

			if (bestChoice == Direction.RIGHT) {
				newScore = this.score + board.getCards()[right];
			} else {
				newScore = this.score + board.getCards()[left];
			}
		}
		setScore(newScore);
		board.setBoardAfterTurn(bestChoice);
	}

}
