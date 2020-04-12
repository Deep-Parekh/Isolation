import java.util.Arrays;
import java.util.HashSet;

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
	private int score;		
	
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
		this.generateScore();
	}
	
	public Board(Board board, Coordinate computer) {
		this.state = copyState(board.state);
		this.computer = computer;
		this.opponent = board.opponent;
		this.move(Player.Computer, computer);
		this.generateScore();
	}
	
	public char[][] getState(){
		return this.state;
	}
	
	public int getScore() {
		return this.score;
	}
	
	/*
	 * Utility function that generates the score (value) of the current board
	 */
	private void generateScore() {
		// TODO
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

	public void move(Player player, Coordinate move){
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
			this.state[this.opponent.x][this.opponent.y] = O;
		}
		generateScore();
	}
	
	public char getCoordinate(Coordinate coordinate) {
		return this.state[coordinate.x][coordinate.y];
	}
	
	public boolean isTerminal() {
		if(this.getSuccessors(Player.Computer).size() == 0 || this.getSuccessors(Player.Opponent).size() == 0)
			return true;
		return false;
	}
	
	/*
	 * Gets the immediate neighbors of the specified player, used to check if a state is a terminal state
	 */
	public HashSet<Board> getSuccessors(Player player){
		HashSet<Board> neighbors = new HashSet<Board>();
		byte x, y = 0;
		if(player.equals(Player.Computer)) {
			x = this.computer.x;
			y = this.computer.y;
		}
		else {
			x = this.opponent.x;
			y = this.opponent.y;
		}
		
		for(int i  = 1; ; ++i) {
			if(x + i < SIZE && this.state[x+i][y] != USED) {	// Up
				Board neighbor = new Board(this, new Coordinate((byte)(x + i), y));
				neighbors.add(neighbor);
			}
			else 
				break;
		}

		for(int i = 1; ; ++i) {
			if(x - i >= 0 && this.state[x-i][y] != USED) {		// Down
				Board neighbor = new Board(this, new Coordinate((byte)(x - i), y));
				neighbors.add(neighbor);
			}
			else
				break;
		}
		
		for(int i = 1; ; ++i) {
			if(y + i < SIZE && this.state[x][y+i] != USED) {	// Right
				Board neighbor = new Board(this, new Coordinate(x, (byte)(y + i)));
				neighbors.add(neighbor);
			}
			else 
				break;
		}
		
		for(int i = 1; ; ++i) {
			if(y - i >= 0 && this.state[x][y-i] != USED) {	// Left
				Board neighbor = new Board(this, new Coordinate(x, (byte)(y - i)));
				neighbors.add(neighbor);
			}
			else 
				break;
		}
		
		for(int i = 1; ;++i) {
			if(x + i < SIZE && y + i < SIZE && this.state[x+i][y+i] != USED) {	// RightUp
				Board neighbor = new Board(this, new Coordinate((byte)(x + i), (byte)(y + i)));
				neighbors.add(neighbor);
			}
			else
				break;
		}
		
		for(int i = 1; ; ++i) {
			if(x - i >= 0 && y + i < SIZE && this.state[x-i][y+i] != USED) {		// RightDown
				Board neighbor = new Board(this, new Coordinate((byte)(x - i), (byte)(y + i)));
				neighbors.add(neighbor);
			}
			else 
				break;
		}
		
		for(int i = 1; ; ++i) {
			if(x + i < SIZE && y - i >= 0 && this.state[x+i][y-i] != USED) {	// LeftUp
				Board neighbor = new Board(this, new Coordinate((byte)(x + i), (byte)(y - i)));
				neighbors.add(neighbor);
			}
			else 
				break;
		}
		
		for(int i = 1; ; ++i) {
			if(x - i >= 0 && y - i >= 0 && this.state[x-i][y-i] != USED) {		// LeftDown
				Board neighbor = new Board(this, new Coordinate((byte)(x - i), (byte)(y - i)));
				neighbors.add(neighbor);
			}
			else 
				break;
		}
		
		return neighbors;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(!obj.getClass().equals(this.getClass()))
			return false;
		Board other = (Board) obj;
		return Arrays.deepEquals(this.state, other.state);
	}
	
	@Override
	public int hashCode() {
		return Arrays.deepHashCode(state);
	}
}
