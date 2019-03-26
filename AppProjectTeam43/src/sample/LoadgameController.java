package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.HashMap;

/**
 * This class loads the map into game window panel. Places the territories at their coordinates.
 * Displays the player statistics such as no of armies held by the player and the no of cards holded by the player
 */
public class LoadgameController {

    private ObservableList<Player> playersList;

    @FXML
    private GraphicsContext gc;

    private String imageName;

    private GameDetails gd;

    private Controller controller = new Controller();

    private int index;

    private HashMap<String,Territories> territoriesHashMap = new HashMap<String, Territories>();

    @FXML
    private Canvas canvasId;

    @FXML
    private Pane canvasPane,centerPane,bottomPane;

    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private Group group;

    private ObservableList<Group> groupList;

    @FXML
    private TextField armiesPlayer1,armiesPlayer2,armiesPlayer3,armiesPlayer4,armiesPlayer5,armiesPlayer6;

    @FXML
    private Label labelPlayer1,labelPlayer2,labelPlayer3,labelPlayer4,labelPlayer5,labelPlayer6;

    @FXML
    private TextField cardsPlayer1,cardsPlayer2,cardsPlayer3,cardsPlayer4,cardsPlayer5,cardsPlayer6;

    private int currentPlayer = 0;

    @FXML
    private Button finishedmoveButton;

    private HashMap<Integer,TextField> textFieldHashMap = new HashMap<Integer, TextField>();

    private int noofReinforcementArmies;

    private Image image;

    @FXML
    private Button closeButton;

    /**
     * To set up the map and buttons.
     */
    @FXML
    public void initialize() {

        groupList = FXCollections.observableArrayList();
        gc = canvasId.getGraphicsContext2D();
        index = controller.returnIndex("NEWGAME");
        gd = GameDetails.getGamedetails().getgamedetails().get(index);
        playersList = FXCollections.observableArrayList(gd.getPlayersList());
        //territoriesHashMap = gd.getTerritoriesList();

        textFieldHashMap.put(0,armiesPlayer1);
        textFieldHashMap.put(1,armiesPlayer2);
        textFieldHashMap.put(2,armiesPlayer3);
        textFieldHashMap.put(3,armiesPlayer4);
        textFieldHashMap.put(4,armiesPlayer5);
        textFieldHashMap.put(5,armiesPlayer6);

        drawMap();
        displayPlayerstats();
        //setPlayerstats();

        armiesPlayer1.setDisable(false);
        cardsPlayer1.setDisable(false);
        labelPlayer1.setDisable(false);

        disableTextnodes(currentPlayer);
        connectCountries();

        noofReinforcementArmies = calculateReinforcementArmies();
        playersList.get(currentPlayer).increaseArmyCountByValue(noofReinforcementArmies);
        armiesPlayer1.setText(Integer.toString(playersList.get(currentPlayer).getPlayerArmies()));
    }

    /**
     * Draws the map image into the window.
     */
    public void drawMap() {

        String mapImageName = gd.getMapName();
        System.out.println("Map name in LoadgameController class is " + mapImageName);
        image = new Image("resources/Maps/" + mapImageName + ".bmp");
        double imageWidth = image.getWidth();
        double imageHeight = image.getHeight();

        /*Stage stage = (Stage) mainBorderPane.getScene().getWindow();
        stage.setWidth(imageWidth);
        stage.setHeight(imageHeight);*/

        centerPane.prefWidth(imageWidth);
        centerPane.prefHeight(imageHeight);

        canvasId.setWidth(imageWidth);
        canvasId.setHeight(imageHeight);

        canvasPane.prefWidth(imageWidth);
        canvasPane.prefHeight(imageHeight);

        mainBorderPane.prefWidth(imageWidth);

        bottomPane.prefWidth(imageWidth);

        gc.drawImage(image,0,0,image.getWidth(),image.getHeight());
        drawTerritories();
        //connectCountries();
    }

