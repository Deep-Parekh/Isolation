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
	private int timeLimit;
	private int totalMoves;
	private String moveLogHeading;
	private Coordinate[] firstPlayerMoves;
	private Coordinate[] secondPlayerMoves;
	
	public IsolationGame(Player first, Player second, int timeLimit) {
		this.currentPlayer = first;
		this.nextPlayer = second;
		this.state = new Board(first);
		this.timeLimit = timeLimit;
		firstPlayerMoves = new Coordinate[32];
		secondPlayerMoves = new Coordinate[32];
		if(this.currentPlayer == Player.Opponent)
			moveLogHeading = "Opponent vs. Computer";
		else
			moveLogHeading = "Computer vs. Opponent";
		this.totalMoves = 0;
	}
	
	public void play() {
		Coordinate move = null;
		try {
			if(this.currentPlayer == Player.Opponent)
				move = getOpponentMove();
			else 
				move = getOpponentMove(); //maxMove();
			state.move(this.currentPlayer, move);
			if(this.totalMoves % 2 == 0)
				firstPlayerMoves[this.totalMoves/2] = move;
			else
				secondPlayerMoves[this.totalMoves/2] = move;
			state.generateScore();
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
		AlphaBetaSearch search = new AlphaBetaSearch(this.state, Player.Computer);
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
				if(state.isInvalidMove(move))
					move = null;
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
		Queue<Coordinate> first = null;
		Queue<Coordinate> second = null;
		rtn += "     1  2  3  4  5  6  7  8  \t" + moveLogHeading + "\n";
		for(int i = 0; i < Board.SIZE; ++i){
			rtn += ("  " + (char)('A' + i) + "  ");
			for(int j = 0; j < Board.SIZE; ++j){
				rtn += board[i][j] + "  ";
			}
			String firstMove = firstPlayerMoves[i] == null ? "" : firstPlayerMoves[i].toString();
			String secondMove = secondPlayerMoves[i] == null ? "" : secondPlayerMoves[i].toString();
			if(firstMove == "" && secondMove == "")
				rtn = rtn + "\n";
			else
				rtn = rtn + "\t" + (i+1) + ". " + firstMove + "\t\t" + secondMove + "\n";
		}
		// Add logic for when there are more moves than 16 (8*2)
		int i = 8;
		while(firstPlayerMoves[i] != null) {
			String firstMove = firstPlayerMoves[i] == null ? "" : firstPlayerMoves[i].toString();
			String secondMove = secondPlayerMoves[i] == null ? "" : secondPlayerMoves[i].toString();
			rtn = rtn + "                             \t" + (i+1) + ". " + firstMove + "\t\t" + secondMove + "\n";
			++i;
		}
		return rtn;
	}
}
