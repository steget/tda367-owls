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
 * SettingsController controls the settingsView.
 */
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

    private User currentUser;
    private List<Team> currentUsersTeams;
    private Team currentTeamSelected;
    private ObservableList<String> teamNames;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        currentUser = StorageSystem.getCurrentUser();
        currentUsersTeams = new ArrayList<>(StorageSystem.getCurrentOrganisation().getUserTeams(currentUser));
        currentTeamSelected = currentUsersTeams.get(0);
        teamNames = FXCollections.observableArrayList();

        refreshView(); //Updates the View

        settingsChooseTeamInput.setValue(teamNames.get(0)); //show first value in box


        settingsNameInput.setText(currentUser.getName());
        settingsTeamNameInput.setText(currentTeamSelected.getName());

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
            for (Team t : currentUsersTeams) {
                if (t.getName().equals(settingsChooseTeamInput.getItems().get(newValue.intValue()))) {
                    currentTeamSelected = t;
                    changeTeam(currentTeamSelected);
                }
            }
        });
    }

    /**
     * Saves the inputs to the active team.
     */
    //todo fix so not NPE when saving
    @FXML
    public void saveTeam() {
        currentTeamSelected.setTermsAndConditions(settingsContractInput.getText());
        currentTeamSelected.setName(settingsTeamNameInput.getText());
        settingsChooseTeamInput.setValue(teamNames.get(0));
        refreshView();
    }


    /**
     * Updates the View when called. It does this by refreshing the values that is shown in boxes.
     * It clears all lists and refills them.
     */
    void refreshView() {

        //Clear all lists
        if(teamNames.size() > 1){
            teamNames.clear();
        }

       settingsChooseTeamInput.getItems().clear();


        for (Team t : currentUsersTeams) { //adds team names into an observable list.
            teamNames.add(t.getName());
        }

        settingsChooseTeamInput.setItems(teamNames);

    }

    /**
     * this method is called when a user switches its team. It changes the inputs in the boxes.
     * @param currentTeam
     */
    private void changeTeam(Team currentTeam) {
        settingsTeamNameInput.setText(currentTeam.getName());
        settingsContractInput.setText(currentTeam.getTermsAndConditions());
    }
}



