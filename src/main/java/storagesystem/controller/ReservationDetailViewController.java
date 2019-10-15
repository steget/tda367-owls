package storagesystem.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import storagesystem.model.IReservation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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



    public ReservationDetailViewController(IReservation res){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reservationDetailView.fxml"));
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

    @FXML
    private void closeReservationDetailView(){
        for(ReservationDetailViewClosedListener listener : listeners){
            listener.reservationdDetailViewClosed();
        }
    }

    private List<ReservationDetailViewClosedListener> listeners = new ArrayList<>();

    public void addReservationDetailViewClosedListener(ReservationDetailViewClosedListener listener){
        listeners.add(listener);
    }

    interface ReservationDetailViewClosedListener{
        void reservationdDetailViewClosed();
    }
}
