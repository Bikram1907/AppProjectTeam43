package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

/**
 * This class is used to store the initial game parameters.
 */
public class GameDetails {

    private static GameDetails gamedetailsInstance = new GameDetails();
    private int numberOfPlayers;
    private File mapFile;
    private HashMap<String,String> playerCharacters;
    private ArrayList<GameDetails> gamedetails  = new ArrayList<GameDetails>();
    private ArrayList<Continent> continentList = new ArrayList<Continent>();
    private HashMap<String, Territories> territoriesList = new HashMap<String, Territories>();
    private ObservableList<Player> playersList;
    private ArrayList<Color> playerColors = new ArrayList<Color>();
    private String mapName;
    private String typeName;
    private ArrayList<String> checkContinent = new ArrayList<String>();
    private HashMap<Integer,Integer> noofArmies = new HashMap<Integer, Integer>();
    private String currentGamePhase;
    private int currentPlayer = 0;
    private int mapSize;
    private List<Card> cardsList = new ArrayList<>();
    private String gameMode;
    private int noofTurns;
    private String previousGamePhase;


    /**
     * Empty Constructor.
     */
    public GameDetails() {

    }

    /**
     * Constructor with parameters.
     * @param numberOfPlayers
     * @param mapFile
     * @param playerCharacters
     */
    public GameDetails(int numberOfPlayers, File mapFile, HashMap<String, String> playerCharacters, String typeName) {

        this.numberOfPlayers = numberOfPlayers;
        this.mapFile = mapFile;
        this.playerCharacters = playerCharacters;
        this.typeName = typeName;
    }

    /**
     * Constructor with parameters.
     * @param mapFile
     * @param typeName
     */
    public GameDetails(File mapFile, String typeName) {

        this.mapFile = mapFile;
        this.typeName = typeName;
    }

    /**
     * Method to set the number of players.
     * @param numberOfPlayers
     */
    public void setNumberOfPlayers(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }

    /**
     * Method to set the map file.
     * @param mapFile
     */
    public void setMapFile(File mapFile) {
        this.mapFile = mapFile;
    }

    /**
     * Method to set the Player characters in a hashmap.
     * @param playerCharacters
     */
    public void setPlayerCharacters(HashMap<String, String> playerCharacters) {
        this.playerCharacters = playerCharacters;
    }

    /**
     * To set the continent list.
     * @param continentList
     */
    public void setContinentList(ArrayList<Continent> continentList) {
        this.continentList = continentList;
    }

    /**
     * To set the territories list.
     * @param territoriesList
     */
    public void setTerritoriesList(HashMap<String, Territories> territoriesList) {
        this.territoriesList = territoriesList;
    }

    /**
     * To set the players list.
     * @param playersList
     */
    public void setPlayersList(ObservableList<Player> playersList) {
        this.playersList = playersList;
    }

    /**
     * To set the players color.
     * @param playerColors
     */
    public void setPlayerColors(ArrayList<Color> playerColors) {
        this.playerColors = playerColors;
    }

    /**
     * To set the name of the map.
     * @param mapName
     */
    public void setMapName(String mapName) {
        this.mapName = mapName;
    }

    /**
     * To set the no of Armies based on number of players.
     * @param noofArmies
     */
    public void setNoofArmies(HashMap<Integer, Integer> noofArmies) {
        this.noofArmies = noofArmies;
    }
    
    /**
     * Method to set the game phase.
     * @param currentGamePhase
     */
    public void setGamePhase(String currentGamePhase) {
        this.currentGamePhase = currentGamePhase;
        setChanged();
        notifyObservers(currentGamePhase);
    }
    
