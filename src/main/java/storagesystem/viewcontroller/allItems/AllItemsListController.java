package storagesystem.viewcontroller.allItems;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import storagesystem.model.IReservable;
import storagesystem.model.StoreIT;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controls a view showing all reservable items
 *
 * @author Hugo Stegrell
 */
public class AllItemsListController implements Initializable {
    @FXML
    private AnchorPane rootPane;

    @FXML
    private FlowPane itemListFlowPane;

    @FXML
    private ScrollPane itemListScrollPane;

    private ReservableItemDetailController reservableItemDetailView;

    private List<SmallItemPanel> allSmallItemPanels = new ArrayList<>();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createAllSmallItemPanels();
        itemListFlowPane.getChildren().addAll(allSmallItemPanels);
    }

    private void createAllSmallItemPanels() {
        allSmallItemPanels.clear();
        for (IReservable reservableItem :
                StoreIT.getCurrentOrganisation().getAllItems()) {
            SmallItemPanel newSmallItemPanel = new SmallItemPanel(reservableItem);
            newSmallItemPanel.addListener(this::smallItemPanelClicked);
            allSmallItemPanels.add(newSmallItemPanel);
        }
    }

    private void smallItemPanelClicked(IReservable item) {
        reservableItemDetailView = new ReservableItemDetailController(item);
        rootPane.getChildren().add(reservableItemDetailView);
    }

    private void updateItemList(List<IReservable> items) {
        itemListFlowPane.getChildren().clear();

        for (IReservable reservableItem : items) {
            SmallItemPanel temp = findProductPanel(reservableItem);
            itemListFlowPane.getChildren().add(temp);
        }
    }

    private SmallItemPanel findProductPanel(IReservable item) {
        for (SmallItemPanel panel : allSmallItemPanels) {
            if (panel.getReservableItem() == item) {
                return panel;
            }
        }

        //if no panel for the item could be found a new one is created
        SmallItemPanel newPanel = new SmallItemPanel(item);
        newPanel.addListener(this::smallItemPanelClicked);
        allSmallItemPanels.add(newPanel);
        return newPanel;
    }
}
