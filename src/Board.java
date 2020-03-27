import java.util.Arrays;

/**
 * 
 */

/**
 * @author dparekh
 *
 */
public class Board {
	
	private final int SIZE = 8;
	private final char EMPTY = '-';
	private final char USED = '#';
	private final char X = 'X';
	private final char O = 'O';
	
	
	private char[][] state;
	private Coordinate computer;
	private Coordinate opponent;
	
	public Board() {
		state = new char[SIZE][SIZE];
		state[0][0] = 'X';
		state[7][7] = 'O';
		this.computer = new Coordinate((byte) 0, (byte) 0);
		this.opponent = new Coordinate((byte) 7, (byte) 7);
	}
	
	public void move(Player player, Coordinate move) throws InvalidMoveException{
		if(move.isInvalid() || getCoordinate(move) != EMPTY)
			throw new InvalidMoveException("Your move is Invalid");
		if (player.equals(Player.Computer)) {
			this.state[this.computer.x][this.computer.y] = USED;
			this.computer = move;
			this.state[this.computer.x][this.computer.y] = X;
		}
		if (player.equals(Player.Opponent)) {
			this.state[this.opponent.x][this.opponent.y] = USED;
			this.opponent = move;
			this.state[this.opponent.x][this.opponent.y] = 'O';
		}
	}
	
	public char getCoordinate(Coordinate coordinate) {
		return this.state[coordinate.x][coordinate.y];
	}
	
	@Override
	public String toString() {
		String board = Arrays.deepToString(this.state) + "\n";
		return board;
	}
}
