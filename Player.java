package sample.model;

import javafx.fxml.FXMLLoader;
import javafx.scene.paint.Color;
import sample.controller.LoadgameController;

import java.util.*;
import java.util.Map;

/**
 * This class maintains the player details and implements methods related to the player.
 * @author Team43
 */
public class Player extends Observable {

    private String playerId;
    private String playerName;
    private int playerArmies;
    private Boolean isKnocked;
    private String playerCharacter;
    private Color playerColor;
    private int cardsHolded;
    private HashMap<String, Territories> territoriesHeld;
    private HashMap<String, Continent> continentHeld;
    private List<Card> cardsHeld;

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
     * To set the cards list.
     * @param cardsHeld
     */
    public void setCardsHeld(List<Card> cardsHeld) {
        this.cardsHeld = cardsHeld;
    }

    /**
     * To get the cards list.
     * @return
     */
    public List<Card> getCardsHeld() {
        return cardsHeld;
    }

    /**
     * returns the Player name.
     * @return playerName
     */
    public String getPlayerName() {
        return playerName;
    }

    /**
     * returns the number of armies player held.
     * @return playerArmies
     */
    public int getPlayerArmies() {
        return playerArmies;
    }

    /**
     * returns if the player is knocked out or not.
     * @return isKnocked
     */
    public Boolean getKnocked() {
        return isKnocked;
    }

    /**
     * returns the players character.
     * @return playerCharacter
     */
    public String getPlayerCharacter() {
        return playerCharacter;
    }

    /**
     * returns players color.
     * @return playerColor
     */
    public Color getPlayerColor() {
        return playerColor;
    }

    /**
     * returns the number of cards players held.
     * @return cardsHolded
     */
    public int getCardsHolded() {
        return cardsHolded;
    }

    /**
     * returns the territories player held.
     * @return territoriesHeld
     */
    public HashMap<String, Territories> getTerritoriesHeld() {
        return territoriesHeld;
    }

    /**
     * returns the continents player held.
     * @return continentHeld
     */
    public HashMap<String, Continent> getContinentHeld() {
        return continentHeld;
    }

    /**
     * To return the player Id.
     * @return playerId
     */
    public String getPlayerId() {
        return playerId;
    }

    /**
     * Overrides the method toString() method.
     * @return playerName
     */
    @Override
    public String toString() {
        return playerName;
    }

    /**
     * To increament player army count by 1.
     */
    public void increamentArmycountby1() {
        playerArmies += 1;
    }

    /**
     * To decreament player army count by 1.
     */
    public void decreamentArmycountby1() {
        playerArmies -= 1;
    }

    /**
     * To increase army count by some value.
     * @param value
     */
    public void increaseArmyCountByValue(int value) {

        playerArmies += value;
        setChanged();
        notifyObservers(playerArmies);
    }

    /**
     * To decrease army count by some value.
     * @param value
     */
    public void decreaseArmyCountByValue(int value) {

        playerArmies -= value;
        setChanged();
        notifyObservers(playerArmies);
    }
    /**
     * To add card to the list.
     * @param card
     */
    public void addCard(Card card) {

        cardsHeld.add(card);
    }

    /**
     * To remove the card from the list.
     * @param card
     */
    public void removeCard(Card card) {

        cardsHeld.remove(card);
    }
     /**
     * To add the territory to the list and notify observers.
     * @param territory
     */
    public void addTerritory(Territories territory) {

        territoriesHeld.put(territory.getTerritorieName(),territory);
        setChanged();
        notifyObservers(territoriesHeld);
    }

}
