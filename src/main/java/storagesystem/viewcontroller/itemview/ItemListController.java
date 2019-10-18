package storagesystem.viewcontroller.itemview;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import storagesystem.StoreIT;
import storagesystem.model.IReservable;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

/**
 * Controls a view showing all reservable items
 *
 * @author Hugo Stegrell
 */
public class ItemListController implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private FlowPane itemListFlowPane;

    @FXML
    private ScrollPane itemListScrollPane;

    private List<SmallItemPanel> allSmallItemPanels = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createAllSmallItemPanels();
        itemListFlowPane.getChildren().addAll(allSmallItemPanels);

    }

    private void createAllSmallItemPanels() {
        for (IReservable reservableItem :
                StoreIT.getCurrentOrganisation().getAllItems()) {
            allSmallItemPanels.add(new SmallItemPanel(reservableItem));
        }
    }

    private void updateItemList(List<IReservable> items) {
        itemListFlowPane.getChildren().clear();

        for (IReservable reservableItem : items) {
            SmallItemPanel temp = findProductPanel(reservableItem);
            itemListFlowPane.getChildren().add(temp);
        }
    }

    private SmallItemPanel findProductPanel(IReservable product) {
        for (SmallItemPanel panel : allSmallItemPanels) {
            if (panel.getReservableItem() == product) {
                return panel;
            }
        }

        throw new NoSuchElementException("No panel for the product could be found");
        //perhaps create a new panel instead?
    }
}
