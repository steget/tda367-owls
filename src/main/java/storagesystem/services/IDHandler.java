package storagesystem.services;

import storagesystem.model.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public class IDHandler {

    public static void updateAllIDs(List<Organisation> organisations) {
        updateNextItemID(organisations);
        updateNextLocationID(organisations);
        updateNextReservationID(organisations);
        updateNextTeamID(organisations);
        updateNextUserID(organisations);
        System.out.println("Updated all nextIDs.");
    }

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


    private static void updateNextReservationID(List<Organisation> organisations) {
        List<Integer> allIDs = new ArrayList<>();
        for (Organisation organisation : organisations) {
            for (int i = 0; i < organisation.getReservationHandler().getReservations().size(); i++) {
                int ID = organisation.getReservationHandler().getReservations().get(i).getID();
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