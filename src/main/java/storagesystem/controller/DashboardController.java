package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       //settingsImage.setImage(new Image("settingsIcon.png"));

    }
/*
    /**
     * Open the settings panel
     * @throws IOException If the settings view cannot be found

    @FXML
    void settingsButtonPressed() throws IOException {
        AnchorPane settings = FXMLLoader.load(getClass().getClassLoader().getResource("settings.fxml"));
        objectPane.getChildren().removeAll();
        objectPane.getChildren().add(settings);
    }
*/
}
