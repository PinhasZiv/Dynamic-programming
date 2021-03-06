// Pinhas Ziv 315709139, Alex Chen 312286545
package maxSumGame;

public class ComputerPlayer extends Player {

	public ComputerPlayer(GameBoard board) {
		super(board);
	}

	// Performs the turn according to the optimal choice
	public void turn(int left, int right) {
		Direction choice = optimalChoice(left, right);
		int newScore = 0;
		if(choice == Direction.RIGHT) {
			System.out.println("The computer choose: " + right);
			newScore = this.score + board.getCards()[right];
		} else {
			System.out.println("The computer choose: " + left);
			newScore = this.score + board.getCards()[left];
		}
		setScore(newScore);
		board.setBoardAfterTurn(choice);
	}

}
