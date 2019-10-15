package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Jonathan Eksberg
 * Controller for the frame
 */
public class FrameworkController implements Initializable, ILoadUI {


    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane centerPane;
    @FXML
    private Button settingsButton;
    @FXML
    private Button searchButton;
    @FXML
    private Button teamButton;
    @FXML
    private Button userButton;
    @FXML
    private Button yourInventoryButton;
    @FXML
    private Button allItemsButton;
    @FXML
    private Button reservationsButton;
    @FXML
    private Pane frameTopPane;
    @FXML
    private BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void settingsButtonPressed() {
        loadUI("settings");
    }

    @FXML
    void allItemsButtonPressed() {
        loadUI("itemList");
    }

    @FXML
    void reservationsButtonPressed() {
        loadUI("reservations");

    }


    /**
     * Method to avoid repetetive code.
     *
     * @param ui The url to a fxml file.
     */
    @Override
    public void loadUI(String ui) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/" + ui + ".fxml"));
        } catch (IOException e) {
            Logger.getLogger(FrameworkController.class.getName()).log(Level.SEVERE, null, e);
        }
        borderPane.setCenter(root);
    }
}
