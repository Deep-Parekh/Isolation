import java.util.HashSet;

/**
 * 
 */

/**
 * @author dparekh
 *
 */
public class AlphaBetaSearch {
	
	private Coordinate bestMove;
	private int timeLimit;
	private long startTime;
	
	public AlphaBetaSearch(int timeLimit) {
		this.timeLimit = timeLimit;
	}
	
	public Coordinate getBestMove() {
		return this.bestMove;
	}
	
	public void search(Player maxPlayer, Board gameBoard)
	{
		iterativeDeepening(maxPlayer, gameBoard, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	private void iterativeDeepening(Player maxPlayer, Board board, int alpha, int beta)
	{
		/*
		 * Similar to MaxValue except it is unique, used as the start of the search
		 * it is meant to update bestMove when a better move is found
		 * it is the implementation of Iterative Deepening
		 */
		this.startTime = System.currentTimeMillis();
		int depth = 6;
		int totalMoves = board.getUsedCoordinates();
		
		int value = Integer.MIN_VALUE;
		int depthLimit = 64 - totalMoves;
		
		Player nextPlayer;
		HashSet<Coordinate> nextMoves;
		if(maxPlayer == Player.Opponent) {
			nextPlayer = Player.Computer;
			nextMoves = board.getOpponentSuccessors();
		}
		else {
			nextPlayer = Player.Opponent;
			nextMoves = board.getComputerSuccessors();
		}
		
		try {
			while (true)
			{
				for (Coordinate nextMove : nextMoves) 
				{
					if(System.currentTimeMillis() - startTime > (this.timeLimit * 1000)) 
						throw new InterruptedException();
					board.move(maxPlayer, nextMove);
					value = Math.max(value, minValue(nextPlayer, board, alpha, beta, depth-1));
					if (value > alpha)
					{
						alpha = value;
						bestMove = nextMove;
					}
					board.undoMove();
				}
				depth++;
				if(depth > depthLimit) 	// depth > depthLimit
					break;
			}
		} catch (InterruptedException e) {
			while(board.getUsedCoordinates() > totalMoves)
				board.undoMove();
			return;
		}
	}
	
	private int maxValue(Player maxPlayer, Board board, int alpha, int beta, int depth) throws InterruptedException
	{
		if(System.currentTimeMillis() - startTime > (this.timeLimit * 1000)) 
			throw new InterruptedException();
		
		int value = Integer.MIN_VALUE;
		
		if (board.isTerminal())
			return board.getScore()*2;
		if (depth == 0) 
			return board.getScore();
		
		Player nextPlayer;
		HashSet<Coordinate> nextMoves;
		if(maxPlayer == Player.Opponent) {
			nextPlayer = Player.Computer;
			nextMoves = board.getOpponentSuccessors();
		}
		else {
			nextPlayer = Player.Opponent;
			nextMoves = board.getComputerSuccessors();
		}
		
		for (Coordinate nextMove : nextMoves) 
		{
			board.move(maxPlayer, nextMove);
			value = Math.max(value, minValue(nextPlayer, board, alpha, beta, depth-1));
			if (value >= beta) {
				board.undoMove();
				break;
			}
			alpha = Math.max(alpha, value);
			board.undoMove();
		}
		return value;
	}
	
	private int minValue(Player minPlayer, Board board, int alpha, int beta, int depth) throws InterruptedException
	{
		if(System.currentTimeMillis() - startTime > (this.timeLimit * 1000))
			throw new InterruptedException();
		
		int value = Integer.MAX_VALUE;
		
		if (board.isTerminal())
			return board.getScore()*2;
		if (depth == 0) 
			return board.getScore();
		
		Player nextPlayer;
		HashSet<Coordinate> nextMoves;
		if(minPlayer == Player.Opponent) {
			nextPlayer = Player.Computer;
			nextMoves = board.getOpponentSuccessors();
		}
		else {
			nextPlayer = Player.Opponent;
			nextMoves = board.getComputerSuccessors();
		}
		
		for (Coordinate nextMove : nextMoves)
		{
			board.move(minPlayer, nextMove);
			value = Math.min(value, maxValue(nextPlayer, board, alpha, beta, depth-1));
			if (value <= alpha) {
				board.undoMove();
				break;
			}
			beta = Math.min(beta, value);
			board.undoMove();
		}
		return value;
	}
}