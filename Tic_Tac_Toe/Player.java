package Tic_Tac_Toe;

public class Player {
    private String playerID;
    private String playerName;
    private char playerSymbol;

    public Player(String playerID, String playerName, char playerSymbol) {
        this.playerID = playerID;
        this.playerName = playerName;
        this.playerSymbol = playerSymbol;
    }

    public String getPlayerName() {
        return playerName;
    }

    public char getPlayerSymbol() {
        return playerSymbol;
    }
}
