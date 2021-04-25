package maxSumGame;

public class MaxSumGame {

	public static void main(String[] args) {
		
		GameBoard board = new GameBoard();
		ComputerPlayer comPlayer = new ComputerPlayer(board);
		HumanPlayer humPlayer = new HumanPlayer(board);
	
		do {
			System.out.println("The game array is: ");
			board.printBoard();
			System.out.println();
			humPlayer.turn(board.getLeftFlag(), board.getRightFlag());
			
			if(board.getSize() == 0) {
				break;
			}
			
			System.out.println("The game array is: ");
			board.printBoard();
			System.out.println();
			comPlayer.turn(board.getLeftFlag(), board.getRightFlag());
		} while (board.getSize() > 0);
		
		int diff = humPlayer.getScore() - comPlayer.getScore();
		
		if(diff > 0) {
		System.out.println("YOU WIN :) , The score is - " + humPlayer.getScore() + " : " + comPlayer.getScore() + ", You win with: " + diff);
		} else if (diff < 0){
			System.out.println("YOU LOSE :( , The score is - " + humPlayer.getScore() + " : " + comPlayer.getScore() + ", You lose with: " + diff);
		} else {
			System.out.println("DRAW!!! :| , The score is - " + humPlayer.getScore() + " : " + comPlayer.getScore());
		}
	}

}
