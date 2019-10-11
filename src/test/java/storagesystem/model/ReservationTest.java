package storagesystem.model;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReservationTest {

    @Test
    public void reservationsShouldBeEqual(){
        IBorrower borrower = new User("John Doe", "Developer", "Call me");
        IReservable object = new Item("mockItem", "desc","requirements",1,Condition.GREAT,true, null, null);
        DateTime time1 = new DateTime();
        DateTime time2 = time1.plusDays(1);


        Interval interval1 = new Interval(time1, time2);

        IReservation res1 = new Reservation(1, borrower, interval1, object,  ReservationStatus.PENDING);
        IReservation res2 = new Reservation(1, borrower, interval1, object, ReservationStatus.APPROVED);

        assertTrue(res1.equals(res2));
    }


    @Test
    public void objectShouldEqual(){
        IBorrower borrower = new User("John Doe", "Developer", "Call me");
        IReservable object = new Item("mockItem", "desc","requirements",1,Condition.GREAT,true, null, null);
        DateTime time1 = new DateTime();
        DateTime time2 = time1.plusDays(1);


        Interval interval1 = new Interval(time1, time2);

        IReservation res1 = new Reservation(1, borrower, interval1, object,  ReservationStatus.PENDING);


        assertTrue(res1.getReservedObject().equals(object));

    }

}
