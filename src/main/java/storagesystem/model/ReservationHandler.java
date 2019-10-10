package storagesystem.model;

import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * A handler taking care of all reservations for one organisation. Methods for creating and retrieving reservations
 */

public class ReservationHandler {

    //TODO Understand exceptions and implement correctly


    private List<IReservation> reservations;


    public ReservationHandler(List<IReservation> reservations) {
        this.reservations = reservations;
    }

    public ReservationHandler(){

    }


    public void setReservations(List<IReservation> reservations) {
        this.reservations = reservations;
    }



    /**
     * @param id
     * @return Reservation matching id. Returns null if none found.
     */

    public IReservation getReservation(int id) throws NoSuchElementException {
        for (IReservation res : reservations) {
            if (res.getID() == id) {
                return res;
            }
        }
        throw new NoSuchElementException("No reservation with that id");
    }


    /**
     * @return List with all known reservations
     */
    public List<IReservation> getReservations() {
        return reservations;
    }

    /**
     * @param object
     * @return A list with all known reservations for one specific object.
     */
    public List<IReservation> getReservations(IReservable object) {


        List<IReservation> objectsReservations = new ArrayList<>();


        for (IReservation res : this.reservations) {
            if (res.getReservedObject().equals(object)) {
                objectsReservations.add(res);
            }
        }

        return objectsReservations;
    }

    /**
     *
     * @param borrower
     * @return A list with all known reservations with specific borrower.
     */
    public List<IReservation> getReservations(IBorrower borrower){

        List<IReservation> borrowersReservations = new ArrayList<>();

        for(IReservation res : this.reservations){
            if(borrower.equals(res.getBorrower())){
                borrowersReservations.add(res);
            }
        }

        return borrowersReservations;
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
     * Creates a new reservation and saves in this ReservationHandler. Firsts tests to see if object isn't already reserved.
     *
     * @param borrower The party which want to borrow object
     * @param interval The interval in which to reserve the object.
     * @param object The object which is to be reserved.
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
    public IReservation getLastReservation() throws NoSuchElementException{
        int lenght = reservations.size();
        if (lenght == 0) {
            throw new NoSuchElementException("No reservations exists");
        }
        return reservations.get(lenght-1);
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
        IReservation resRem = getReservation(res.getID());
        reservations.remove(resRem);

    }


}
