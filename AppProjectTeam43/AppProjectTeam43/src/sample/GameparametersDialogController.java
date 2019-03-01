package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;

import java.io.File;
import java.util.HashMap;

public class GameparametersDialogController {

    @FXML
    private ComboBox NumberofPlayers,player1,player2,player3,player4,player5,player6;

    @FXML
    private Label labelPlayer1,labelPlayer2,labelPlayer3,labelPlayer4,labelPlayer5,labelPlayer6;

    private Boolean setPlayer1,setPlayer2,setPlayer3,setPlayer4,setPlayer5,setPlayer6;

    @FXML
    private Label mapLabel;

    @FXML
    private Button chooseMapbutton;

    @FXML
    private DialogPane gameSettingsdialog;

    private File selectedfile;

    private String noofPlayers;

    private HashMap<String,String> playerCharacters = new HashMap<String,String>();

    /**
     * Get the number of players count.
     */
    public void getNumberofPlayers() {

        noofPlayers = (String) NumberofPlayers.getSelectionModel().getSelectedItem();
        System.out.println("User has selected number of players to be = "+noofPlayers);
        disableMapbutton(false);
        setPlayersCharacters(noofPlayers);
    }

    /**
     * This method is used to display combo boxes based on number of players choosed.
     * @param noofPlayers
     */
    public void setPlayersCharacters(String noofPlayers) {

        disablePlayerCombobox(false,false,false,false,false,false);

        switch (noofPlayers) {
            case "2":
                disablePlayerCombobox(true,true,false,false,false,false);
                disableMapbutton(true);
                break;
            case "3":
                disablePlayerCombobox(true,true,true,false,false,false);
                disableMapbutton(true);
                break;
            case "4":
                disablePlayerCombobox(true,true,true,true,false,false);
                disableMapbutton(true);
                break;
            case "5":
                disablePlayerCombobox(true,true,true,true,true,false);
                disableMapbutton(true);
                break;
            case "6":
                disablePlayerCombobox(true,true,true,true,true,true);
                disableMapbutton(true);
                break;

        }

    }
}