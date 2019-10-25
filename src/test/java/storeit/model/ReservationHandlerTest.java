package storeit.model;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


public class ReservationHandlerTest {


    @Test
    public void shouldCreateMultipleReservations() {

        IBorrower borrower = new Team("Team1");
        IReservable object = IReservableFactory.createReservableItem("mockItem", "desc","requirements",1,Condition.GREAT,true, 0);
        ReservationHandler handler = new ReservationHandler(new ArrayList<>());

        DateTime time1 = new DateTime();
        DateTime time2 = time1.plusDays(1);
        DateTime time3 = time2.plusDays(1);
        DateTime time4 = time3.plusDays(1);

        Interval interval1 = new Interval(time1, time2);
        Interval interval2 = new Interval(time3, time4);

        handler.createReservation(borrower.getID(), interval1, object.getID());
        handler.createReservation(borrower.getID(), interval2, object.getID());

        int size = handler.getAllReservations().size();

        assertEquals(2, size);

    }

    @Test
    public void onlyOneReservationPerObjectAndIntervalShouldExist() {
        Organisation mockOrg = new Organisation("MockOrg");
        StoreIT.setCurrentOrganisation(mockOrg);
        Team borrower = new Team("Team1");
        Team owner = new Team("Owner");
        mockOrg.addTeam(borrower);
        mockOrg.addTeam(owner);
        IReservable object1 = IReservableFactory.createReservableItem("mockItem", "desc","requirements",1,Condition.GREAT,true, 0);
        IReservable object2 = IReservableFactory.createReservableItem("mockItem", "desc","requirements",1,Condition.GREAT,true, 0);
        ReservationHandler handler = new ReservationHandler(new ArrayList<>());

        mockOrg.addItem(object1, owner);
        mockOrg.addItem(object2, owner);
        DateTime time1 = new DateTime();
        DateTime time2 = time1.plusDays(1);


        Interval interval = new Interval(time1, time2);



        if (!handler.isObjectReservedBetween(object1.getID(), interval))
            handler.createReservation(borrower.getID(), interval, object1.getID());//First reservation should go through without issues
        handler.getLastReservation().setStatus(ReservationStatus.APPROVED);

        assertEquals(1, handler.getAllReservations().size());

        if (!handler.isObjectReservedBetween(object2.getID(), interval))
            handler.createReservation(borrower.getID(), interval, object2.getID()); //New reservation with same interval but different object
        handler.getLastReservation().setStatus(ReservationStatus.APPROVED);

        assertEquals(2, handler.getAllReservations().size());

        if (!handler.isObjectReservedBetween(object1.getID(), interval))
            handler.createReservation(borrower.getID(), interval, object1.getID()); //Object1 is already reserved in this interval so it shouldn't get created.
        handler.getLastReservation().setStatus(ReservationStatus.APPROVED);

        assertEquals(2, handler.getAllReservations().size());
    }


    @Test
    public void shouldGiveID() {
        Organisation mockOrg = new Organisation("MockOrg");
        StoreIT.setCurrentOrganisation(mockOrg);
        Team borrower = new Team("Team1");
        Team owner = new Team("Owner");
        mockOrg.addTeam(borrower);
        mockOrg.addTeam(owner);

        IReservable object = IReservableFactory.createReservableItem("mockItem", "desc","requirements",1,Condition.GREAT,true, 0);
        ReservationHandler handler = new ReservationHandler(new ArrayList<>());

        mockOrg.addItem(object, owner);
        DateTime startTime = new DateTime(1999, 8, 14, 12, 30);
        DateTime endTime = new DateTime();

        Interval interval = new Interval(startTime, endTime);

        handler.createReservation(borrower.getID(), interval, object.getID());


        List<IReservation> objectReservations = handler.getObjectsReservations(object.getID());

        IReservation reservation = objectReservations.get(0);


        assertEquals(0, reservation.getID());

    }

    @Test
    public void shouldReturnAllBorrowerReservations() {
        Organisation mockOrg = new Organisation("MockOrg");
        StoreIT.setCurrentOrganisation(mockOrg);
        Team borrower1 = new Team("Team1");
        Team borrower2 = new Team("Owner");
        mockOrg.addTeam(borrower1);
        mockOrg.addTeam(borrower2);


        IReservable object1 = IReservableFactory.createReservableItem("mockItem", "desc","requirements",1,Condition.GREAT,true, 0);
        IReservable object2 = IReservableFactory.createReservableItem("mockItem", "desc","requirements",1,Condition.GREAT,true, 0);

        mockOrg.addItem(object1, borrower2);
        mockOrg.addItem(object2, borrower2);

        ReservationHandler handler = new ReservationHandler(new ArrayList<>());

        DateTime time1 = new DateTime();
        DateTime time2 = time1.plusDays(1);
        DateTime time3 = time2.plusDays(1);
        DateTime time4 = time3.plusDays(1);

        Interval interval1 = new Interval(time1, time2);
        Interval interval2 = new Interval(time3, time4);

        handler.createReservation(borrower1.getID(), interval1, object1.getID());
        handler.createReservation(borrower1.getID(), interval2, object1.getID());
        handler.createReservation(borrower2.getID(), interval1, object2.getID());
        handler.createReservation(borrower2.getID(), interval2, object2.getID());

        assertEquals(4, handler.getAllReservations().size());

        assertEquals(2, handler.getBorrowersReservations(borrower1.getID()).size());
    }

}

