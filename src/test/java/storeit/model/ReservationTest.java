package storeit.model;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ReservationTest {

    @Test
    public void reservationsShouldBeEqual(){
        IBorrower borrower = new Team("Team1");
        IReservable object = IReservableFactory.createReservableItem("mockItem", "desc","requirements",1,Condition.GREAT,true, 0);
        DateTime time1 = new DateTime();
        DateTime time2 = time1.plusDays(1);


        Interval interval1 = new Interval(time1, time2);

        IReservation res1 = new Reservation(1, borrower.getID(), interval1, object.getID(), ReservationStatus.PENDING);
        IReservation res2 = new Reservation(1, borrower.getID(), interval1, object.getID(), ReservationStatus.APPROVED);

        assertTrue(res1.equals(res2));
    }


    @Test
    public void objectShouldEqual(){
        Organisation mockOrg = new Organisation("MockOrg");
        StoreIT.setCurrentOrganisation(mockOrg);
        Team borrower = new Team("Team1");
        Team owner = new Team("Owner");
        mockOrg.addTeam(borrower);
        mockOrg.addTeam(owner);
        IReservable object = IReservableFactory.createReservableItem("mockItem", "desc","requirements",1,Condition.GREAT,true, 0);
        DateTime time1 = new DateTime();
        DateTime time2 = time1.plusDays(1);
        mockOrg.addItem(object, owner);


        Interval interval1 = new Interval(time1, time2);

        IReservation res1 = new Reservation(1, borrower.getID(), interval1, object.getID(), ReservationStatus.PENDING);


        assertTrue(StoreIT.getCurrentOrganisation().getItem(res1.getReservedObjectID()).equals(object));

    }

}


