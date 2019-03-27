package sample.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import sample.model.Card;
import sample.model.GameDetails;
import sample.model.Player;

/**
 * This class is responsible for handling the cards exchange view.
 * This class checks for cards sets from the player cards.
 * if any set has found then it will exchange the cards for armies.
 * @author Team43
 */
public class CardExchangeController {

    @FXML
    private Label cardHoldedLabel;

    @FXML
    private ListView cardsListView;

    @FXML
    private Button handInButton;

    @FXML
    private AnchorPane anchorPane;
    private ObservableList<Player> playerList;
    private ObservableList<Card> originalCardList;
    private ObservableList<Card> cardsList;
    private int currentPlayer;

    /**
     * An empty initialize method.
     */
    @FXML
    public void initialize() {

    }

    /**
     * This method adds the cards to the list view.
     * @param cardsList
     */
    @FXML
    public void addDataToTheListView(ObservableList<Card> cardsList) {

        cardsListView.getItems().addAll(cardsList);
    }

    /**
     * This method gets the playerList the gameController.
     * @param playerList
     */
    public void getPlayerListFromTheController(ObservableList<Player> playerList) {

        this.playerList = playerList;
    }
}