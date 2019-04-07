package sample.strategies;

import javafx.collections.ObservableList;
import sample.model.Constants;
import sample.model.Player;
import sample.model.Territories;

import java.util.*;
import java.util.Random;

/**
 * This class implements the Strategy interface methods.
 */
public class Aggressive implements Strategy {

    private Territorries strongestTerritory;

    /**
     * An empty constructor.
     */
    public Aggressive() {
    }
    
    /**
     * Returns the reinforcing country.
     * @param playersList
     * @param currentPlayer
     * @return country name
     */
    @Override
    public String reinforce(ObservableList<Player> playersList, int currentPlayer) {

        Territories territory = getStrongestCountries(playersList.get(currentPlayer).getTerritoriesHeld());
        if(territory != null) {
            int noofArmies = playersList.get(currentPlayer).getPlayerArmies();
            playersList.get(currentPlayer).getTerritoriesHeld().get(territory.getTerritorieName())
                    .increaseArmyCountByValue(noofArmies);
            playersList.get(currentPlayer).decreaseArmyCountByValue(noofArmies);
            return territory.getTerritorieName();
        }
        return null;
    }
