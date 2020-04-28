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
		return board.getComputerSuccessors().size() - (2 * board.getOpponentSuccessors().size());
	}

}
