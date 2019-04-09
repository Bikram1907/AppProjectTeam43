package sample.model;

import java.util.ArrayList;

/**
 * This class will store the tournament mode inputs and results.
 */
public class TournamentMode {

    private int noofMaps;
    private int noofGames;
    private int noofPlayers;
    private ArrayList<ArrayList<String>> results = new ArrayList<>();

    private static TournamentMode ourInstance = new TournamentMode();

    public static TournamentMode getInstance() {
        return ourInstance;
    }
}
