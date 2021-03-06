package sample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the GameDetails class methods.
 */
class GameDetailsTest {

    private File validfile;
    private File invalidfile;
    private File notconnectedfile;
    private GameDetails validObject = new GameDetails();
    private GameDetails invalidObject = new GameDetails();
    private GameDetails notconnectedObject = new GameDetails();
    private HashMap<String,String> playerCharacters = new HashMap<String,String>();
    private int noofarmiesofPlayers;

    /**
     * This method setups the initial objects need to test the methods.
     */
    @BeforeEach
    void setUp() {

        setPlayerCharacters();

        validfile = new File("E:\\IntelliJ\\AppProject\\src\\resources\\Maps\\World.map");
        invalidfile = new File("E:\\IntelliJ\\AppProject\\src\\resources\\Maps\\invalid.map");
        notconnectedfile = new File("E:\\IntelliJ\\AppProject\\src\\resources\\Maps\\notconnected.map");

        validObject.setMapFile(validfile);
        invalidObject.setMapFile(invalidfile);
        notconnectedObject.setMapFile(notconnectedfile);

        validObject.setNumberOfPlayers(6);
        invalidObject.setNumberOfPlayers(5);
        notconnectedObject.setNumberOfPlayers(4);

        validObject.setPlayerCharacters(playerCharacters);
        invalidObject.setPlayerCharacters(playerCharacters);
        notconnectedObject.setPlayerCharacters(playerCharacters);

        GameDetails.getGamedetails().getgamedetails().add(validObject);
        GameDetails.getGamedetails().getgamedetails().add(invalidObject);
        GameDetails.getGamedetails().getgamedetails().add(notconnectedObject);

        GameDetails.getGamedetails().IntializeColors(0);
        GameDetails.getGamedetails().IntializeColors(1);
        GameDetails.getGamedetails().IntializeColors(2);

        GameDetails.getGamedetails().IntializePlayers(0);
        GameDetails.getGamedetails().IntializePlayers(1);
        GameDetails.getGamedetails().IntializePlayers(2);
        
        GameDetails.getGamedetails().InitializeArmies(0);
        GameDetails.getGamedetails().InitializeArmies(1);
        GameDetails.getGamedetails().InitializeArmies(2);

        GameDetails.getGamedetails().distributeArmies(0);
        GameDetails.getGamedetails().distributeArmies(1);
    }

    /**
     * This method checks the map validation.
     */
    @Test
    void validateMap() {

        GameDetails.getGamedetails().createMap(0);
        assertTrue(GameDetails.getGamedetails().validateMap(0));

        GameDetails.getGamedetails().createMap(1);
        assertFalse(GameDetails.getGamedetails().validateMap(1));

        GameDetails.getGamedetails().createMap(2);
        assertFalse(GameDetails.getGamedetails().validateMap(2));
    }

    /**
     * This method test if the armies are distributed properly or not.
     */
    @Test
    void distributeArmies() {

        noofarmiesofPlayers = GameDetails.getGamedetails().getgamedetails().get(0).getPlayersList().get(0).getPlayerArmies();
        assertEquals(20,noofarmiesofPlayers);
        
        noofarmiesofPlayers = GameDetails.getGamedetails().getgamedetails().get(1).getPlayersList().get(0).getPlayerArmies();
        assertEquals(25,noofarmiesofPlayers);

    }

    /**
     * This method setups the players characters.
     */
    void setPlayerCharacters() {

        playerCharacters.put("Player1","Human");
        playerCharacters.put("Player2","Human");
        playerCharacters.put("Player3","Human");
        playerCharacters.put("Player4","Human");
        playerCharacters.put("Player5","Human");
        playerCharacters.put("Player6","Human");
    }

}
