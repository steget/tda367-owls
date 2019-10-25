package storagesystem.viewcontroller.yourReservations;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import storagesystem.model.IReservation;
import storagesystem.model.ReservationStatus;

import java.io.IOException;

/**
 * Shows detail about a reservation including borrowed item, time and date, status etc. Intended to be used as a lightbox.
 *
 * @author William Albertsson
 */

public class ReservationDetailViewController extends AnchorPane {

    IReservation reservation;

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
    Button approveButton;
    @FXML
    Button declineButton;

    @FXML
    private AnchorPane lightboxContentPane;

    ReservationDetailViewController(IReservation res) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/yourReservations/reservationDetailView.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        reservation = res;
        updateButtons();

        lightboxContentPane.setOnMouseClicked(Event::consume);

        update();

    }

    private void updateButtons() {
        declineButton.setVisible(false);
        approveButton.setVisible(false);
        if(reservation.getStatus() == ReservationStatus.PENDING) {
            declineButton.setVisible(true);
            approveButton.setVisible(true);
        }
    }

    void update(){
        itemField.setText(reservation.getReservedObject().getName());
        borrowerField.setText(reservation.getBorrower().getName());
        //TODO Set owner field. Method is missing in organisation at time of writing
        timeAndDateField.setText(reservation.getReadableInterval());
        statusField.setText(reservation.getStatus().toString());
        IDField.setText(Integer.toString(reservation.getID()));
        updateButtons();
    }
}
