package dev.ilovapples;

import dev.ilovapples.TicTacToeGame;
import java.util.Scanner;

public final class Main {
    public static void main(String[] args) {
        System.out.print("\u001b[?1049h");

        TicTacToeGame game = new TicTacToeGame();

        Scanner scan = new Scanner(System.in);
        game_loop:
        while (true) {
            System.out.print("\u001b[2J\u001b[f");
            game.printBoard();

            while (true) {
                System.out.print("Enter move (1-9): ");
                String line = scan.nextLine();
                try {
                    int tmp = Integer.parseInt(line);
                    if (tmp < 1 || tmp > 9) {
                        continue;
                    }

                    --tmp;

                    if (game.makeMove(tmp%3, tmp/3)) {
                        break;
                    }
                } catch (Exception e) {
                    if (line.equalsIgnoreCase("q")) {
                        break game_loop;
                    }
                    continue;
                }
            }

            final int winner = game.checkWinner();
            if (winner == -1) {
                continue;
            }
            System.out.print("\u001b[2J\u001b[f");
            game.printBoard();
            switch (winner) {
                case 0:
                    System.out.println("Tie! Game over.");
                    break;
                case 1:
                    System.out.println("Player X wins! Game over.");
                    break;
                case 2:
                    System.out.println("Player O wins! Game over.");
                    break;
            }
            break;
        }

        System.out.print("Press enter to exit Tic Tac Toe...");
        scan.nextLine();
        System.out.println("\u001b[?1049l");
    }
}
