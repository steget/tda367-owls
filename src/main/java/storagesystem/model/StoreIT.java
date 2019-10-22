package storagesystem.model;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import storagesystem.services.JSONHandler;
import storagesystem.services.IDHandler;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class StoreIT {
    private static List<Organisation> organisations = new ArrayList<>();
    private static User currentUser;
    private static Organisation currentOrganisation;

    /**
     * Loads all data into the program. Should be run at start.
     */
    public void initializeBackend() throws IOException {
        //reset();
        try {
            organisations.addAll(JSONHandler.getOrganisationList());
        } catch (NullPointerException e) {
            System.out.println("Organisation json is empty.");
        }
        System.out.println("Loaded Organisations from json.");
        IDHandler.updateAllIDs(organisations);
        currentOrganisation = organisations.get(0);
        System.out.println("Current Organisation Set.");
    }

    private static void reset() throws IOException { //Run if fresh start or after tests!!!
        JSONHandler.clearAllJsonFiles();
        mockData();

    }

    private static void mockData() throws IOException {
        //Hardcoded stuff for testing
        Location location = new Location("MockLocation", "This location does not exist", "pictures/art.png");
        Item mockItem = new Item("mockItem", "This is a description", "Behave please.",
                2, Condition.GOOD, true, location.getID(), "pictures/art.png");
        Item mockItem2 = new Item("mockItem nr 2", "This is a description", "Behave please.",
                2, Condition.GOOD, true, location.getID(), "pictures/art.png");

        Team tempTeam = new Team("sexNollK");
        Team tempTeam2 = new Team("P.R.NollK");

        ReservationHandler mockReservationHandler = new ReservationHandler();
        mockReservationHandler.createReservation(tempTeam, new Interval(new DateTime(2019, 9, 12, 17, 30), new DateTime(2019, 11, 16, 20, 0)), mockItem);

        Organisation informationsteknik = new Organisation("Informationsteknik");
        Organisation data = new Organisation("Data");


        informationsteknik.createUser("Albert");
        informationsteknik.createUser("admin");
        informationsteknik.createUser("eke");
        informationsteknik.createUser("kvick");
        informationsteknik.createUser("sponken");
        informationsteknik.createUser("giff");
        informationsteknik.createUser("steget");

        tempTeam.setTermsAndConditions("För att låna våra prylar måste prylen vara i samma skick som den var när den lånades ut. Behövs den diskas så diska den osv. Prylen ska också vara tillbaka på samma plats igen");
        tempTeam2.setTermsAndConditions("text 2");

        informationsteknik.addTeam(tempTeam);
        informationsteknik.addTeam(tempTeam2);

        tempTeam.addMember(informationsteknik.getUsers().get(0).getID());
        tempTeam.addMember(informationsteknik.getUsers().get(1).getID());
        tempTeam2.addMember(informationsteknik.getUsers().get(0).getID());
        tempTeam2.addMember(informationsteknik.getUsers().get(1).getID());


        Interval interval1 = new Interval(new DateTime(2019, 9, 10, 12, 40), new DateTime(2019, 9, 10, 15, 0));
        Interval interval2 = new Interval(new DateTime(2019, 9, 12, 17, 30), new DateTime(2019, 10, 16, 20, 0));
        IReservation res = new Reservation(informationsteknik.getTeams().get(0), interval1, mockItem, ReservationStatus.APPROVED);
        IReservation res2 = new Reservation(informationsteknik.getTeams().get(0), interval2, mockItem, ReservationStatus.APPROVED);
        ReservationHandler resHandler = informationsteknik.getReservationHandler();
        List<IReservation> reservations = resHandler.getReservations();
        reservations.add(res);
        reservations.add(res2);

        organisations.add(informationsteknik);
        organisations.add(data);
        setCurrentOrganisation(informationsteknik);

        informationsteknik.addItem(mockItem, tempTeam);
        informationsteknik.addItem(mockItem2, tempTeam);
        System.out.println("Mock Data added.");
    }

    public static void setCurrentUser(User currentUser) {
        StoreIT.currentUser = currentUser;
    }

    public static void setCurrentOrganisation(Organisation currentOrganisation) {
        StoreIT.currentOrganisation = currentOrganisation;
    }

    public static List<Organisation> getOrganisations() {
        return organisations;
    }

    public static Organisation getCurrentOrganisation() {
        return currentOrganisation;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    /**
     * Searches through the organisations and tries to find one with the input String
     *
     * @param organisationName Name to search after
     * @return Organisation with @param name
     * @throws NoSuchElementException If no such organisation could be found
     */
    public static Organisation findOrganisation(String organisationName) throws NoSuchElementException {
        for (Organisation org : organisations) {
            if (org.getName().equals(organisationName)) {
                return org;
            }
        }
        throw new NoSuchElementException("Organisation cannot be found");
    }
}
