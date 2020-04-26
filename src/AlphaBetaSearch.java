/**
 * 
 */

/**
 * @author dparekh
 *
 */
public class AlphaBetaSearch extends Thread {
	
	private Coordinate bestMove;
	private Board gameBoard;
	private Player maxPlayer;
	
	public AlphaBetaSearch(Board gameBoard, Player maxPlayer) {
		this.gameBoard = gameBoard;
		this.maxPlayer = maxPlayer;
	}
	
	public Coordinate getBestMove() {
		return this.bestMove;
	}
	
	@Override
	public void run() {
		/*
		 * Logic for finding the best move, update bestMove variable every time a 
		 * better move is foundAlpha-Beta Search
		 */
		MaxValue(this.gameBoard, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	private int MaxValue(Board board, int alpha, int beta)
	{
		int value = Integer.MIN_VALUE;
		
		if (board.isTerminal())
			return board.getScore();
		
		for (Board successor : board.computerSuccessors) 
		{
			value = Math.max(value, MinValue(successor, alpha, beta));
			if (value >= beta)
				return value;
			alpha = Math.max(alpha, value);
		}
		return value;
	}
	
	private int MinValue(Board board, int alpha, int beta)
	{
		int value = Integer.MAX_VALUE;
		
		if (board.isTerminal())
			return board.getScore();
		
		for (Board successor : board.opponentSuccessors)
		{
			value = Math.min(value, MaxValue(successor, alpha, beta));
			if (value <= alpha)
				return value;
			beta = Math.min(beta, value);
		}
		return value;
	}
}
