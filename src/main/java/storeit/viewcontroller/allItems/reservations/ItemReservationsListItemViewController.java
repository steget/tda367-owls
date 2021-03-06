package storeit.viewcontroller.allItems.reservations;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import storeit.model.IReservation;
import storeit.model.StoreIT;

import java.io.IOException;

public class ItemReservationsListItemViewController extends AnchorPane {

    @FXML
    private Label borrowerLabel;
    @FXML
    private Label intervalLabel;
    @FXML
    private Label statusLabel;


    public ItemReservationsListItemViewController(IReservation reservation){
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
