package Tic_Tac_Toe;
import java.util.Objects;

public class Player {
    private String playerID;
    private String playerName;
    private PlayerSymbol playerSymbol;

    public Player(String playerID, String playerName, PlayerSymbol playerSymbol) {
        this.playerID = Objects.requireNonNull(playerID, "Player ID cannot be null");
        this.playerName = Objects.requireNonNull(playerName, "Player name cannot be null");
        this.playerSymbol = Objects.requireNonNull(playerSymbol, "Player symbol cannot be null");
    }

    public String getPlayerName() {
        return playerName;
    }

    public PlayerSymbol getPlayerSymbol() {
        return playerSymbol;
    }
}
