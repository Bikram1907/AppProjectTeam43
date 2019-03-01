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
}