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
}