import java.util.Scanner;

/**
 * 
 */

/**
 * @author dparekh
 *
 */
public class Isolation {

	public static void main(String[] args) {
		// TODO
		Scanner kb = new Scanner(System.in);
		System.out.println("Who goes first (C for computer, O for opponent)?: ");
		String input = kb.nextLine().toUpperCase();
		Player first = null;
		if(input.charAt(0) == 'C')
			first = Player.Computer;
		else if (input.charAt(0) == 'O')
			first = Player.Opponent;
		IsolationGame game = new IsolationGame(first);
		while(!game.isOver()) {
			game.play();
		}
	}
}
