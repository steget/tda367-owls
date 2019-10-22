package storagesystem.model;

import javafx.scene.image.Image;
import org.joda.time.DateTime;
import org.joda.time.Interval;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class StoreIT {
    private static List<Organisation> organisations = new ArrayList<>();
    private static User currentUser;
    private static Organisation currentOrganisation;
    private static Team currentTeam;


    /**
     * Loads all data into the program. Should be run at start.
     */
    public void initializeBackend() {
        mockData();
    }

    private static void mockData() {
        //Hardcoded stuff for testing
        Organisation informationsteknik = new Organisation("Informationsteknik");
        Organisation data = new Organisation("Data");

        Team tempTeam = new Team("sexNollK");
        Team tempTeam2 = new Team("P.R.NollK");

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

        Location MockLocation = new Location("Hubben", "This location does not exist");
        Location MockLocation2 = new Location("Garaget", "This location is unavailable");

        informationsteknik.getLocations().add(MockLocation);
        informationsteknik.getLocations().add(MockLocation2);
        IReservable mockItem = IReservableFactory.createReservableItem("mockItem", "This is a description", "Behave please.",
                2, Condition.GOOD, true, MockLocation, new Image("pictures/art.png"));
        IReservable mockItem2 = IReservableFactory.createReservableItem("mockItem nr 2", "This is a description", "Behave please.",
                2, Condition.GOOD, true, MockLocation2, new Image("pictures/art.png"));


        Interval interval1 = new Interval(new DateTime(2019, 9, 10, 12, 40), new DateTime(2019, 9, 10, 15, 0));
        Interval interval2 = new Interval(new DateTime(2019, 9, 12, 17, 30), new DateTime(2019, 10, 16, 20, 0));
        IReservation res = new Reservation(informationsteknik.getUsers().get(0), interval1, mockItem, ReservationStatus.APPROVED);
        IReservation res2 = new Reservation(informationsteknik.getUsers().get(0), interval2, mockItem2, ReservationStatus.APPROVED);
        ReservationHandler resHandler = informationsteknik.getReservationHandler();
        List<IReservation> reservations = resHandler.getReservations();
        reservations.add(res);
        reservations.add(res2);

        organisations.add(informationsteknik);
        organisations.add(data);
        setCurrentOrganisation(informationsteknik);

        tempTeam.addItemToInventory(mockItem);
        tempTeam.addItemToInventory(mockItem2);
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
    public static Team getCurrentTeam() {
        return currentTeam;
    }

    public static void setCurrentTeam(Team currentTeam) {
        StoreIT.currentTeam = currentTeam;
    }

    /**
     * Searches through the organisations and tries to find one with the input String
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
