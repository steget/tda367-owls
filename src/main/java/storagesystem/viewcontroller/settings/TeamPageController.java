package storagesystem.viewcontroller.settings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import storagesystem.model.StoreIT;
import storagesystem.model.Team;
import storagesystem.model.User;
import storagesystem.viewcontroller.AbstractFader;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Control the  teamPage
 * @author Hugo Stegrell, Pär Aronsson, Jonathan Eksberg
 */
public class TeamPageController extends AnchorPane implements Initializable {

    @FXML
    private AnchorPane teamAnchorPane;

    @FXML
    private AnchorPane editTeamAnchorPane;

    @FXML
    private TextField settingsAddUserInput;

    @FXML
    private TextField settingsRemoveUserInput;

    @FXML
    private TextField settingsTeamNameInput;

    @FXML
    private TextArea settingsTeamContractInput;

    @FXML
    private TextArea teamToCTextArea;

    @FXML
    private Button saveTeamButton;

    @FXML
    private Button cancelTeamButton;

    @FXML
    private ChoiceBox<String> settingsChooseTeamInput;

    @FXML
    private Label teamLabel;

    @FXML
    private Label userAddedMsg;

    @FXML
    private Label userRemovedMsg;

    @FXML
    private Label userAlreadyInTeamMsg;

    @FXML
    private Label userDoesNotExistMsg;

    @FXML
    private Label userNotPartOfTeamMsg;

    @FXML
    private Label teamNameTooLongMsg;

    @FXML
    private Label teamNameTooShortMsg;

    private List<Team> currentUsersTeams = new ArrayList<>();
    private ObservableList<String> teamNames = FXCollections.observableArrayList();
    private int currentlySelectedTeamIndex;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        boolean isUserPartOfTeam;
        User currentUser = StoreIT.getCurrentUser();
        isUserPartOfTeam = StoreIT.getCurrentOrganisation().getUsersTeams(currentUser).size() > 0;

        if (isUserPartOfTeam) {
            fillTeamAttributes();
        }

        assignListeners();
    }

    /**
     * Adds listeners to the User and Team labels, aswell as the dropdown for teams
     */
    private void assignListeners() {

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
                        StoreIT.setCurrentTeam(t);
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

        currentUsersTeams = StoreIT.getCurrentOrganisation().getUsersTeams(StoreIT.getCurrentUser());
        Team currentlySelectedTeam = currentUsersTeams.get(0);

        for (Team t : currentUsersTeams) { //adds team names into an observable list.
            teamNames.add(t.getName());
        }
        settingsChooseTeamInput.setItems(teamNames);
        settingsChooseTeamInput.setValue(teamNames.get(0)); //show first value in box
        currentlySelectedTeamIndex = 0;

        //fill text boxes in settings page for team
        settingsTeamNameInput.setText(currentlySelectedTeam.getName());
        settingsTeamContractInput.setText(currentlySelectedTeam.getTermsAndConditions());
        teamLabel.setText(currentlySelectedTeam.getName());
        teamToCTextArea.setText(currentlySelectedTeam.getTermsAndConditions());
    }

    /**
     * Saves the inputs to the active team.
     */
    @FXML
    public void saveTeam() {
        StoreIT.getCurrentTeam().setTermsAndConditions(settingsTeamContractInput.getText());
        if(settingsTeamNameInput.getText().length() > 20){
            AbstractFader.fadeTransition(teamNameTooLongMsg, 3);
        }
        else if(settingsTeamNameInput.getText().length() < 6){
            AbstractFader.fadeTransition(teamNameTooShortMsg, 3);
        }
        else{
            StoreIT.getCurrentTeam().setName(settingsTeamNameInput.getText());
            updateChangedTeamNameInChoicebox();
            updateTeamsChoicebox();
            teamAnchorPane.toFront();
        }
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
        Team currentlySelectedTeam = StoreIT.getCurrentTeam();
        settingsTeamNameInput.setText(currentlySelectedTeam.getName());
        settingsTeamContractInput.setText(currentlySelectedTeam.getTermsAndConditions());
        teamLabel.setText(currentlySelectedTeam.getName());
        teamToCTextArea.setText(currentlySelectedTeam.getTermsAndConditions());
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
                if (StoreIT.getCurrentTeam().getAllMemberIDs().contains(user.getID())) {
                    AbstractFader.fadeTransition(userAlreadyInTeamMsg, 2);
                } else {
                    StoreIT.getCurrentTeam().addMember(user.getID());
                    AbstractFader.fadeTransition(userAddedMsg, 2);
                }
            }
        }
        if (!doesUserExist) {
            AbstractFader.fadeTransition(userDoesNotExistMsg, 2);
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
        for (int i : StoreIT.getCurrentTeam().getAllMemberIDs()) {
            if (i == userID) {
                StoreIT.getCurrentTeam().removeMember(userID);
                AbstractFader.fadeTransition(userRemovedMsg, 2);
                memberFound = true;
                break;
            }
        }

        if (!memberFound) {
            AbstractFader.fadeTransition(userNotPartOfTeamMsg, 2);
        }
    }

    @FXML
    private void editTeamButtonPressed(){
        editTeamAnchorPane.toFront();
    }

    @FXML
    private void cancelTeamButtonPressed(){
        teamAnchorPane.toFront();
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
        for (User user : StoreIT.getCurrentOrganisation().getUsers()) {
            if (user.getName().equals(userName)) {
                tempUserID = user.getID();
                break;
            }
        }
        return tempUserID;
    }
}