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
	
	private Player firstPlayer;
	private Player currentPlayer;
	private Board state;
	private String status;
	private HashMap<Player, Queue<Coordinate>> moveTable;
	
	public IsolationGame(Player first) {
		this.firstPlayer = first;
		this.currentPlayer = first;
		this.state = new Board();
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
				move = maxMove();
			moveTable.get(this.currentPlayer).add(move);
			state.move(this.currentPlayer, move);
			
		} catch (InvalidMoveException e) {
			System.out.println(e.getMessage());
		}
			
	}
	
	private Coordinate maxMove() {
		// TODO Auto-generated method stub
		return null;
	}

	public Coordinate getOpponentMove() throws InvalidMoveException {
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter Opponent's move: ");
		String input = kb.nextLine().trim().toUpperCase();
		Coordinate move = convertMove(input);
		kb.close();
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
		return "";
	}
}
