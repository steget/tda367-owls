package storagesystem.viewcontroller.Inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import storagesystem.model.IReservable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Pär Aronsson
 */

public class InventoryListItemController extends AnchorPane {

    public Label getName() {
        return name;
    }

    public Label getAmount() {
        return amount;
    }

    public Label getLocation() {
        return itemLocation;
    }

    public Label getCondition() {
        return condition;
    }

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label name;

    @FXML
    private Label amount;

    @FXML
    private Label itemLocation;

    @FXML
    private Label condition;

    IReservable thisItem;

    public InventoryListItemController(IReservable item) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Inventory/InventoryListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.name.setText(item.getName());
        this.amount.setText("" + item.getAmount());
        this.itemLocation.setText("" + item.getLocation().getName());
        this.condition.setText("" + item.getCondition());

        this.thisItem = item;
    }


    /**
     * listener method
     */
    @FXML
    void listItemPressed() {

        for (InventoryListItemListener l : listeners) {
            l.inventoryListItemClicked(thisItem);
        }

    }

    /**
     * below contains listener configs
     */
    List<InventoryListItemListener> listeners = new ArrayList<>();

    public void addListener(InventoryListItemListener listener) {
        listeners.add(listener);
    }

    interface InventoryListItemListener {

        void inventoryListItemClicked(IReservable item);
    }


}
