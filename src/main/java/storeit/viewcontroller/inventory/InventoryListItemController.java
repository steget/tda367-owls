package storeit.viewcontroller.inventory;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import storeit.model.IReservable;
import storeit.model.StoreIT;

import java.io.IOException;

/**
 * @author Pär Aronsson
 */

public class InventoryListItemController extends AnchorPane {

    private IReservable reservableItem;

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
    @FXML
    private Label bookable;

    public InventoryListItemController(IReservable item) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/inventory/InventoryListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.name.setText(item.getName());
        this.amount.setText("" + item.getAmount());
        this.itemLocation.setText("" + StoreIT.getCurrentOrganisation().getLocation(item.getLocationID()).getName());
        this.condition.setText("" + item.getCondition());
        this.bookable.setText("" + item.isReservable());
        this.reservableItem = item;
    }

    IReservable getReservableItem() {
        return reservableItem;
    }

}
