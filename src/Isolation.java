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
		Player second = null;
		if(input.charAt(0) == 'C') {
			first = Player.Computer;
			second = Player.Opponent;
		}
		else if (input.charAt(0) == 'O') {
			first = Player.Opponent;
			second = Player.Computer;
		}
		IsolationGame game = new IsolationGame(first, second, 20);
		while(!game.isOver()) {
			System.out.println(game);
			game.play();
		}
	}
}
