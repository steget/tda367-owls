package storeit.viewcontroller.yourReservations;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import storeit.model.*;

import java.io.IOException;

/**
 * Shows detail about a reservation including borrowed item, time and date, status etc. Intended to be used as a lightbox.
 * Gives the ability to accept or decline pending reservations as long as the current team should be able to.
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

        itemField.setText(StoreIT.getCurrentOrganisation().getItem(res.getReservedObjectID()).getName());
        borrowerField.setText(StoreIT.getCurrentOrganisation().getTeamFromID(res.getBorrowerID()).getName());
        updateAllViews();

    }

    private void updateButtons() {
        declineButton.setVisible(false);
        approveButton.setVisible(false);
        if(teamShouldEdit()) {
            declineButton.setVisible(true);
            approveButton.setVisible(true);
        }
    }

    private boolean teamShouldEdit() {
        if(reservation.getStatus() == ReservationStatus.PENDING && teamRecievesReservation())
            return true;
        return false;
    }

    private boolean teamRecievesReservation() {
        int itemID = reservation.getReservedObjectID();
        IBorrower borrower = StoreIT.getCurrentOrganisation().getItemOwner(itemID);
        if(StoreIT.getCurrentTeam().equals(borrower))
            return true;
        return false;
    }


    void updateAllViews(){
        IReservable item = StoreIT.getCurrentOrganisation().getItem(reservation.getReservedObjectID());
        itemField.setText(StoreIT.getCurrentOrganisation().getItem(reservation.getReservedObjectID()).getName());
        borrowerField.setText(StoreIT.getCurrentOrganisation().getItem(reservation.getBorrowerID()).getName());
        ownerField.setText(StoreIT.getCurrentOrganisation().getItemOwner(item).getName());
        timeAndDateField.setText(reservation.getReadableInterval());
        statusField.setText(reservation.getStatus().toString());
        IDField.setText(Integer.toString(reservation.getID()));
        updateButtons();
    }
}
