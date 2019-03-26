package sample.test;

import sample.model.GameDetails;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class GameDetailsTest1 {
    private File validfile;
    private File invalidfile;
    private GameDetails validObject = new GameDetails();
    private GameDetails invalidObject = new GameDetails();

    @org.junit.Before
    public void setUp() throws Exception {
        validfile = new File("E:\\IntelliJ\\AppProject\\src\\resources\\Maps\\World.map");
        invalidfile = new File("E:\\IntelliJ\\AppProject\\src\\resources\\Maps\\invalid.map");

        validObject.setMapFile(validfile);
        invalidObject.setMapFile(invalidfile);
        validObject.setNumberOfPlayers(6);
        invalidObject.setNumberOfPlayers(5);

        GameDetails.getGamedetails().getgamedetails().add(validObject);
        GameDetails.getGamedetails().getgamedetails().add(invalidObject);

        GameDetails.getGamedetails().IntializeColors(0);
        GameDetails.getGamedetails().IntializeColors(1);

        GameDetails.getGamedetails().IntializePlayers(0);
        GameDetails.getGamedetails().IntializePlayers(1);

        GameDetails.getGamedetails().InitializeArmies(0);
        GameDetails.getGamedetails().InitializeArmies(1);

        GameDetails.getGamedetails().distributeArmies(0);
        GameDetails.getGamedetails().distributeArmies(1);
    }
    @org.junit.After
    public void tearDown() throws Exception {
    }

    /**
     * This method checks the map validation.
     */
    @org.junit.Test
    public void validateMap() {

        GameDetails.getGamedetails().createMap(0);
        boolean result = GameDetails.getGamedetails().validateMap(0);
        assertTrue(result);

        GameDetails.getGamedetails().createMap(1);
        assertFalse(GameDetails.getGamedetails().validateMap(1));
    }
    @org.junit.Test
    public void distributeArmies() {

        int noofarmiesofPlayers = GameDetails.getGamedetails().getgamedetails().get(0).getPlayersList().get(0).getPlayerArmies();
        assertEquals(20,noofarmiesofPlayers);

        noofarmiesofPlayers = GameDetails.getGamedetails().getgamedetails().get(1).getPlayersList().get(0).getPlayerArmies();
        assertEquals(25,noofarmiesofPlayers);
    }
}
