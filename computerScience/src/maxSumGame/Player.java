package maxSumGame;

public abstract class Player {

	protected int score = 0;
	protected GameBoard board;

	public Player(GameBoard board) {
		this.board = board;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int newScore) {
		this.score = newScore;
	}

	public boolean optimalChoice(int left, int right) {

		int x = ((left + 2) <= right) ? board.getOptionTable()[left + 2][right] : 0;
		int y = ((left + 1) <= (right - 1)) ? board.getOptionTable()[left + 1][right - 1] : 0;
		int z = (left <= (right - 2)) ? board.getOptionTable()[left][right - 2] : 0;

		int maxLeft = board.getCards()[left] + Math.min(x, y);
		int maxRight = board.getCards()[right] + Math.min(y, z);
		
		if(board.getOptionTable()[left][right] == maxRight) {
			return true;
		} else {
			return false;
		}

	}
}