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
	
	public Board(Player first) {
		state = new char[SIZE][SIZE];
		this.initializeBoard();
		if(first == Player.Opponent) {
			this.opponent = new Coordinate((byte)0, (byte)0);
			this.state[0][0] = Board.O;
			this.computer = new Coordinate((byte)7, (byte)7);
			this.state[7][7] = Board.X;
		}
		else {
			this.computer = new Coordinate((byte)0, (byte)0);
			this.state[0][0] = Board.X;
			this.opponent = new Coordinate((byte)7, (byte)7);
			this.state[7][7] = Board.O;
		}
	}
	
	public Board(Board board) {
		this.state = copyState(board.state);
		this.computer = board.computer;
		this.opponent = board.opponent;
	}
	
	public char[][] getState(){
		return this.state;
	}
	
	private void initializeBoard() {
		for(char[] c: this.state)
			Arrays.fill(c, EMPTY);
	}
	
	private char[][] copyState(char[][] state) {
		char[][] newState = new char[SIZE][SIZE];
		for(int i = 0; i < SIZE; ++i)
			newState[i] = Arrays.copyOf(state[i], SIZE);
		return newState;
	}

	public void move(Player player, Coordinate move) throws InvalidMoveException{
//		if(move.isInvalid() || getCoordinate(move) != EMPTY || !validMovement(move, player))
//			throw new InvalidMoveException("Your move is Invalid");
		if (player==Player.Computer) {
			this.state[this.computer.x][this.computer.y] = USED;
			this.computer = move;
			this.state[this.computer.x][this.computer.y] = X;
		}
		if (player==Player.Opponent) {
			this.state[this.opponent.x][this.opponent.y] = USED;
			this.opponent = move;
			this.state[this.opponent.x][this.opponent.y] = 'O';
		}
	}
	
	public boolean validMovement(Coordinate move, Player player) {
		if(player == Player.Computer) 
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
	
	public boolean isTerminal() {
		byte x = this.computer.x;
		byte y = this.computer.y;
		// check if its immediate neighbors are Empty, if they are all not Empty, this is a terminal state
		x = this.opponent.x;
		y = this.opponent.y;
		return false;
	}

	public Board getSuccessor() {
		// TODO Auto-generated method stub
		return null;
	}
}
