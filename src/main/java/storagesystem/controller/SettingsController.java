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
    private TextField settingsAddUserInput;

    @FXML
    private TextField settingsRemoveUserInput;


    /**
     * These textfields are where data is gathered
     */

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
        currentUsersTeams = StorageSystem.getCurrentOrganisation().getUsersTeams(currentUser);
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
     * Save the current users data that inputted.
     */
    @FXML
    public void saveUser() {
        currentUser.setName(settingsNameInput.getText());
        currentUser.setDescription(settingsDescriptionInput.getText());
        currentUser.setContactInformation(settingsContactInput.getText());
    }

    /**
     * Updates the View when called. It does this by refreshing the values that is shown in boxes.
     * It clears all lists and refills them.
     */
    private void updateTeamsChoicebox() {
        settingsChooseTeamInput.getSelectionModel().select(currentlySelectedTeamIndex);
    }

    /**
     * Changes the text of the team which just changed name to its new name in the Choicebox values.
     */
    private void updateChangedTeamNameInChoicebox() {
        teamNames.set(settingsChooseTeamInput.getSelectionModel().getSelectedIndex(), settingsTeamNameInput.getText());
    }

    /**
     * This method is called when a user switches team in the choicebox. It changes the values in the boxes.
     */
    private void changeTeam() {
        settingsTeamNameInput.setText(currentlySelectedTeam.getName());
        settingsTeamContractInput.setText(currentlySelectedTeam.getTermsAndConditions());
    }

    /**
     * Adds a member to the current team selected if a username is written in the input.
     * Returns true if user get added in team.
     * If user already exists in team it returns false.
     * @return true or false depending the user got added to the team.
     */
    @FXML
    private boolean addMemberToTeam() {

        for (User user : StorageSystem.getCurrentOrganisation().getUsers()) {
            if (user.getName().equals(settingsAddUserInput.getText())) {
                if (!currentlySelectedTeam.doesMemberIDexist(user.getID())) {

                    currentlySelectedTeam.addMember(user.getID());
                    return true;
                } else {
                    System.out.println("User is already apart of this team.");
                    return false;
                }
            }
        }
        System.out.println("User is does not exist.");
        return false;
    }

    /**
     * Removes a user from the current selected team.
     * If the name in @settingsRemoveUserInput variable exists in the team,it gets removed from the team list and returns true.
     * If the name in @settingsRemoveUserInput does not exist it returns false and does nothing.
     *  @return true or false depending if the user got removed or not
     */
    @FXML
    private boolean removeMemberToTeam() {

        int userID = 0;
        for (User user : StorageSystem.getCurrentOrganisation().getUsers()) {
            if (user.getName().equals(settingsRemoveUserInput.getText())) {
                userID = user.getID();
            }
        }
        for (int i : currentlySelectedTeam.getAllMemberIDs()) {
            if (i == userID) {
                currentlySelectedTeam.removeMember(userID);
                return true;
            }

        }
        System.out.println("Could not remove member. Member is not apart of the team.");
        return false;
    }


}