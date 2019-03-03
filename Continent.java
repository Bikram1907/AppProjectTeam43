package sample;

import java.util.ArrayList;

/**
 * This class contains information about the continent and methods to store and retrieve information.
 */
public class Continent {

    private String continentName;
    private int continentScore;
    private ArrayList<Continent> adjacentContinents;
    private ArrayList<Territories> territoriesHeld;

    /**
     * Constructor with parameters.
     * @param continentName
     * @param continentScore
     */
    public Continent(String continentName, int continentScore) {
        this.continentName = continentName;
        this.continentScore = continentScore;
    }
}