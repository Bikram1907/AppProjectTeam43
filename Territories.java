package sample;

import java.util.ArrayList;

/**
 * This class is used to maintain territories and methods used to store and retrieve information.
 */

public class Territories {

    private String territorieName;
    private int x_Position;
    private int y_Position;
    private int armiesHeld;
    private Boolean hasPlayer;
    private Player player;
    private String continentName;
    private ArrayList<String> adjacentTerritories;

    /**
     * Constructor with parameters.
     * @param territorieName
     * @param x_Position
     * @param y_Position
     * @param armiesHeld
     * @param hasPlayer
     */
    public Territories(String territorieName, int x_Position, int y_Position, int armiesHeld, Boolean hasPlayer) {
        this.territorieName = territorieName;
        this.x_Position = x_Position;
        this.y_Position = y_Position;
        this.armiesHeld = armiesHeld;
        this.hasPlayer = hasPlayer;
    }

    /**
     * Constructor with parameters.
     * @param territorieName
     * @param x_Position
     * @param y_Position
     * @param continentName
     * @param adjacentTerritories
     */
    public Territories(String territorieName, int x_Position, int y_Position, String continentName, ArrayList<String> adjacentTerritories) {
        this.territorieName = territorieName;
        this.x_Position = x_Position;
        this.y_Position = y_Position;
        this.continentName = continentName;
        this.adjacentTerritories = adjacentTerritories;
    }
}