package storagesystem.viewcontroller.yourReservations;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import storagesystem.model.IReservation;
import storagesystem.model.ReservationStatus;
import storagesystem.model.StoreIT;
import storagesystem.model.Team;

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
    @FXML
    ChoiceBox<String> teamChooser;

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
        updateReservations();
        fillTeamAttributes();

        teamChooser.valueProperty().addListener((obs, oldValue, newValue) -> {
            String teamName = teamChooser.getValue();
            Team t = StoreIT.getCurrentOrganisation().getTeamFromName(teamName);
            StoreIT.setCurrentTeam(t);
            updateReservations();
        });

    }

    private void fillTeamAttributes() {
        List<Team> currentUsersTeams = StoreIT.getCurrentOrganisation().getUsersTeams(StoreIT.getCurrentUser());
        ObservableList<String> teamNames = FXCollections.observableArrayList();

        for (Team t : currentUsersTeams) { //adds team names into an observable list.
            teamNames.add(t.getName());
        }
        teamChooser.setItems(teamNames);
        teamChooser.setValue(StoreIT.getCurrentTeam().getName()); //show first value in box
    }


    private void createListViews() {
        reservationViews = new ArrayList<>();
        boolean alternating = false;
        for (IReservation res : StoreIT.getCurrentTeamsIncomingReservations()) {
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

    private void updateReservations() {
        createListViews();
        reservationListFlowPane.getChildren().clear();
        reservationListFlowPane.getChildren().addAll(reservationViews);

    }


    private void listViewClicked(IReservation res) {
        detailView = new ReservationDetailViewController(res);
        detailView.addEventHandler(MouseEvent.MOUSE_CLICKED, detailViewClickedHandler);
        reservationsRootPane.getChildren().add(detailView);


    }

    private EventHandler<MouseEvent> approveButtonClicked = e -> {
        detailView.reservation.setStatus(ReservationStatus.APPROVED);
        detailView.updateAllViews();
        e.consume();
    };
    private EventHandler<MouseEvent> declineButtonClicked = e -> {
        detailView.reservation.setStatus(ReservationStatus.DECLINED);
        detailView.updateAllViews();
        e.consume();
    };


    private void detailViewClicked() {
        reservationsRootPane.getChildren().remove(detailView);
        updateReservations();
    }

    private void reservationListViewClicked(IReservation reservation) {
        detailView = new ReservationDetailViewController(reservation);
        detailView.addEventHandler(MouseEvent.MOUSE_CLICKED, detailViewClickedHandler);
        detailView.approveButton.addEventHandler(MouseEvent.MOUSE_CLICKED, approveButtonClicked);
        detailView.declineButton.addEventHandler(MouseEvent.MOUSE_CLICKED, declineButtonClicked);
        reservationsRootPane.getChildren().add(detailView);
    }
}
