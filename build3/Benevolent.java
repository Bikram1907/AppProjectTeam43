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
     * @param player
     * @return country name
     */
    @Override
    public String reinforce(ObservableList<Player> playersList, int player) {

        String reinforcingCountry = getWeakestTerritory(playersList.get(player).getTerritoriesHeld());
        if(reinforcingCountry != null) {
            int noofArmies = playersList.get(player).getPlayerArmies();
            playersList.get(player).getTerritoriesHeld().get(reinforcingCountry).increaseArmyCountByValue(noofArmies);
            playersList.get(player).decreaseArmyCountByValue(noofArmies);
        }
        return reinforcingCountry;
    }
}
