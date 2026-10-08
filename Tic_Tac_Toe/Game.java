package Tic_Tac_Toe;

import java.util.Scanner;

public class Game {
    private final Board board;
    private final Scanner scanner = new Scanner(System.in);
    private Player currentPlayer;
    private boolean gameOver;

    public Game() {
        board = new Board(3, 3);
    }

    public String play(Player player1, Player player2) {
        if (gameOver) {
            throw new IllegalStateException("This game has already ended");
        }
        if (player1 == null || player2 == null) {
            throw new IllegalArgumentException("Players cannot be null");
        }
        if (player1 == player2) {
            throw new IllegalArgumentException("Players must be distinct");
        }
        if (player1.getPlayerSymbol() == player2.getPlayerSymbol()) {
            throw new IllegalArgumentException("Players must have different symbols");
        }

        currentPlayer = player1;
        
        while (true) {
            System.out.println("Current player: " + currentPlayer.getPlayerName());
            board.printBoard();
            System.out.println("Enter row and column (0-2), separated by spaces:");
            if (!scanner.hasNextLine()) {
                throw new IllegalStateException("Input ended before the game completed");
            }
            String input = scanner.nextLine();
            String[] inputArray = input.trim().split("\\s+");
            if (inputArray.length != 2) {
                System.out.println("Invalid input. Enter exactly two numbers.");
                continue;
            }

            int row;
            int column;
            try {
                row = Integer.parseInt(inputArray[0]);
                column = Integer.parseInt(inputArray[1]);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Row and column must be numbers.");
                continue;
            }

            PlayerSymbol symbol = currentPlayer.getPlayerSymbol();
            try {
                board.placeSymbol(row, column, symbol);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid move. Try again.");
                continue;
            }
            if (board.isWinner(currentPlayer)) {
                System.out.println("Winner: " + currentPlayer.getPlayerName());
                board.printBoard();
                gameOver = true;
                return "Winner";
            }
            if (board.isDraw()) {
                System.out.println("Draw");
                board.printBoard();
                gameOver = true;
                return "Draw";
            }
            currentPlayer = (currentPlayer == player1) ? player2 : player1;

        }
    }


    public static void main(String[] args) {
        Game game = new Game();

        Player player1 = new Player("P1", "Alice", PlayerSymbol.X);
        Player player2 = new Player("P2", "Bob", PlayerSymbol.O);

        game.play(player1, player2);
    }

}
