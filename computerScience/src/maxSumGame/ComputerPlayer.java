package maxSumGame;

public class ComputerPlayer extends Player {

	public ComputerPlayer(GameBoard board) {
		super(board);
		
	}

	
	public void turn(int left, int right) {
		boolean choice = optimalChoice(left, right);
		int newScore = 0;
		if(choice) {
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
