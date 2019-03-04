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
    /**
     * set the continent name.
     * @param continentName
     */
    public void setContinentName(String continentName) {
        this.continentName = continentName;
    }

    /**
     * set the continent score.
     * @param continentScore
     */
    public void setContinentScore(int continentScore) {
        this.continentScore = continentScore;
    }

    /**
     * set the adjacent continents.
     * @param adjacentContinents
     */
    public void setAdjacentContinents(ArrayList<Continent> adjacentContinents) {
        this.adjacentContinents = adjacentContinents;
    }

    /**
     * set the territories.
     * @param territoriesHeld
     */
    public void setTerritoriesHeld(ArrayList<Territories> territoriesHeld) {
        this.territoriesHeld = territoriesHeld;
    }
}