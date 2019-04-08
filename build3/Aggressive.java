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

    private Territories strongestTerritory;

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

        Territories territory = getStrongestCountry(playersList.get(currentPlayer).getTerritoriesHeld());
        if(territory != null) {
            int noofArmies = playersList.get(currentPlayer).getPlayerArmies();
            playersList.get(currentPlayer).getTerritoriesHeld().get(territory.getTerritorieName())
                    .increaseArmyCountByValue(noofArmies);
            playersList.get(currentPlayer).decreaseArmyCountByValue(noofArmies);
            return territory.getTerritorieName();
        }
        return null;
   }
    
  /**
  * This method returns the strongest country to start the attack from and attacking country.
  * @param playersList
  * @param currentPlayer
  * @param isPlayerAttackerOrDefender
  * @return country name
  */
    @Override
    public String attack(ObservableList<Player> playersList, int currentPlayer,String isPlayerAttackerOrDefender) {

        if(isPlayerAttackerOrDefender.equalsIgnoreCase(Constants.ATTACKER)) {
            Territories strongestCountry = getStrongestCountry(playersList.get(currentPlayer).getTerritoriesHeld());
            playersList.get(currentPlayer).setAttackingCountry(strongestCountry);
            return strongestCountry.getTerritorieName();
        } else {
            // get the neighbouring country of the attacker country
            Territories strongestCountry = playersList.get(currentPlayer).getAttackingCountry();
            String attackedCountry = getAttackedCountry(strongestCountry,playersList.get(currentPlayer).getTerritoriesHeld());
            playersList.get(currentPlayer).setDefendingCountry(attackedCountry);
            return attackedCountry;
        }
    }
    
    /**
     * Returns the source country from where fortification is done and the fortifying country.
     * @param playersList
     * @param currentPlayer
     * @return country name
     */
    @Override
    public String fortify(ObservableList<Player> playersList, int currentPlayer,String isToFindFortifyingCountryOrSourceCountry) {

        if(isToFindFortifyingCountryOrSourceCountry.equalsIgnoreCase(Constants.SOURCECOUNTRY)) {
            Territories sourceTerritory = getStrongestCountryAdjacentStrongestCountry(playersList.get(currentPlayer).getTerritoriesHeld());
            playersList.get(currentPlayer).setSourceCountry(sourceTerritory);

            if(sourceTerritory !=null && sourceTerritory.getArmiesHeld() > 1) {
                return sourceTerritory.getTerritorieName();
            }
        } else {
            Territories strongCountry = getStrongestCountry(playersList.get(currentPlayer).getTerritoriesHeld());
            if(strongCountry != null) {
                return strongCountry.getTerritorieName();
            }
        }
        return null;
    }
    
    /**
     * This method returns the strongest country to start the attack from.
     * @param territoriesHeld
     * @return country name
     */
    public Territories getStrongestCountry(HashMap<String,Territories> territoriesHeld) {

        Territories strongestCountry = territoriesHeld.get(territoriesHeld.keySet().toArray()[0]);

        for(String key : territoriesHeld.keySet()) {
            Territories territory = territoriesHeld.get(key);
            if(territory.getArmiesHeld() > strongestCountry.getArmiesHeld()) {
                strongestCountry = territory;
            }
        }

        if(strongestCountry.getArmiesHeld() == 1) {
            Random r = new Random();
            List<String> keyset = new ArrayList<>(territoriesHeld.keySet());
            strongestCountry = territoriesHeld.get(keyset.get(r.nextInt(keyset.size())));
        }

        return strongestCountry;
    }
}
