// Pinhas Ziv 315709139, Alex Chen 312286545
package maxSumGame;

public class MaxSumGame {

	public static void main(String[] args) {

		GameBoard board = new GameBoard();
		HumanPlayer humPlayer = new HumanPlayer(board);
		ComputerPlayer comPlayer = new ComputerPlayer(board);
		Player[] players = { humPlayer, comPlayer };
		int playerIndex = 0;

		do {
			System.out.println("The game array is: ");
			board.printBoard();
			System.out.println();
			
			players[playerIndex].turn(board.getLeftFlag(), board.getRightFlag());
			playerIndex = (playerIndex + 1) % players.length; // choose the next player to play.
		
		} while (board.getSize() > 0); // stop while loop when board is empty.

		int diff = players[0].getScore() - players[1].getScore(); // Calculating the difference between the players scores

		if (diff > 0) {
			System.out.println("YOU WIN :) , The score is - " + players[0].getScore() + " : " + players[1].getScore()
					+ ", You win with: " + diff);
		} else if (diff < 0) {
			System.out.println("YOU LOSE :( , The score is - " + players[0].getScore() + " : " + players[1].getScore()
					+ ", You lose with: " + diff);
		} else {
			System.out.println("DRAW!!! :| , The score is - " + players[0].getScore() + " : " + players[1].getScore());
		}
	}

}
