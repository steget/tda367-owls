package storagesystem.viewcontroller.reservations;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
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

    private IReservable item;
    private List<ItemReservationListItemViewController> itemReservationListItemViews = new ArrayList();

    public ItemReservationsController(IReservable item){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reservations/ItemReservations.fxml"));
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
    }

    private void updateItemReservations(){
        boolean alternating = false;
        for(IReservation res : StoreIT.getCurrentOrganisation().getReservationHandler().getReservations(item)){
            ItemReservationListItemViewController listView = new ItemReservationListItemViewController(res);
            itemReservationListItemViews.add(listView);
            if (alternating) {
                listView.setStyle("-fx-background-color: secondaryColor");
                alternating = !alternating;
            } else {
                listView.setStyle("-fx-background-color: primaryColoR");
                alternating = !alternating;
            };

        }
    }

}
