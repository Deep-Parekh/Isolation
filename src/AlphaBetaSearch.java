/**
 * 
 */

/**
 * @author dparekh
 *
 */
public class AlphaBetaSearch {
	
	private Coordinate bestMove;
	private Board gameBoard;
	private Player maxPlayer;
	private int timeLimit;
	private long startTime;
	
	public AlphaBetaSearch(Board gameBoard, Player maxPlayer, int timeLimit) {
		this.gameBoard = gameBoard;
		this.maxPlayer = maxPlayer;
		this.timeLimit = timeLimit;
	}
	
	public Coordinate getBestMove() {
		return this.bestMove;
	}
	
	private void InitialMaxValue(Board board, int alpha, int beta, int depth)
	{
		/*
		 * Similar to MaxValue except it is unique, used as the start of the search
		 * it is meant to update bestMove when a better move is found
		 */
		startTime = System.currentTimeMillis();
		int value = Integer.MIN_VALUE;
		
		for (Coordinate nextMove : board.getComputerSuccessors()) 
		{
			value = Math.max(value, MinValue(nextMove,Player.Opponent, alpha, beta, depth-1));
			if (value > alpha)
			{
				alpha = value;
				//bestMove = successor.getComputerCoordinate();
			}
			
		}
	}
	
	private int MaxValue(Coordinate move, Player maxPlayer, int alpha, int beta, int depth)
	{
		if(System.currentTimeMillis() - startTime > (this.timeLimit * 1000)) {/* Placeholder */}
			// return with the best move or throw exception
		
		int value = Integer.MIN_VALUE;
		
		gameBoard.move(maxPlayer, move);
		
		if (gameBoard.isTerminal() || depth == 0)
			return gameBoard.getScore();
		
		Player nextPlayer;
		if(maxPlayer == Player.Opponent)
			nextPlayer = Player.Computer;
		else
			nextPlayer = Player.Opponent;
		
		for (Coordinate nextMove : gameBoard.getComputerSuccessors()) 
		{
			value = Math.max(value, MinValue(nextMove, nextPlayer, alpha, beta, depth-1));
			if (value >= beta)
				break;
			alpha = Math.max(alpha, value);
		}
		gameBoard.undoMove(maxPlayer);
		return value;
	}
	
	private int MinValue(Coordinate move, Player minPlayer, int alpha, int beta, int depth)
	{
		if(System.currentTimeMillis() - startTime > (this.timeLimit * 1000)) {/* Placeholder */}
		// return with the best move or throw exception
		
		int value = Integer.MAX_VALUE;
		
		gameBoard.move(minPlayer, move);
		
		if (gameBoard.isTerminal() || depth == 0)
			return gameBoard.getScore();
		
		Player nextPlayer;
		if(maxPlayer == Player.Opponent)
			nextPlayer = Player.Computer;
		else
			nextPlayer = Player.Opponent;
		
		for (Coordinate nextMove : gameBoard.getOpponentSuccessors())
		{
			value = Math.min(value, MaxValue(nextMove, nextPlayer, alpha, beta, depth-1));
			if (value <= alpha) 
				break;
			beta = Math.min(beta, value);
		}
		gameBoard.undoMove(minPlayer);
		return value;
	}
}
