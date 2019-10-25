package storeit.viewcontroller.allItems.reservations;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import storeit.model.IReservable;
import storeit.model.IReservation;
import storeit.model.StoreIT;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemReservationsController extends AnchorPane {

    @FXML
    private FlowPane listFlowPane;
    @FXML
    private AnchorPane contentPane;

    private IReservable item;
    private List<ItemReservationsListItemViewController> itemReservationListItemViews = new ArrayList();

    public ItemReservationsController(IReservable item){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/allItems/reservations/ItemReservations.fxml"));
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
            ItemReservationsListItemViewController listView = new ItemReservationsListItemViewController(res);
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
