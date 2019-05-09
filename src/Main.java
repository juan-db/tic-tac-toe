import tictactoe.TicTacToe;

import java.util.Scanner;

public class Main {
	private static final String USAGE_STRING =
			"Usage: java -jar tictactoe.jar [width height consec]\n" +
			"Width and height are the dimensions of the board.\n" +
			"Consec is how many pieces need to be in a line in order for a player to win the match.";

	private static final String INVALID_ARGUMENTS_MESSAGE =
			"Board dimensions were specified as command line arguments but the format was invalid.";

	private static final String INVALID_ARGUMENT_COUNT_MESSAGE = "Invalid argument count.";

	/**
	 * Convenience function that prints out the specified message, followed by the usage message, then exits.
	 *
	 * @param message Message to print to inform the user what's wrong with their arguments.
	 */
	private static void argumentError(String message) {
		System.err.println(message);
		System.err.println(USAGE_STRING);
		System.exit(1);
	}

	private static int readInt() {
		Scanner input = new Scanner(System.in);
		Integer output = null;
		do {
			try {
				output = Integer.parseInt(input.nextLine());
			} catch (NumberFormatException nfe) {
				System.out.print("Please enter a single integer (e.g. 13): ");
			}
		} while (output == null);
		return output;
	}

	public static void main(String[] args) {
		int width, height, consec;

		if (args.length == 3) {
			try {
				width = Integer.parseInt(args[0]);
				height = Integer.parseInt(args[1]);
				consec = Integer.parseInt(args[2]);
			} catch (NumberFormatException nfe) {
				argumentError(INVALID_ARGUMENTS_MESSAGE);
				return;
			}
		} else if (args.length == 0) {
			System.out.print("Please enter board width: ");
			width = readInt();
			System.out.print("Please enter board height: ");
			height = readInt();
			// Being prompted to specify consec is annoying.
			// If you want to specify it, do so using the command line arguments.
			consec = 3;
		} else {
			argumentError(INVALID_ARGUMENT_COUNT_MESSAGE);
			return;
		}

		TicTacToe game = new TicTacToe(width, height, consec);
		TicTacToe.Piece playerPiece = TicTacToe.Piece.X;
		TicTacToe.Piece opponentPiece = TicTacToe.Piece.O;

		while (true) {
			game.printBoard();
			game.performPlayerMove(playerPiece);
			if (game.isWon(playerPiece)) {
				System.out.printf("%nPLAYER HAS WON!!!%n");
				game.printBoard();
				break;
			} else if (game.isBoardFull()) {
				System.out.println("Game is a tie!");
				game.printBoard();
				break;
			}
			game.performRandomMove(opponentPiece);
			if (game.isWon(opponentPiece)) {
				System.out.printf("%nOPPONENT HAS WON!!!%n");
				game.printBoard();
				break;
			} else if (game.isBoardFull()) {
				System.out.println("Game is a tie!");
				game.printBoard();
				break;
			}
		}
	}
}
