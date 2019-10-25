package storagesystem.viewcontroller.allItems.reservations;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import storagesystem.model.IReservation;
import storagesystem.model.StoreIT;

import java.io.IOException;

public class ItemReservationListItemViewController extends AnchorPane {

    @FXML
    private Label borrowerLabel;
    @FXML
    private Label intervalLabel;
    @FXML
    private Label statusLabel;


    public ItemReservationListItemViewController(IReservation reservation){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/allItems/reservations/ItemReservationsListItemView.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        borrowerLabel.setText(StoreIT.getCurrentOrganisation().getTeamFromID(reservation.getBorrowerID()).getName());
        intervalLabel.setText(reservation.getReadableInterval());
        statusLabel.setText(reservation.getStatus().toString());
    }
}
