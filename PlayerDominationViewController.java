package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import sample.model.Continent;
import sample.model.GameDetails;

import java.util.Observable;
import java.util.Observer;

/**
 * This controller is controls the players domination view window
 * This controller responsible for displaying the actions happening in the background of the game.
 * @author Team43
 */
public class PlayersDominationViewController implements Observer {

    @FXML
    public TextArea dominationViewTextArea;

    /**
     * This method initializes the default text to the text area.
     */
    @FXML
    public void initialize() {

        this.dominationViewTextArea.setText("This window will display the background actions of the game.");
        this.dominationViewTextArea.appendText("\n");
        this.dominationViewTextArea.appendText("........................................................................." +
                "..................................................................................................."
                );
        this.dominationViewTextArea.appendText("\n");
    }
    
    /**
     * This method adds the text to the text area.
     * @param text
     */
    @FXML
    public void addTextToTextArea(String text) {

        this.dominationViewTextArea.appendText(text);
        this.dominationViewTextArea.appendText("\n");
    }

    /**
     * To set the text to the view.
     * @param text
     */
    @FXML
    public void setTextToTextArea(String text) {

        this.dominationViewTextArea.setText(text);
        this.dominationViewTextArea.appendText("\n");
    }
    
    /**
     * To clear the contents on the view.
     */
    @FXML
    public void clearTextInTextArea() {

        this.dominationViewTextArea.clear();
    }
    /**
     * This method closes the window.
     */
    public void closeWindow() {

        Stage stage = (Stage) dominationViewTextArea.getScene().getWindow();
        stage.close();
    }
}
