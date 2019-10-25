package storagesystem.model;

import org.joda.time.Interval;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author William Albertsson
 * A handler taking care of all reservations for one organisation. Methods for creating and retrieving reservations
 */

public class ReservationHandler {

    //TODO Understand exceptions and make sure they are used properly


    private final List<IReservation> reservations;


    public ReservationHandler(List<IReservation> reservations) {
        this.reservations = reservations;
    }

    public ReservationHandler() {
        reservations = new ArrayList<>();
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
    public List<IReservation> getAllReservations() {
        return reservations;
    }

    /**
     * @param objectID
     * @return A list with all known reservations for one specific object.
     */
    public List<IReservation> getObjectsReservations(int objectID) {


        List<IReservation> objectsReservations = new ArrayList<>();


        for (IReservation res : this.reservations) {
            if (res.getReservedObjectID() == objectID) {
                objectsReservations.add(res);
            }
        }

        return objectsReservations;
    }

    /**
     * @param borrowerID
     * @return A list with all known reservations with specific borrower.
     */
    public List<IReservation> getBorrowersReservations(int borrowerID) {

        List<IReservation> borrowersReservations = new ArrayList<>();

        for (IReservation res : this.reservations) {
            if (borrowerID == res.getBorrowerID()) {
                borrowersReservations.add(res);
            }
        }

        return borrowersReservations;
    }

    /**
     * Finds all the incoming reservations a team has
     * @param team Owner of the items in the reservations
     * @return List with all incoming reservations the team has
     */
    List<IReservation> getTeamsIngoingReservations(Team team) {
        List<IReservation> teamReservations = new ArrayList<>();
        for(IReservation res : reservations){
            if(team.isItemOwner(res.getReservedObjectID()))
                teamReservations.add(res);
        }
        return teamReservations;
    }

    List<IReservation> getTeamsOutgoingReservations(Team team){
        List<IReservation> teamReservations = new ArrayList<>();
        for(IReservation res : reservations){
            if(res.getBorrowerID() == team.getID()){
                teamReservations.add(res);
            }
        }

        return teamReservations;
    }

    /**
     * Checks too see if an interval overlaps the reservations of an object.
     *
     * @param objectID   ID of Object to test
     * @param interval Interval to test object against
     * @return True if there is overlap, false otherwise.
     */
    public boolean isObjectReservedBetween(int objectID, Interval interval) {
        List<IReservation> reservations = getObjectsReservations(objectID);

        for (IReservation res : reservations) {
            if (res.getReservedObjectID() == objectID && !(res.getInterval().overlap(interval) == null) && res.getStatus() == ReservationStatus.APPROVED) {
                return true;
            }
        }
        return false;
    }


    /**
     * Creates a new reservation and saves in this ReservationHandler. Firsts tests to see if object isn't already reserved.
     *
     * @param borrowerID The ID of the  party which want to borrow object
     * @param interval   The interval in which to reserve the object.
     * @param objectID   The ID of the object which is to be reserved.
     */
    public void createReservation(int borrowerID, Interval interval, int objectID) {
        Reservation reservation = new Reservation(borrowerID, interval, objectID, ReservationStatus.PENDING);
        reservations.add(reservation);
    }

    /**
     * Get the last created reservation
     *
     * @return Null if no reservation found
     */
    public IReservation getLastReservation() throws NoSuchElementException {
        int length = reservations.size();
        if (length == 0) {
            throw new NoSuchElementException("No reservations exists");
        }
        return reservations.get(length - 1);
    }

    /**
     * Removes reservation matching id
     *
     * @param id
     */
    public void removeReservation(int id) {
        IReservation res = getReservation(id);
        removeReservation(res);
    }

    /**
     * Removes first found reservation with same ID as res
     *
     * @param res
     */
    private void removeReservation(IReservation res) {
        IReservation resRem = getReservation(res.getID());
        reservations.remove(resRem);

    }

}

