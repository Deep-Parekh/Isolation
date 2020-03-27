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
	
	public IsolationGame(Player first) {
		this.firstPlayer = first;
		this.currentPlayer = first;
		this.state = new Board();
	}
	
	public void play() {
		try {
			if(this.currentPlayer == Player.Opponent) {
				Coordinate move = getOpponentMove();
				state.move(Player.Opponent, move);
			} else {
				Coordinate move = maxMove();
				state.move(Player.Computer, move);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
			
	}
	
	private Coordinate maxMove() {
		// TODO Auto-generated method stub
		return null;
	}

	public Coordinate getOpponentMove() {
		Scanner kb = new Scanner(System.in);
		System.out.println("Enter Opponent's move: ");
		String input = kb.nextLine().trim().toUpperCase();
		Coordinate move = convertMove(input);
		return move;
	}
	
	public Coordinate convertMove(String coordinate) {
		char alphabet = coordinate.charAt(0);
		byte x = (byte) (alphabet - 'A');
		byte y = (byte) (coordinate.charAt(1) - '1');
		return new Coordinate(x,y);
	}
	
	@Override
	public String toString() {
		// TODO
		return null;
	}
}
