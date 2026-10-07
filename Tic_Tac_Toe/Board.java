package Tic_Tac_Toe;

public class Board {
    private Cell[][] board;
    private int row;
    private int column;

    public Board(int row, int column) {
        this.row = row;
        this.column = column;
        board = new Cell[row][column];
        this.initializeBoard();
    }

    void initializeBoard() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                board[i][j] = new Cell(i, j, '-');
            }
        }
    }

    public void printBoard() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                System.out.print(board[i][j].getSymbol() + "  ");
            }
            System.out.println();
        }
    }

    public boolean placeSymbol(int row, int column, char symbol) {
        if (row >= 0 && row < this.row && column >= 0 && column < this.column && board[row][column].isEmpty()) {
            board[row][column] = new Cell(row, column, symbol);
            return true;
        } else {
            System.out.println("Invalid position or cell already occupied");
            return false;
        }
    }

    public boolean isWinner(Player player) {
        char symbol = player.getPlayerSymbol();

        // diagonal check
        for (int i = 0; i < row; i++) {
            if (board[i][i].getSymbol() != symbol) break;
            if (i == row - 1) return true;
        }

        // anti-diagonal check
        for (int i = 0; i < row; i++) {
            if (board[i][column - i - 1].getSymbol() != symbol) break;
            if (i == row - 1) return true;
        }
        
        // row check
        for (int i = 0; i < row; i++) {
            int count = 0;
            for (int j = 0; j < column; j++) {
                if (board[i][j].getSymbol() == symbol) {
                    count++;
                } else {
                    break;
                }
            }
            if (count == column) return true;
        }

        // column check
        for (int j = 0; j < column; j++) {
            int count = 0;
            for (int i = 0; i < row; i++) {
                if (board[i][j].getSymbol() == symbol) {
                    count++;
                } else {
                    break;
                }
            }
            if (count == row) return true;
        }

        return false;
    }

    public boolean isDraw() {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                if (board[i][j].isEmpty()) return false;
            }
        }
        return true;
    }
}
