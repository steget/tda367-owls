package storagesystem.services;

import storagesystem.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An IDHandler with static methods which updates the nextIDs of Items, Locations, Reservations, Teams and Users in
 * the selected list of organisations. This is to avoid duplicate IDs in the database. IDHandler uses the IDs that
 * already exists in the Organisations and sets the nextID to the next integer after the highest ID in each respective
 * class.
 *
 * @author Carl Lindh
 */
public class IDHandler {

    /**
     * updates all nextIDs in the Items, Locations, Reservations, Teams and Users in the list of organisations
     *
     * @param organisations a list of organisations
     */

    public static void updateAllIDs(List<Organisation> organisations) {
        updateNextItemID(organisations);
        updateNextLocationID(organisations);
        updateNextReservationID(organisations);
        updateNextTeamID(organisations);
        updateNextUserID(organisations);
        System.out.println("Updated all nextIDs.");
    }

    /**
     * Sets all nextIDs to 0, used for testing.
     */
    static void clearAllNextIDs() {
        Item.setNextID(0);
        User.setNextID(0);
        Team.setNextID(0);
        Reservation.setNextID(0);
        Location.setNextID(0);
    }


    /**
     * updates nextID for Item in the selected list of organisations by checking the highest item ID in the
     * organisations' items.
     *
     * @param organisations list of organisations
     */

    private static void updateNextItemID(List<Organisation> organisations) {
        List<Integer> allIDs = new ArrayList<>();
        for (Organisation organisation : organisations) {
            for (int i = 0; i < organisation.getAllItems().size(); i++) {
                int ID = organisation.getAllItems().get(i).getID();
                if (!allIDs.contains(ID)) {
                    allIDs.add(ID);
                }
            }
        }
        try {
            int maxUserID = Collections.max(allIDs);
            Item.setNextID(maxUserID + 1);
        } catch (NoSuchElementException e) {
            System.out.println("There are no itemIDs.");
            Item.setNextID(0);
        }
    }

    /**
     * updates nextID for Location in the selected list of organisations by checking the highest location ID in the
     * organisations' locations.
     *
     * @param organisations list of organisations
     */

    private static void updateNextLocationID(List<Organisation> organisations) {
        List<Integer> allIDs = new ArrayList<>();
        for (Organisation organisation : organisations) {
            for (int i = 0; i < organisation.getLocations().size(); i++) {
                int ID = organisation.getLocations().get(i).getID();
                if (!allIDs.contains(ID)) {
                    allIDs.add(ID);
                }
            }
        }
        try {
            int maxUserID = Collections.max(allIDs);
            Location.setNextID(maxUserID + 1);
        } catch (NoSuchElementException e) {
            System.out.println("There are no locationIDs.");
            Location.setNextID(0);
        }
    }

    /**
     * updates nextID for Reservation in the selected list of organisations by checking the highest reservation ID in the
     * organisations' reservations.
     *
     * @param organisations list of organisations
     */
    private static void updateNextReservationID(List<Organisation> organisations) {
        List<Integer> allIDs = new ArrayList<>();
        for (Organisation organisation : organisations) {
            for (int i = 0; i < organisation.getAllReservations().size(); i++) {
                int ID = organisation.getAllReservations().get(i).getID();
                if (!allIDs.contains(ID)) {
                    allIDs.add(ID);
                }
            }
        }
        try {
            int maxUserID = Collections.max(allIDs);
            Reservation.setNextID(maxUserID + 1);
        } catch (NoSuchElementException e) {
            System.out.println("There are no reservationIDs.");
            Reservation.setNextID(0);
        }
    }

    /**
     * updates nextID for Team in the selected list of organisations by checking the highest team ID in the
     * organisations' teams.
     *
     * @param organisations list of organisations
     */
    private static void updateNextTeamID(List<Organisation> organisations) {
        List<Integer> allIDs = new ArrayList<>();
        for (Organisation organisation : organisations) {
            for (int i = 0; i < organisation.getTeams().size(); i++) {
                int ID = organisation.getTeams().get(i).getID();
                if (!allIDs.contains(ID)) {
                    allIDs.add(ID);
                }
            }
        }
        try {
            int maxUserID = Collections.max(allIDs);
            Team.setNextID(maxUserID + 1);
        } catch (NoSuchElementException e) {
            System.out.println("There are no teamIDs.");
            Team.setNextID(0);
        }
    }

    /**
     * updates nextID for User in the selected list of organisations by checking the highest user ID in the
     * organisations' users.
     *
     * @param organisations list of organisations
     */
    private static void updateNextUserID(List<Organisation> organisations) {
        List<Integer> allUserIDs = new ArrayList<>();
        for (Organisation organisation : organisations) {
            for (int i = 0; i < organisation.getUsers().size(); i++) {
                int ID = organisation.getUsers().get(i).getID();
                if (!allUserIDs.contains(ID)) {
                    allUserIDs.add(ID);
                }
            }
        }
        try {
            int maxUserID = Collections.max(allUserIDs);
            User.setNextID(maxUserID + 1);
        } catch (NoSuchElementException e) {
            System.out.println("There are no userIDs.");
            User.setNextID(0);
        }

    }

}