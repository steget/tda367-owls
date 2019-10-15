package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import storagesystem.model.IReservation;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReservationListViewController extends AnchorPane {


    IReservation reservation;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label itemLabel;

    @FXML
    private Label borrowerLabel;

    @FXML
    private Label intervalLabel;

    private List<ReservationClickedListener> reservationClickedListenersList = new ArrayList<>();




    public ReservationListViewController(IReservation res){

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reservationListView.fxml"));
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

    @FXML
    private void clicked(){
        this.itemLabel.setText("Clicked");

        for(ReservationClickedListener listener : reservationClickedListenersList){
            listener.reservationClicked(reservation);
        }
    }

    interface ReservationClickedListener {
        void reservationClicked(IReservation res);
    }

    public void addReservationClickedListener(ReservationClickedListener listener){
        reservationClickedListenersList.add(listener);
    }

}
