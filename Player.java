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
    /**
     * To set Number of armies.
     * @param playerArmies
     */
    public void setPlayerArmies(int playerArmies) {
        this.playerArmies = playerArmies;
    }

    /**
     * Set if player knocked.
     * @param knocked
     */
    public void setKnocked(Boolean knocked) {
        isKnocked = knocked;
    }

    /**
     * Set Player's character.
     * @param playerCharacter
     */
    public void setPlayerCharacter(String playerCharacter) {
        this.playerCharacter = playerCharacter;
    }

    /**
     * Set Player's color.
     * @param playerColor
     */
    public void setPlayerColor(Color playerColor) {
        this.playerColor = playerColor;
    }

    /**
     * Set number of cards holded.
     * @param cardsHolded
     */
    public void setCardsHolded(int cardsHolded) {
        this.cardsHolded = cardsHolded;
    }

    /**
     * Set number of territories held by the player.
     * @param territoriesHeld
     */
    public void setTerritoriesHeld(HashMap<String, Territories> territoriesHeld) {
        this.territoriesHeld = territoriesHeld;
    }

    /**
     * Set number of continents held by the player.
     * @param continentHeld
     */
    public void setContinentHeld(HashMap<String, Continent> continentHeld) {
        this.continentHeld = continentHeld;
    }

    /**
     * To set the player Id;
     * @param playerId
     */
    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    /**
     * returns the Player name.
     * @return
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * returns the number of armies player held.
     * @return
     */
    public int getPlayerArmies() {
        return playerArmies;
    }

    /**
     * returns if the player is knocked out or not.
     * @return
     */
    public Boolean getKnocked() {
        return isKnocked;
    }

    /**
     * returns the players character.
     * @return
     */
    public String getPlayerCharacter() {
        return playerCharacter;
    }

    /**
     * returns players color.
     * @return
     */
    public Color getPlayerColor() {
        return playerColor;
    }

    /**
     * returns the number of cards players held.
     * @return
     */
    public int getCardsHolded() {
        return cardsHolded;
    }

    /**
     * returns the territories player held.
     * @return
     */
    public HashMap<String, Territories> getTerritoriesHeld() {
        return territoriesHeld;
    }

    /**
     * returns the continents player held.
     * @return
     */
    public HashMap<String, Continent> getContinentHeld() {
        return continentHeld;
    }
}