    /**
     * To set the current player.
     * @param currentPlayer
     */
    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
        setChanged();
        notifyObservers(currentPlayer);
    }
    
    /**
     * To set the game mode.
     * @param gameMode
     */
    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    /**
     * To set the game mode.
     * @param noofTurns
     */
    public void setNoofTurns(int noofTurns) {
        this.noofTurns = noofTurns;
    }
    
    /**
     * To get the current player
     * @return currentPlayer
     */
    public int getCurrentPlayer() {
        return currentPlayer;
    }
    
    /**
     * To return the gamephase.
     * @return currentGamePhase
     */
    public String getGamePhase() {
        return currentGamePhase;
    }
    
    /**
     * To set the map size.
     * @param mapSize
     */
    public void setMapSize(int mapSize) {
        this.mapSize = mapSize;
    }
    
    /**
     * To get the map size
     * @return mapSize
     */
    public int getMapSize() {
        return mapSize;
    }

    /**
     * Method to get the class object.
     * @return
     */
    public static GameDetails getGamedetails() {
        return gamedetailsInstance;
    }

    /**
     * Method to return the number of players.
     * @return
     */
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    /**
     * Method to return the map file.
     * @return
     */
    public File getMapFile() {
        return mapFile;
    }

    /**
     * Method to return the player characters.
     * @return
     */
    public HashMap<String, String> getPlayerCharacters() {
        return playerCharacters;
    }

    /**
     * return the game details arraylist.
     * @return
     */
    public ArrayList<GameDetails> getgamedetails() {
        return gamedetails;
    }

    /**
     * To add Game details object to the arrayList.
     * @param Instance
     */
    public void addgamedetailsInstance(GameDetails Instance) {
        gamedetails.add(Instance);
    }

    /**
     * To return the continentList from the map file.
     * @return
     */
    public ArrayList<Continent> getContinentList() {
        return continentList;
    }

    /**
     * To return the territorie List.
     * @return
     */
    public HashMap<String, Territories> getTerritoriesList() {
        return territoriesList;
    }

    /**
     * Returns the Players List.
     * @return
     */
    public ObservableList<Player> getPlayersList() {
        return playersList;
    }

    /**
     * Returns the Players colors.
     * @return
     */
    public ArrayList<Color> getPlayerColors() {
        return playerColors;
    }

    /**
     * To return Map name.
     * @return
     */
    public String getMapName() {
        return mapName;
    }

    /**
     * Return the game object or map editor object.
     * @return
     */
    public String getTypeName() {
        return typeName;
    }

    /**
     * To return the no of Armies hashmap.
     * @return
     */
    public HashMap<Integer, Integer> getNoofArmies() {
        return noofArmies;
    }

    /**
     * To Load the continents and territories to continent list and territories list.
     */
    public void createMap(int index) {

        mapFile = GameDetails.getGamedetails().getgamedetails().get(index).getMapFile();
        System.out.println("mapFile length is = " + mapFile.length());
        System.out.println("***************************************Map Contents in text box*****************************");
        System.out.println(mapFile.toString());
        System.out.println("***************************************Till here********************************************");

        ArrayList<Territories> adjacentterritoriesList = new ArrayList<Territories>();

        try{
            BufferedReader br = new BufferedReader(new FileReader(mapFile));
            String line;
            int i = 0;
            while((line = br.readLine()) != null) {

                if(line.contains("[Continents]")) {
                    while((line = br.readLine()) != null) {
                        if(line.contains("[Territories]")) {
                            while((line = br.readLine()) != null) {
                                if(line.trim().isEmpty()){
                                    if(continentList.size() > i) {
                                       continentList.get(i).copyArrayListElementsToAnother(adjacentterritoriesList);
                                        System.out.println(adjacentterritoriesList.size());
                                        adjacentterritoriesList.clear();
                                    }
                                    i++;
                                } else {
                                    String splitTerritorie[] = line.split(",");
                                    ArrayList<String> adjlist = new ArrayList<String>(Arrays.asList(splitTerritorie));

                                    adjlist.remove(0);
                                    adjlist.remove(0);
                                    adjlist.remove(0);
                                    adjlist.remove(0);
                                    
                                    territoriesList.put(splitTerritorie[0].trim(),new Territories(splitTerritorie[0].trim(),Integer.parseInt(splitTerritorie[1]),
                                            Integer.parseInt(splitTerritorie[2]),splitTerritorie[3],adjlist));
                                    adjacentterritoriesList.add(splitTerritorie[0].trim());
                                    cardsList.add(new Card(splitTerritorie[0].trim(),generateRandomCardType()));
                                }
                            }
                        } else {
                            // Reads the continents from the map file.
                            if(!(line.trim().isEmpty())){
                                String splitString[] = line.trim().split("=");
                                continentList.add(new Continent(splitString[0].trim(),Integer.parseInt(splitString[1].trim()),new ArrayList<>()));
                                System.out.println("Name = "+splitString[0]+" Score = "+splitString[1]);
                            }
                        }
                    }
                }
            }

            System.out.println("ContinentList size is = " + continentList.size());
            System.out.println("TerritoriesList size is = " + territoriesList.size());
            GameDetails.getGamedetails().getgamedetails().get(index).setContinentList(continentList);
            GameDetails.getGamedetails().getgamedetails().get(index).setTerritoriesList(territoriesList);
            
            cardsList.add(new Card("None","WILD"));
            cardsList.add(new Card("NONE1","WILD"));

            GameDetails.getGamedetails().getgamedetails().get(index).setCardsList(cardsList);


            System.out.println("Loaded the Map files into the List.");

        } catch(Exception e) {
            System.out.println("Could not find the file");
            e.printStackTrace();
            return;
        }

    }

    /**
     * To check whether the selected is valid map and connecte map or not.
     * @return true if valid
     * else false.
     */
    public boolean validateMap(int index) {

        mapFile = GameDetails.getGamedetails().getgamedetails().get(index).getMapFile();
        boolean result = false;

        try {
            BufferedReader br = new BufferedReader(new FileReader(mapFile));
            String line;

            while((line = br.readLine()) != null) {
                if(line.contains("[Territories]")) {
                    while((line = br.readLine()) != null) {
                        if(line.trim().isEmpty()) {
                            // Do nothing.
                        } else {
                            String[] str = line.split(",");
                            for(int i = 4; i<str.length; i++) {
                                if(!str[0].trim().equalsIgnoreCase(str[i].trim()) && territoriesList.keySet().contains(str[i].trim())) {
                                    if(territoriesList.get(str[i].trim()).getAdjacentTerritories().size() != 0 && territoriesList
                                    .get(str[i].trim()).getAdjacentTerritories().contains(str[0].trim())) {
                                        result = true;
                                    } else {
                                        result = false;
                                        System.out.println("Invalid Map. No adjacent territories for: "+str[i]);
                                        return result;
                                    }
                                } else {
                                    result = false;
                                    System.out.println("Invalid Map. No connection for: "+str[i]);
                                    return result;

                                }
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Exception at map Validation..");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * To create the player objects and save them to the list.
     */
    public void IntializePlayers(int index) {

        // Get the no of players count.
        int size = gamedetails.get(index).getNumberOfPlayers();
        playersList = FXCollections.observableArrayList();
        int j = 0;

        // Create player class object for each player and store it to the playerslist of type arraylist.
        for(int i = 0; i<size; i++) {
            j = i + 1;
            HashMap<String,Territories> territoriesHeld = new HashMap<String,Territories>();
            playersList.add(new Player("Player"+j,0,false,"None",playerColors.get(i),0));
             playersList.get(i).setTerritoriesHeld(new HashMap<String,Territories>());
            playersList.get(i).setContinentHeld(new HashMap<String,Continent>());
            playersList.get(i).setCardTurn(1);
            playersList.get(i).setCardsHeld(new ArrayList<Card>());
        }

        
        // Add the playerslist of type arraylist to the gamedetails class object.
        gamedetails.get(index).setPlayersList(playersList);

        System.out.println("Initialized the player and created the player objects.");
    }

    /**
     * Initialize Player colors to ArrayList.
     */
    public void IntializeColors(int index) {

        // Adds the colors to the Player colors of type arraylist.
        playerColors.add(Color.BLUE);
        playerColors.add(Color.GREEN);
        playerColors.add(Color.DEEPPINK);
        playerColors.add(Color.RED);
        playerColors.add(Color.AQUA);
        playerColors.add(Color.ORANGE);

        // Add playercolors arraylist to the gamedetails class object.
        gamedetails.get(index).setPlayerColors(playerColors);

        System.out.println("Added The colors to the list.");
    }

    /**
     * This will assign the armies based on the no of players.
     * @param index
     */
    public void InitializeArmies(int index) {

        // Assign armies based on the no of players.
        noofArmies.put(2,40);
        noofArmies.put(3,35);
        noofArmies.put(4,30);
        noofArmies.put(5,25);
        noofArmies.put(6,20);

        // Add the noofArmies hashmap to the gamedetails object.
        gamedetails.get(index).setNoofArmies(noofArmies);

        System.out.println("Added the armies to the game details object");
    }

    /**
     * To distribute the territories to the players.
     * @param index
     */
    public void distributeTerritories(int index) {

        territoriesList = gamedetails.get(index).getTerritoriesList();
        playersList = gamedetails.get(index).getPlayersList();
        int noofPlayers = playersList.size();
        int player = 0;
        for(String key : territoriesList.keySet()) {
            if(player == noofPlayers) {
                player = 0;
            }
            Territories territory = territoriesList.get(key);
            territory.setHasPlayer(true);
            territory.setPlayer(playersList.get(player));

            playersList.get(player).getTerritoriesHeld().put(key,territory);

            player++;
        }

        System.out.println("Distribution of territories has been done.");
    }

    /**
     * This function distribute the no of armies of to the players.
     * @param index
     */
    public void distributeArmies(int index) {

        numberOfPlayers = gamedetails.get(index).getNumberOfPlayers();
        int noofArmies = gamedetails.get(index).getNoofArmies().get(numberOfPlayers);
        playersList = gamedetails.get(index).getPlayersList();

        for(Player player : playersList) {
            player.setPlayerArmies(noofArmies);
        }
    }

    /**
     * This function distributes the player armies to their corresponding territories.
     * First retrieves the no of players.
     * For each player it retrives the territories and for each territory it assigns one army.
     * After the loop if the player left any armies then it retrives the random territory and increaments
     * the count by 1. We do this untill player has no armies left.
     * @param index
     */
    public void distributeArmiestoTerritories(int index) {

        // get the no of Players count.
        numberOfPlayers = gamedetails.get(index).getNumberOfPlayers();
        for(int i = 0; i < numberOfPlayers; i++) {
            // get the no of armies played had.
            int playerArmies = gamedetails.get(index).getPlayersList().get(i).getPlayerArmies();
            int noofTerritories = gamedetails.get(index).getPlayersList().get(i).getTerritoriesHeld().size();
            for (String key : gamedetails.get(index).getPlayersList().get(i).getTerritoriesHeld().keySet()) {
                if (playerArmies > 0) {
                    Territories territory = gamedetails.get(index).getPlayersList().get(i).getTerritoriesHeld().get(key);
                    territory.setArmiesHeld(1);
                    playerArmies -= 1;
                } else {
                    // set the player army to zero.
                    gamedetails.get(index).getPlayersList().get(i).setPlayerArmies(0);
                }
            }

            }
        }
    }

    /**
     * This function generates the random card type.
     * @return card type
     */
    public String generateRandomCardType() {

        int min = 1;
        int max = 3;
        int range = max - min + 1;
        int randomNumber = (int) (Math.random()*range) + min;
        if(randomNumber == 1) {
            return "INFANTRY";
        } else if(randomNumber == 2) {
            return "CAVALRY";
        } else {
            return "ARTILLERY";
        }
    }

    /**
     * This will clears the data and objects and contents from hashmap and lists.
     */
    public void clearData() {

        // If GameDetails class object is not null then clear the objects.
        if(!GameDetails.getGamedetails().getgamedetails().isEmpty()) {
            GameDetails.getGamedetails().getgamedetails().clear();
        }

        // Clear the territory list contents stored in territory hashmap.
        GameDetails.getGamedetails().getTerritoriesList().clear();

        // Clear the continent list contents stored in continent list.
        GameDetails.getGamedetails().getContinentList().clear();
    }

}
