package storagesystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import storagesystem.StorageSystem;
import storagesystem.model.Organisation;
import storagesystem.model.Team;
import storagesystem.model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Control a settings page
 */
public class SettingsController extends AnchorPane implements Initializable {

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
    private TextField settingsAddUserInput;

    @FXML
    private TextField settingsRemoveUserInput;

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
    private ChoiceBox<String> settingsChooseTeamInput;

    private User currentUser;
    private List<Team> currentUsersTeams = new ArrayList<>();
    private Team currentlySelectedTeam;
    private ObservableList<String> teamNames = FXCollections.observableArrayList();
    private int currentlySelectedTeamIndex;
    private Organisation currentOrganisation;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentOrganisation = StorageSystem.getCurrentOrganisation();
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
        settingsDescriptionInput.setText(currentUser.getDescription());
        settingsContactInput.setText(currentUser.getContactInformation());
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

//        currentOrganisation.saveTeam(currentlySelectedTeam);
//        StorageSystem.getCurrentOrganisation().saveTeam(currentlySelectedTeam);

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

//        currentOrganisation.saveUser(currentUser);
//        StorageSystem.setCurrentOrganisation(currentOrganisation);
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

    /**
     * Adds a member to the current team selected if a username is written in the input.
     * Returns true if user get added in team.
     * If user already exists in team it returns false.
     *
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
     *
     * @return true or false depending if the user got removed or not
     */
    @FXML
    private boolean removeMemberFromTeam() {

        int userID = 0;
        for (User user : currentOrganisation.getUsers()) {
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