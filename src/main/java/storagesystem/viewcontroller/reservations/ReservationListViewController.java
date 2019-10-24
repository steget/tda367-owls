package storagesystem.viewcontroller.reservations;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import storagesystem.model.IReservation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

    ReservationListViewController(IReservation res) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reservations/reservationListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.reservation = res;

        itemLabel.setText(reservation.getReservedObject().getName());
        borrowerLabel.setText(reservation.getBorrower().getName());
        intervalLabel.setText(reservation.getReadableInterval());
    }

    IReservation getReservation(){
        return reservation;
    }
}
