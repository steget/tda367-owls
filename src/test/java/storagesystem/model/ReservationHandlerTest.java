package storagesystem.model;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

import static org.junit.Assert.*;


import java.util.ArrayList;
import java.util.List;


public class ReservationHandlerTest {


    @Test
    public void shouldCreateMultipleReservations() {

        IBorrower borrower = new User("John Doe", "Developer", "Call me");
        IReservable object = IReservableFactory.createReservableItem("mockItem", "desc","requirements",1,Condition.GREAT,true, null, null);
        ReservationHandler handler = new ReservationHandler(new ArrayList<>());

        DateTime time1 = new DateTime();
        DateTime time2 = time1.plusDays(1);
        DateTime time3 = time2.plusDays(1);
        DateTime time4 = time3.plusDays(1);

        Interval interval1 = new Interval(time1, time2);
        Interval interval2 = new Interval(time3, time4);

        handler.createReservation(borrower, interval1, object);
        handler.createReservation(borrower, interval2, object);

        int size = handler.getReservations().size();

        assertEquals(2, size);

    }

    @Test
    public void onlyOneReservationPerObjectAndIntervalShouldExist() {
        IBorrower borrower = new User("John Doe", "Developer", "Call me");
        IReservable object1 = IReservableFactory.createReservableItem("mockItem", "desc","requirements",1,Condition.GREAT,true, null, null);
        IReservable object2 = IReservableFactory.createReservableItem("mockItem", "desc","requirements",1,Condition.GREAT,true, null, null);
        ReservationHandler handler = new ReservationHandler(new ArrayList<>());

        DateTime time1 = new DateTime();
        DateTime time2 = time1.plusDays(1);


        Interval interval = new Interval(time1, time2);

        if(!handler.isObjectReservedBetween(object1, interval))
            handler.createReservation(borrower, interval, object1); //First reservation should go through without issues

        assertEquals(1, handler.getReservations().size());

        if(!handler.isObjectReservedBetween(object2, interval))
            handler.createReservation(borrower, interval, object2); //New reservation with same interval but different object

        assertEquals(2, handler.getReservations().size());

        if(!handler.isObjectReservedBetween(object1, interval))
            handler.createReservation(borrower, interval, object1); //Object1 is already reserved in this interval so it shouldn't get created.

        assertEquals(2, handler.getReservations().size());
    }


    @Test
    public void shouldGiveID() {
        IBorrower borrower = new User("John Doe", "Developer", "Call me");
        IReservable object = IReservableFactory.createReservableItem("mockItem", "desc","requirements",1,Condition.GREAT,true, null, null);
        ReservationHandler handler = new ReservationHandler(new ArrayList<>());

        DateTime startTime = new DateTime(1999, 8, 14, 12, 30);
        DateTime endTime = new DateTime();

        Interval interval = new Interval(startTime, endTime);

        handler.createReservation(borrower, interval, object);


        List<IReservation> objectReservations = handler.getReservations(object);

        IReservation reservation = objectReservations.get(0);


        assertEquals(0, reservation.getID());

    }

    @Test
    public void shouldReturnAllBorrowerReservations() {
        IBorrower borrower1 = new User("John Doe", "Developer", "Call me");
        IBorrower borrower2 = new User("William", "Admin", "Mail me");

        IReservable object1 = IReservableFactory.createReservableItem("mockItem", "desc","requirements",1,Condition.GREAT,true, null, null);
        IReservable object2 = IReservableFactory.createReservableItem("mockItem", "desc","requirements",1,Condition.GREAT,true, null, null);

        ReservationHandler handler = new ReservationHandler(new ArrayList<>());

        DateTime time1 = new DateTime();
        DateTime time2 = time1.plusDays(1);
        DateTime time3 = time2.plusDays(1);
        DateTime time4 = time3.plusDays(1);

        Interval interval1 = new Interval(time1, time2);
        Interval interval2 = new Interval(time3, time4);

        handler.createReservation(borrower1, interval1, object1);
        handler.createReservation(borrower1, interval2, object1);
        handler.createReservation(borrower2, interval1, object2);
        handler.createReservation(borrower2, interval2, object2);

        assertEquals(4, handler.getReservations().size());

        assertEquals(2,handler.getReservations(borrower1).size());
    }

}
