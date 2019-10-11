package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import storagesystem.StorageSystem;
import storagesystem.model.IReservation;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationsController implements Initializable {



    List<ReservationListViewController> reservationViews = new ArrayList<>();

    @FXML
    private FlowPane reservationListFlowPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createListViews();
        reservationListFlowPane.getChildren().addAll(reservationViews);

    }




    private void createListViews() {
        for(IReservation res : StorageSystem.getCurrentOrganisation().getReservationHandler().getReservations()){
            reservationViews.add(new ReservationListViewController(res));
            System.out.println(res.toString());
        }
    }
}
