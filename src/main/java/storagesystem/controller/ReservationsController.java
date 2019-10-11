package storagesystem.controller;

import javafx.fxml.Initializable;
import storagesystem.StorageSystem;
import storagesystem.model.IReservation;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationsController implements Initializable {

    List<ReservationListViewController> reservationViews = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createListViews();
    }




    private void createListViews() {
        System.out.println(StorageSystem.getCurrentOrganisation().getReservationHandler().getReservations().toString());

        for(IReservation res : StorageSystem.getCurrentOrganisation().getReservationHandler().getReservations()){
            reservationViews.add(new ReservationListViewController(res));
            System.out.println(res.toString());
        }

    }
}
