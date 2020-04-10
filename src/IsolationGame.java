import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author dparekh
 *
 */
public class IsolationGame {
	
	private Player currentPlayer;
	private Player nextPlayer;
	private Board state;
	private String status;
	private HashMap<Player, Queue<Coordinate>> moveTable;
	private int timeLimit;
	
	public IsolationGame(Player first, Player second, int timeLimit) {
		this.currentPlayer = first;
		this.nextPlayer = second;
		this.state = new Board(first);
		this.timeLimit = timeLimit;
		this.moveTable = new HashMap<Player, Queue<Coordinate>>();
		moveTable.put(Player.Computer, new LinkedList<Coordinate>());
		moveTable.put(Player.Opponent, new LinkedList<Coordinate>());
	}
	
	public void play() {
		Coordinate move = null;
		try {
			if(this.currentPlayer == Player.Opponent)
				move = getOpponentMove();
			else 
				move = convertMove("G8");//getOpponentMove();			// Should be maxMove()
			moveTable.get(this.currentPlayer).add(move);
			state.move(this.currentPlayer, move);
			Player temp = this.currentPlayer;
			this.currentPlayer = this.nextPlayer;
			this.nextPlayer = temp;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
			
	}
	
	/*
	 * MiniMax with AlphaBet pruning and Iterative Deepening
	 */
	private Coordinate maxMove(){
		// TODO Auto-generated method stub
		return null;
	}

	public Coordinate getOpponentMove() throws InvalidMoveException {
		BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter Opponent's move: ");
		Coordinate move = null;
		try {
			String input = kb.readLine();
			input = input.trim().toUpperCase();
			move = convertMove(input);
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		if(!state.validMovement(move, currentPlayer))
			throw new InvalidMoveException("Invalid move");
		return move;
	}
	
	public Coordinate convertMove(String coordinate) throws InvalidMoveException{
		char alphabet = coordinate.charAt(0);
		byte x = (byte) (alphabet - 'A');
		byte y = (byte) (coordinate.charAt(1) - '1');
		Coordinate move = new Coordinate(x,y);
		if(x > Board.SIZE || y > Board.SIZE)
			throw new InvalidMoveException("Invalid move");		
		System.out.println("X: " + x + "\nY: " + y);
		return move;
	}
	
	public boolean isOver() {
		return state.isTerminal();
	}
	
	@Override
	public String toString() {
		String rtn = new String();
		char[][] board = this.state.getState();
		rtn += "\t\t1\t2\t3\t4\t5\t6\t7\t8\t\n";
		for(int i = 0; i < Board.SIZE; ++i){
			rtn += ("\t" + (char)('A' + i) + "\t");
			for(int j = 0; j < Board.SIZE; ++j){
				rtn += board[i][j] + "\t";
			}
			rtn = rtn + "\n";
		}
		return rtn;
	}
}
