package sample.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class will store the tournament mode inputs and results.
 */
public class TournamentMode {
  
  private static TournamentMode ourInstance = new TournamentMode();

  /**
  * returns the object.
  */
  public static TournamentMode getInstance() {
      return ourInstance;
  }
  
  /**
  * An empty Constructor
  */
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

    /**
     * This method sets the results.
     * @param results
     */
    public void setResults(ArrayList<ArrayList<String>> results) {
        this.results = results;
    }

    /**
     * To set the index
     * @param index
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Stores the user input map files
     * @param mapFiles
     */
    public void setMapFiles(ArrayList<File> mapFiles) {
        this.mapFiles = mapFiles;
    }

    /**
     * Stores the user input characters.
     * @param playerCharacters
     */
    public void setPlayerCharacters(HashMap<String, String> playerCharacters) {
        this.playerCharacters = playerCharacters;
    }

    /**
     * Stores the no of turns.
     * @param noofTurns
     */
    public void setNoofTurns(int noofTurns) {
        this.noofTurns = noofTurns;
    }
  
}
