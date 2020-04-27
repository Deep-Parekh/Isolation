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
		Offensive heuristic = new Offensive();
		//Defensive heuristic = new Defensive();
		
		InitialMaxValue(this.gameBoard, Integer.MIN_VALUE, Integer.MAX_VALUE, heuristic);
	}
	
	private void InitialMaxValue(Board board, int alpha, int beta, Heuristic heuristic)
	{
		/*
		 * Similar to MaxValue except it is unique, used as the start of the search
		 * it is meant to update bestMove when a better move is found
		 */
		int value = Integer.MIN_VALUE;
		
		for (Board successor : board.getComputerSuccessors()) 
		{
			value = Math.max(value, MinValue(successor, alpha, beta, heuristic));
			if (value > alpha)
			{
				alpha = value;
				bestMove = successor.getComputerCoordinate();
			}
			
		}
	}
	
	private int MaxValue(Board board, int alpha, int beta, Heuristic heuristic)
	{
		int value = Integer.MIN_VALUE;
		
		if (board.isTerminal())
			return board.getScore();
		
		for (Board successor : board.getComputerSuccessors()) 
		{
			value = Math.max(value, MinValue(successor, alpha, beta, heuristic));
			if (value >= beta)
				return value;
			alpha = Math.max(alpha, value);
		}
		return value;
	}
	
	private int MinValue(Board board, int alpha, int beta, Heuristic heuristic)
	{
		int value = Integer.MAX_VALUE;
		
		if (board.isTerminal())
			return board.getScore();
		
		for (Board successor : board.getOpponentSuccessors())
		{
			value = Math.min(value, MaxValue(successor, alpha, beta, heuristic));
			if (value <= alpha)
				return value;
			beta = Math.min(beta, value);
		}
		return value;
	}
}
