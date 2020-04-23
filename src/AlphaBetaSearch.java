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
		 * better move is found using Alpha-Beta Search
		 */
	}
}
