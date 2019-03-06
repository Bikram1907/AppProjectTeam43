package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.Optional;

public class Controller {

    @FXML
    private MenuBar menubarId;

    @FXML
    private BorderPane MainBorderPane;

    @FXML
    private Canvas canvasId;

    @FXML
    public void initialize() {

        GraphicsContext graphicsContext = canvasId.getGraphicsContext2D();
        Image image = new Image("resources/risk.png");
        graphicsContext.drawImage(image,0,0,canvasId.getWidth(),canvasId.getHeight());
    }
    
    /**
     * This methods starts the new game and asks about the parameters needed for the game.
     */
    @FXML
    public void startGame() {

        // Intializing the dialog pane and loading the dialog.
        Dialog<ButtonType> gameParametersdialog = new Dialog<>();
        gameParametersdialog.initOwner(MainBorderPane.getScene().getWindow());
        FXMLLoader gameparametersDialogloader = new FXMLLoader();
        gameparametersDialogloader.setLocation(getClass().getResource("Gameparameters.fxml"));

        try {
            gameParametersdialog.getDialogPane().setContent(gameparametersDialogloader.load());

        } catch(IOException e){
            System.out.println("Cannot load the game parameters dialog");
            e.printStackTrace();
            return;
        }

        gameParametersdialog.getDialogPane().getButtonTypes().add(ButtonType.FINISH);
        gameParametersdialog.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);

        // if the user has entered finish then read the game parameters and store in the GameDetails object.
        Optional<ButtonType> result = gameParametersdialog.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.FINISH) {
            GameparametersDialogController gpdc_controller = gameparametersDialogloader.getController();
            gpdc_controller.createGameinstance();

            // Get the index of the Game details object of type new game.
            int index = returnIndex("NEWGAME");

            GameDetails object2 = GameDetails.getGamedetails().getgamedetails().get(index);

            System.out.println("The user selected = "+object2.getNumberOfPlayers());

            // Intialize the player objects and player colors and map.
            GameDetails.getGamedetails().IntializeColors(index);
            GameDetails.getGamedetails().IntializePlayers(index);
            GameDetails.getGamedetails().InitializeArmies(index);
            GameDetails.getGamedetails().createMap(index);

            // If map is valid then Load the game window.
            if(GameDetails.getGamedetails().validateMap(index)){
                System.out.println("Map is valid.");
                System.out.println("*********************************************************************************");
                System.out.println("Proceeding to the next step");
                //System.out.println("The player name was "+GameDetails.getGamedetails().getgamedetails().get(0).getPlayersList().get(0).getPlayerName());

                // To distribute the territories to the players.
                GameDetails.getGamedetails().distributeTerritories(index);

                // To distribute the no of armies to the players.
                GameDetails.getGamedetails().distributeArmies(index);

                // To distribute the armies to the territories.
                GameDetails.getGamedetails().distributeArmiestoTerritories(index);

                System.out.println(GameDetails.getGamedetails().getPlayersList().get(0).getTerritoriesHeld().size());
                System.out.println(GameDetails.getGamedetails().getPlayersList().get(1).getTerritoriesHeld().size());

                // To get the name of the map with out .map extension.
                String string = object2.getMapFile().getName();
                string = string.substring(0,string.length()-4);
                System.out.println("Map name with out extension is "+ string);
                GameDetails.getGamedetails().getgamedetails().get(index).setMapName(string);

                loadGamewindow();   // Loads the game window when map is valid.
            } else {
                System.out.println("********************************MAP IS INVALID***********************************");
                System.out.println("----------------------------Cannot Load the Game Window--------------------------");

                // Clear the data.
                GameDetails.getGamedetails().clearData();
            }
        } else {
            System.out.println("Cancel button is pressed");
        }
    }
    
    /**
     * Returns the index of the gamedetail object based on new game or editmap or newmap.
     * @param typeName
     * @return index
     */
    public int returnIndex(String typeName) {

        int size = GameDetails.getGamedetails().getgamedetails().size();
        int index = -1;

        for(int i = 0; i<size; i++) {
            GameDetails instance = GameDetails.getGamedetails().getgamedetails().get(i);
            if(instance.getTypeName().contains(typeName)) {
                index = i;
                System.out.printf("%s object index in arraylist is = %d%n", typeName, index);
            }
        }

        return index;
    }
    
    /**
     * To load the game Window.
     */
    public void loadGamewindow() {

        FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("loadGame.fxml"));
        try{
            Parent root1 = gameLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Game window");
            stage.setScene(new Scene(root1,1000,700));
            stage.setResizable(false);
            stage.show();

            Stage primaryStage = (Stage) MainBorderPane.getScene().getWindow();
            primaryStage.hide();

            stage.setOnHidden(e -> primaryStage.show());

        } catch (Exception e) {
            System.out.println("Cannot load the game window");
            e.printStackTrace();
            return;
        }

    }
}
