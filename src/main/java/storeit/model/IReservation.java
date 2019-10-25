package storeit.model;

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
     * @return The ID of the party that borrows the object in Reservation
     */
    int getBorrowerID();

    /**
     * @return The time interval when the reservation is
     */
    Interval getInterval();




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

    int getReservedObjectID();
}
