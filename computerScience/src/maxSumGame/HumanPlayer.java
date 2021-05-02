package maxSumGame;

import java.util.Scanner;

public class HumanPlayer extends Player {

	private static Scanner sc = new Scanner(System.in);

	public HumanPlayer(GameBoard board) {
		super(board);
	}

	// Performs the turn according to the user choice
	public void turn(int left, int right) {
		Direction bestChoice = optimalChoice(left, right);
		int userChoice = -1;
		int newScore = 0;

		if (left == right) { // A situation where only one number remains in the game
			System.out.println("Your best choise is selected automaticaly (only one number left in the array)");
			newScore = this.score + board.getCards()[right];
		} else {
			if (bestChoice == Direction.RIGHT) { // Shows the player the optimal choice
				System.out.println("Your best choice is: " + right);
			} else {
				System.out.println("Your best choice is: " + left);
			}

			do { // Receives from the player his choice (verifies input correctness)
				System.out.print("Please enter your choice: (" + left + " or " + right + "):");
				userChoice = sc.nextInt();
			} while (userChoice != right && userChoice != left);

			if (userChoice == right) { 
				newScore = this.score + board.getCards()[right];
			} else {
				newScore = this.score + board.getCards()[left];
			}
		}
		setScore(newScore); // Update the player's score according to his choice
		Direction select = (userChoice == right) ? select = Direction.RIGHT : Direction.LEFT;
		board.setBoardAfterTurn(select); // Updating the status of the board after the turn
	}

}
