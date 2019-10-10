package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    @FXML
    AnchorPane rootPane;
    @FXML
    AnchorPane centerPane;
    @FXML
    ImageView settingsImage;
    @FXML
    Button settingsButton;
    @FXML
    Button searchButton;
    @FXML
    Button teamButton;
    @FXML
    Button userButton;

    @FXML
    AnchorPane objectPane;

    @FXML
    Label title;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settingsImage.setImage(new Image("settingsIcon.png"));

    }

    /**
     * Open the settings panel
     * @throws IOException If the settings view cannot be found
     */
    @FXML
    void settingsButtonPressed() throws IOException {
        AnchorPane settings = FXMLLoader.load(getClass().getClassLoader().getResource("settings.fxml"));
        objectPane.getChildren().removeAll();
        objectPane.getChildren().add(settings);
    }

}
