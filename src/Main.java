import tictactoe.TicTacToe;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Please enter board width: ");
		int width = Integer.parseInt(input.nextLine());
		System.out.print("Please enter board height: ");
		int height = Integer.parseInt(input.nextLine());
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
