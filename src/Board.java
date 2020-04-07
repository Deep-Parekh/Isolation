import java.util.Arrays;

/**
 * 
 */

/**
 * @author dparekh
 *
 */
public class Board {
	
	public static final int SIZE = 8;
	public static final char EMPTY = '-';
	public static final char USED = '#';
	public static final char X = 'X';
	public static final char O = 'O';
	
	
	private char[][] state;
	private Coordinate computer;
	private Coordinate opponent;
	
	public Board() {
		state = new char[SIZE][SIZE];
	}
	
	public Board(Board board) {
		this.state = copyState(board.state);
		this.computer = board.computer;
		this.opponent = board.opponent;
	}
	
	private char[][] copyState(char[][] state) {
		char[][] newState = new char[SIZE][SIZE];
		for(int i = 0; i < SIZE; ++i)
			newState[i] = Arrays.copyOf(state[i], SIZE);
		return newState;
	}

	public void move(Player player, Coordinate move) throws InvalidMoveException{
		if(move.isInvalid() || getCoordinate(move) != EMPTY || validMovement(move, player))
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
	
	public boolean validMovement(Coordinate move, Player player) {
		if(player.equals(Player.Computer)) 
			if(move.x != this.computer.x || move.y != this.computer.y || move.x + move.y != this.computer.x + this.computer.y|| move.x - move.y != this.computer.x - this.computer.y) 
				return false;
			else 
				if(move.x != this.opponent.x || move.y != this.opponent.y || move.x + move.y != this.opponent.x + this.opponent.y|| move.x - move.y != this.opponent.x - this.opponent.y) 
					return false;
		return true;
	}

	public char getCoordinate(Coordinate coordinate) {
		return this.state[coordinate.x][coordinate.y];
	}
	
	@Override
	public String toString() {
		String board = new String();
		System.out.println("   1   2   3   4   5   6   7   8   ");
		for(int i = 0; i < SIZE; ++i){
			board = board + "" + (char)('A' + i) + "  ";
			for(int j = 0; j < SIZE; ++j){
				board = board + this.state[i][j] + "  ";
			}
			board = board + "\n";
		}
		return board;
	}

	public boolean isTerminal() {
		// TODO Auto-generated method stub
		return false;
	}

	public Board getSuccessor() {
		// TODO Auto-generated method stub
		return null;
	}
}
