package tictactoe;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class TicTacToe {
	public static final int MIN_CONSEC_FOR_WIN = 2;

	public enum Piece {
		X,
		O
	}

	private final int width, height;
	/**
	 * Amount of consecutive pieces needed for win, e.g. if consecForWin is 3, the player will need 3 pieces in a line
	 * to win.
	 */
	private final int consecForWin;
	private final Piece[][] board;

	public TicTacToe(int width, int height) {
		this(width, height, 3);
	}

	/**
	 * Creates a new Tic Tac Toe game.
	 *
	 * @param width width of the board.
	 * @param height height of the board.
	 * @param consecForWin amount of consecutive pieces needed for a win.
	 * @see TicTacToe#consecForWin
	 */
	public TicTacToe(int width, int height, int consecForWin) {
		if (consecForWin < MIN_CONSEC_FOR_WIN) {
			throw new IllegalArgumentException("Consecutive piece for win property must be at least 2.");
		}
		if (width < consecForWin && height < consecForWin) {
			throw new IllegalArgumentException("Width or height must be at least large enough for a player to win, i.e. width or height must be greater than consecutive pieces for win.");
		}

		this.width = width;
		this.height = height;
		this.board = new Piece[height][width];
		this.consecForWin = consecForWin;
	}

	/**
	 * Check if the board is won by the player using the given piece.
	 *
	 * @param piece piece of the player to check if the board is won by.
	 * @return true if the game is won by the given player.
	 */
	public boolean isWon(Piece piece) {
		for (int row = 0; row < height; ++row) {
			if (isRowWon(row, piece)) {
				return true;
			}
		}

		for (int col = 0; col < width; ++col) {
			if (isColumnWon(col, piece)) {
				return true;
			}
		}

		return isDiagonalWon(piece);
	}

	/**
	 * Checks if the line starting at {@code start} and ending at {@code end} (exclusive) by applying {@code slope} at
	 * every step is won by the given piece.
	 *
	 * @apiNote
	 * It is up to the called to make sure that the line does not go outside the bounds of the board and that end will
	 * be reached.
	 * <br>
	 * E.g. with {@code start} as {@code [0,0]} and {@code slope} as {@code [0,1]}, the line will never reach
	 * {@code [1,0]} so the method will run indefinitely if {@code end} is {@code [1,0]}.
	 *
	 * @param slope slope of the line.
	 * @param start start of the line.
	 * @param end end of the line (exclusive).
	 * @param piece piece to check if the line is won by.
	 * @return true if the line is won by the given piece.
	 * @throws IndexOutOfBoundsException if the line goes outside of the board's bounds.
	 */
	private boolean isLineWon(Vec2<Integer> slope, Vec2<Integer> start, Vec2<Integer> end, Piece piece) throws IndexOutOfBoundsException {
		int consec = 0;
		for (; !(start.equals(end)); start = start.add(slope)) {
			if (getPiece(start.x, start.y) == piece) {
				consec += 1;
				if (consec >= consecForWin) {
					return true;
				}
			} else {
				consec = 0;
			}
		}
		return false;
	}

	/**
	 * Check if the given row is won by the given piece.
	 *
	 * @param row the row to check
	 * @param piece the piece to check if the row is won by.
	 * @return true if the row is won by the given piece.
	 */
	private boolean isRowWon(int row, Piece piece) {
		return isLineWon(new Vec2.Int(1, 0),
				new Vec2.Int(0, row),
				new Vec2.Int(width, row),
				piece);
	}

	/**
	 * Check if the given column is won by the given piece.
	 *
	 * @param col the column to check
	 * @param piece the piece to check if the column is won by.
	 * @return true if the column is won by the given piece.
	 */
	private boolean isColumnWon(int col, Piece piece) {
		return isLineWon(new Vec2.Int(0, 1),
				new Vec2.Int(col, 0),
				new Vec2.Int(col, height),
				piece);
	}

	/**
	 * Checks if any of the diagonals on the board is won.
	 *
	 * @param piece piece to check if it has a winning diagonal.
	 * @return true if any of the diagonal lines on the board is won by the given piece.
	 */
	private boolean isDiagonalWon(Piece piece) {
		Vec2.Int diagSlope = new Vec2.Int(1, 1);
		for (int x = 0; x < width; ++x) {
			try {
				if (isLineWon(diagSlope, new Vec2.Int(x, 0), new Vec2.Int(width + 1, height + 1), piece)) {
					return true;
				}
			} catch (IndexOutOfBoundsException ignored) {
			}
		}
		for (int y = 0; y < height; ++y) {
			try {
				if (isLineWon(diagSlope, new Vec2.Int(0, y), new Vec2.Int(width + 1, height + 1), piece)) {
					return true;
				}
			} catch (IndexOutOfBoundsException ignored) {
			}
		}

		diagSlope = new Vec2.Int(-1, 1);
		for (int x = width - 1; x >= 0; --x) {
			try {
				if (isLineWon(diagSlope, new Vec2.Int(x, 0), new Vec2.Int(-1, -1), piece)) {
					return true;
				}
			} catch (IndexOutOfBoundsException ignored) {
			}
		}

		for (int y = 0; y < height; ++y) {
			try {
				if (isLineWon(diagSlope, new Vec2.Int(width - 1, y), new Vec2.Int(-1, -1), piece)) {
					return true;
				}
			} catch (IndexOutOfBoundsException ignored) {
			}
		}

		return false;
	}

	/**
	 * Makes sure the given coordinates are within the bounds of the board.
	 * <p>
	 * If the coordinates are both valid nothing with be effected.
	 *
	 * @param x x coordinate to validate.
	 * @param y y coordinate to validate.
	 * @throws IndexOutOfBoundsException if either of the coordinates are not within bounds.
	 */
	private void validateIndex(int x, int y) throws IndexOutOfBoundsException {
		if (x < 0 || x >= width) {
			throw new IndexOutOfBoundsException(String.format("X: %d, Width: %d", x, width));
		}
		if (y < 0 || y >= height) {
			throw new IndexOutOfBoundsException(String.format("Y: %d, Height: %d", y, height));
		}
	}

	/**
	 * Gets the piece at the specified coordinates on the board.
	 *
	 * @param x x coordinate of the piece to get.
	 * @param y y coordinate of the piece to get.
	 * @return the piece at the specified coordinates on the board or null if no piece is present at those coordinates.
	 * @throws IndexOutOfBoundsException if either of the given coordinates are not valid.
	 */
	public Piece getPiece(int x, int y) throws IndexOutOfBoundsException {
		validateIndex(x, y);

		return board[y][x];
	}

	/**
	 * Sets the piece at the specified coordinates on the board.
	 *
	 * @param x     x coordinate on the board to set.
	 * @param y     y coordinate on the board to set.
	 * @param piece piece to set the coordinate to.
	 * @throws IndexOutOfBoundsException if either of the given coordinates are invalid.
	 * @throws IllegalStateException     if the piece at the specified coordinates has already been set.
	 */
	public void setPiece(int x, int y, Piece piece) throws IndexOutOfBoundsException, IllegalStateException {
		validateIndex(x, y);

		if (this.board[y][x] == null) {
			this.board[y][x] = piece;
		} else {
			throw new IllegalStateException(String.format("Piece at %d,%d has already been set.", x, y));
		}
	}

	/**
	 * Checks if the board is full, i.e. has no more empty tiles.
	 *
	 * @return true if the board is full, false otherwise.
	 */
	public boolean isBoardFull() {
		return Stream.of(board)
					 .noneMatch(row -> Stream.of(row)
							 				 .anyMatch(Objects::isNull));
	}

	/**
	 * Finds all the empty tiles on the board and returns them as a list of integer {@link Vec2} objects which each
	 * contain the coordinates for an empty tile.
	 *
	 * @return a list of {@link Vec2} objects with the coordinates for empty tiles.
	 */
	private List<Vec2.Int> getEmptyTiles() {
		return IntStream.range(0, height)
						.mapToObj(row -> IntStream.range(0, width)
												  .filter(col -> getPiece(col, row) == null)
												  .mapToObj(col -> new Vec2.Int(col, row))
												  .collect(Collectors.toList()))
						.flatMap(Collection::stream)
						.collect(Collectors.toList());
	}

	/**
	 * Prints the board's state in a human-readable format to stdout.
	 */
	public void printBoard() {
		List<String> rowStrings = Stream.of(board)
										.map(row -> Stream.of(row)
														  .map(piece -> piece != null ? piece.name() : " ")
														  .collect(Collectors.joining("|")))
										.collect(Collectors.toList());
		String boardString = String.join("\n" + new String(new char[rowStrings.get(0).length()]).replace("\0", "-") + "\n", rowStrings);
		System.out.printf("%s%n%n", boardString);
	}

	/**
	 * Continuously prompts the user for coordinates where he wants to play his piece until the given coordinates are
	 * (1) valid board coordinates (i.e. it's within the board's bounds) and (2) the tile at the given coordinates is
	 * empty.
	 *
	 * Once legal coordinates are given the player's piece will be placed at those coordinates.
	 *
	 * @param playerPiece the player's piece, i.e. an X or an O.
	 * @throws IllegalStateException if the method was invoked when the board is full.
	 */
	public void performPlayerMove(Piece playerPiece) {
		if (isBoardFull()) {
			throw new IllegalStateException("The board is full so the player cannot make a move.");
		}

		Scanner input = new Scanner(System.in);

		boolean hasPlayed = false;
		while (!hasPlayed) {
			System.out.print("Please enter the x coordinate for your piece: ");
			int x = Integer.parseInt(input.nextLine());
			System.out.print("Please enter the y coordinate for your piece: ");
			int y = Integer.parseInt(input.nextLine());

			try {
				setPiece(x, y, playerPiece);
				hasPlayed = true;
			} catch (IndexOutOfBoundsException ioobe) {
				System.out.printf("Those coordinates are invalid, the board size is %d by %d.%n", width, height);
				System.out.printf("Legal values for x are 0 to %d.%n", width - 1);
				System.out.printf("Legal values for y are 0 to %d.%n", height - 1);
			} catch (IllegalStateException ise) {
				System.out.printf("A piece has already been placed at x: %d, y: %d.%n", x, y);
			}
		}
	}

	/**
	 * Places the specified piece at a random coordinate on the board.
	 *
	 * @param piece piece to place on the board.
	 * @throws IllegalStateException if there are no empty tiles where the piece can be placed.
	 */
	public void performRandomMove(Piece piece) throws IllegalStateException {
		Random random = new Random();
		List<Vec2.Int> emptyTiles = getEmptyTiles();
		try {
			Vec2.Int selectedTile = emptyTiles.get(random.nextInt(emptyTiles.size()));
			setPiece(selectedTile.x, selectedTile.y, piece);
		} catch (IllegalArgumentException iae) {
			throw new IllegalStateException("The board is full so the piece could not be placed.");
		}
	}
}