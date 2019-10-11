package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sun.plugin.javascript.navig.Anchor;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {


    @FXML
    AnchorPane rootPane;

    @FXML
    ImageView settingsImage;

    @FXML
    Button settingsButton;

    @FXML
    AnchorPane objectPane;

    @FXML
    Label title;

    @FXML
    private Button showAllItemsButton;

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

    /**
     * Open the itemList panel
     * @throws IOException If the itemList view cannot be found
     */
    @FXML
    void searchButtonPressed() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/itemList.fxml"));
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
