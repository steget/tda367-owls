package storagesystem.model;

import org.joda.time.Interval;

/**
 * Interface used for reservations.
 */

public interface IReservation {
    /**
     *
     * @return The unique ID for this reservation
     */
    int getID();

    /**
     *
     * @return The party that borrows the object in Resercation
     */
    IBorrower getBorrower();

    /**
     *
     * @return The time interval when the reservation is
     */
    Interval getInterval();


    IReservable getReservedObject();


    ReservationStatus getStatus();


    /**
     * Should be implemented in all implementors and should compare only the ID's
     * @param o An object against which to test
     * @return True if o is of the same class and has the same ID, otherwise false
     */
    @Override
    boolean equals(Object o);
}