    /**
     * Displays the no of armies in the territories by assigning Text node to each territory.
     * By default the no of armies will be places as zero in the territory.
     */
    public void drawTerritories() {

        int noofPlayers = playersList.size();
        for(int i = 0; i < noofPlayers; i++) {
            territoriesHashMap = playersList.get(i).getTerritoriesHeld();
            group = new Group();
            for(String key : territoriesHashMap.keySet()) {
                Territories territory = territoriesHashMap.get(key);

                Text text = new Text();
                text.setText(Integer.toString(territory.getArmiesHeld()));
                text.setFont(Font.font(25));
                text.setFill(playersList.get(i).getPlayerColor());
                text.setY(territory.getY_Position());
                text.setX(territory.getX_Position());
                text.setId(key);
                text.onMouseClickedProperty().set(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {

                        Text clickedtext = (Text) event.getSource();
                        mouseclicked(clickedtext);
                    }
                });
                group.getChildren().add(text);
            }
            //groupList.add(group);
            canvasPane.getChildren().add(group);
        }
    }

    /**
     * This function will connect the adjacent countries by drawing the line to adjacent countries.
     * To help the user to know the adjacent countries.
     */
    public void connectCountries() {

        territoriesHashMap = gd.getTerritoriesList();
        for(int i = 0; i < playersList.size(); i++) {
            HashMap<String,Territories> playersTerritoriesmap = playersList.get(i).getTerritoriesHeld();
            Color color = playersList.get(i).getPlayerColor();

            for(String key : playersTerritoriesmap.keySet()) {
                Territories territories = playersTerritoriesmap.get(key);
                if(territories.getAdjacentTerritories().size() > 0) {
                    for(String adjacentTerritoriekey : territories.getAdjacentTerritories()) {
                        Territories adjTerritory = territoriesHashMap.get(adjacentTerritoriekey);
                        connectadjacentCountries(territories.getX_Position(),territories.getY_Position(),adjTerritory
                                .getX_Position(),adjTerritory.getY_Position(),color);
                    }
                }
            }
        }
    }

    /**
     * This function will draw the line between the countries.
     * @param srcX_pos
     * @param srcY_pos
     * @param desX_pos
     * @param desY_pos
     */
    public void connectadjacentCountries(int srcX_pos,int srcY_pos,int desX_pos,int desY_pos,Color color) {

        gc.setLineWidth(1);
        gc.setLineDashes(2d);
        gc.setStroke(color);
        gc.strokeLine(srcX_pos,srcY_pos,desX_pos,desY_pos);
    }

    /**
     * This will increament the armies count when the player clicks on their territory.
     */
    public void mouseclicked(Text clickedtext) {
        // Get the text Id.
        // using text Id find the country on which the user has clicked.
        // check if the player has army count greater than zero. if true
        //  a) get the player object of the country where mouse is clicked.
        //  b) get the country object and set the armies to no of armies you put in that country.
        //  c) Now decreament the count of no armies of the player
        //  d) Replace the army count on the country with the no of amies you put on that country.
        // else
        //  a) throw an error that army count is zero to the console.

        System.out.println("Mouse has been clicked.");

        String countryName = clickedtext.getId();
        System.out.println("Mouse clicked country name is = " + countryName);
        if(playersList.get(currentPlayer).getPlayerArmies() > 0) {
            playersList.get(currentPlayer).getTerritoriesHeld().get(countryName).increamentarmyCountby1();
            playersList.get(currentPlayer).decreamentArmycountby1();
            clickedtext.setText(Integer.toString(playersList.get(currentPlayer).getTerritoriesHeld().get(countryName)
                    .getArmiesHeld()));
            textFieldHashMap.get(currentPlayer).setText(Integer.toString(playersList.get(currentPlayer)
                    .getPlayerArmies()));

        } else {
            System.out.println("There are no armies left. Cannot increament the army count");
        }
    }

    /**
     * This will starts the attack when player drags their army count to another territory.
     */
    public void mousedragged() {
        System.out.println("Mouse has been dragged.");
    }

    /**
     * This will send the control to the next player when current player has done with the moving the
     * armies.
     */
    public void finishedMove() {

        currentPlayer++;

        if(currentPlayer == playersList.size()) {
            currentPlayer = 0;
        }

        switch (currentPlayer) {
            case 0:
                displayPlayer1stats(false);
                displayPlayer2stats(true);
                displayPlayer3stats(true);
                displayPlayer4stats(true);
                displayPlayer5stats(true);
                displayPlayer6stats(true);
                
                disableTextnodes(currentPlayer);

                noofReinforcementArmies = calculateReinforcementArmies();
                playersList.get(currentPlayer).increaseArmyCountByValue(noofReinforcementArmies);
                armiesPlayer1.setText(Integer.toString(playersList.get(currentPlayer).getPlayerArmies()));

                break;

            case 1:
                displayPlayer1stats(true);
                displayPlayer2stats(false);
                displayPlayer3stats(true);
                displayPlayer4stats(true);
                displayPlayer5stats(true);
                displayPlayer6stats(true);

                disableTextnodes(currentPlayer);

                noofReinforcementArmies = calculateReinforcementArmies();
                playersList.get(currentPlayer).increaseArmyCountByValue(noofReinforcementArmies);
                armiesPlayer2.setText(Integer.toString(playersList.get(currentPlayer).getPlayerArmies()));

                break;

            case 2:
                displayPlayer1stats(true);
                displayPlayer2stats(true);
                displayPlayer3stats(false);
                displayPlayer4stats(true);
                displayPlayer5stats(true);
                displayPlayer6stats(true);

                disableTextnodes(currentPlayer);

                noofReinforcementArmies = calculateReinforcementArmies();
                playersList.get(currentPlayer).increaseArmyCountByValue(noofReinforcementArmies);
                armiesPlayer3.setText(Integer.toString(playersList.get(currentPlayer).getPlayerArmies()));

                break;

            case 3:
                displayPlayer1stats(true);
                displayPlayer2stats(true);
                displayPlayer3stats(true);
                displayPlayer4stats(false);
                displayPlayer5stats(true);
                displayPlayer6stats(true);

                disableTextnodes(currentPlayer);

                noofReinforcementArmies = calculateReinforcementArmies();
                playersList.get(currentPlayer).increaseArmyCountByValue(noofReinforcementArmies);
                armiesPlayer4.setText(Integer.toString(playersList.get(currentPlayer).getPlayerArmies()));

                break;

            case 4:
                displayPlayer1stats(true);
                displayPlayer2stats(true);
                displayPlayer3stats(true);
                displayPlayer4stats(true);
                displayPlayer5stats(false);
                displayPlayer6stats(true);

                disableTextnodes(currentPlayer);

                noofReinforcementArmies = calculateReinforcementArmies();
                playersList.get(currentPlayer).increaseArmyCountByValue(noofReinforcementArmies);
                armiesPlayer5.setText(Integer.toString(playersList.get(currentPlayer).getPlayerArmies()));

                break;

            case 5:
                displayPlayer1stats(true);
                displayPlayer2stats(true);
                displayPlayer3stats(true);
                displayPlayer4stats(true);
                displayPlayer5stats(true);
                displayPlayer6stats(false);

                disableTextnodes(currentPlayer);

                noofReinforcementArmies = calculateReinforcementArmies();
                playersList.get(currentPlayer).increaseArmyCountByValue(noofReinforcementArmies);
                armiesPlayer6.setText(Integer.toString(playersList.get(currentPlayer).getPlayerArmies()));

                break;
        }
    }

    /**
     * This function displays the armies count and cards holded by each player.
     */
    public void displayPlayerstats() {

        int noofPlayers = playersList.size();

        switch (noofPlayers) {
            case 2:
                displayPlayersdetails(true,true,false,false,false,false);
                break;

            case 3:
                displayPlayersdetails(true,true,true,false,false,false);
                break;

            case 4:
                displayPlayersdetails(true,true,true,true,false,false);
                break;

            case 5:
                displayPlayersdetails(true,true,true,true,true,false);
                break;

            case 6:
                displayPlayersdetails(true,true,true,true,true,true);
                break;
        }
    }

    /**
     * This is to display the players information in the game panel.
     * By default these are hidden.
     * This function will set the visible property to true.
     * @param p1
     * @param p2
     * @param p3
     * @param p4
     * @param p5
     * @param p6
     */
    public void displayPlayersdetails(boolean p1,boolean p2,boolean p3,boolean p4,boolean p5,boolean p6) {

        armiesPlayer1.setVisible(p1);
        cardsPlayer1.setVisible(p1);
        labelPlayer1.setVisible(p1);

        armiesPlayer2.setVisible(p2);
        cardsPlayer2.setVisible(p2);
        labelPlayer2.setVisible(p2);

        armiesPlayer3.setVisible(p3);
        cardsPlayer3.setVisible(p3);
        labelPlayer3.setVisible(p3);

        armiesPlayer4.setVisible(p4);
        cardsPlayer4.setVisible(p4);
        labelPlayer4.setVisible(p4);

        armiesPlayer5.setVisible(p5);
        cardsPlayer5.setVisible(p5);
        labelPlayer5.setVisible(p5);

        armiesPlayer6.setVisible(p6);
        cardsPlayer6.setVisible(p6);
        labelPlayer6.setVisible(p6);
    }

    /**
     * This function will place the players army count and cards holded to the text field.
     */
    public void setPlayerstats() {

        int noofPlayers = playersList.size();
        switch (noofPlayers) {
            case 2:
                armiesPlayer1.setText(Integer.toString(playersList.get(0).getPlayerArmies()));
                cardsPlayer1.setText(Integer.toString(playersList.get(0).getCardsHolded()));

                armiesPlayer2.setText(Integer.toString(playersList.get(1).getPlayerArmies()));
                cardsPlayer2.setText(Integer.toString(playersList.get(1).getCardsHolded()));
                break;

            case 3:
                armiesPlayer1.setText(Integer.toString(playersList.get(0).getPlayerArmies()));
                cardsPlayer1.setText(Integer.toString(playersList.get(0).getCardsHolded()));

                armiesPlayer2.setText(Integer.toString(playersList.get(1).getPlayerArmies()));
                cardsPlayer2.setText(Integer.toString(playersList.get(1).getCardsHolded()));

                armiesPlayer3.setText(Integer.toString(playersList.get(2).getPlayerArmies()));
                cardsPlayer3.setText(Integer.toString(playersList.get(2).getCardsHolded()));
                break;

            case 4:
                armiesPlayer1.setText(Integer.toString(playersList.get(0).getPlayerArmies()));
                cardsPlayer1.setText(Integer.toString(playersList.get(0).getCardsHolded()));

                armiesPlayer2.setText(Integer.toString(playersList.get(1).getPlayerArmies()));
                cardsPlayer2.setText(Integer.toString(playersList.get(1).getCardsHolded()));

                armiesPlayer3.setText(Integer.toString(playersList.get(2).getPlayerArmies()));
                cardsPlayer3.setText(Integer.toString(playersList.get(2).getCardsHolded()));

                armiesPlayer4.setText(Integer.toString(playersList.get(3).getPlayerArmies()));
                cardsPlayer4.setText(Integer.toString(playersList.get(3).getCardsHolded()));
                break;

            case 5:
                armiesPlayer1.setText(Integer.toString(playersList.get(0).getPlayerArmies()));
                cardsPlayer1.setText(Integer.toString(playersList.get(0).getCardsHolded()));

                armiesPlayer2.setText(Integer.toString(playersList.get(1).getPlayerArmies()));
                cardsPlayer2.setText(Integer.toString(playersList.get(1).getCardsHolded()));

                armiesPlayer3.setText(Integer.toString(playersList.get(2).getPlayerArmies()));
                cardsPlayer3.setText(Integer.toString(playersList.get(2).getCardsHolded()));

                armiesPlayer4.setText(Integer.toString(playersList.get(3).getPlayerArmies()));
                cardsPlayer4.setText(Integer.toString(playersList.get(3).getCardsHolded()));

                armiesPlayer5.setText(Integer.toString(playersList.get(4).getPlayerArmies()));
                cardsPlayer5.setText(Integer.toString(playersList.get(4).getCardsHolded()));
                break;

            case 6:
                armiesPlayer1.setText(Integer.toString(playersList.get(0).getPlayerArmies()));
                cardsPlayer1.setText(Integer.toString(playersList.get(0).getCardsHolded()));

                armiesPlayer2.setText(Integer.toString(playersList.get(1).getPlayerArmies()));
                cardsPlayer2.setText(Integer.toString(playersList.get(1).getCardsHolded()));

                armiesPlayer3.setText(Integer.toString(playersList.get(2).getPlayerArmies()));
                cardsPlayer3.setText(Integer.toString(playersList.get(2).getCardsHolded()));

                armiesPlayer4.setText(Integer.toString(playersList.get(3).getPlayerArmies()));
                cardsPlayer4.setText(Integer.toString(playersList.get(3).getCardsHolded()));

                armiesPlayer5.setText(Integer.toString(playersList.get(4).getPlayerArmies()));
                cardsPlayer5.setText(Integer.toString(playersList.get(4).getCardsHolded()));

                armiesPlayer6.setText(Integer.toString(playersList.get(5).getPlayerArmies()));
                cardsPlayer6.setText(Integer.toString(playersList.get(5).getCardsHolded()));
                break;
        }
    }

    /**
     * To display the attackerDice to the window.
     * @param attackerDiceList
     */
    public void displayAttackerDiceToTheConsole(ArrayList<Integer> attackerDiceList) {

        switch(attackerDiceList.size()) {
            case 1:
                attackerDice1 = new Image("resources/WhiteDice"+attackerDiceList.get(0));
                attackerDiceView1.setImage(attackerDice1);
                break;

            case 2:
                attackerDice1 = new Image("resources/WhiteDice"+attackerDiceList.get(0));
                attackerDice2 = new Image("resources/WhiteDice"+attackerDiceList.get(1));
                attackerDiceView1.setImage(attackerDice1);
                attackerDiceView2.setImage(attackerDice2);
                break;

            case 3:
                attackerDice1 = new Image("resources/WhiteDice"+attackerDiceList.get(0));
                attackerDice2 = new Image("resources/WhiteDice"+attackerDiceList.get(1));
                attackerDice3 = new Image("resources/WhiteDice"+attackerDiceList.get(2));
                attackerDiceView1.setImage(attackerDice1);
                attackerDiceView2.setImage(attackerDice2);
                attackerDiceView3.setImage(attackerDice3);
                break;
        }
    }
    
    /**
     * This will display or disable the player1 stats to the game panel.
     * @param value
     */
    public void displayPlayer1stats(boolean value) {

        armiesPlayer1.setDisable(value);
        cardsPlayer1.setDisable(value);
        labelPlayer1.setDisable(value);
    }

    /**
     * This will display or disable the player2 stats to the game panel.
     * @param value
     */
    public void displayPlayer2stats(boolean value) {

        armiesPlayer2.setDisable(value);
        cardsPlayer2.setDisable(value);
        labelPlayer2.setDisable(value);
    }

    /**
     * This will display or disable the player3 stats to the game panel.
     * @param value
     */
    public void displayPlayer3stats(boolean value) {

        armiesPlayer3.setDisable(value);
        cardsPlayer3.setDisable(value);
        labelPlayer3.setDisable(value);
    }

    /**
     * This will display or disable the player4 stats to the game panel.
     * @param value
     */
    public void displayPlayer4stats(boolean value) {

        armiesPlayer4.setDisable(value);
        cardsPlayer4.setDisable(value);
        labelPlayer4.setDisable(value);
    }

    /**
     * This will display or disable the player5 stats to the game panel.
     * @param value
     */
    public void displayPlayer5stats(boolean value) {

        armiesPlayer5.setDisable(value);
        cardsPlayer5.setDisable(value);
        labelPlayer5.setDisable(value);
    }

    /**
     * This will display or disable the player6 stats to the game panel.
     * @param value
     */
    public void displayPlayer6stats(boolean value) {

        armiesPlayer6.setDisable(value);
        cardsPlayer6.setDisable(value);
        labelPlayer6.setDisable(value);
    }

    /**
     * This will set the current player mouseclick actions and disables other countries mouse click actions.
     * @param currentPlayer
     */
    public void disableTextnodes(int currentPlayer) {

        ObservableList<Node> nodes = FXCollections.observableArrayList();
        nodes = canvasPane.getChildren();
        System.out.println("Node size is = " + nodes.size());

        for(int i = 0; i < nodes.size(); i++) {
            if(i == currentPlayer) {
                nodes.get(i).setDisable(false);
            } else {
                nodes.get(i).setDisable(true);
            }
        }
    }
    
    /**
     * This loads the players domination view where we can see the players control over the map
     * and other actions.
     */
    public void loadPlayersDominationView() {

        loader = new FXMLLoader(getClass().getResource("/sample/view/PlayersDominationView.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setTitle("PlayersDominationWindow");
            stage.setScene(new Scene(root,600,448));
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            System.out.println("Cannot load the players domination view.");
            e.printStackTrace();
            return;
        }
    }
    
    /**
     * This loads the phase view.
     */
    public void loadPhaseView() {

        phaseViewLoader = new FXMLLoader(getClass().getResource("/sample/view/PhaseView.fxml"));
        try{
            Parent root = phaseViewLoader.load();
            Stage stage = new Stage();
            stage.setTitle("PhaseView Window");
            stage.setScene(new Scene(root,800,700));
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            System.out.println("Cannot load the phase view.");
            e.printStackTrace();
            return;
        }
    }
    
    /**
     * To find the player who hold the particular territory.
     * @param country
     * @return position of player object in arraylist
     */
    public int findPlayerofTheTerritory(Text country) {

        for(int i = 0; i<playersList.size(); i++) {
            if(playersList.get(i).getTerritoriesHeld().containsKey(country.getId())) {
                return i;
            }
        }
        return -1;
    }
    
    /**
     * To set visible or hide the attack and fortify buttons.
     * @param value
     */
    public void isVisibleAttackOrFortifyButtons(boolean value) {

        attackButton.setVisible(value);
        fortifyButton.setVisible(value);
    }
    
    /**
     * This method dispays the attacker label and text field in the attack phase to select the attackers country.
     * @param value
     */
    public void isVisibleAttackerProperties(boolean value) {

        attackerCountryLabel.setVisible(value);
        attackerCountryTextField.setVisible(value);
        diceLabel.setVisible(value);
        diceTextField.setVisible(value);
        attackProceedButton.setVisible(value);
    }
    
    /**
     * This method displays the label and text field to select the attacked country.
     * @param value
     */
    public void isVisibleDefenderProperties(boolean value) {

        attackedCountryLabel.setVisible(value);
        attackedCountryTextField.setVisible(value);
        diceLabel.setVisible(value);
        diceTextField.setVisible(value);
        attackProceedButton.setVisible(value);
    }

    
    /**
     * To set visible or hide the cards exchange and skip buttons.
     * @param value
     */
    public void isVisbleCardsExchangeButtons(boolean value) {

        loadCardExchangeButton.setVisible(value);
        skipButton.setVisible(value);
    }

    /**
     * To display the defender Dice to the window
     * @param defenderDiceList
     */
    public void displayDefenderDiceToTheConsole(ArrayList<Integer> defenderDiceList) {

        switch(defenderDiceList.size()) {
            case 1:
                defenderDice1 = new Image("resources/RedDice"+defenderDiceList.get(0));
                defenderDiceView1.setImage(defenderDice1);
                break;

            case 2:
                defenderDice1 = new Image("resources/RedDice"+defenderDiceList.get(0));
                defenderDice2 = new Image("resources/RedDice"+defenderDiceList.get(0));
                defenderDiceView1.setImage(defenderDice1);
                defenderDiceView2.setImage(defenderDice2);
                break;
        }
    }
    
    /**
     * To display or hide the dice image views.
     * @param value
     */
    public void displayOrHideTheDiceImageView(boolean value) {

        attackerDiceView1.setVisible(value);
        attackerDiceView2.setVisible(value);
        attackerDiceView3.setVisible(value);

        defenderDiceView1.setVisible(value);
        defenderDiceView2.setVisible(value);
    }
    
    /**
     * This method returns a random card from a deck of cards;
     * @return Card
     */
    public Card generateRandomCard() {

        int min = 0;
        int max = cardsList.size()-1;
        int range = max - min + 1;
        int randomIndex = (int) (Math.random() * range) + min;
        Card card = cardsList.get(randomIndex);
        cardsList.remove(randomIndex);
        return card;
    }
    
    /**
     * To check if the player has won any continent.
     * @param territoryWon
     * @return boolean
     */
    public boolean checkIfPlayerHasWonAnyContinent(Text territoryWon) {

        boolean result = false;
        Territories territory = playersList.get(currentPlayer).getTerritoriesHeld().get(territoryWon.getId());
        String continentName = territory.getContinentName().trim();
        int continentIndex = getIndexOfTheContinent(continentName);
        System.out.println("Continent Index = " + continentIndex);
        ArrayList<String> adjacentCountries = continentList.get(continentIndex).getTerritoriesHeld();
        for(int i = 0; i<adjacentCountries.size(); i++) {
            if(playersList.get(currentPlayer).getTerritoriesHeld().containsKey(adjacentCountries.get(i))) {
                result = true;
            } else {
                result = false;
                return result;
            }
        }
        return result;
    }
    
    /**
     * To assign the continent to the player.
     * @param territoryWon
     */
    public void assignTheContinentToThePlayer(Text territoryWon) {

        Territories territory = playersList.get(currentPlayer).getTerritoriesHeld().get(territoryWon.getId());
        int continentIndex = getIndexOfTheContinent(territory.getContinentName());
        playersList.get(currentPlayer).addContinent(continentList.get(continentIndex));
    }
    
    /**
     * To check if the player has lost the continent.
     * @param territoryLost
     * @param defenderPlayerIndex
     * @return boolean
     */
    public boolean checkIfPlayerHasLostTheContinent(Text territoryLost,int defenderPlayerIndex) {

        Territories territory = playersList.get(defenderPlayerIndex).getTerritoriesHeld().get(territoryLost.getId());
        if(playersList.get(defenderPlayerIndex).getContinentHeld().containsKey(territory.getContinentName())) {
            return true;
        }
        return false;
    }

     /**
     * This function removes the continent from the player list, if the player has lost the territory during
     * the battle.
     * @param territoryLost
     * @param defenderPlayerIndex
     */
    public void removeThecontinentFromThePlayerList(Text territoryLost,int defenderPlayerIndex) {

        Territories territory = playersList.get(defenderPlayerIndex).getTerritoriesHeld().get(territoryLost.getId());
        boolean result = playersList.get(defenderPlayerIndex).removeContinent(territory.getContinentName());
    }   
    
    /**
     * To get the continent index from the list of continents.
     * @param continentName
     * @return continentIndex
     */
    public int getIndexOfTheContinent(String continentName) {

        System.out.println("Continent name is = " + continentName);
        int size = continentList.size();
        System.out.println("SIze is = " + size);
        for(int i = 0; i<size; i++) {
            Continent continent = continentList.get(i);
            if(continent.getContinentName().equalsIgnoreCase(continentName)) {
                System.out.println("Continent index is = " + i);
                return i;
            }
        }
        return -1;
    }

    /**
     * This closes the window and returns to the main window.
     */
    public void closeWindow() {

        Stage stage = (Stage) closeButton.getScene().getWindow();

        gd = null;
        playersList.clear();
        textFieldHashMap.clear();
        GameDetails.getGamedetails().clearData();

        stage.close();
    }

}
