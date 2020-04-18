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
	private int totalMoves;
	
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
				move = getOpponentMove(); //maxMove();
			moveTable.get(this.currentPlayer).add(move);
			state.move(this.currentPlayer, move);
			Player temp = this.currentPlayer;
			this.currentPlayer = this.nextPlayer;
			this.nextPlayer = temp;
			++totalMoves;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
			
	}
	
	/*
	 * MiniMax with AlphaBeta pruning and Iterative Deepening
	 */
	private Coordinate maxMove(){
		/*
		 * Alpha-Beta Search methods should most likely be implemented in separate 
		 * thread to return within a specific time limit
		 */
		AlphaBetaSearch search = new AlphaBetaSearch(this.state);
		search.start();
		try {
			search.join(timeLimit*1000);
		} catch (InterruptedException interrupted) {
			interrupted.printStackTrace();
		}
		return search.getBestMove();
	}

	public Coordinate getOpponentMove() throws InvalidMoveException {
		BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
		Coordinate move = null;
		try {
			while(move == null) {
				System.out.print("Enter Opponent's move: ");
				String input = kb.readLine();
				input = input.trim().toUpperCase();
				move = convertMove(input);
				if(move == null) {
					System.out.println("Invalid move! Try again");
				}
			}
		} catch(IOException e) {
			System.out.println(e.getMessage());
		}
		return move;
	}
	
	public Coordinate convertMove(String coordinate){
		char alphabet = coordinate.charAt(0);
		byte x = (byte) (alphabet - 'A');
		byte y = (byte) (coordinate.charAt(1) - '1');
		Coordinate move = new Coordinate(x,y);
		if(x > Board.SIZE || y > Board.SIZE || x < 0 || y < 0)
			return null;		
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
