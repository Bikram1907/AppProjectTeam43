package sample;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

class NewMapControllerTest {

    private File validfile;
    private File invalidfile;

    @BeforeEach
    void setUp() {

        validfile = new File("E:\\IntelliJ\\AppProject\\src\\resources\\Maps\\World.map");
        invalidfile = new File("E:\\IntelliJ\\AppProject\\src\\resources\\Maps\\invalid.map");

        GameDetails.getGamedetails().getgamedetails().add(new GameDetails(validfile,"NEWMAP"));
        GameDetails.getGamedetails().getgamedetails().add(new GameDetails(invalidfile,"NEWMAP"));
    }

    @Test
    void saveButtonAction() {

        GameDetails.getGamedetails().createMap(0);
        assertTrue(GameDetails.getGamedetails().validateMap(0));

        GameDetails.getGamedetails().createMap(1);
        assertFalse(GameDetails.getGamedetails().validateMap(1));
    }
}