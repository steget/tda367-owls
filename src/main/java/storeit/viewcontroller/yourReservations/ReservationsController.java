package storeit.viewcontroller.yourReservations;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import storeit.model.IReservation;
import storeit.model.ReservationStatus;
import storeit.model.StoreIT;
import storeit.model.Team;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Shows a list of reservations using ReservationListViewController. A lightbox with details about reservations is shown
 * when listitem is clicked. Can show ingoing reservations(other teams who reserved your items) and outgoing reservatins
 * (your reservations on other teams items.)
 *
 * @author William Albertsson, Hugo Stegrell
 */

public class ReservationsController implements Initializable {


    List<ReservationListViewController> reservationViews = new ArrayList<>();
    @FXML
    ChoiceBox<String> teamChooser;
    @FXML
    private AnchorPane reservationsRootPane;
    @FXML
    private Label reservationsLabel;
    @FXML
    private Label teamLabel;
    @FXML
    private FlowPane reservationListFlowPane;
    @FXML
    private Toggle ingoingToggle;
    @FXML
    private Toggle outgoingToggle;

    private ReservationDetailViewController detailView;
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
    private EventHandler<MouseEvent> reservationListViewClickedHandler = e -> {
        ReservationListViewController panel = (ReservationListViewController) e.getSource();
        reservationListViewClicked(panel.getReservation());
        e.consume();
    };
    private EventHandler<MouseEvent> detailViewClickedHandler = e -> {
        detailViewClicked();
        e.consume();
    };

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeToggleButtons();
        updateReservations();
        fillTeamAttributes();


        teamChooser.valueProperty().addListener((obs, oldValue, newValue) -> {
            String teamName = teamChooser.getValue();
            Team t = StoreIT.getCurrentOrganisation().getTeamFromName(teamName);
            StoreIT.setCurrentTeam(t);
            updateReservations();
        });

    }

    private void initializeToggleButtons() {
        ToggleGroup toggleGroup = new ToggleGroup();

        ingoingToggle.setToggleGroup(toggleGroup);
        outgoingToggle.setToggleGroup(toggleGroup);

        toggleGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle toggle, Toggle new_toggle) {
                if (new_toggle == ingoingToggle) {
                    setIngoing();
                } else if (new_toggle == outgoingToggle) {
                    setOutgoing();
                } else
                    reservationsLabel.setText("Choose in- our outgoing");
                updateReservations();
            }
        });
        ingoingToggle.setSelected(true);
    }

    private void setOutgoing() {
        reservationsLabel.setText("Outgoing requests");
        teamLabel.setText("Item owner");
    }

    private void setIngoing() {
        reservationsLabel.setText("Ingoing requests");
        teamLabel.setText("Borrower");
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
        List<IReservation> resToCreate = new ArrayList<>();
        if (ingoingToggle.isSelected()) {
            resToCreate = StoreIT.getCurrentTeamIngoingReservations();
        } else if (outgoingToggle.isSelected()) {
            resToCreate = StoreIT.getCurrentTeamOutgoingReservations();
        }

        for (IReservation res : resToCreate) {
            ReservationListViewController listView = new ReservationListViewController(res, isOutgoing());
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

    private boolean isOutgoing() {
        return outgoingToggle.isSelected();
    }

    private void updateReservations() {
        createListViews();
        reservationListFlowPane.getChildren().clear();
        reservationListFlowPane.getChildren().addAll(reservationViews);

    }

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
