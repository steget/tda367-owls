package storagesystem.model;

import org.joda.time.Interval;

/**
 * @author William Albertsson
 * Interface used for reservations.
 */

public interface IReservation {
    /**
     * @return The unique ID for this reservation
     */
    int getID();

    /**
     * @return The party that borrows the object in Reservation
     */
    IBorrower getBorrower();

    /**
     * @return The time interval when the reservation is
     */
    Interval getInterval();


    IReservable getReservedObject();


    ReservationStatus getStatus();

    void setStatus(ReservationStatus status);


    /**
     * Should be implemented in all implementors and should compare only the ID's
     *
     * @param o An object against which to test
     * @return True if o is of the same class and has the same ID, otherwise false
     */
    @Override
    boolean equals(Object o);

    String getReadableInterval();
}
