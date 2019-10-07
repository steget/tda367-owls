package storagesystem.model;

import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;

/**
 * A handler taking care of all reservations for one organisation. Methods for creating and retrieving reservations
 */

public class ReservationHandler {


    private List<IReservation> reservations;


    public ReservationHandler(int nextReservationID, List<IReservation> reservations) {
        Reservation.setNxtResId(nextReservationID);
        this.reservations = reservations;
    }


    /**
     * @param id
     * @return Reservation matching id. Returns null if none found.
     */

    public IReservation getReservation(int id) throws NullPointerException {
        for (IReservation res : reservations) {
            if (res.getID() == id) {
                return res.copy();
            }
        }
        return null;
    }


    /**
     * @return List with all known reservations
     */
    public List<IReservation> getReservations() {
        return reservations;
    }

    /**
     * @param object
     * @return A list with all known reservations with specific object.
     */
    public List<IReservation> getReservations(IReservable object) {


        List<IReservation> reservations = new ArrayList<>();


        for (IReservation res : this.reservations) {
            if (res.getReservedObject().equals(object)) {
                reservations.add(res.copy());
            }
        }

        return reservations;
    }

    /**
     *
     * @param borrower
     * @return A list with all known reservations with specific borrower.
     */
    public List<IReservation> getReservations(IBorrower borrower){

        List<IReservation> reservations = new ArrayList<>();

        for(IReservation res : this.reservations){
            if(borrower.equals(res.getBorrower())){
                reservations.add(res.copy());
            }
        }

        return reservations;
    }


    /**Checks too see if an interval overlaps the reservations of an object.
     *
     * @param object   Object to test
     * @param interval Interval to test object against
     * @return True if there is overlap, false otherwise.
     */
    private boolean isObjectReservedBetween(IReservable object, Interval interval) {
        List<IReservation> reservations = getReservations(object);

        for (IReservation res : reservations) {
            if (!(res.getInterval().overlap(interval) == null)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Creates a new reservation and saves in this ReservationHandler.
     *
     * @param borrower The party which want to borrow object
     * @param interval The interval in which
     * @param object
     */
    public void createReservation(IBorrower borrower, Interval interval, IReservable object) {
        if (!isObjectReservedBetween(object, interval)) {
            Reservation reservation = new Reservation(borrower, interval, object, ReservationStatus.PENDING);
            reservations.add(reservation);

        }
    }

    /**
     *  Get the last created reservation
     * @return Null if no reservation found
     */
    public IReservation getLastReservation() throws NullPointerException{
        int lenght = reservations.size();
        if (lenght == 0) {
            System.out.println("No reservation found");
            return null;
        }
        return reservations.get(lenght-1).copy();
    }

    /**
     * Removes reservation matching id
     * @param id
     */
    public void removeReservation(int id) {
        IReservation res = getReservation(id);
        removeReservation(res);
    }

    /**
     * Removes first found reservation with same ID as res
     * @param res
     */
    private void removeReservation(IReservation res) {
        reservations.remove(res);

    }


}
