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
    
    /**
     * This method fortifies the weakest country with the strongest country adjacent to the weakest country
     * @param playersList
     * @param currentPlayer
     * @param isToFindFortifyingCountryOrSourceCountry
     * @return country name
     */
    @Override
    public String fortify(ObservableList<Player> playersList, int currentPlayer,String isToFindFortifyingCountryOrSourceCountry) {

        // To get the source country
        if(isToFindFortifyingCountryOrSourceCountry.equalsIgnoreCase(Constants.SOURCECOUNTRY)) {
            String strongestAdjacent = getWeakestTerritoryStrongestAdjacent(playersList.get(currentPlayer).getTerritoriesHeld());
            return strongestAdjacent;
        // To get the fortifying country.
        } else if(isToFindFortifyingCountryOrSourceCountry.equalsIgnoreCase(Constants.FORTIFYINGCOUNTRY)) {
            String weakestTerritory = getWeakestTerritory(playersList.get(currentPlayer).getTerritoriesHeld());
            return weakestTerritory;
        }
        return null;
    }
    
    /**
     * This method returns the weakest territory from the territories of the player.
     * @param territoriesHeld
     * @return  country name
     */
    public String getWeakestTerritory(HashMap<String, Territories> territoriesHeld) {

        Territories weakTerritory = territoriesHeld.get(territoriesHeld.keySet().toArray()[0]);
        Vector<Territories> territories = new Vector<>();
        for(String key : territoriesHeld.keySet()) {
            Territories territory = territoriesHeld.get(key);
            if(territory.getArmiesHeld() < weakTerritory.getArmiesHeld()) {
                weakTerritory = territory;
            }
            
            if(territory.getArmiesHeld() == 1) {
                territories.add(territory);
            }
        }
        if(territories.size() > 1) {
            Random r = new Random();
            weakTerritory = territories.get(r.nextInt(territories.size()));
        }
        return weakTerritory.getTerritorieName();
    }
}
