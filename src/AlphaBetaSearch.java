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
	private int timeLimit;
	private long startTime;
	
	public AlphaBetaSearch(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public Coordinate getBestMove() {
		return this.bestMove;
	}
	
	public void Search(Board gameBoard)
	{
		this.gameBoard = gameBoard;
		IterativeDeepening(gameBoard, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	private void IterativeDeepening(Board board, int alpha, int beta)
	{
		/*
		 * Similar to MaxValue except it is unique, used as the start of the search
		 * it is meant to update bestMove when a better move is found
		 * it is the implementation of Iterative Deepening
		 */
		this.startTime = System.currentTimeMillis();
		int depth = 1;
		int value = Integer.MIN_VALUE;
		
		while (true)
		{
			for (Coordinate nextMove : board.getComputerSuccessors()) 
			{
				board.move(Player.Computer, nextMove);
				value = Math.max(value, MinValue(Player.Opponent, alpha, beta, depth-1));
				if (value > alpha)
				{
					alpha = value;
					bestMove = nextMove;
				}
				board.undoMove(Player.Computer);
			}
			System.out.println("Depth " + depth + " took " + (System.currentTimeMillis()-startTime) + " milliseconds");
			depth++;
			if(depth > 5) // System.currentTimeMillis() - startTime > (this.timeLimit * 1000)
				break;
		}
	}
	
	private int MaxValue(Player maxPlayer, int alpha, int beta, int depth)
	{
		if(System.currentTimeMillis() - startTime > (this.timeLimit * 1000)) {/* Placeholder */}
			// return with the best move or throw exception
		
		int value = Integer.MIN_VALUE;
		
		if (gameBoard.isTerminal() || depth == 0) {
			//gameBoard.undoMove(maxPlayer);
			return gameBoard.getScore();
		}
		
		Player nextPlayer;
		if(maxPlayer == Player.Opponent)
			nextPlayer = Player.Computer;
		else
			nextPlayer = Player.Opponent;
		
		for (Coordinate nextMove : gameBoard.getComputerSuccessors()) 
		{
			gameBoard.move(maxPlayer, nextMove);
			value = Math.max(value, MinValue(nextPlayer, alpha, beta, depth-1));
			if (value >= beta) {
				gameBoard.undoMove(maxPlayer);
				break;
			}
			alpha = Math.max(alpha, value);
			gameBoard.undoMove(maxPlayer);
		}
		return value;
	}
	
	private int MinValue(Player minPlayer, int alpha, int beta, int depth)
	{
		if(System.currentTimeMillis() - startTime > (this.timeLimit * 1000)) {/* Placeholder */}
		// return with the best move or throw exception
		
		int value = Integer.MAX_VALUE;
		
		if (gameBoard.isTerminal() || depth == 0) {
			//gameBoard.undoMove(minPlayer);
			return gameBoard.getScore();
		}
		
		Player nextPlayer;
		if(minPlayer == Player.Opponent)
			nextPlayer = Player.Computer;
		else
			nextPlayer = Player.Opponent;
		
		for (Coordinate nextMove : gameBoard.getOpponentSuccessors())
		{
			gameBoard.move(minPlayer, nextMove);
			value = Math.min(value, MaxValue(nextPlayer, alpha, beta, depth-1));
			if (value <= alpha) {
				gameBoard.undoMove(minPlayer);
				break;
			}
			beta = Math.min(beta, value);
			gameBoard.undoMove(minPlayer);
		}
		return value;
	}
}

