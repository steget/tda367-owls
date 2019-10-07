package storagesystem.model;

import org.joda.time.Interval;

public interface IReservation {
    int getID();

    IBorrower getBorrower();

    Interval getInterval();

    IReservable getReservedObject();

    ReservationStatus getStatus();

    IReservation copy();


    @Override
    boolean equals(Object o);
}
