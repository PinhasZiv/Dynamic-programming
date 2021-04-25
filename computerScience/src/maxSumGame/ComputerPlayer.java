package maxSumGame;

public class ComputerPlayer extends Player {

	public ComputerPlayer(GameBoard board) {
		super(board);
		
	}

	
	public void turn(int left, int right) {
		direction choice = optimalChoice(left, right);
		int newScore = 0;
		if(choice == direction.RIGHT) {
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
