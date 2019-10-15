package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import storagesystem.StorageSystem;
import storagesystem.model.IReservation;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ReservationsController implements Initializable {



    List<ReservationListViewController> reservationViews = new ArrayList<>();

    @FXML
    private AnchorPane reservationsRootPane;

    @FXML
    private FlowPane reservationListFlowPane;

    private ReservationDetailViewController detailView;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createListViews();
        reservationListFlowPane.getChildren().addAll(reservationViews);

    }




    private void createListViews() {
        for(IReservation res : StorageSystem.getCurrentOrganisation().getReservationHandler().getReservations()){
            ReservationListViewController listView = new ReservationListViewController(res);
            reservationViews.add(listView);
            listView.addReservationClickedListener(this::listViewClicked);
        }
    }

    private void listViewClicked(IReservation res){

        detailView = new ReservationDetailViewController(res);

        reservationsRootPane.getChildren().add(detailView);
        detailView.addReservationDetailViewClosedListener(this::reservationDetailViewClosed);
        detailView.setVisible(true);

    }

    private void reservationDetailViewClosed(){
        detailView.setVisible(false);
    }


}
