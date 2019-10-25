package storagesystem.model;


import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 * @author William Albertsson
 * A reservation most importantly contains a object that is reserved, a time of reservation and who the borrower is
 */

public class Reservation implements IReservation {


    private static int nextID = 0;
    private final int id;
    private int borrowerID;
    private Interval interval;
    private int reservedObjectID;
    private ReservationStatus status;


    public Reservation(int borrowerID, Interval interval, int reservedObjectID, ReservationStatus status) {
        this.id = nextID;
        this.borrowerID = borrowerID;
        this.interval = interval;
        this.reservedObjectID = reservedObjectID;
        this.status = status;

        nextID++;
    }

    //TODO: cleanup

    /**
     * Should not be used. Created for testing purposes.
     *
     * @param id               ID of reservation
     * @param borrowerID       ID of borrower
     * @param interval         Interval for reservation
     * @param reservedObjectID ID for object to be reserved
     * @param status           status of reservation
     */

    public Reservation(int id, int borrowerID, Interval interval, int reservedObjectID, ReservationStatus status) {
        this.id = id;
        this.borrowerID = borrowerID;
        this.interval = new Interval(interval);
        this.reservedObjectID = reservedObjectID;
        this.status = status;
    }

    @Override
    public int getID() {
        return id;
    }

    public static void setNextID(int nextID) {
        Reservation.nextID = nextID;
    }

    @Override
    public int getBorrowerID() {
        return borrowerID;
    }

    private void setBorrowerID(int borrowerID) {
        this.borrowerID = borrowerID;
    }

    @Override
    public Interval getInterval() {
        return interval;
    }

    private void setInterval(Interval interval) {
        this.interval = new Interval(interval);
    }

    @Override
    public int getReservedObjectID() {
        return reservedObjectID;
    }

    private void setReservedObjectID(int reservedObjectID) {
        this.reservedObjectID = reservedObjectID;
    }

    @Override
    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }

    /**
     * Compares ID's if o is a reservation
     *
     * @param o Object to compare
     * @return true if ID's are equal
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
     * @return a readable interval as a String
     */
    @Override
    public String getReadableInterval() {
        DateTime start = interval.getStart();
        DateTime end = interval.getEnd();

        int startYear = start.getYear();
        int startMonth = start.getMonthOfYear();
        int startDay = start.getDayOfMonth();
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
            sb.append(startDay + "/" + startMonth + " " + startHour + ":" + getToDoubleZero(startMinute) + " - ");


        if (startYear != endYear)
            sb.append(endYear + "." + endMonth + "." + endDay + " " + endHour + ":" + getToDoubleZero(endMinute));
        else if (startMonth != endMonth)
            sb.append(endDay + "/" + endMonth + " " + endHour + ":" + getToDoubleZero(endMinute));
        else if (startDay != endDay)
            sb.append(endDay + "/" + endMonth + " " + endHour + ":" + getToDoubleZero(endMinute));
        else {
            sb.append(endHour + ":" + getToDoubleZero(endMinute));
        }

        return sb.toString();
    }


    /**
     * Converts the value of startMinute to double zeroes if it is zero
     *
     * @param minute time in minutes to convert
     * @return Double zeroes if startMinute equals 0, otherwise returns startMinute
     */
    private String getToDoubleZero(int minute) {

        if (minute == 0) {
            return "00";
        }
        return Integer.toString(minute);
    }
}