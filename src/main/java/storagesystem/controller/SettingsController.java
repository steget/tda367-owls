package storagesystem.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import storagesystem.StorageSystem;
import storagesystem.model.Team;
import storagesystem.model.User;
import sun.plugin.javascript.navig.Anchor;
import sun.plugin.javascript.navig.Array;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeoutException;

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

    @FXML
    Button settingsUserSave;

    @FXML
    Button settingsTeamSave;


    /**
     * These textfields are where data is gathered
     */

    @FXML
    TextField settingsNameInput;

    @FXML
    TextField settingsTeamNameInput;

    @FXML
    TextField settingsContractInput;

    @FXML
    TextField settingsContactInput;

    @FXML
    TextField settingsDescriptionInput;

    @FXML
    ChoiceBox settingsChooseTeamInput;

    private User user;
    private List<Team> teams;
    private Team currentTeam;
    private ObservableList<String> teamNames;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        user = StorageSystem.getCurrentUser();
        teams = new ArrayList<>(StorageSystem.getCurrentOrganisation().getUserTeams(user));
        currentTeam = teams.get(0);
        teamNames = FXCollections.observableArrayList();

        update();

        settingsChooseTeamInput.setValue(teamNames.get(0)); //show first value in box


        settingsNameInput.setText(user.getName());
        settingsTeamNameInput.setText(currentTeam.getName());

        settingsUserLabel.setOnMouseClicked((event -> {
            if (!settingsUserAnchorPane.isVisible()) {
                settingsUserAnchorPane.setVisible(true);
                settingsTeamAnchorPane.setVisible(false);
            }
        }));
        settingsTeamLabel.setOnMouseClicked((event -> {
            if (settingsUserAnchorPane.isVisible()) {
                settingsUserAnchorPane.setVisible(false);
                settingsTeamAnchorPane.setVisible(true);
            }
        }));


        settingsChooseTeamInput.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            for (Team t : teams) {
                if (t.getName().equals(settingsChooseTeamInput.getValue())) {   //removed toString()
                    currentTeam = t;
                    changeTeam(currentTeam);
                }
            }
        });
    }
    @FXML
    public void saveTeam() {
        currentTeam.setTermsAndConditions(settingsContractInput.getText());
        currentTeam.setName(settingsTeamNameInput.getText());
        settingsChooseTeamInput.setValue(teamNames.get(0));
        update();
    }

    void update() {

        //Clear all lists
        if(teamNames.size() > 1){
            teamNames.clear();
        }

       settingsChooseTeamInput.getItems().clear();


        for (Team t : teams) { //adds team names into an observable list.
            teamNames.add(t.getName());
        }

        settingsChooseTeamInput.setItems(teamNames);

    }

    private void changeTeam(Team currentTeam) {
        settingsTeamNameInput.setText(currentTeam.getName());
        settingsContractInput.setText(currentTeam.getTermsAndConditions());
    }
}



