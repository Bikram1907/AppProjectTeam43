package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NewMapController {

    @FXML
    private Button newmapSavebutton,newmapCancelbutton;

    @FXML
    private BorderPane NewMapBorderPane;

    @FXML
    private TextArea newmapTextarea,mapformatTextarea;

    private MapEditorController meController = new MapEditorController();

    private String filePath = "E:\\IntelliJ\\AppProject\\src\\resources\\ModifiedMaps\\";

    private String fileName;

    private static int count = 1;

    @FXML
    private TextField mapnameTextfield;

    /**
     * To set the map format instructions to the textarea.
     */
    @FXML
    public void initialize() {

        String mapInstruction = "[Map]\n" + "image=world.bmp\n" + "wrap=yes\n" + "scroll=horizontal\n" +
                "author=Your name\n" + "warn=yes\n" + "\n" + "The second section describes the Continent names and the " +
                "scores for owning them completely.\n" + "\n" + "[Continents]\n" + "North America=5\n" + "South America=2\n" +
                "Africa=3\n" + "Europe=5\n" + "Asia=7\n" + "Australia=2\n" + "\n" + "The final section describes the " +
                "territories. The first parameter is the territory's name, then the \"x\" and \"y\" coordinates of the " +
                "center of the territory in pixels from the top left corner of the bitmap, then the continent that the " +
                "territory is in and finally list all the territories that this territory connects to. The maximum " +
                "number of continents that a map can have is 32\n" + "\n" + "[Territories]\n" + "Siam,270,139,Asia," +
                "China,India,Indonesia\n" + "Japan,322,104,Asia,Kamchatka,Mongolia\n" + "Ural,241,68,Asia,Siberia," +
                "China,Afghanistan,Ukraine\n" + "etc...\n" + "\n" + "The maximum number of territories that any territory " +
                "can connect to is 10. The maximum number of territories that a map can have is 255.";

        mapformatTextarea.setText(mapInstruction);
    }
}