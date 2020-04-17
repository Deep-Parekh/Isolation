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
		Scanner keyboard = new Scanner(System.in);
		System.out.print("Who goes first (C for computer, O for opponent)?: ");
		String input = keyboard.nextLine().toUpperCase();
		System.out.print("Time limit for computer (in seconds)?: ");
		int timeLimit = keyboard.nextInt();
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
		IsolationGame game = new IsolationGame(first, second, timeLimit);
		while(!game.isOver()) {
			System.out.println(game);
			game.play();
		}
	}
}
