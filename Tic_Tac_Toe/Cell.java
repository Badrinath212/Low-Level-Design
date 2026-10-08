package Tic_Tac_Toe;

public class Cell {
    private int row;
    private int column;
    private PlayerSymbol playerSymbol;

    public Cell(int row, int column, PlayerSymbol playerSymbol) {
        this.row = row;
        this.column = column;
        this.playerSymbol = playerSymbol;
    }

    public PlayerSymbol getPlayerSymbol() {
        return playerSymbol;
    }

    public boolean isEmpty() {
        return playerSymbol == null;
    }
}
