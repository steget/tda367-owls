package storagesystem.viewcontroller.inventory.reservations;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import storagesystem.model.IReservable;
import storagesystem.model.IReservation;
import storagesystem.model.StoreIT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemReservationsController extends AnchorPane {

    @FXML
    private FlowPane listFlowPane;
    @FXML
    private AnchorPane contentPane;

    private IReservable item;
    private List<ItemReservationListItemViewController> itemReservationListItemViews = new ArrayList();

    public ItemReservationsController(IReservable item){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/inventory/reservations/ItemReservations.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        this.item = item;
        initialize();
    }

    private void initialize() {
        updateItemReservations();

        listFlowPane.getChildren().addAll(itemReservationListItemViews);
        contentPane.setOnMouseClicked(Event::consume);
    }

    private void updateItemReservations() {
        boolean alternating = false;
        for (IReservation res : StoreIT.getCurrentOrganisation().getReservationHandler().getObjectsReservations(item.getID())) {
            ItemReservationListItemViewController listView = new ItemReservationListItemViewController(res);
            itemReservationListItemViews.add(listView);
            if (alternating) {
                listView.setStyle("-fx-background-color: secondaryColor");
                alternating = !alternating;
            } else {
                listView.setStyle("-fx-background-color: primaryColor");
                alternating = !alternating;
            }
        }
    }

}
