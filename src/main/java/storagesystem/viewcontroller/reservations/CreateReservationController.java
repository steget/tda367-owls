package storagesystem.viewcontroller.reservations;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import storagesystem.model.IReservable;
import storagesystem.model.StoreIT;
import storagesystem.model.Team;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author William Albertsson
 */

public class CreateReservationController {

    @FXML
    AnchorPane rootPane;
    @FXML
    AnchorPane lightboxContentPane;

    @FXML
    TextField itemField;
    @FXML
    TextField ownerField;

    @FXML
    CheckBox manyDaysCheckbox;
    @FXML
    ChoiceBox teamChoicebox;
    @FXML
    DatePicker startDatePicker;
    @FXML
    DatePicker endDatePicker;


    public CreateReservationController(IReservable item){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/reservations/createReservation.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        //Set text
        itemField.setText(item.getName());
        //TODO Set owner
        ownerField.setText("Temp owner");

        //Populate teamChoiceBox
        List<String> teamNames = new ArrayList<>();
        for(Team t : StoreIT.getCurrentOrganisation().getUsersTeams(StoreIT.getCurrentUser())){
            teamNames.add(t.getName());
        }
        teamChoicebox.setItems(FXCollections.observableArrayList(teamNames));


        endDatePicker.disableProperty().bind(manyDaysCheckbox.selectedProperty());

    }

    @FXML
    private void close(){
        for(CreateReservationViewClosedListener listener : listeners){
            listener.reservationDetailViewClosed();
        }
    }


    private List<CreateReservationViewClosedListener> listeners = new ArrayList<>();

    /**
     * Used together with "listeners" list as an observer pattern.
     */
    interface CreateReservationViewClosedListener {
        void reservationDetailViewClosed();
    }

    public void addReservationDetailViewClosedListener(CreateReservationViewClosedListener listener) {
        listeners.add(listener);
    }

}
