package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import basic.GameDetails;

public class MapEditorController {

    @FXML
    private TextArea textArea;

    private File selectedfile;
    @FXML
    private AnchorPane anchorPane;

    private StringBuilder builder = new StringBuilder();

    @FXML
    private Button saveButton;

    @FXML
    private Button closeButton;

    private File file;

    private String filePath = "E:\\IntelliJ\\AppProject\\src\\resources\\ModifiedMaps\\";

    private Controller controller = new Controller();

    /**
     * To choose the map.
     */
    public void chooseMap() {

        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Map");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Map files(*.map)","*.map"));
        selectedfile = chooser.showOpenDialog(anchorPane.getScene().getWindow());
        readFilecontents();
        textArea.setText(builder.toString());
    }

    /**
     * To read the contents from a file.
     */
    public void readFilecontents() {

        String line;
        try {
            BufferedReader reader = new BufferedReader(new FileReader(selectedfile));
            while((line = reader.readLine()) != null) {
                builder.append(line + "\n");
            }
        } catch(Exception e) {
            System.out.println("Could not load the contents from the file");
            e.printStackTrace();
            return;
        }
    }

    public void saveContent() {

        file = new File(selectedfile.getName());

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(textArea.getText());
            writer.flush();
            writer.close();

            boolean result = ismapValid(file, "EDITMAP");
            if(result) {
                System.out.println("Map is Valid and successfully saved.");
                Path path = Paths.get(filePath+selectedfile.getName());
                try {
                    writer = Files.newBufferedWriter(path);
                    writer.write(textArea.getText());
                    writer.flush();
                } finally {
                    if(writer != null) writer.close();
                    cancelAction();
                }

            } else {
                System.out.println("Invalid Map. Remodify the map.");

                // Clear the data.
                GameDetails.getGamedetails().clearData();

                // Closes the window
                cancelAction();
            }

        } catch(Exception e) {
            System.out.println("Cannot write contents to the file.");
            e.printStackTrace();
            return;
        }
    }
    
    /**
     * To close the Map editor window.
     */
    public void cancelAction() {

        Stage stage = (Stage) closeButton.getScene().getWindow();
        GameDetails.getGamedetails().getgamedetails().clear();
        stage.close();
    }

    public boolean ismapValid(File file, String typename) {

        GameDetails.getGamedetails().getgamedetails().add(new GameDetails(file, typename));

        // To get the index of the Game details object of type map editor.
        int index = controller.returnIndex(typename);

        GameDetails.getGamedetails().getgamedetails().get(index).createMap(index);
        boolean result = GameDetails.getGamedetails().getgamedetails().get(index).validateMap(index);
        System.out.println("Is Map Valid function result is = " + result);

        return result;
    }
}

    