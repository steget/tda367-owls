package storagesystem.model;


import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.util.Objects;

/**
 * @author William Albertsson
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
     *
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

    private Reservation(IReservation res) {
        this(res.getID(), res.getBorrower(), res.getInterval(), res.getReservedObject(), res.getStatus());
    }

    public Reservation copy() {
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
     *
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

    /**
     * Converts the interval to a string that can be easily read.
     *
     * @return
     */
    @Override
    public String getReadableInterval() {
        DateTime start = interval.getStart();
        DateTime end = interval.getEnd();

        int startYear = start.getYear();
        int startMonth = start.getMonthOfYear();
        int startDay = start.getMonthOfYear();
        int startHour = start.getHourOfDay();
        int startMinute = start.getMinuteOfHour();

        int endYear = end.getYear();
        int endMonth = end.getMonthOfYear();
        int endDay = end.getDayOfMonth();
        int endHour = end.getHourOfDay();
        int endMinute = end.getMinuteOfHour();

        StringBuilder sb = new StringBuilder();
        if (startYear != new DateTime().getYear())
            sb.append(startYear + "." + startMonth + "." + startDay + " " + startHour + ":" + getToDoubleZero(startMinute) + " - ");
        else
            sb.append(startMonth + "." + startDay + " " + startHour + ":" + getToDoubleZero(startMinute) + " - ");


        if (startYear != endYear)
            sb.append(endYear + "." + endMonth + "." + endDay + " " + endHour + ":" + getToDoubleZero(endMinute));
        else if (startMonth != endMonth)
            sb.append(endMonth + "." + endDay + " " + endHour + ":" + getToDoubleZero(endMinute));
        else if (startDay != endDay)
            sb.append(endMonth + "." + endDay + " " + endHour + ":" + getToDoubleZero(endMinute));
        else {
            sb.append(endHour + ":" + getToDoubleZero(endMinute));
        }

        String readable = sb.toString();

        return readable;
    }

    private String getToDoubleZero(int startMinute) {

        if (startMinute == 0) {
            return "00";
        }
        return Integer.toString(startMinute);

    }

    private String getMonth(int nr) {
        switch (nr) {
            case 1:
                return "Jan";
            case 2:
                return "Feb";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
            default:
                return "NaN";
        }
    }

}
