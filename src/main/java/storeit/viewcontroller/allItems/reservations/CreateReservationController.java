package storeit.viewcontroller.allItems.reservations;

import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import storeit.model.*;
import storeit.viewcontroller.AbstractFader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for creation of a Reservation
 *
 * @author William Albertsson, Hugo Stegrell
 */

public class CreateReservationController extends AnchorPane {

    @FXML
    AnchorPane rootPane;
    @FXML
    AnchorPane lightboxContentPane;
    @FXML
    AnchorPane intervalError;
    @FXML
    AnchorPane alreadyReservedError;

    @FXML
    TextField itemField;
    @FXML
    TextField ownerField;
    @FXML
    Label terms;
    @FXML
    ChoiceBox<String> teamChoicebox;

    @FXML
    private Spinner<Integer> startYearSpinner;
    @FXML
    private Spinner<Integer> startMonthSpinner;
    @FXML
    private Spinner<Integer> startDaySpinner;
    @FXML
    private Spinner<Integer> startHourSpinner;
    @FXML
    private Spinner<Integer> startMinuteSpinner;
    @FXML
    private Spinner<Integer> endYearSpinner;
    @FXML
    private Spinner<Integer> endMonthSpinner;
    @FXML
    private Spinner<Integer> endDaySpinner;
    @FXML
    private Spinner<Integer> endHourSpinner;
    @FXML
    private Spinner<Integer> endMinuteSpinner;

    @FXML
    private CheckBox termsCheckbox;

    @FXML
    public
    Button confirmButton;
    @FXML
    private Button cancelButton;

    private IReservable item;


    public CreateReservationController(IReservable item) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/allItems/reservations/createReservation.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.setRoot(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        intervalError.setOpacity(0);
        alreadyReservedError.setOpacity(0);

        lightboxContentPane.setOnMouseClicked(Event::consume);

        //Set text
        itemField.setText(item.getName());
        ownerField.setText(StoreIT.getCurrentOrganisation().getItemOwner(item).getName());
        terms.setText(StoreIT.getCurrentOrganisation().getItemOwner(item).getTermsAndConditions());

        //Populate teamChoiceBox
        List<String> teamNames = new ArrayList<>();
        for (Team t : StoreIT.getCurrentOrganisation().getUsersTeams(StoreIT.getCurrentUser())) {
            teamNames.add(t.getName());
        }
        teamChoicebox.setItems(FXCollections.observableArrayList(teamNames));
        teamChoicebox.getSelectionModel().select(0);
        initiateSpinners();

        confirmButton.disableProperty().bind(termsCheckbox.selectedProperty().not());

        this.item = item;

    }

