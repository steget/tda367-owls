package storagesystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import storagesystem.StorageSystem;
import storagesystem.model.Item;
import storagesystem.model.Organisation;
import storagesystem.model.Team;
import storagesystem.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    private Organisation currentOrganisation;
    private Team currentlySelectedTeam;
    private User currentUser;
    private List<Item> inventory;
    private List<Team> currentUsersTeams = new ArrayList<>();
    private ObservableList<String> teamNames = FXCollections.observableArrayList();


    private int currentlySelectedTeamIndex;
    private boolean isUserPartOfTeam;

    @FXML
    FlowPane itemPane;

    @FXML
    ChoiceBox teamChooser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentOrganisation = StorageSystem.getCurrentOrganisation();
        currentUser = StorageSystem.getCurrentUser();
        isUserPartOfTeam = currentOrganisation.getUsersTeams(currentUser).size() > 0;
        fillTeamAttributes();
        refreshItems();
    }

    private void fillTeamAttributes() {
        currentUsersTeams = currentOrganisation.getUsersTeams(currentUser);
        currentlySelectedTeam = currentUsersTeams.get(0);

        for (Team t : currentUsersTeams) { //adds team names into an observable list.
            teamNames.add(t.getName());
        }
        teamChooser.setItems(teamNames);
        teamChooser.setValue(teamNames.get(0)); //show first value in box

        currentlySelectedTeamIndex = 0;
        teamChooserListener();


    }

    //checks when the users changes team in the dropdown
    private void teamChooserListener() {
        teamChooser.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int newIndex = newValue.intValue();
            for (Team t : currentUsersTeams) {
                if (t.getName().equals(teamChooser.getItems().get(newIndex))) {
                    currentlySelectedTeamIndex = newIndex;
                    currentlySelectedTeam = t;
                    refreshItems();
                    break;
                }
            }
        });

    }

    private void refreshItems() {

        inventory = currentlySelectedTeam.getAllItems();
        itemPane.getChildren().removeAll();
        itemPane.getChildren().add(new InventoryListItemController("Namn", "antal", "bra", "NollKIT äger allt", "FRUM"));

        for (Item i : inventory) {
            itemPane.getChildren().add(new InventoryListItemController("Namn", "antal", "bra", "NollKIT äger allt", "FRUM"));

        }

    }
}
