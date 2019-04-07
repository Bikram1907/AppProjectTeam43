package sample.strategies;

import javafx.collections.ObservableList;
import sample.model.Constants;
import sample.model.Player;
import sample.model.Territories;

import java.util.*;
import java.util.Random;

/**
 * This class implements the Strategy interface methods.
 */
public class Aggressive implements Strategy {

    private Territory strongestTerritory;

    /**
     * An empty constructor.
     */
    public Aggressive() {
    }