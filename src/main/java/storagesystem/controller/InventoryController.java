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
import storagesystem.model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Pär Aronsson
 */
public class InventoryController implements Initializable {

    private Organisation currentOrganisation;
    private Team currentlySelectedTeam;
    private User currentUser;
    private List<Item> inventory;
    private List<Team> currentUsersTeams = new ArrayList<>();
    private ObservableList<String> teamNames = FXCollections.observableArrayList();


    private int currentlySelectedTeamIndex;
    private boolean isUserPartOfTeam;

    private DetailedItemViewController detailView;

    @FXML
    FlowPane itemPane;

    @FXML
    AnchorPane rootPane;

    @FXML
    ChoiceBox teamChooser;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentOrganisation = StorageSystem.getCurrentOrganisation();
        currentUser = StorageSystem.getCurrentUser();
        currentlySelectedTeam = StorageSystem.getCurrentTeam();
        isUserPartOfTeam = currentOrganisation.getUsersTeams(currentUser).size() > 0;
        currentUsersTeams = StorageSystem.getCurrentOrganisation().getUsersTeams(currentUser);
        fillTeamAttributes();
        refreshItems();
    }

    /**
     * if a user is apart of a team. then this method fills the choicebox.
     */
    private void fillTeamAttributes() {
        currentlySelectedTeam = currentUsersTeams.get(0);
        for (Team t : currentUsersTeams) { //adds team names into an observable list.
            teamNames.add(t.getName());
        }
        teamChooser.setItems(teamNames);
        teamChooser.setValue(teamNames.get(0)); //show first value in box
        currentlySelectedTeamIndex = 0;
        teamChooserListener();
    }

    /**
     * choicebox that listens for change, selects new team & refreshes the list.
     */
    //checks when the users changes team in the dropdown
    private void teamChooserListener() {
        teamChooser.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int newIndex = newValue.intValue();
            for (Team t : currentUsersTeams) {
                if (t.getName().equals(teamChooser.getItems().get(newIndex))) {
                    currentlySelectedTeamIndex = newIndex;
                    StorageSystem.setCurrentTeam(t);
                    refreshItems();
                    break;
                }
            }
        });
    }

    /**
     * refreshed the list of items in the inventory.
     * It removes all the items in list and renews with new items.
     */
    private void refreshItems() {
        currentlySelectedTeam = StorageSystem.getCurrentTeam();
        inventory = currentlySelectedTeam.getAllItems();
        itemPane.getChildren().remove(0, itemPane.getChildren().size());
        for (Item i : inventory) {
            InventoryListItemController listItem = new InventoryListItemController(i);
            listItem.addListener(this::listItemClicked);
            itemPane.getChildren().add(listItem);
        }
    }

    /**
     * opens up a detailed view of the pressed item.
     * @param item
     */
    private void listItemClicked(IReservable item){
        detailView = new DetailedItemViewController(item, StorageSystem.getCurrentOrganisation().getItemOwner(item));
        rootPane.getChildren().add(detailView);
        detailView.addDetailListener(this::detailItemViewClicked);
        detailView.addSaveButtonListener(this::saveButtonClicked);
        detailView.editItem();
    }



    /**
     * removes the detailed itemView from rootPane.
     */
    private void detailItemViewClicked() {
        rootPane.getChildren().remove(detailView);
    }

    private void saveButtonClicked(){
        refreshItems();
    }


}
