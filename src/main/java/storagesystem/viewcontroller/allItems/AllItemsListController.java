package storagesystem.viewcontroller.allItems;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import storagesystem.model.IReservable;
import storagesystem.model.StoreIT;
import storagesystem.viewcontroller.allItems.reservations.CreateReservationController;
import storagesystem.viewcontroller.allItems.reservations.ItemReservationsController;

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
    private CreateReservationController createReservationView;
    private ItemReservationsController allReservationsForOneItemView;

    private List<SmallItemPanel> allSmallItemPanels = new ArrayList<>();
    private EventHandler<MouseEvent> detailViewClickedHandler = e -> {
        detailViewClicked();
        e.consume();
    };
    private EventHandler<MouseEvent> smallPanelClickedHandler = e -> {
        SmallItemPanel panel = (SmallItemPanel) e.getSource();
        smallItemPanelClicked(panel.getReservableItem());
        e.consume();
    };
    private EventHandler<MouseEvent> reserveItemCloseHandler = e -> {
        rootPane.getChildren().remove(createReservationView);
        e.consume();
    };
    private EventHandler<MouseEvent> createReservationButtonClickedHandler = e -> {
        if (createReservationView.isReservationLegal()) {
            createReservationView.confirmReservation();
            rootPane.getChildren().remove(createReservationView);
        }
        e.consume();
    };
    private EventHandler<MouseEvent> reserveButtonClickedHandler = e -> {
        createReservationView = new CreateReservationController(reservableItemDetailView.getItem());
        createReservationView.addEventHandler(MouseEvent.MOUSE_CLICKED, reserveItemCloseHandler);
        createReservationView.confirmButton.addEventHandler(MouseEvent.MOUSE_CLICKED, createReservationButtonClickedHandler);
        rootPane.getChildren().add(createReservationView);
        e.consume();
    };
    private EventHandler<MouseEvent> showItemsReservationButtonClickedHandler = e -> {
        allReservationsForOneItemView = new ItemReservationsController(reservableItemDetailView.getItem());
        allReservationsForOneItemView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                rootPane.getChildren().remove(allReservationsForOneItemView);
                event.consume();
            }
        });
        rootPane.getChildren().add(allReservationsForOneItemView);
        e.consume();
    };


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
            newSmallItemPanel.addEventHandler(MouseEvent.MOUSE_CLICKED, smallPanelClickedHandler);
            allSmallItemPanels.add(newSmallItemPanel);
        }
    }

    private void smallItemPanelClicked(IReservable item) {
        reservableItemDetailView = new ReservableItemDetailController(item);
        reservableItemDetailView.addEventHandler(MouseEvent.MOUSE_CLICKED, detailViewClickedHandler);
        reservableItemDetailView.reservationsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, showItemsReservationButtonClickedHandler);
        reservableItemDetailView.detailViewReserveBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, reserveButtonClickedHandler);
        rootPane.getChildren().add(reservableItemDetailView);
    }

    private void detailViewClicked() {
        rootPane.getChildren().remove(reservableItemDetailView);
    }

}
