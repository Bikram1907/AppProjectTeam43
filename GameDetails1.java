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
}