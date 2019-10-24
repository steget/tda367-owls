package storagesystem.viewcontroller.reservations;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import storagesystem.model.IReservable;
import storagesystem.model.IReservation;
import storagesystem.model.StoreIT;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Shows a list of reservations using ReservationListViewController. A lightbox with details about reservations is shown when listitem is clicked.
 *
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


    private void updateReservations(List<IReservation> reservations){

        boolean alternating = false;
        for (IReservation res : reservations) {
            ReservationListViewController listView = new ReservationListViewController(res);
            reservationViews.add(listView);
            listView.addReservationClickedListener(this::listViewClicked);
            if (alternating) {
                listView.setStyle("-fx-background-color: secondaryColor");
                alternating = !alternating;
            } else {
                listView.setStyle("-fx-background-color: primaryColoR");
                alternating = !alternating;
            }
        }

    }

    private void createListViews() {
        List<IReservation> reservations = StoreIT.getCurrentOrganisation().getReservationHandler().getAllReservations();
        updateReservations(reservations);
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
