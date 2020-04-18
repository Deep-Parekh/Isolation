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
	
	public AlphaBetaSearch(Board gameBoard) {
		this.gameBoard = gameBoard;
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
	}
}
