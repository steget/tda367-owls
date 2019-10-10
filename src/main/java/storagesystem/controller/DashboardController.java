package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    AnchorPane rootPane;

    @FXML
    Button settingsButton;

    @FXML
    private Button searchButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * Open the settings panel
     * @throws IOException If the settings view cannot be found
     */
    @FXML
    void settingsButtonPressed() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/settings.fxml"));
        Stage stage = (Stage) rootPane.getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
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
