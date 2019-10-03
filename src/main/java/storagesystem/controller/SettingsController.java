package storagesystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import storagesystem.StorageSystem;
import storagesystem.model.Team;
import storagesystem.model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Control a settings page
 */
public class SettingsController implements Initializable {

    @FXML
    private Label settingsLabel;

    @FXML
    private AnchorPane settingsTeamAnchorPane;

    @FXML
    private AnchorPane settingsUserAnchorPane;

    @FXML
    private Label settingsUserLabel;

    @FXML
    private Label settingsTeamLabel;

    @FXML
    private Button settingsUserSave;

    @FXML
    private Button settingsTeamSave;

    @FXML
    private TextField settingsNameInput;

    @FXML
    private TextField settingsTeamNameInput;
    //todo change this to a TextArea so we can set the text to wrap
    @FXML
    private TextField settingsTeamContractInput;

    @FXML
    private TextField settingsContactInput;

    @FXML
    private TextField settingsDescriptionInput;

    @FXML
    private ChoiceBox settingsChooseTeamInput;

    private User currentUser;
    private List<Team> currentUsersTeams = new ArrayList<>();
    private Team currentlySelectedTeam;
    private ObservableList<String> teamNames = FXCollections.observableArrayList();
    private int currentlySelectedTeamIndex;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentUser = StorageSystem.getCurrentUser();
        currentUsersTeams = StorageSystem.getCurrentOrganisation().getUserTeams(currentUser);
        currentlySelectedTeam = currentUsersTeams.get(0);
        for (Team t : currentUsersTeams) { //adds team names into an observable list.
            teamNames.add(t.getName());
        }

        settingsChooseTeamInput.setItems(teamNames);
        settingsChooseTeamInput.setValue(teamNames.get(0)); //show first value in box
        currentlySelectedTeamIndex = 0;

        settingsNameInput.setText(currentUser.getName());
        settingsTeamNameInput.setText(currentlySelectedTeam.getName());
        settingsTeamContractInput.setText(currentlySelectedTeam.getTermsAndConditions());

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
            int newIndex = newValue.intValue();
            /*
            index will be changed to -1 if you change a value in the code,
            don't want to check that value in an array and it isn't an actual teamchange
             */
            if (newIndex >= 0) {
                for (Team t : currentUsersTeams) {
                    if (t.getName().equals(settingsChooseTeamInput.getItems().get(newIndex))) {
                        currentlySelectedTeamIndex = newIndex;
                        currentlySelectedTeam = t;
                        changeTeam();
                        break;
                    }
                }
            }
        });
    }

    /**
     * Saves the inputs to the active team.
     */
    @FXML
    public void saveTeam() {
        currentlySelectedTeam.setTermsAndConditions(settingsTeamContractInput.getText());
        currentlySelectedTeam.setName(settingsTeamNameInput.getText());

        updateChangedTeamNameInChoicebox();
        updateTeamsChoicebox();
    }

    /**
     * Updates the View when called. It does this by refreshing the values that is shown in boxes.
     * It clears all lists and refills them.
     */
    private void updateTeamsChoicebox() {
        settingsChooseTeamInput.getSelectionModel().select(currentlySelectedTeamIndex);
    }

    /**
     * Changes the text in the Choicebox, of the team which just changed name, to the teams new name.
     */
    private void updateChangedTeamNameInChoicebox() {
        teamNames.set(settingsChooseTeamInput.getSelectionModel().getSelectedIndex(), settingsTeamNameInput.getText());
    }

    /**
     * Update the texts in team name and a teams contract to their values.
     */
    private void changeTeam() {
        settingsTeamNameInput.setText(currentlySelectedTeam.getName());
        settingsTeamContractInput.setText(currentlySelectedTeam.getTermsAndConditions());
    }
}