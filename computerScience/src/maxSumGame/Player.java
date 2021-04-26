package maxSumGame;

public abstract class Player {

	protected int score = 0;
	protected GameBoard board;

	public enum Direction{RIGHT, LEFT}
	
	public Player(GameBoard board) {
		this.board = board;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int newScore) {
		this.score = newScore;
	}

	public abstract void turn(int left, int right);
	
	public Direction optimalChoice(int left, int right) {

//		int x = ((left + 2) <= right) ? board.getOptionTable()[left + 2][right] : 0; // x gets max value from left+2 to right.
		int y = ((left + 1) <= (right - 1)) ? board.getOptionTable()[left + 1][right - 1] : 0; // x gets max value from left+1 to right-1.
		int z = (left <= (right - 2)) ? board.getOptionTable()[left][right - 2] : 0; // x gets max value from left to right-2.

//		int maxLeft = board.getCards()[left] + Math.min(x, y); // gets the max value if player chose the left card.
		int maxRight = board.getCards()[right] + Math.min(y, z); // gets the max value if player chose the right card.
		
		if(board.getOptionTable()[left][right] == maxRight) {
			return Direction.RIGHT;
		} else {
			return Direction.LEFT;
		}

	}
}