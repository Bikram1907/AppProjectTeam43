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
    
    private TournamentMode() {
    }

    /**
     * This method sets the no of maps.
     * @param noofMaps
     */
    public void setNoofMaps(int noofMaps) {
        this.noofMaps = noofMaps;
    }
    
    /**
     * This method sets the no of games.
     * @param noofGames
     */
    public void setNoofGames(int noofGames) {
        this.noofGames = noofGames;
    }
    
    /**
     * This method sets the no of players.
     * @param noofPlayers
     */
    public void setNoofPlayers(int noofPlayers) {
        this.noofPlayers = noofPlayers;
    }
}
