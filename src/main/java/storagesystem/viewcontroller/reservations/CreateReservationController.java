package storagesystem.viewcontroller.reservations;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import storagesystem.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for creation of a Reservation
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
    TextArea terms;


    @FXML
    ChoiceBox teamChoicebox;

    @FXML
    private Spinner startYearSpinner;
    @FXML
    private Spinner startMonthSpinner;
    @FXML
    private Spinner startDaySpinner;
    @FXML
    private Spinner startHourSpinner;
    @FXML
    private Spinner startMinuteSpinner;
    @FXML
    private Spinner endYearSpinner;
    @FXML
    private Spinner endMonthSpinner;
    @FXML
    private Spinner endDaySpinner;
    @FXML
    private Spinner endHourSpinner;
    @FXML
    private Spinner endMinuteSpinner;

    @FXML
    private Button confirmButton;
    @FXML
    private Button cancelButton;

    private IReservable item;


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
        //TODO Set terms
        ownerField.setText("Temp owner");

        //Populate teamChoiceBox
        List<String> teamNames = new ArrayList<>();
        for(Team t : StoreIT.getCurrentOrganisation().getUsersTeams(StoreIT.getCurrentUser())){
            teamNames.add(t.getName());
        }
        teamChoicebox.setItems(FXCollections.observableArrayList(teamNames));
        DateTime now = new DateTime();
        SpinnerValueFactory<Integer> yearFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2019, now.getYear()+5, now.getYear(), 1);
        SpinnerValueFactory<Integer> monthFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, now.getMonthOfYear(), 1);
        SpinnerValueFactory<Integer> dayFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, getMonthDays(now.getMonthOfYear()), now.getDayOfMonth(), 1);
        SpinnerValueFactory<Integer> hourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 24, now.getHourOfDay(), 1);
        SpinnerValueFactory<Integer> minuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 60, 0, 15);

        startYearSpinner.setValueFactory(yearFactory);
        endYearSpinner.setValueFactory(yearFactory);
        startMonthSpinner.setValueFactory(monthFactory);
        endMonthSpinner.setValueFactory(monthFactory);
        startDaySpinner.setValueFactory(dayFactory);
        endDaySpinner.setValueFactory(dayFactory);
        startHourSpinner.setValueFactory(hourFactory);
        endHourSpinner.setValueFactory(hourFactory);
        startMinuteSpinner.setValueFactory(minuteFactory);
        endMinuteSpinner.setValueFactory(minuteFactory);

        this.item = item;

    }



    @FXML
    private void confirm(){
        ReservationHandler resHandler = StoreIT.getCurrentOrganisation().getReservationHandler();
        resHandler.createReservation(StoreIT.getCurrentUser(), getInterval(), item);
        close();
    }

    private Interval getInterval() {
        int startYear = (int) startYearSpinner.getValue();
        int startMonth = (int) startMonthSpinner.getValue();
        int startDay = (int) startDaySpinner.getValue();
        int startHour = (int) startHourSpinner.getValue();
        int startMinute = (int) startMinuteSpinner.getValue();
        int endYear = (int) endYearSpinner.getValue();
        int endMonth = (int) endMonthSpinner.getValue();
        int endDay = (int) endDaySpinner.getValue();
        int endHour = (int) endHourSpinner.getValue();
        int endMinute = (int) endMinuteSpinner.getValue();
        DateTime start = new DateTime(startYear, startMonth, startDay, startHour, startMinute);
        DateTime end = new DateTime(endYear, endMonth, endDay, endHour, endMinute);

        return new Interval(start, end);
    }

    @FXML
    private void cancel(){
        close();
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

    private int getMonthDays(int nr) {
        switch (nr) {
            case 1:
                return 31;
            case 2:
                return 28;
            case 3:
                return 31;
            case 4:
                return 30;
            case 5:
                return 31;
            case 6:
                return 30;
            case 7:
                return 31;
            case 8:
                return 31;
            case 9:
                return 30;
            case 10:
                return 31;
            case 11:
                return 30;
            case 12:
                return 31;}
            return 0;
}
}
