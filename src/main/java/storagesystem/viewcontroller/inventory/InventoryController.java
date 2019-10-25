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
import storagesystem.model.IReservable;
import storagesystem.model.StoreIT;
import storagesystem.model.Team;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Pär Aronsson, Hugo Stegrell
 */
public class InventoryController implements Initializable {

    @FXML
    FlowPane itemPane;
    @FXML
    AnchorPane rootPane;
    @FXML
    ChoiceBox teamChooser;
    private List<Team> currentUsersTeams = new ArrayList<>();
    private ObservableList<String> teamNames = FXCollections.observableArrayList();
    private EditItemDetailViewController detailView;
    private ItemCreateViewController createView;

    private EventHandler<MouseEvent> closeDetailViewClickedHandler = e -> {
        closeDetailView();
        e.consume();
    };
    private EventHandler<MouseEvent> saveButtonClickedHandler = e -> {
        saveButtonClicked();
        e.consume();
    };
    private EventHandler<MouseEvent> listItemClickedHandler = e -> {
        InventoryListItemController panel = (InventoryListItemController) e.getSource();
        inventoryListItemClicked(panel.getReservableItem());
        e.consume();
    };
    private EventHandler<MouseEvent> createItemClickedHandler = e -> {
        if (createView.checkIfBoxesAreFilled()) {
            createView.createItem();
            refreshItems();
            closeCreateItemView();
        }
        e.consume();
    };
    private EventHandler<MouseEvent> closeCreateItemHandler = e -> {
        closeCreateItemView();
        e.consume();
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentUsersTeams = StoreIT.getCurrentOrganisation().getUsersTeams(StoreIT.getCurrentUser());
        fillTeamAttributes();
        refreshItems();
    }

    /**
     * if a user is apart of a team. then this method fills the choicebox.
     */
    private void fillTeamAttributes() {
        StoreIT.setCurrentTeam(currentUsersTeams.get(0));
        for (Team t : currentUsersTeams) { //adds team names into an observable list.
            teamNames.add(t.getName());
        }
        teamChooser.setItems(teamNames);
        teamChooser.setValue(teamNames.get(0)); //show first value in box
        teamChooserListener();
    }

    /**
     * removes the detailed itemView from rootPane.
     */
    private void closeDetailView() {
        rootPane.getChildren().remove(detailView);
    }

    private void closeCreateItemView() {
        rootPane.getChildren().remove(createView);
    }

    private void inventoryListItemClicked(IReservable item) {
        detailView = new EditItemDetailViewController(item);
        detailView.addEventHandler(MouseEvent.MOUSE_CLICKED, closeDetailViewClickedHandler);
        detailView.closeButtonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, closeDetailViewClickedHandler);
        detailView.itemPageSaveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, saveButtonClickedHandler);
        detailView.enableEditMode();
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
        List<IReservable> inventory = StoreIT.getCurrentOrganisation().getTeamsItems(StoreIT.getCurrentTeam());
        itemPane.getChildren().clear();
        for (IReservable i : inventory) {
            InventoryListItemController newListItem = new InventoryListItemController(i);
            newListItem.addEventHandler(MouseEvent.MOUSE_CLICKED, listItemClickedHandler);
            itemPane.getChildren().add(newListItem);
        }
    }

    @FXML
    private void addItem() {
        createView = new ItemCreateViewController();
        createView.createItemButton.addEventHandler(MouseEvent.MOUSE_CLICKED, createItemClickedHandler);
        createView.addEventHandler(MouseEvent.MOUSE_CLICKED, closeCreateItemHandler);
        createView.closeButtonImageView.addEventHandler(MouseEvent.MOUSE_CLICKED, closeCreateItemHandler);
        rootPane.getChildren().add(createView);
    }

    private void saveButtonClicked() {
        detailView.saveItem();
        refreshItems();
    }
}