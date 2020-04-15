import java.util.Comparator;

/**
 * 
 */

/**
 * @author dparekh
 *
 */
public class BoardComparator implements Comparator<Board> {

	@Override
	public int compare(Board o1, Board o2) {
		return o1.getScore() - o2.getScore();
	}
}
