package sample.strategies;

import javafx.collections.ObservableList;
import sample.model.Constants;
import sample.model.Player;
import sample.model.Territories;

import java.util.HashMap;
import java.util.Random;
import java.util.Vector;

/**
 * This class implements the Strategy interface methods.
 */
public class Benevolent implements Strategy {

    /**
     * An empty constructor.
     */
    public Benevolent() {
    }

     /**
     * This method finds the weakest country to reinforce.
     * @param playersList
     * @param currentPlayer
     * @return country name
     */
    @Override
    public String reinforce(ObservableList<Player> playersList, int currentPlayer) {

        String reinforcingCountry = getWeakestTerritory(playersList.get(currentPlayer).getTerritoriesHeld());
        if(reinforcingCountry != null) {
            int noofArmies = playersList.get(currentPlayer).getPlayerArmies();
            playersList.get(currentPlayer).getTerritoriesHeld().get(reinforcingCountry).increaseArmyCountByValue(noofArmies);
            playersList.get(currentPlayer).decreaseArmyCountByValue(noofArmies);
        }
        return reinforcingCountry;
    }
    
    /**
     * This method skips to the next phase.
     * @param playersList
     * @param currentPlayer
     * @param isPlayerAttackerOrDefender
     * @return null
     */
    @Override
    public String attack(ObservableList<Player> playersList, int currentPlayer,String isPlayerAttackerOrDefender) {
        // Do nothing skip to next phase.
        return null;
    }
}
