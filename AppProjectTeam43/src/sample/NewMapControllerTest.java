package sample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This class tests the NewMapController class methods.
 */
class NewMapControllerTest {

    private File validfile;
    private File invalidfile;

    /**
     * This method sets the objects needed to test the methods.
     */
    @BeforeEach
    void setUp() {

        validfile = new File("E:\\IntelliJ\\AppProject\\src\\resources\\Maps\\World.map");
        invalidfile = new File("E:\\IntelliJ\\AppProject\\src\\resources\\Maps\\invalid.map");
    }

    /**
     * This method tests whether new map is valid or not.
     */
    @Test
    void saveButtonAction() {

        GameDetails.getGamedetails().clearData();
        GameDetails.getGamedetails().getgamedetails().add(new GameDetails(validfile,"NEWMAP"));
        GameDetails.getGamedetails().createMap(0);
        assertTrue(GameDetails.getGamedetails().validateMap(0));

        GameDetails.getGamedetails().clearData();
        GameDetails.getGamedetails().getgamedetails().add(new GameDetails(invalidfile,"NEWMAP"));
        GameDetails.getGamedetails().createMap(0);
        assertFalse(GameDetails.getGamedetails().validateMap(0));
    }
}
