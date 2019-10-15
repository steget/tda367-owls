package storagesystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import storagesystem.StoreIT;
import storagesystem.model.Organisation;
import storagesystem.model.Team;
import storagesystem.model.User;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Control a settings page
 * @author Hugo Stegrell, Pär Aronsson
 */
public class SettingsController extends AnchorPane implements Initializable {

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
    private boolean isUserPartOfTeam;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentOrganisation = StoreIT.getCurrentOrganisation();
        currentUser = StoreIT.getCurrentUser();

        isUserPartOfTeam = StoreIT.getCurrentOrganisation().getUsersTeams(currentUser).size() > 0;

        if (isUserPartOfTeam) {
            fillTeamAttributes();
        }

        //fill user text boxes
        settingsNameInput.setText(currentUser.getName());
        settingsDescriptionInput.setText(currentUser.getDescription());
        settingsContactInput.setText(currentUser.getContactInformation());

        assignListeners();
    }

    /**
     * Adds listeners to the User and Team labels, aswell as the dropdown for teams
     */
    private void assignListeners() {
        //when user is clicked the users settings should show
        settingsUserLabel.setOnMouseClicked((event -> {
            if (!settingsUserAnchorPane.isVisible()) {
                settingsUserAnchorPane.setVisible(true);
                settingsTeamAnchorPane.setVisible(false);
            }
        }));

        //when team is clicked the teams settings should show
        if (isUserPartOfTeam) {
            settingsTeamLabel.setOnMouseClicked((event -> {
                if (settingsUserAnchorPane.isVisible()) {
                    settingsUserAnchorPane.setVisible(false);
                    settingsTeamAnchorPane.setVisible(true);
                }
            }));
        }

        //checks when the users changes team in the dropdown
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
     * Fills attributes that has to do with the current users teams.
     */
    private void fillTeamAttributes() {

        currentUsersTeams = StoreIT.getCurrentOrganisation().getUsersTeams(currentUser);
        currentlySelectedTeam = currentUsersTeams.get(0);

        for (Team t : currentUsersTeams) { //adds team names into an observable list.
            teamNames.add(t.getName());
        }
        settingsChooseTeamInput.setItems(teamNames);
        settingsChooseTeamInput.setValue(teamNames.get(0)); //show first value in box
        currentlySelectedTeamIndex = 0;

        //fill text boxes in settings page for team
        settingsTeamNameInput.setText(currentlySelectedTeam.getName());
        settingsTeamContractInput.setText(currentlySelectedTeam.getTermsAndConditions());

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
     * Save the current users data that was put in.
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
     * Adds a member to the currently selected team if a existing username is written in the input.
     */
    @FXML
    private void addMemberButtonPressed() {
        boolean doesUserExist = false;
        for (User user : StoreIT.getCurrentOrganisation().getUsers()) {
            if (user.getName().equals(settingsAddUserInput.getText())) {
                doesUserExist = true;
                    if (currentlySelectedTeam.getAllMemberIDs().contains(user.getID())) {
                        //todo print in program
                    System.out.println("User is already a part of this team.");
                } else {
                    currentlySelectedTeam.addMember(user.getID());
                }
            }
        }
        if (!doesUserExist) {
            //todo print in program
            System.out.println("User does not exist.");
        }
    }

    /**
     * Handle when the removeButton has been pressed
     */
    @FXML
    private void removeMemberButtonPressed() {
        int tempUserID = getUserIDFromName(settingsRemoveUserInput.getText());
        removeMemberFromTeam(tempUserID);
    }

    /**
     * Removes a user from the currently selected team.
     */
    @FXML
    private void removeMemberFromTeam(int userID) {
        boolean memberFound = false;
        //remove member from team
        for (int i : currentlySelectedTeam.getAllMemberIDs()) {
            if (i == userID) {
                currentlySelectedTeam.removeMember(userID);
                memberFound = true;
                break;
            }
        }

        if (!memberFound) {
            //todo print in program instead
            System.out.println("Could not remove user since user is not a part of the team.");
        }
    }

    /**
     * Compares the input with all users in the organisation. If a match is made the userID is returned. Returns -1 otherwise which will never be any user's  ID.
     *
     * @param userName Name of the user which ID you want to find
     * @return Found ID
     */
    private int getUserIDFromName(String userName) {
        int tempUserID = -1;
        //check if user with matching name in textbox exists
        for (User user : currentOrganisation.getUsers()) {
            if (user.getName().equals(userName)) {
                tempUserID = user.getID();
                break;
            }
        }
        return tempUserID;
    }
}