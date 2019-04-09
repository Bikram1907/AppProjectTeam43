package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import sample.model.Constants;
import sample.model.GameDetails;
import sample.model.TournamentMode;

import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class is resposible for handling the tournament view
 */
public class TournamentController {
  
  @FXML
  public Label noofPlayersLabel,player1Label,player2Label,player3Label,player4Label,noOfGamesLabel,noOfTurnsLabel,
            noOfMapsLabel;

  @FXML
  public ComboBox noofPlayersComboBox,player1ComboBox,player2ComboBox,player3ComboBox,player4ComboBox,
            noOfGamesComboBox,noOfTurnsComboBox,noOfMapsComboBox;

  @FXML
  public Button chooseMapButton1,chooseMapButton2,chooseMapButton3,chooseMapButton4,chooseMapButton5;

  public String noofPlayers,noofGames,noofTurns,noofMaps;

  public File mapFile1,mapFile2,mapFile3,mapFile4,mapFile5;

  public HashMap<String,String> playerCharacters = new HashMap<>();

  @FXML
  private AnchorPane tAnchorPane;
  
  /**
     * To get the no of players from the view.
     */
    public void getNoOfPlayers() {

        displayPlayerComboBox(false,false,false,false);
        noofPlayers = (String) noofPlayersComboBox.getSelectionModel().getSelectedItem();
        setPlayers(noofPlayers);
    }
  
}
