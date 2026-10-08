package Tic_Tac_Toe;

public class Board {
    private Cell[][] board;
    private int rowLength;
    private int columnLength;

    public Board(int rowLength, int columnLength) {
        if (rowLength <= 0 || columnLength <= 0) {
            throw new IllegalArgumentException("Board dimensions must be positive");
        }
        this.rowLength = rowLength;
        this.columnLength = columnLength;
        board = new Cell[rowLength][columnLength];
        this.initializeBoard();
    }

    void initializeBoard() {
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
                board[i][j] = new Cell(i, j, null);
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
                char symbol = board[i][j].isEmpty() ? '-' : board[i][j].getPlayerSymbol().toString().charAt(0);
                System.out.print(symbol + "  ");
            }
            System.out.println();
        }
    }

    public void placeSymbol(int row, int column, PlayerSymbol symbol) {
        if (row >= 0 && row < this.rowLength && column >= 0 && column < this.columnLength && board[row][column].isEmpty()) {
            board[row][column] = new Cell(row, column, symbol);
        } else {
            throw new IllegalArgumentException("Invalid position or cell already occupied");
        }
    }

    public boolean isWinner(Player player) {
        PlayerSymbol symbol = player.getPlayerSymbol();

        if (rowLength == columnLength) {
            boolean mainDiagonal = true;
            boolean antiDiagonal = true;
            for (int i = 0; i < rowLength; i++) {
                if (board[i][i].getPlayerSymbol() != symbol) {
                    mainDiagonal = false;
                }
                if (board[i][columnLength - i - 1].getPlayerSymbol() != symbol) {
                    antiDiagonal = false;
                }
            }
            if (mainDiagonal || antiDiagonal) return true;
        }

        for (int i = 0; i < rowLength; i++) {
            int count = 0;
            for (int j = 0; j < columnLength; j++) {
                if (board[i][j].getPlayerSymbol() == symbol) {
                    count++;
                } else {
                    break;
                }
            }
            if (count == columnLength) return true;
        }

        for (int j = 0; j < columnLength; j++) {
            int count = 0;
            for (int i = 0; i < rowLength; i++) {
                if (board[i][j].getPlayerSymbol() == symbol) {
                    count++;
                } else {
                    break;
                }
            }
            if (count == rowLength) return true;
        }

        return false;
    }

    public boolean isDraw() {
        for (int i = 0; i < rowLength; i++) {
            for (int j = 0; j < columnLength; j++) {
                if (board[i][j].isEmpty()) return false;
            }
        }
        return true;
    }
}
