/**
 * 
 */

/**
 * @author dparekh
 *
 */
public class Defensive implements Heuristic {

	/*
	 * Favors moves that maximizes the number of moves the agent can make rather than minimizing computer's moves
	 */
	@Override
	public int getScore(Board board) {
		return (board.computerSuccessors.size() * 2) - board.opponentSuccessors.size();
	}

}
