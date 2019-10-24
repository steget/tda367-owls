package storagesystem.viewcontroller.inventory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import storagesystem.model.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Pär Aronsson
 */
public class InventoryController implements Initializable {

    private Team currentlySelectedTeam;
    private List<Team> currentUsersTeams = new ArrayList<>();
    private ObservableList<String> teamNames = FXCollections.observableArrayList();
    private ItemDetailViewController detailView;

    @FXML
    FlowPane itemPane;

    @FXML
    AnchorPane rootPane;

    @FXML
    ChoiceBox teamChooser;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentlySelectedTeam = StoreIT.getCurrentTeam();
        currentUsersTeams = StoreIT.getCurrentOrganisation().getUsersTeams(StoreIT.getCurrentUser());
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
        teamChooserListener();
    }

    private EventHandler<MouseEvent> detailViewClickedHandler = e -> {
        detailViewClicked();
        if(e.getSource() == null){

        }
        e.consume();
    };

    private EventHandler<MouseEvent> listItemClickedHandler = e -> {
        InventoryListItemController panel = (InventoryListItemController) e.getSource();
        inventoryListItemClicked(panel.getReservableItem());
        e.consume();
    };

    /**
     * removes the detailed itemView from rootPane.
     */
    private void detailViewClicked() {
        rootPane.getChildren().remove(detailView);
    }

    private void inventoryListItemClicked(IReservable item) {
        detailView = new ItemDetailViewController(item);
        detailView.addEventHandler(MouseEvent.MOUSE_CLICKED, detailViewClickedHandler);
        rootPane.getChildren().add(detailView);
    }

    /**
     * Choicebox that listens for change, selects new team & refreshes the list.
     * checks when the users changes team in the ChoiceBox.
     */
    private void teamChooserListener() {
        teamChooser.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
            int newIndex = newValue.intValue();
            for (Team t : currentUsersTeams) {
                if (t.getName().equals(teamChooser.getItems().get(newIndex))) {
                    StoreIT.setCurrentTeam(t);
                    currentlySelectedTeam = t;
                    refreshItems();
                    break;
                }
            }
        });
    }

    /**
     * refreshes the list of items in the inventory.
     * It removes all the items in list and renews with new items.
     */
    private void refreshItems() {
        List<IReservable> inventory = StoreIT.getCurrentOrganisation().getTeamsItems(currentlySelectedTeam);
        itemPane.getChildren().remove(0, itemPane.getChildren().size());
        for (IReservable i : inventory) {
            InventoryListItemController newListItem = new InventoryListItemController(i);
            newListItem.addEventHandler(MouseEvent.MOUSE_CLICKED, listItemClickedHandler);
            itemPane.getChildren().add(newListItem);
        }
    }

   /* *//**
     * opens up a detailed view of the pressed item.
     *
     * @param item
     *//*
    private void listItemClicked(IReservable item) {
        detailView = new ItemDetailViewController(item);
        rootPane.getChildren().add(detailView);
        detailView.addDetailListener(this::detailViewClicked);
        detailView.addSaveButtonListener(this::saveButtonClicked);
        detailView.editItem();
    }*/

    private void saveButtonClicked() {
        refreshItems();
    }


}
