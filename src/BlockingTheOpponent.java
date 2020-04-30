import java.util.HashSet;

/**
 * 
 */

/**
 * @author dparekh
 *
 */
public class BlockingTheOpponent implements Heuristic {

	@Override
	public int getScore(Board board) {
		int sameMoves = 0; 
		HashSet<Coordinate> computerMoves = board.getComputerSuccessors();
		HashSet<Coordinate> opponentMoves = board.getOpponentSuccessors();
		for(Coordinate coord : computerMoves)
			if(opponentMoves.contains(coord))
				++sameMoves;
		return computerMoves.size() - (opponentMoves.size() * 2) + sameMoves;
	}

}
