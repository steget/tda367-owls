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

public class FrameController implements Initializable, IController {


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
    @FXML
    BorderPane borderPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void settingsButtonPressed(){
        loadUI("/settings");
    }

    @Override
    public void loadUI(String ui){
        Parent root = null;
        try{
            root = FXMLLoader.load(getClass().getResource(ui+".fxml"));
        } catch (IOException e){
            Logger.getLogger(FrameController.class.getName()).log(Level.SEVERE, null, e);
        }
        borderPane.setCenter(root);
    }
}