    private void initiateSpinners() {
        DateTime now = new DateTime();
        SpinnerValueFactory<Integer> startYearFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2019, now.getYear() + 5, now.getYear(), 1);
        SpinnerValueFactory<Integer> startMonthFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, now.getMonthOfYear(), 1);
        SpinnerValueFactory<Integer> startDayFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, getMonthDays(now.getMonthOfYear()), now.getDayOfMonth(), 1);
        SpinnerValueFactory<Integer> startHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 23, now.getHourOfDay(), 1);
        SpinnerValueFactory<Integer> startMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, getClosestQuarter(), 15);

        SpinnerValueFactory<Integer> endYearFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(2019, now.getYear() + 5, now.getYear(), 1);
        SpinnerValueFactory<Integer> endMonthFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 12, now.getMonthOfYear(), 1);
        SpinnerValueFactory<Integer> endDayFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, getMonthDays(now.getMonthOfYear()), now.getDayOfMonth(), 1);
        SpinnerValueFactory<Integer> endHourFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 23, now.getHourOfDay(), 1);
        SpinnerValueFactory<Integer> endMinuteFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 60, getClosestQuarter(), 15);


        startYearSpinner.setValueFactory(startYearFactory);
        endYearSpinner.setValueFactory(endYearFactory);
        startMonthSpinner.setValueFactory(startMonthFactory);
        endMonthSpinner.setValueFactory(endMonthFactory);
        startDaySpinner.setValueFactory(startDayFactory);
        endDaySpinner.setValueFactory(endDayFactory);
        startHourSpinner.setValueFactory(startHourFactory);
        endHourSpinner.setValueFactory(endHourFactory);
        startMinuteSpinner.setValueFactory(startMinuteFactory);
        endMinuteSpinner.setValueFactory(endMinuteFactory);

        startMinuteSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue == 60){
                startMinuteSpinner.getValueFactory().setValue(0);
                startHourSpinner.getValueFactory().setValue(startHourFactory.getValue()+1);
            }
        });
        startHourSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue == 24){
                startHourSpinner.getValueFactory().setValue(0);
                startDaySpinner.getValueFactory().setValue(startDayFactory.getValue()+1);
            }
        });

        endMinuteSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue == 60){
                endMinuteSpinner.getValueFactory().setValue(0);
                endHourSpinner.getValueFactory().setValue(endHourFactory.getValue()+1);
            }
        });
        endHourSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
            if(newValue == 24){
                endHourSpinner.getValueFactory().setValue(0);
                endDaySpinner.getValueFactory().setValue(endDayFactory.getValue()+1);
            }
        });


    }


    private int getClosestQuarter(){
        DateTime now = new DateTime();
        int minute = now.getMinuteOfHour();
        int temp = minute % 15;
        int temp2 = minute - temp;
        return temp2;
    }

    private boolean isEndAfterStart(){
        long start = getStartDate().getMillis();
        long end = getEndDate().getMillis();
        return end > start;
    }


    @FXML
    public void confirmReservation() {
        ReservationHandler resHandler = StoreIT.getCurrentOrganisation().getReservationHandler();
        if (isReservationLegal()) {
            resHandler.createReservation(getTeam().getID(), getInterval(), item.getID());
        }
    }

    public boolean isReservationLegal() {
        if(!isEndAfterStart()){
            fadeMessage(intervalError);
            return false;
        }
        if(StoreIT.getCurrentOrganisation().getReservationHandler().isObjectReservedBetween(item.getID(), getInterval())) {
            fadeMessage(alreadyReservedError);
            return false;
        }
        return true;
    }

    private void fadeMessage(AnchorPane labelToFade) {
        AbstractFader.fadeTransition(labelToFade, 3);
    }

    private IBorrower getTeam() {
        String teamName = teamChoicebox.getValue();
        return StoreIT.getCurrentOrganisation().getTeamFromName(teamName);
    }

    private Interval getInterval() {
        return new Interval(getStartDate(), getEndDate());
    }


    private DateTime getStartDate(){
        int startYear = startYearSpinner.getValue();
        int startMonth = startMonthSpinner.getValue();
        int startDay = startDaySpinner.getValue();
        int startHour = startHourSpinner.getValue();
        int startMinute = startMinuteSpinner.getValue();
        DateTime start = new DateTime(startYear, startMonth, startDay, startHour, startMinute);
        return start;
    }

    private DateTime getEndDate(){
        int endYear = endYearSpinner.getValue();
        int endMonth = endMonthSpinner.getValue();
        int endDay = endDaySpinner.getValue();
        int endHour = endHourSpinner.getValue();
        int endMinute = endMinuteSpinner.getValue();
        DateTime end = new DateTime(endYear, endMonth, endDay, endHour, endMinute);
        return end;
    }

    private int getMonthDays(int nr) {
        if(nr == 1 || nr == 3 || nr == 5 || nr == 7 || nr == 8 || nr ==10 || nr == 12)
            return 31;
        else if(nr == 2)
            return 28;
        else if(nr ==4|| nr == 6|| nr == 9 || nr == 11)
            return 30;
        return 0;
    }
}
