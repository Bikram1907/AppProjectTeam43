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
     * This function will check for adjacency between the attacking country and attacked country and
     * then performs the attack.
     */
    public void startAttacks() {

        if(playersList.get(currentPlayer).canAttack(attackerCountryNode.getId(),defenderCountryNode.getId())) {
            int opponentPlayerIndex = findPlayerofTheTerritory(defenderCountryNode);
            String battleResult = playersList.get(currentPlayer).doAttack(attackerArmy,defenderArmy,attackerCountryNode.getId()
                    ,defenderCountryNode.getId(),playersList.get(opponentPlayerIndex));

            pvcInstance.addTextToTextArea("The battle result is = " + battleResult);
            determineWhatToHappenAfterBattle(battleResult,opponentPlayerIndex);
        } else {
            System.out.println("Cannot attack the country. It is not adjacent");
            pvcInstance.addTextToTextArea("Cannot attack the country. It is not adjacent");
            isPlayerAttackerOrDefender = ATTACKER;
            disableTextnodes(currentPlayer);
            isVisibleDefenderProperties(false);
            attackPhase();
        }
    }
    
    /**
     * This function will implement the logic according to the battle result.
     * @param battleResult
     * @param opponentPlayerIndex
     */
    public void determineWhatToHappenAfterBattle(String battleResult,int opponentPlayerIndex) {

        if(battleResult.equalsIgnoreCase(WINNER)) {
            System.out.println("Congratulations, You won the battle.");
            pvcInstance.addTextToTextArea("Congratulation, You won the battle.");
            isVisibleDefenderProperties(false);
            battleWinnerAction(opponentPlayerIndex);

        } else if(battleResult.equalsIgnoreCase(LOSER)) {
            System.out.println("Player" +currentPlayer + " lost the battle");
            pvcInstance.addTextToTextArea("Player" +currentPlayer + " lost the battle");
            updateThePlayerFieldsAfterAttack(opponentPlayerIndex);

        } else if(battleResult.equalsIgnoreCase(TIE)) {
            System.out.println("The battle is a TIE");
            pvcInstance.addTextToTextArea("The battle is a TIE");
            updateThePlayerFieldsAfterAttack(opponentPlayerIndex);
        }
    }
    
    /**
     * This method will update the player fields after attack is done.
     * @param opponentPlayerIndex
     */
    public void updateThePlayerFieldsAfterAttack(int opponentPlayerIndex) {
        clickedtext.setText(Integer.toString(playersList.get(opponentPlayerIndex).getTerritoriesHeld()
                .get(defenderCountryNode.getId()).getArmiesHeld()));
        attackerCountryNode.setText(Integer.toString(playersList.get(currentPlayer).getTerritoriesHeld()
                .get(attackerCountryNode.getId()).getArmiesHeld()));
        resetTheControlsAfterAttackIsDone();
    }
    
    /**
     * This function will set the UI to the default after attack is done.
     */
    public void resetTheControlsAfterAttackIsDone() {

        isPlayerAttackerOrDefender = ATTACKER;
        disableTextnodes(currentPlayer);
        isVisibleDefenderProperties(false);
        isVisibleAttackOrFortifyButtons(true);
        displayOrHideTheDiceImageView(false);
    }
    
    /**
     * This function will implement the logic when the battle is won by the player
     * checks if the player has won the territory, if yes the it checks it results in the winning the
     * continent. And also it draws a card if player has won any territory during the battle.
     * checks if the defending player has lost the territory, if yes then it check if it results in loosing the
     * continent. if yes it removes the continent.
     * @param opponentPlayerIndex
     */
    public void battleWinnerAction(int opponentPlayerIndex) {

        if(playersList.get(opponentPlayerIndex).checkIfATerritoryHasLostAllArmies(defenderCountryNode.getId())) {
            Territories territory = playersList.get(opponentPlayerIndex).getTerritoriesHeld().get(defenderCountryNode.getId());

            displayNumberOfArmiesYouWantToMoveAfterWinningTerritory(true);

            // set the player of defeated country to attacker
            territory.setPlayer(playersList.get(currentPlayer).getPlayerName());
            playersList.get(currentPlayer).getTerritoriesHeld().put(territory.getTerritorieName(),territory);

            // check if the attacker has won the continent.
            if(checkIfPlayerHasWonAnyContinent(defenderCountryNode)) {
                System.out.println("Congratulations, you have won the continent");
                assignTheContinentToThePlayer(defenderCountryNode);
            }

            // check if the attacked player has lost the continent due to attack phase.
            if(checkIfPlayerHasLostTheContinent(defenderCountryNode,opponentPlayerIndex)) {
                System.out.println("The Player has lost the continent");
                removeThecontinentFromThePlayerList(defenderCountryNode,opponentPlayerIndex);
            }

            // Then, remove the defeated country from the player list
            playersList.get(opponentPlayerIndex).getTerritoriesHeld().remove(defenderCountryNode.getId());

            defenderCountryNode.setFill(playersList.get(currentPlayer).getPlayerColor());
            defenderCountryNode.setText(Integer.toString(playersList.get(currentPlayer).getTerritoriesHeld().get(defenderCountryNode.getId()
            ).getArmiesHeld()));
            attackerCountryNode.setText(Integer.toString(playersList.get(currentPlayer).getTerritoriesHeld()
                    .get(attackerCountryNode.getId()).getArmiesHeld()));

            moveTextNodeFromOneGroupToAnother(defenderCountryNode,opponentPlayerIndex);
            isPlayerWonTheTerritoryDuringTheAttack = true;

            // if attacked country player lost all the countries then remove the player from the list
            // Transfer the cards to the attacker.
            if(playersList.get(opponentPlayerIndex).checkIfPlayerHasLostAllCountries()) {
                playersList.get(opponentPlayerIndex).transferCardsFromOnePlayerToAnother(playersList.get(currentPlayer));
                playersList.get(opponentPlayerIndex).setKnocked(true);
                System.out.println("The " + playersList.get(opponentPlayerIndex).getPlayerName() + " has been" +
                        "knocked out.");
                playersList.remove(opponentPlayerIndex);
                displayCardStats();
            }

            if(playersList.get(currentPlayer).getTerritoriesHeld().size() == gd.getMapSize()) {

                Alert WinAlert = new Alert(Alert.AlertType.CONFIRMATION);
                WinAlert.setTitle("Win Alert");
                WinAlert.setContentText("Congratulation, you won the game.");
                Optional<ButtonType> result = WinAlert.showAndWait();
                if(result.isPresent() && result.get() == ButtonType.OK) {
                    pdvcInstance.closeWindow();
                    pvcInstance.closeWindow();
                    closeWindow();
                }
            }
        } else {
            attackerCountryNode.setText(Integer.toString(playersList.get(currentPlayer).getTerritoriesHeld()
                    .get(attackerCountryNode.getId()).getArmiesHeld()));
            defenderCountryNode.setText(Integer.toString(playersList.get(opponentPlayerIndex).getTerritoriesHeld()
                    .get(defenderCountryNode.getId()).getArmiesHeld()));
            displayNumberOfArmiesYouWantToMoveAfterWinningTerritory(true);
        }
    }
    
    /**
     * get the no of armies to move after winning the territory from the player.
     */
    @FXML
    public void getTheNoOfArmiesToMoveFromPlayerAfterWinningTerritory() {

        System.out.println("Confirm button has been clicked");
        int getNumberofArmiesToMove = Integer.parseInt(armiesToMoveTextField.getText());
        pvcInstance.addTextToTextArea("The Player has choosen the armies to move is = " + getNumberofArmiesToMove);
        System.out.println("getNumberofArmiesToMove = " + getNumberofArmiesToMove);

        if(playersList.get(currentPlayer).getTerritoriesHeld().get(attackerCountryNode.getId())
                .getArmiesHeld() > getNumberofArmiesToMove) {
            System.out.println("You can move the armies.");

            if(playersList.get(currentPlayer).doFortification(getNumberofArmiesToMove,attackerCountryNode.getId()
                    ,defenderCountryNode.getId())) {
                attackerCountryNode.setText(Integer.toString(playersList.get(currentPlayer).getTerritoriesHeld()
                        .get(attackerCountryNode.getId()).getArmiesHeld()));
                defenderCountryNode.setText(Integer.toString(playersList.get(currentPlayer).getTerritoriesHeld()
                        .get(defenderCountryNode.getId()).getArmiesHeld()));

                System.out.println("Successfully Move the armies to the location");
                pvcInstance.addTextToTextArea("Successfully Move the armies to the location");
                displayNumberOfArmiesYouWantToMoveAfterWinningTerritory(false);
                resetTheControlsAfterAttackIsDone();
            }
        }
    }
    
    /**
     * This function displays the label and textfield of the ountry from which player can start fortify.
     */
    public void fortifyButtonAction() {

        gd.setGamePhase(FORTIFICATIONPHASE);
        isVisibleAttackOrFortifyButtons(false);
        displayFromWhichCountryPlayerForitify(true);
    }
    
    /**
     * This functions implements the card exchange view.
     */
    public void loadCardsExchangeView() {

        if(playersList.get(currentPlayer).getCardsHolded() >= 3) {
            Dialog<ButtonType> cardsExchangeDialog = new Dialog<>();
            cardsExchangeDialog.initOwner(mainBorderPane.getScene().getWindow());
            cardExchangeLoader = new FXMLLoader();
            cardExchangeLoader.setLocation(getClass().getResource("/sample/view/CardExchange.fxml"));
            try {
                cardsExchangeDialog.getDialogPane().setContent(cardExchangeLoader.load());

                cecController = cardExchangeLoader.getController();
                cecController.initialize();
                cecController.getCurrentPlayer(currentPlayer);
                cecController.getPlayerListFromTheController(playersList);
                cecController.getOriginalCardList(cardsList);
                cecController.getCardsListFromThePlayer(FXCollections.observableList(playersList.get(currentPlayer).getCardsHeld()));

                cardsExchangeDialog.getDialogPane().getButtonTypes().add(ButtonType.OK);
                Optional<ButtonType> result = cardsExchangeDialog.showAndWait();

                if(result.isPresent() && (result.get() == ButtonType.OK)) {
                    playersList = cecController.returnPlayerList();
                    cardsList = cecController.returnOrginalCardList();

                    updateThePlayerArmyField();
                    displayCardStats();

                    gd.setGamePhase(ATTACKPHASE);
                    isVisbleCardsExchangeButtons(false);
                    isVisibleAttackOrFortifyButtons(true);

                }
            } catch (Exception e) {
                System.out.println("Cannot load the cards exchange dialog");
                e.printStackTrace();
                return;
            }
        } else {
            Alert cardsExchangeAlert = new Alert(Alert.AlertType.WARNING);
            cardsExchangeAlert.setTitle("Cards Exchange Alert");
            cardsExchangeAlert.setContentText("You Should have minimum of 3 cards to load the cards Exchange");
            Optional<ButtonType> result = cardsExchangeAlert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                gd.setGamePhase(ATTACKPHASE);
                isVisbleCardsExchangeButtons(false);
                isVisibleAttackOrFortifyButtons(true);
            }
        }
    }

    
    /**
     * This function updates the armies field of the current player.
     */
    public void updateThePlayerArmyField() {

        if(currentPlayer == 0) {
            armiesPlayer1.setText(Integer.toString(playersList.get(currentPlayer).getPlayerArmies()));

        } else if(currentPlayer == 1) {
            armiesPlayer2.setText(Integer.toString(playersList.get(currentPlayer).getPlayerArmies()));

        } else if(currentPlayer == 2) {
            armiesPlayer3.setText(Integer.toString(playersList.get(currentPlayer).getPlayerArmies()));

        } else if(currentPlayer == 3) {
            armiesPlayer4.setText(Integer.toString(playersList.get(currentPlayer).getPlayerArmies()));

        } else if(currentPlayer == 4) {
            armiesPlayer5.setText(Integer.toString(playersList.get(currentPlayer).getPlayerArmies()));

        } else if(currentPlayer == 5) {
            armiesPlayer6.setText(Integer.toString(playersList.get(currentPlayer).getPlayerArmies()));
        }
    }
    
    /**
     * This functions is used to skip from reinforcement phase to attack phase or fortify phase.
     */
    public void skipToNextPhase() {

        if(playersList.get(currentPlayer).getCardsHolded() <= 5) {
            //playersList.get(currentPlayer).setPlayerCurrentPhase(ATTACKPHASE);
            gd.setGamePhase(ATTACKPHASE);

            isVisbleCardsExchangeButtons(false);
            isVisibleAttackOrFortifyButtons(true);
        } else {
            Alert cardsExchangeAlert = new Alert(Alert.AlertType.WARNING);
            cardsExchangeAlert.setTitle("Cards Exchange Alert");
            cardsExchangeAlert.setContentText("Cannot skip to next phase, you have minimum 5 cards. So you " +
                    "must trade the cards.");
            cardsExchangeAlert.getButtonTypes().add(ButtonType.OK);
            Optional<ButtonType> result = cardsExchangeAlert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK) {
                isVisbleCardsExchangeButtons(false);
                loadCardsExchangeView();
            }
            System.out.println("Cannot skip to the next phase. You have 5 cards, So you have to trade the cards");
        }
    }
    
    /**
     * This function will call the reinforcementPhase function and display buttons for cards exchange or skip the
     * cards exchange and proceed to next phase.
     * @param armiesPlayer
     * @param cardsPlayer
     */
    public void proceedToNextPhase(TextField armiesPlayer, TextField cardsPlayer) {

        if(gd.getGamePhase().equalsIgnoreCase(REINFORCEMENTPHASE)) {
            reinforcementPhase(armiesPlayer);
            isVisbleCardsExchangeButtons(true);
            isVisibleAttackOrFortifyButtons(false);
        }
    }

    
    /**
     * This function displays the no of armies player want to move after winning the territory
     * @param value
     */
    public void displayNumberOfArmiesYouWantToMoveAfterWinningTerritory(boolean value) {

        armiesToMoveTextField.clear();
        armiesToMoveLabel.setVisible(value);
        armiesToMoveTextField.setVisible(value);
        confirmButton.setVisible(value);
    }
    
    /**
     * To display the details from which player can foritify the territory
     * @param value
     */
    public void displayFromWhichCountryPlayerForitify(boolean value) {

        selectFromLabel.setVisible(value);
        fromCountryTextField.setVisible(value);
    }

    
    /**
     * To display to which country player want to fortify.
     * @param value
     */
    public void displayToWhichCountryPlayerFortify(boolean value) {

        toCountryLabel.setVisible(value);
        toCountryTextField.setVisible(value);
    }
    
     /**
     * This method will display the confirmation button when selecting the source and destination countries and
     * if both source and destination are different, then it will ask the player how many armies he want to move
     * then it will call the player class can fortify method. If it returns true, then it will call a method from
     * Player class that can do fortify and return true if it fortified.
     */
    public void proceedAction() {

        // count = 1 means to select the source country.
        if(count == 1) {
            // get the no of armies to move from the text field
            noOfArmiesToMove = Integer.parseInt(armiesCountTextField.getText());
            System.out.println("noOfArmiesToMove is = " +noOfArmiesToMove);
            pvcInstance.addTextToTextArea("The player has choosen number of armies to move is = " + noOfArmiesToMove);

            // clear the text fields and hide the armies label and text field.
            fromCountryTextField.clear();
            armiesCountTextField.clear();
            armiesCountLabel.setVisible(false);
            armiesCountTextField.setVisible(false);

            // get the source country
            sourceNodeId = clickedtext.getId();
            sourceText = clickedtext;
            pvcInstance.addTextToTextArea("The has choosen the country = " + sourceText.getId());

            // if the selected country has more than one army and no of armies to move should be less than armies in territory.
            if(Integer.parseInt(clickedtext.getText())>1 && noOfArmiesToMove<(Integer.parseInt(clickedtext.getText()))) {
                displayFromWhichCountryPlayerForitify(false);
                displayToWhichCountryPlayerFortify(true);

                pvcInstance.addTextToTextArea("Player has selected " + sourceNodeId + " country from which he want" +
                        "to fortify.");
                pvcInstance.addTextToTextArea("Player has selected " + noOfArmiesToMove + " armies to move.");

                count = 2;
                proceedButton.setVisible(false);
            } else {
                fortifyButtonAction();
                pvcInstance.addTextToTextArea("Player has selected country that has minimum armies or no of armies" +
                        "count to maximum");
            }
        // count = 2 means to select the destination country.
        } else if(count == 2) {
            // hide the text fields and labels.
            displayToWhichCountryPlayerFortify(false);
            proceedButton.setVisible(false);
            toCountryTextField.clear();

            // get the destination country
            destinationNodeId = clickedtext.getId();
            destinationText = clickedtext;

            // if source and destination country not same then do fortify.
            if(!sourceNodeId.equalsIgnoreCase(destinationNodeId)) {
                if(playersList.get(currentPlayer).canFortify(sourceNodeId,destinationNodeId)) {
                    if(playersList.get(currentPlayer).doFortification(noOfArmiesToMove,sourceNodeId,destinationNodeId)){
                        pvcInstance.addTextToTextArea("Player has selected " + destinationNodeId + " country to which he want" +
                                "to fortify.");
                        int noofArmies = playersList.get(currentPlayer).getTerritoriesHeld().get(sourceNodeId).getArmiesHeld();
                        sourceText.setText(Integer.toString(noofArmies));

                        noofArmies = playersList.get(currentPlayer).getTerritoriesHeld().get(destinationNodeId).getArmiesHeld();
                        destinationText.setText(Integer.toString(noofArmies));

                        count = 0;
                        pvcInstance.addTextToTextArea("Player has sucessfully done the fotification.");
                        finishedMove();
                    } else {
                        fortifyButtonAction();
                    }
                } else {
                    System.out.println("The selected country is not adjacent. Please select another country");
                    pvcInstance.addTextToTextArea("The selected country is not adjacent. Please select another country");
                    displayToWhichCountryPlayerFortify(true);
                }
            } else {
                System.out.println("Both source country and destination country are equal. Select the another country.");
                pvcInstance.addTextToTextArea("The selected country is not adjacent. Please select another country");
                displayToWhichCountryPlayerFortify(true);
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
