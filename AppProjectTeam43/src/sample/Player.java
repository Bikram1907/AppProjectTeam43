package sample;

import javafx.scene.paint.Color;

import java.util.HashMap;

/**
 * This class maintains the player details and implements methods related to the player.
 */
public class Player {

    private String playerId;
    private String playerName;
    private int playerArmies;
    private Boolean isKnocked;
    private String playerCharacter;
    private Color playerColor;
    private int cardsHolded;
    private HashMap<String, Territories> territoriesHeld;
    private HashMap<String, Continent> continentHeld;

    /**
     * Constructor with parameters.
     *
     * @param playerName
     * @param playerArmies
     * @param isKnocked
     * @param playerCharacter
     * @param playerColor
     * @param cardsHolded
     */
    public Player(String playerName, int playerArmies, Boolean isKnocked, String playerCharacter, Color playerColor, int cardsHolded) {

        this.playerName = playerName;
        this.playerArmies = playerArmies;
        this.isKnocked = isKnocked;
        this.playerCharacter = playerCharacter;
        this.playerColor = playerColor;
        this.cardsHolded = cardsHolded;
    }

    /**
     * constructor with parameters.
     * @param playerId
     * @param playerName
     * @param playerArmies
     * @param isKnocked
     * @param cardsHolded
     */
    public Player(String playerId, String playerName, int playerArmies, Boolean isKnocked, int cardsHolded) {

        this.playerId = playerId;
        this.playerName = playerName;
        this.playerArmies = playerArmies;
        this.isKnocked = isKnocked;
        this.cardsHolded = cardsHolded;
    }

    /**
     * To set player name.
     * @param playerName
     */
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

}