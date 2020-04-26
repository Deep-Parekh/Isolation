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
	private HeuristicManager heuristicManager;
	private HashSet<Board> opponentSuccessors;
	private HashSet<Board> computerSuccessors;
	private boolean hasChanged;
	
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
		this.heuristicManager = new HeuristicManager("OffensiveToDefensive");
		this.hasChanged = true;
	}
	
	public Board(Board board, Coordinate computer) {
		this.state = copyState(board.state);
		this.computer = computer;
		this.opponent = board.opponent;
		this.heuristicManager = board.heuristicManager;
		this.move(Player.Computer, computer);
		this.hasChanged = true;
	}
	
	public char[][] getState(){
		return this.state;
	}
	
	public HashSet<Board> getOpponentSuccessors(){
		if(this.hasChanged)
			this.opponentSuccessors = this.getSuccessors(Player.Opponent);
		return this.opponentSuccessors;
	}
	
	public HashSet<Board> getComputerSuccessors(){
		if(this.hasChanged)
			this.computerSuccessors = this.getSuccessors(Player.Computer);
		return this.computerSuccessors;
	}
	
	public int getScore() {
		if(this.hasChanged) {
			this.getOpponentSuccessors();
			this.getComputerSuccessors();
			this.hasChanged = false;
			generateScore();
		}
		return this.score;
	}
	
	public void setScore(int score) {
		this.score = score;
	}
	
	/*
	 * Utility function that generates the score (value) of the current board
	 */
	private void generateScore() {
		Heuristic heuristic = heuristicManager.getHeuristic();
		this.setScore(heuristic.getScore(this));
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
		if (player==Player.Computer) {
			this.state[this.computer.x][this.computer.y] = USED;
			this.computer = move;
			this.state[this.computer.x][this.computer.y] = X;
		}else{
			this.state[this.opponent.x][this.opponent.y] = USED;
			this.opponent = move;
			this.state[this.opponent.x][this.opponent.y] = O;
		}
	}
	
	public char getCoordinate(Coordinate coordinate) {
		return this.state[coordinate.x][coordinate.y];
	}
	

	public boolean isInvalidMove(Coordinate move) {
		if(move.equals(this.opponent) || this.getCoordinate(move) != EMPTY) {
			move = null;
			return true;
		}
		
		// Horizontal
		if (move.x == this.opponent.x) {
			if(move.y > this.opponent.y) {
				for(int i = 1; i <= move.y -  this.opponent.y; ++i) {
					if(this.state[this.opponent.x][this.opponent.y+i] != EMPTY) {
						move = null;
						return true;
					}
				}
				return false;
			}else {
				for(int i = 1; i <= this.opponent.y - move.y; ++i) {
					if(this.state[this.opponent.x][this.opponent.y-i] != EMPTY) {
						move = null;
						return true;
					}
				}
				return false;
			}
		}
		
		// Vertical
		if (move.y == this.opponent.y) {
			if(move.x > this.opponent.x) {
				for(int i = 1; i <= move.x - this.opponent.x; ++i) {
					if(this.state[this.opponent.x+i][this.opponent.y] != EMPTY) {
						move = null;
						return true;
					}
				}
				return false;
			}else {
				for(int i = 1; i <= this.opponent.x - move.x; ++i) {
					if(this.state[this.opponent.x-i][this.opponent.y] != EMPTY) {
						move = null;
						return true;
					}
				}
				return false;
			}
		}
		
		// Positive slope
		if (Math.abs(move.x + move.y) == Math.abs(this.opponent.x + this.opponent.y)) {
			move = null;
			return false;
		}
		
		// Negative slope
		if (Math.abs(move.x - move.y) == Math.abs(this.opponent.x-this.opponent.y)) {
			move = null;
			return false;
		}
		return true;
	}
	
	public boolean isTerminal() {
		if(this.getComputerSuccessors().size() > 0 || this.getOpponentSuccessors().size() > 0)
			return false;
		return true;
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
		
		// Up
		for(int i  = 1; x + i < SIZE; ++i) {
			if(this.state[x+i][y] != USED) {	
				Board neighbor = new Board(this, new Coordinate((byte)(x + i), y));
				neighbors.add(neighbor);
			}
			else 
				break;
		}

		// Down
		for(int i = 1; x - i >= 0; ++i) {
			if(this.state[x-i][y] != USED) {		
				Board neighbor = new Board(this, new Coordinate((byte)(x - i), y));
				neighbors.add(neighbor);
			}
			else
				break;
		}
		
		// Right
		for(int i = 1; y + i < SIZE; ++i) {
			if(this.state[x][y+i] != USED) {	
				Board neighbor = new Board(this, new Coordinate(x, (byte)(y + i)));
				neighbors.add(neighbor);
			}
			else 
				break;
		}
		
		// Left
		for(int i = 1; y - i >= 0; ++i) {
			if(this.state[x][y-i] != USED) {	
				Board neighbor = new Board(this, new Coordinate(x, (byte)(y - i)));
				neighbors.add(neighbor);
			}
			else 
				break;
		}
		
		// RightUp
		for(int i = 1; x + i < SIZE && y + i < SIZE;++i) {
			if(this.state[x+i][y+i] != USED) {	
				Board neighbor = new Board(this, new Coordinate((byte)(x + i), (byte)(y + i)));
				neighbors.add(neighbor);
			}
			else
				break;
		}
		
		// RightDown
		for(int i = 1; x - i >= 0 && y + i < SIZE; ++i) {
			if(this.state[x-i][y+i] != USED) {		
				Board neighbor = new Board(this, new Coordinate((byte)(x - i), (byte)(y + i)));
				neighbors.add(neighbor);
			}
			else 
				break;
		}
		
		// LeftUp
		for(int i = 1; x + i < SIZE && y - i >= 0; ++i) {
			if(this.state[x+i][y-i] != USED) {	
				Board neighbor = new Board(this, new Coordinate((byte)(x + i), (byte)(y - i)));
				neighbors.add(neighbor);
			}
			else 
				break;
		}
		
		// LeftDown
		for(int i = 1; x - i >= 0 && y - i >= 0; ++i) {
			if(this.state[x-i][y-i] != USED) {		
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
