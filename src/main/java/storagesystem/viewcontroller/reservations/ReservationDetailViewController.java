package storagesystem.viewcontroller.reservations;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import storagesystem.model.IReservation;

import java.io.IOException;

/**
 * Shows detail about a reservation including borrowed item, time and date, status etc. Intended to be used as a lightbox.
 *
 * @author William Albertsson
 */

public class ReservationDetailViewController extends AnchorPane {

    private IReservation reservation;

    @FXML
    private TextField itemField;

    @FXML
    private TextField borrowerField;

    @FXML
    private TextField ownerField;

    @FXML
    private TextField timeAndDateField;

    @FXML
    private TextField statusField;

    @FXML
    private TextField IDField;

    @FXML
    private AnchorPane lightboxContentPane;

    ReservationDetailViewController(IReservation res) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reservations/reservationDetailView.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        reservation = res;

        lightboxContentPane.setOnMouseClicked(Event::consume);

        itemField.setText(res.getReservedObject().getName());
        borrowerField.setText(res.getBorrower().getName());
        //TODO Set owner field. Method is missing in organisation at time of writing
        timeAndDateField.setText(res.getReadableInterval());
        statusField.setText(res.getStatus().toString());
        IDField.setText(Integer.toString(res.getID()));

    }
}
