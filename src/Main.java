import tictactoe.TicTacToe;

import java.util.Scanner;

public class Main {
	private static final String INVALID_ARGUMENTS_MESSAGE =
			"Board dimensions were specified as command line arguments but the format was invalid.\nUsage: java -jar tictactoe.jar [width height]";

	private static final String INVALID_ARGUMENT_COUNT_MESSAGE =
			"Invalid argument count.\nUsage: java -jar tictactoe.jar [width height]";

	public static void main(String[] args) {
		int width, height;

		if (args.length != 0 && args.length != 2) {
			System.out.println(INVALID_ARGUMENT_COUNT_MESSAGE);
			System.exit(1);
			return;
		} else if (args.length == 2) {
			try {
				width = Integer.parseInt(args[0]);
				height = Integer.parseInt(args[1]);
			} catch (NumberFormatException nfe) {
				System.out.println(INVALID_ARGUMENTS_MESSAGE);
				System.exit(1);
				return;
			}
		} else {
			Scanner input = new Scanner(System.in);
			System.out.print("Please enter board width: ");
			width = Integer.parseInt(input.nextLine());
			System.out.print("Please enter board height: ");
			height = Integer.parseInt(input.nextLine());
		}

		TicTacToe game = new TicTacToe(width, height);
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
