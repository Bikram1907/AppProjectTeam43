package sample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

class GameDetailsTest {

    private File validfile;
    private File invalidfile;
    private File notconnectedfile;
    private GameDetails validObject = new GameDetails();
    private GameDetails invalidObject = new GameDetails();
    private GameDetails notconnectedObject = new GameDetails();
    private HashMap<String,String> playerCharacters = new HashMap<String,String>();
    private int noofarmiesofPlayers;


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
    }

    @Test
    void validateMap() {

        GameDetails.getGamedetails().createMap(0);
        assertTrue(GameDetails.getGamedetails().validateMap(0));

        GameDetails.getGamedetails().createMap(1);
        assertFalse(GameDetails.getGamedetails().validateMap(1));

        GameDetails.getGamedetails().createMap(2);
        assertFalse(GameDetails.getGamedetails().validateMap(2));
    }

    @Test
    void distributeArmies() {

        GameDetails.getGamedetails().distributeArmies(0);
        noofarmiesofPlayers = GameDetails.getGamedetails().getgamedetails().get(0).getPlayersList().get(0).getPlayerArmies();
        assertEquals(20,noofarmiesofPlayers);

    }

    void setPlayerCharacters() {

        playerCharacters.put("Player1","Human");
        playerCharacters.put("Player2","Human");
        playerCharacters.put("Player3","Human");
        playerCharacters.put("Player4","Human");
        playerCharacters.put("Player5","Human");
        playerCharacters.put("Player6","Human");
    }

}