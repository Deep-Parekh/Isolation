/**
 * 
 */

/**
 * @author dparekh
 *
 */
public class Offensive implements Heuristic {

	/*
	 * Favors moves that minimize the number of moves the opponent can make rather than maximizing own moves
	 */
	@Override
	public int getScore(Board board) {
		return board.computerSuccessors.size() - (2 * board.opponentSuccessors.size());
	}

}
