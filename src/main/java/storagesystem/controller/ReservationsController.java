package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import storagesystem.StoreIT;
import storagesystem.model.IReservation;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Shows a list of reservations using ReservationListViewController. A lightbox with details about reservations is shown when listitem is clicked.
 * @author William Albertsson
 */

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
        for (IReservation res : StoreIT.getCurrentOrganisation().getReservationHandler().getReservations()) {
            ReservationListViewController listView = new ReservationListViewController(res);
            reservationViews.add(listView);
            listView.addReservationClickedListener(this::listViewClicked);
        }
    }

    private void listViewClicked(IReservation res) {

        detailView = new ReservationDetailViewController(res);
        reservationsRootPane.getChildren().add(detailView);

        detailView.addReservationDetailViewClosedListener(this::reservationDetailViewClosed);

    }

    private void reservationDetailViewClosed() {
        reservationsRootPane.getChildren().remove(detailView);
    }


}
