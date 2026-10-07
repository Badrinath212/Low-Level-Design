package Tic_Tac_Toe;

import java.util.Scanner;

public class Game {
    private final Board board;
    private Player currentPlayer;
    private Scanner scanner = new Scanner(System.in);

    public Game(int row, int column) {
        board = new Board(row, column);
    }

    public String play(Player player1, Player player2) {
        currentPlayer = player1;
        
        while (true) {
            System.out.println("Current player: " + currentPlayer.getPlayerName());
            board.printBoard();
            System.out.println("Enter row and column: ");
            String input = scanner.nextLine();
            String[] inputArray = input.split(" ");
            int row = Integer.parseInt(inputArray[0]);
            int column = Integer.parseInt(inputArray[1]);
            char symbol = currentPlayer.getPlayerSymbol();
            if (!board.placeSymbol(row, column, symbol)) {
                System.out.println("Invalid move. Try again.");
                continue;
            }
            if (board.isWinner(currentPlayer)) {
                System.out.println("Winner: " + currentPlayer.getPlayerName());
                return "Winner";
            }
            if (board.isDraw()) {
                System.out.println("Draw");
                return "Draw";
            }
            currentPlayer = (currentPlayer == player1) ? player2 : player1;

        }
    }


    public static void main(String[] args) {
        Game game = new Game(3, 3);
        game.board.printBoard();

        Player player1 = new Player("P1", "Alice", 'X');
        Player player2 = new Player("P2", "Bob", 'O');
        
    }

}
