package sample.model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class will store the tournament mode inputs and results.
 */
public class TournamentMode {
  
  private static TournamentMode ourInstance = new TournamentMode();

    public static TournamentMode getInstance() {
        return ourInstance;
    }
}
