package sample.test;

import javafx.collections.ObservableList;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import sample.model.*;

import java.io.File;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class PlayerTest1 {

    private File mapfile;
    private ObservableList<Player> playerList;

    @Before
    public void setUp() throws Exception {
        GameDetails.getGamedetails().getgamedetails().clear();
        mapfile = new File("E:\\IntelliJ\\AppProject\\src\\resources\\Maps\\World.map");
        GameDetails gameObject = new GameDetails(2,mapfile,"NEWGAME","STARTPHASE");
        GameDetails.getGamedetails().getgamedetails().add(gameObject);
        GameDetails.getGamedetails().createMap(0);
        boolean result = GameDetails.getGamedetails().validateMap(0);
        GameDetails.getGamedetails().IntializeColors(0);
        GameDetails.getGamedetails().IntializePlayers(0);
        GameDetails.getGamedetails().InitializeArmies(0);
        GameDetails.getGamedetails().distributeArmies(0);
        GameDetails.getGamedetails().distributeTerritories(0);
        GameDetails.getGamedetails().distributeArmiestoTerritories(0);
        playerList = GameDetails.getGamedetails().getgamedetails().get(0).getPlayersList();
    }
    
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void increaseArmyCountByValue() {
        playerList.get(0).increaseArmyCountByValue(5);
        assertEquals(playerList.get(0).getPlayerArmies(),24);
    }

    @Test
    public void decreaseArmyCountByValue() {
        playerList.get(0).decreaseArmyCountByValue(5);
        assertEquals(playerList.get(0).getPlayerArmies(),14);
    }

    @Test
    public void addCard() {
        playerList.get(0).addCard(new Card("ALASKA","INFANTRY"));
        assertEquals(playerList.get(0).getCardsHeld().size(),1);
    }
    
    @Test
    public void removeCard() {
        playerList.get(0).addCard(new Card("ALASKA","INFANTRY"));
        Card card = new Card("AUSTRALIA","CAVALRY");
        playerList.get(0).addCard(card);
        playerList.get(0).removeCard(card);
        assertEquals(playerList.get(0).getCardsHeld().size(),1);
    }

    @Test
    public void addTerritory() {
        Territories territory = playerList.get(1).getTerritoriesHeld().get("Venezuala");
        playerList.get(0).addTerritory(territory);
        playerList.get(1).getTerritoriesHeld().remove("Venezuala");
        assertEquals(playerList.get(0).getTerritoriesHeld().size(),22);
    }
    
     @Test
    public void removeTerritory() {
        Territories territory = playerList.get(1).getTerritoriesHeld().get("Venezuala");
        playerList.get(1).removeTerritory(territory);
        assertEquals(playerList.get(1).getTerritoriesHeld().size(),20);
    }

    @Test
    public void addContinent() {
        Continent continent = GameDetails.getGamedetails().getgamedetails().get(0).getContinentList().get(0);
        playerList.get(0).addContinent(continent);
        assertEquals(playerList.get(0).getContinentHeld().size(),1);
    }
    
    @Test
    public void removeContinent() {
        Continent continent = GameDetails.getGamedetails().getgamedetails().get(0).getContinentList().get(0);
        playerList.get(0).addContinent(continent);
        playerList.get(0).removeContinent(continent.getContinentName());
        assertEquals(playerList.get(0).getContinentHeld().size(),0);
    }

    @Test
    public void calculateReinforcementArmies() {
        playerList.get(0).calculateReinforcementArmies();
        assertEquals(playerList.get(0).getPlayerArmies(),26);
    }
    
    @Test
    public void canFortify() {
        assertTrue(playerList.get(0).canFortify("Peru","Brazil"));
    }

    @Test
    public void doFortification() {
        playerList.get(0).getTerritoriesHeld().get("Peru").setArmiesHeld(10);
        playerList.get(0).doFortification(3,"Peru","Brazil");
        assertEquals(playerList.get(0).getTerritoriesHeld().get("Brazil").getArmiesHeld(),4);
        assertEquals(playerList.get(0).getTerritoriesHeld().get("Peru").getArmiesHeld(),7);
    }

    @Test
    public void canAttack() {
        assertTrue(playerList.get(0).canAttack("Peru","Venezuala"));
    }

    @Test
    public void doAttack() {
        playerList.get(0).getTerritoriesHeld().get("Peru").setArmiesHeld(10);
        assertNotEquals(playerList.get(0).doAttack(3,2,"Peru","Venezuala",playerList.get(1)),"WINNER");
    }

    @Test
    public void rollDice() {

        ArrayList<Integer> diceList = playerList.get(0).rollDice(3);
        assertEquals(diceList.size(),3);
    }
    @Test
    public void determineWinner() {
        ArrayList<Integer> diceA = new ArrayList<Integer>();
        diceA.add(5);
        diceA.add(3);
        diceA.add(2);

        ArrayList<Integer> diceB = new ArrayList<Integer>();
        diceA.add(4);
        diceA.add(4);

        int attackerDecreamentArmy=0,attackedDecreamentArmy=0,index = 0;

        if(diceB.size() < diceA.size()) {
            index = diceB.size();
        } else if(diceA.size() < diceB.size()) {
            index = diceA.size();
        } else if(diceA.size() == diceB.size()) {
            index = diceB.size();
        }

        for(int i = 0; i<index; i++) {
            if(diceA.get(i) > diceB.get(i)) {
                attackedDecreamentArmy++;
            } else {
                attackerDecreamentArmy++;
            }
        }

        String result = "";
        if(attackerDecreamentArmy < attackedDecreamentArmy) {
            result = "WINNER";
        } else if(attackerDecreamentArmy > attackedDecreamentArmy) {
            result = "LOSER";
        } else if(attackedDecreamentArmy == attackerDecreamentArmy) {
            result = "TIE";
        }

        assertEquals(result,"TIE");
    }
    
    @Test
    public void checkIfATerritoryHasLostAllArmies() {
        assertFalse(playerList.get(0).checkIfATerritoryHasLostAllArmies("Peru"));
    }

    @Test
    public void checkIfPlayerHasLostAllCountries() {
        assertFalse(playerList.get(0).checkIfPlayerHasLostAllCountries());
    }

    @Test
    public void transferCardsFromOnePlayerToAnother() {
        Card card = new Card("ALASKA","INFANTRY");
        playerList.get(1).addCard(card);
        playerList.get(1).transferCardsFromOnePlayerToAnother(playerList.get(0));
        assertEquals(playerList.get(0).getCardsHeld().size(),1);
        assertEquals(playerList.get(1).getCardsHeld().size(),0);
    }
}
