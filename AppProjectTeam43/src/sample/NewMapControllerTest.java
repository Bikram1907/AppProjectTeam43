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

        GameDetails.getGamedetails().getgamedetails().add(new GameDetails(validfile,"NEWMAP"));
        GameDetails.getGamedetails().getgamedetails().add(new GameDetails(invalidfile,"NEWMAP"));
    }

    /**
     * This method tests whether new map is valid or not.
     */
    @Test
    void saveButtonAction() {

        GameDetails.getGamedetails().createMap(0);
        assertTrue(GameDetails.getGamedetails().validateMap(0));

        GameDetails.getGamedetails().createMap(1);
        assertFalse(GameDetails.getGamedetails().validateMap(1));
    }
}
