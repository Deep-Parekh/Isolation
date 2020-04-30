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
	private String moveLogHeading;
	private Coordinate[] firstPlayerMoves;
	private Coordinate[] secondPlayerMoves;
	private AlphaBetaSearch searcher;
	
	public IsolationGame(Player first, Player second, int timeLimit) {
		this.currentPlayer = first;
		this.nextPlayer = second;
		this.state = new Board(first);
		firstPlayerMoves = new Coordinate[32];
		secondPlayerMoves = new Coordinate[32];
		this.searcher = new AlphaBetaSearch(timeLimit);
		if(this.currentPlayer == Player.Opponent)
			moveLogHeading = "Opponent vs. Computer";
		else
			moveLogHeading = "Computer vs. Opponent";
	}
	
	public void play() {
		Coordinate move = null;
		int totalMoves = this.state.getUsedCoordinates();
		try {
			if(this.currentPlayer == Player.Opponent)
				move = getOpponentMove();		//maxMove(currentPlayer); // for AI vs AI(buggy)
			else 
				move = maxMove(currentPlayer);
			state.move(this.currentPlayer, move);
			if(totalMoves % 2 == 0)
				firstPlayerMoves[(totalMoves/2) - 1] = move;
			else
				secondPlayerMoves[(totalMoves/2) - 1] = move;
			Player temp = this.currentPlayer;
			this.currentPlayer = this.nextPlayer;
			this.nextPlayer = temp;
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
			
	}
	
	/* 
	 * MiniMax with AlphaBeta pruning and Iterative Deepening
	 */
	private Coordinate maxMove(Player maxPlayer) {
		searcher.Search(maxPlayer, this.state);
		return searcher.getBestMove();
	}

	public Coordinate getOpponentMove() {
		BufferedReader kb = new BufferedReader(new InputStreamReader(System.in));
		Coordinate move = null;
		try {
			while(move == null) {
				System.out.print("Enter Opponent's move ( Q to exit): ");
				String input = kb.readLine();
				input = input.trim().toUpperCase();
				if(input.charAt(0) == 'Q')
					System.exit(0);
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
		// When there are more than 8 moves played by either player 
		int i = 8;
		while(firstPlayerMoves[i] != null) {
			String firstMove = firstPlayerMoves[i] == null ? "" : firstPlayerMoves[i].toString();
			String secondMove = secondPlayerMoves[i] == null ? "" : secondPlayerMoves[i].toString();
			rtn = rtn + "                             \t" + (i+1) + ". " + firstMove + "\t\t" + secondMove + "\n";
			++i;
		}
		// If the game is over
		if(this.isOver()) {
			String winner = null;
			if(this.state.getComputerSuccessors().size() == 0)
				winner = "Congratulations! You won, the Computer lost\n";
			else 
				winner = "You lost, the Computer won\n";
			rtn += winner;
		}
		return rtn;
	}
}
