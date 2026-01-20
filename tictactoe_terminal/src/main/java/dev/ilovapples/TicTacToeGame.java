package dev.ilovapples;

import java.util.Arrays;

public final class TicTacToeGame {
	private Piece[] grid;
	private int turn;

	public static enum Piece {
		EMPTY(0), X(1), O(2);

		private final int piece;

		private Piece(int piece) {
			this.piece = piece;
		}

		public char toChar() {
			return this == X ? 'X' : (this == O ? 'O' : ' ');
		}
	}

	public TicTacToeGame() {
		this.grid = new Piece[9];
		Arrays.fill(this.grid, Piece.EMPTY);

		this.turn = 0;
	}

	public Piece getGridAt(int x, int y) {
		return this.grid[y*3 + x];
	}

	public char currentPiece() {
		return Piece.values()[this.turn%2 + 1].toChar();
	}

	// return true if move was successful, otherwise false
	public boolean makeMove(int x, int y) {
		if (this.grid[y*3 + x] == Piece.EMPTY) {
			this.grid[y*3 + x] = Piece.values()[(this.turn++)%2 + 1]; // 1 or 2 for X or O
			return true;
		}
		return false;
	}

	public int checkWinner() {
		// checking if patterns are filled with the piece that the last player would've played
		final int last_player_turn = (this.turn-1)%2+1;

		// top row
		if (this.grid[0] == this.grid[1] && this.grid[1] == this.grid[2] && this.grid[0].ordinal() == last_player_turn) {
			System.out.println("top row");
			return this.grid[0].ordinal();
		}
		// middle row
		if (this.grid[3] == this.grid[4] && this.grid[4] == this.grid[5] && this.grid[3].ordinal() == last_player_turn) {
			System.out.println("middle row");
			return this.grid[3].ordinal();
		}
		// bottom row
		if (this.grid[6] == this.grid[7] && this.grid[7] == this.grid[8] && this.grid[6].ordinal() == last_player_turn) {
			System.out.println("bottom row");
			return this.grid[6].ordinal();
		}

		// left column
		if (this.grid[0] == this.grid[3] && this.grid[3] == this.grid[6] && this.grid[0].ordinal() == last_player_turn) {
			System.out.println("left column");
			return this.grid[0].ordinal();
		}
		// middle column
		if (this.grid[1] == this.grid[4] && this.grid[4] == this.grid[7] && this.grid[1].ordinal() == last_player_turn) {
			System.out.println("middle column");
			return this.grid[1].ordinal();
		}
		// right column
		if (this.grid[2] == this.grid[5] && this.grid[5] == this.grid[8] && this.grid[2].ordinal() == last_player_turn) {
			System.out.println("right column");
			return this.grid[2].ordinal();
		}

		// major diagonal (top-left to bottom-right)
		if (this.grid[0] == this.grid[4] && this.grid[4] == this.grid[8] && this.grid[0].ordinal() == last_player_turn) {
			System.out.println("major diagonal");
			return this.grid[0].ordinal();
		}
		// minor diagonal (top-left to bottom-right)
		if (this.grid[2] == this.grid[4] && this.grid[4] == this.grid[6] && this.grid[2].ordinal() == last_player_turn) {
			System.out.println("minor diagonal");
			return this.grid[2].ordinal();
		}

		// tie if past 9 turns
		if (this.turn > 8) {
			System.out.println("tie!(J!F(JSA");
			return 0;
		}

		return -1; // game isn't over
	}

	public void printBoard() {
		System.out.println("___________________");
		System.out.println("|     |     |     |");
		System.out.println("|  " + this.grid[0].toChar()
						 + "  |  " + this.grid[1].toChar()
						 + "  |  " + this.grid[2].toChar() + "  |");
		System.out.println("|_____|_____|_____|");
		System.out.println("|     |     |     |");
		System.out.println("|  " + this.grid[3].toChar()
						 + "  |  " + this.grid[4].toChar()
						 + "  |  " + this.grid[5].toChar() + "  |");
		System.out.println("|_____|_____|_____|");
		System.out.println("|     |     |     |");
		System.out.println("|  " + this.grid[6].toChar()
						 + "  |  " + this.grid[7].toChar()
						 + "  |  " + this.grid[8].toChar() + "  |");
		System.out.println("|_____|_____|_____|");
	}
}
