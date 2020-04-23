import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author dparekh
 *
 */
public class Isolation {
	
	static String input = null;
	static int timeLimit = 0;

	static Player first = null;
	static Player second = null;

	public static void main(String[] args) {
		// TODO
		Scanner keyboard = new Scanner(System.in);
		while(true) {
			try {
				System.out.print("Time limit for computer (in seconds)?: ");
				timeLimit = keyboard.nextInt();
				keyboard.nextLine();
				System.out.print("Who goes first (C for computer, O for opponent)?: ");
				input = keyboard.nextLine().toUpperCase();
				if(input.trim().charAt(0) == 'C') {
					first = Player.Computer;
					second = Player.Opponent;
				}
				else if (input.trim().charAt(0) == 'O') {
					first = Player.Opponent;
					second = Player.Computer;
				} else
					throw new InputMismatchException();
				break;
			} catch (Exception e) {
				System.out.println("Your input is invalid. Try Again.");
			}
		}
		IsolationGame game = new IsolationGame(first, second, timeLimit);
		while(!game.isOver()) {
			System.out.println(game);
			game.play();
		}
	}
}
