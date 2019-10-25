package storagesystem.viewcontroller.reservations;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
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
 * @author William Albertsson, Hugo Stegrell
 */

public class ReservationsController implements Initializable {


    List<ReservationListViewController> reservationViews = new ArrayList<>();

    @FXML
    private AnchorPane reservationsRootPane;

    @FXML
    private FlowPane reservationListFlowPane;


    private ReservationDetailViewController detailView;
    private EventHandler<MouseEvent> detailViewClickedHandler = e -> {
        detailViewClicked();
        e.consume();
    };
    private EventHandler<MouseEvent> reservationListViewClickedHandler = e -> {
        ReservationListViewController panel = (ReservationListViewController) e.getSource();
        reservationListViewClicked(panel.getReservation());
        e.consume();
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createListViews();
        reservationListFlowPane.getChildren().addAll(reservationViews);

    }

    private void createListViews() {
        boolean alternating = false;
        for (IReservation res : StoreIT.getCurrentOrganisation().getReservationHandler().getAllReservations()) {
            ReservationListViewController listView = new ReservationListViewController(res);
            reservationViews.add(listView);
            listView.addEventHandler(MouseEvent.MOUSE_CLICKED, reservationListViewClickedHandler);
            if (alternating) {
                listView.setStyle("-fx-background-color: secondaryColor");
                alternating = !alternating;
            } else {
                listView.setStyle("-fx-background-color: primaryColoR");
                alternating = !alternating;
            }
        }

    }

    private void listViewClicked(IReservation res) {
        detailView = new ReservationDetailViewController(res);
        detailView.addEventHandler(MouseEvent.MOUSE_CLICKED, detailViewClickedHandler);
        reservationsRootPane.getChildren().add(detailView);
    }

    private void detailViewClicked() {
        reservationsRootPane.getChildren().remove(detailView);
    }

    private void reservationListViewClicked(IReservation reservation) {
        detailView = new ReservationDetailViewController(reservation);
        detailView.addEventHandler(MouseEvent.MOUSE_CLICKED, detailViewClickedHandler);
        reservationsRootPane.getChildren().add(detailView);
    }
}
