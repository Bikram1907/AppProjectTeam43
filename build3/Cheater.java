package sample.strategies;

import javafx.collections.ObservableList;
import sample.model.Player;
import sample.model.Territories;

import java.util.Vector;

/**
 * This class implements the Strategy interface methods.
 */
public class Cheater implements Strategy {

    /**
     * An empty constructor.
     */
    public Cheater() {
    }

    /**
     * This function reinforces all the countries by doubling the armies of the territory.
     * @param playersList
     * @param currentPlayer
     * @return null
     */
    @Override
    public String reinforce(ObservableList<Player> playersList, int currentPlayer) {

        for(String key : playersList.get(currentPlayer).getTerritoriesHeld().keySet()) {
            Territories territory = playersList.get(currentPlayer).getTerritoriesHeld().get(key);
            playersList.get(currentPlayer).getTerritoriesHeld().get(key).increaseArmyCountByValue(territory.getArmiesHeld());
            System.out.println("Doubling the armies of the country = " + territory.getTerritorieName());
        }
        playersList.get(currentPlayer).setPlayerArmies(0);
        return null;
    }
