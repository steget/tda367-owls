package storagesystem.model;


import org.joda.time.Interval;

import java.util.Objects;

/**
 * A reservation most importantly contains a object that is reserved, a time of reservation and who the borrower is
 */

public class Reservation implements IReservation {



    static int NXT_RES_ID = 0;
    private final int id;
    private IBorrower borrower;
    private Interval interval;
    private IReservable reservedObject;
    private ReservationStatus status;



    public Reservation(IBorrower borrower, Interval interval, IReservable reservedObject, ReservationStatus status) {
        this.id = NXT_RES_ID;
        this.borrower = borrower;
        this.interval = interval;
        this.reservedObject = reservedObject;
        this.status = status;

        NXT_RES_ID++;
    }

    /**
     * Should not be used. Created for testing purposes.
     * @param id
     * @param borrower
     * @param interval
     * @param reservedObject
     * @param status
     */

    public Reservation(int id, IBorrower borrower, Interval interval, IReservable reservedObject, ReservationStatus status) {
        this.id = id;
        this.borrower = borrower;
        this.interval = new Interval(interval);
        this.reservedObject = reservedObject;
        this.status = status;
    }

    private Reservation(IReservation res){
        this(res.getID(), res.getBorrower(), res.getInterval(), res.getReservedObject(), res.getStatus());
    }

    public Reservation copy(){
        return new Reservation(this);
    }

    @Override
    public int getID() {
        return id;
    }

    public static void setNxtResId(int nxtResId) {
        Reservation.NXT_RES_ID = nxtResId;
    }

    @Override
    public IBorrower getBorrower() {
        return borrower;
    }

    private void setBorrower(IBorrower borrower) {
        this.borrower = borrower;
    }

    @Override
    public Interval getInterval() {
        return interval;
    }

    private void setInterval(Interval interval) {
        this.interval = new Interval(interval);
    }

    @Override
    public IReservable getReservedObject() {
        return reservedObject;
    }

    private void setReservedObject(IReservable reservedObject) {
        this.reservedObject = reservedObject;
    }

    @Override
    public ReservationStatus getStatus() {
        return status;
    }

    private void setStatus(ReservationStatus status) {
        this.status = status;
    }

    /**
     * Compares ID's if o is a reservation
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return id == that.id;
    }


}
