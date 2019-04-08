package sample.strategies;

import javafx.collections.ObservableList;
import sample.model.Player;

/**
 * This interface contains methods to handle the phases of the game.
 */
public interface Strategy {

    /**
     * This method implements the reinforcement phase using player strategies.
     * @param playersList
     * @param currentPlayer
     * @return country name
     */
    String reinforce(ObservableList<Player> playersList, int currentPlayer);
    
    /**
     * This method implements the fortification using the player strategies.
     * @param playersList
     * @param currentPlayer
     * @return county name
     */

     String attack(ObservableList<Player> playersList, int currentPlayer,String isPlayerAttackerOrDefender);
    
     String fortify(ObservableList<Player> playersList, int currentPlayer,String isToFindFortifyingCountryOrSourceCountry);



