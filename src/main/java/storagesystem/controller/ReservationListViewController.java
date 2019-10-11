package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import storagesystem.model.IReservation;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReservationListViewController {


    IReservation reservation;

    @FXML
    Label itemLabel;

    @FXML
    Label borrowerLabel;

    @FXML
    Label intervalLabel;




    public ReservationListViewController(IReservation res){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reservationsListView.fxml"));
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
        intervalLabel.setText(reservation.getInterval().toString());
    }


}
