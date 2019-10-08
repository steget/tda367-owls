package storagesystem.model;

import org.joda.time.Interval;

/**
 * Interface used for reservations.
 */

public interface IReservation {
    int getID();

    IBorrower getBorrower();

    Interval getInterval();

    IReservable getReservedObject();

    ReservationStatus getStatus();



    @Override
    boolean equals(Object o);
}
