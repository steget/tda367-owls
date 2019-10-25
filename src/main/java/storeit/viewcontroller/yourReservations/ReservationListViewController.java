package storeit.viewcontroller.yourReservations;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import storeit.model.IReservation;
import storeit.model.ReservationStatus;
import storeit.model.StoreIT;

import java.io.IOException;

/**
 * View meant to be used in ReservationsController. Represents a reservation in a small item meant to be used in a list.
 *
 * @author William Albertsson
 */

public class ReservationListViewController extends AnchorPane {

    private IReservation reservation;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label itemLabel;
    @FXML
    private Label borrowerLabel;
    @FXML
    private Label intervalLabel;
    @FXML
    private Label statusLabel;

    ReservationListViewController(IReservation res, boolean isOutgoing) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/yourReservations/reservationListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.reservation = res;

        itemLabel.setText(StoreIT.getCurrentOrganisation().getItem(reservation.getReservedObjectID()).getName());
        if (isOutgoing)
            borrowerLabel.setText(StoreIT.getCurrentOrganisation().getItemOwner(reservation.getReservedObjectID()).getName());
        else {
            borrowerLabel.setText(StoreIT.getCurrentOrganisation().getTeamFromID(reservation.getBorrowerID()).getName());
        }
        intervalLabel.setText(reservation.getReadableInterval());
        statusLabel.setText(reservation.getStatus().toString());
        if (reservation.getStatus() == ReservationStatus.PENDING) {
            statusLabel.setStyle("-fx-text-fill: secondaryButtonColor");
        }
    }

    IReservation getReservation() {
        return reservation;
    }
}
