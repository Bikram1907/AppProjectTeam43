package sample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the LoadgameController class methods.
 */
class LoadgameControllerTest {
   
    private File mapfile;
    private HashMap<String,String> playerCharacters = new HashMap<String,String>();
    private LoadgameController lgcObject = new LoadgameController();

    **
     * This method sets the objects that are needed to test the methods.
     */
    @BeforeEach
    void setUp() {
       
       setPlayerCharacters();
        mapfile = new File("E:\\IntelliJ\\AppProject\\src\\resources\\Maps\\World.map");
        GameDetails gameObject = new GameDetails(6,mapfile,playerCharacters,"NEWGAME");
        GameDetails.getGamedetails().getgamedetails().add(gameObject);
        GameDetails.getGamedetails().createMap(0);
        boolean result = GameDetails.getGamedetails().validateMap(0);
        GameDetails.getGamedetails().IntializeColors(0);
        GameDetails.getGamedetails().IntializePlayers(0);
        GameDetails.getGamedetails().InitializeArmies(0);
        GameDetails.getGamedetails().distributeArmies(0);
        GameDetails.getGamedetails().distributeTerritories(0);
        GameDetails.getGamedetails().distributeArmiestoTerritories(0);

    }

    /**
     * This method tests if the territory army is increamenting or not when user clicks.
     */
    @Test
    void mouseclicked() {
       
        GameDetails.getGamedetails().getgamedetails().get(0).getPlayersList().get(0).setPlayerArmies(2);
        String[] keys = GameDetails.getGamedetails().getgamedetails().get(0).getPlayersList().get(0).getTerritoriesHeld().keySet().toArray(new
                String[GameDetails.getGamedetails().getgamedetails().get(0).getPlayersList().get(0).getTerritoriesHeld().size()]);

        int noofarmies = GameDetails.getGamedetails().getgamedetails().get(0).getPlayersList().get(0).getTerritoriesHeld().get(keys[0]).getArmiesHeld();

        GameDetails.getGamedetails().getgamedetails().get(0).getPlayersList().get(0).getTerritoriesHeld().get(keys[0]).increamentarmyCountby1();

        int aftermouseclickedArmies = GameDetails.getGamedetails().getgamedetails().get(0).getPlayersList().get(0).getTerritoriesHeld().get(keys[0]).getArmiesHeld();

        assertNotEquals(noofarmies,aftermouseclickedArmies);

    }
   
    /**
     * This method tests the if the reinforcement armies are calculated properly or not.
     */
    @Test
    void calculateReinforcementArmies() {
       
        int reinforcementarmies = GameDetails.getGamedetails().getgamedetails().get(0).getPlayersList().get(0).getTerritoriesHeld().size();
        assertEquals(2,reinforcementarmies/3);
        assertEquals(3,10/3);
        assertEquals(4,14/3);
    }
   
   /**
     * This method sets the player characters.
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
