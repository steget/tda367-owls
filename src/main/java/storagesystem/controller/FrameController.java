package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FrameController implements Initializable {


    @FXML
    AnchorPane rootPane;
    @FXML
    AnchorPane centerPane;
    @FXML
    Button settingsButton;
    @FXML
    Button searchButton;
    @FXML
    Button teamButton;
    @FXML
    Button userButton;
    @FXML
    Button yourInventoryButton;
    @FXML
    Pane frameTopPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       //settingsImage.setImage(new Image("settingsIcon.png"));

    }

    /**
     * Open the settings panel
     * @throws IOException If the settings view cannot be found
     *
     */

    @FXML
    void settingsButtonPressed() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/settings.fxml"));
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

}