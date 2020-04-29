import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

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
	private Stack<Coordinate> computer;
	private Stack<Coordinate> opponent;
	private int score;
	private HeuristicManager heuristicManager;
	private HashSet<Coordinate> opponentSuccessors;
	private HashSet<Coordinate> computerSuccessors;
	private boolean hasChanged;
	private int usedCoordinates;
	
	public Board(Player first) {
		state = new char[SIZE][SIZE];
		this.initializeBoard();
		this.computer = new Stack<Coordinate>();
		this.opponent = new Stack<Coordinate>();
		if(first == Player.Opponent) {
			this.opponent.push(new Coordinate((byte)0, (byte)0));
			this.state[0][0] = Board.O;
			this.computer.push(new Coordinate((byte)7, (byte)7));
			this.state[7][7] = Board.X;
		}
		else {
			this.computer.push(new Coordinate((byte)0, (byte)0));
			this.state[0][0] = Board.X;
			this.opponent.push(new Coordinate((byte)7, (byte)7));
			this.state[7][7] = Board.O;
		}
		this.heuristicManager = new HeuristicManager("OffensiveToDefensive");
		this.hasChanged = true;
		this.usedCoordinates = 2;
	}
	
	public Coordinate getComputerCoordinate() {
		return computer.peek();
	}
	
	public Coordinate getOpponentCoordinate() {
		return opponent.peek();
	}
	
	public char[][] getState(){
		return this.state;
	}
	
	public HashSet<Coordinate> getOpponentSuccessors(){
		if(this.hasChanged) 
			this.opponentSuccessors = this.getNextMoves(Player.Opponent);
		return this.opponentSuccessors;
	}
	
	public HashSet<Coordinate> getComputerSuccessors(){
		if(this.hasChanged)
			this.computerSuccessors = this.getNextMoves(Player.Computer);
		return this.computerSuccessors;
	}

	public int getUsedCoordinates() {
		return this.usedCoordinates;
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
		Heuristic heuristic = heuristicManager.getHeuristic(this.usedCoordinates);
		this.setScore(heuristic.getScore(this));
	}
	
	private void initializeBoard() {
		for(char[] c: this.state)
			Arrays.fill(c, EMPTY);
	}

	public void move(Player player, Coordinate move){
		Coordinate currentPlayer = null;
		if (player==Player.Computer) {
			currentPlayer = this.computer.peek();
			this.state[currentPlayer.x][currentPlayer.y] = USED;
			this.computer.push(move);
			this.state[move.x][move.y] = X;
		}else{
			currentPlayer = this.opponent.peek();
			this.state[currentPlayer.x][currentPlayer.y] = USED;
			this.opponent.push(move);
			this.state[move.x][move.y] = O;
		}
		this.hasChanged = true;
		++this.usedCoordinates;
	}
	
	public void undoMove(Player player) {
		Coordinate currentPlayer = null;
		if (player==Player.Computer) {
			currentPlayer = this.computer.peek();
			this.state[currentPlayer.x][currentPlayer.y] = EMPTY;
			this.computer.pop();
			currentPlayer = this.computer.peek();
			this.state[currentPlayer.x][currentPlayer.y] = X;
		}else{
			currentPlayer = this.opponent.peek();
			this.state[currentPlayer.x][currentPlayer.y] = EMPTY;
			this.opponent.pop();
			this.state[currentPlayer.x][currentPlayer.y] = O;
		}
		this.hasChanged = true;
		--this.usedCoordinates;
	}
	
	public char getCoordinate(Coordinate coordinate) {
		return this.state[coordinate.x][coordinate.y];
	}
	

	public boolean isInvalidMove(Coordinate move) {
		if(move.equals(this.opponent.peek()) || this.getCoordinate(move) != EMPTY || !this.opponentSuccessors.contains(move)) 
			return true;
		return false;
	}
	
	public boolean isTerminal() {
		if(this.getComputerSuccessors().size() == 0 || this.getOpponentSuccessors().size() == 0)
			return true;
		return false;
	}
	
	public HashSet<Coordinate> getNextMoves(Player player){
		HashSet<Coordinate> neighbors = new HashSet<Coordinate>();
		byte x, y = 0;
		if(player.equals(Player.Computer)) {
			x = this.computer.peek().x;
			y = this.computer.peek().y;
		}
		else {
			x = this.opponent.peek().x;
			y = this.opponent.peek().y;
		}
		
		// Down
		for(int i  = 1; x + i < SIZE; ++i) 
			if(this.state[x+i][y] == EMPTY) 
				neighbors.add(new Coordinate((byte)(x + i), y));
			else 
				break;

		// Up
		for(int i = 1; x - i >= 0; ++i) 
			if(this.state[x-i][y] == EMPTY)
				neighbors.add(new Coordinate((byte)(x - i), y));
			else
				break;
		
		// Right
		for(int i = 1; y + i < SIZE; ++i) 
			if(this.state[x][y+i] == EMPTY) 
				neighbors.add(new Coordinate(x, (byte)(y + i)));
			else 
				break;
		
		// Left
		for(int i = 1; y - i >= 0; ++i) 
			if(this.state[x][y-i] == EMPTY)
				neighbors.add(new Coordinate(x, (byte)(y - i)));
			else 
				break;
		
		// RightDown
		for(int i = 1; x + i < SIZE && y + i < SIZE;++i) 
			if(this.state[x+i][y+i] == EMPTY)
				neighbors.add(new Coordinate((byte)(x + i), (byte)(y + i)));
			else
				break;
		
		// RightDown
		for(int i = 1; x - i >= 0 && y + i < SIZE; ++i) 
			if(this.state[x-i][y+i] == EMPTY) 
				neighbors.add(new Coordinate((byte)(x - i), (byte)(y + i)));
			else 
				break;
		
		// LeftUp
		for(int i = 1; x + i < SIZE && y - i >= 0; ++i) 
			if(this.state[x+i][y-i] == EMPTY)
				neighbors.add(new Coordinate((byte)(x + i), (byte)(y - i)));
			else 
				break;
		
		// LeftDown
		for(int i = 1; x - i >= 0 && y - i >= 0; ++i) 
			if(this.state[x-i][y-i] == EMPTY) 
				neighbors.add(new Coordinate((byte)(x - i), (byte)(y - i)));
			else 
				break;
		
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
