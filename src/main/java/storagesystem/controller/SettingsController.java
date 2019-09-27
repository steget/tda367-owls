package storagesystem.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sun.plugin.javascript.navig.Anchor;

import java.io.IOException;
import java.net.URL;
import java.util.EventListener;
import java.util.ResourceBundle;

public class SettingsController implements Initializable {

    @FXML
    Label settingsLabel;

    @FXML
    AnchorPane settingsTeamAnchorPane;

    @FXML
    AnchorPane settingsUserAnchorPane;

    @FXML
    Label settingsUserLabel;

    @FXML
    Label settingsTeamLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settingsLabel.setText("Hello World!");


        settingsUserLabel.setOnMouseClicked((event -> {

            if(!settingsUserAnchorPane.isVisible()) {
                settingsUserAnchorPane.setVisible(true);
                settingsTeamAnchorPane.setVisible(false);
            }
        }));
        settingsTeamLabel.setOnMouseClicked((event -> {
            if(settingsUserAnchorPane.isVisible()) {
                settingsUserAnchorPane.setVisible(false);
                settingsTeamAnchorPane.setVisible(true);
            }

        }));





    }






    }



