package Tic_Tac_Toe;

public class Cell {
    private int row;
    private int column;
    private char symbol;

    public Cell(int row, int column, char symbol) {
        this.row = row;
        this.column = column;
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean isEmpty() {
        return symbol == '-';
    }
}
