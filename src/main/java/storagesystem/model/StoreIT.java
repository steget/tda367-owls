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

    /**
     * Loads all data into the program. Should be run at start.
     */
    public void initializeBackend() {
        mockData();
    }

    private void mockData() {
        //Hardcoded stuff for testing
        Organisation informationsteknik = new Organisation("Informationsteknik");
        Organisation data = new Organisation("Data");
        organisations.add(informationsteknik);
        organisations.add(data);
        setCurrentOrganisation(informationsteknik);

        createUser("Albert");
        createUser("admin");
        createUser("eke");
        createUser("kvick");
        createUser("sponken");
        createUser("giff");
        createUser("steget");

        Team tempTeam = new Team("sexNollK");
        Team tempTeam2 = new Team("P.R.NollK");

        tempTeam.setTermsAndConditions("För att låna våra prylar måste prylen vara i samma skick som den var när den lånades ut. Behövs den diskas så diska den osv. Prylen ska också vara tillbaka på samma plats igen");
        tempTeam2.setTermsAndConditions("text 2");

        informationsteknik.addTeam(tempTeam);
        informationsteknik.addTeam(tempTeam2);

        tempTeam.addMember(informationsteknik.getUsers().get(0).getID());
        tempTeam.addMember(informationsteknik.getUsers().get(1).getID());
        tempTeam2.addMember(informationsteknik.getUsers().get(0).getID());
        tempTeam2.addMember(informationsteknik.getUsers().get(1).getID());

        Location location = new Location("MockLocation", "This location does not exist", new Image("pictures/creepy.jpg"));
        IReservable mockItem = IReservableFactory.createReservableItem("mockItem", "This is a description", "Behave please.",
                2, Condition.GOOD, true, location, location.getImage());
        IReservable mockItem2 = IReservableFactory.createReservableItem("mockItem nr 2", "This is a description", "Behave please.",
                2, Condition.GOOD, true, location, new Image("pictures/art.png"));


        Interval interval1 = new Interval(new DateTime(2019, 9, 10, 12, 40), new DateTime(2019, 9, 10, 15, 0));
        Interval interval2 = new Interval(new DateTime(2019, 9, 12, 17, 30), new DateTime(2019, 10, 16, 20, 0));
        IReservation res = new Reservation(informationsteknik.getUsers().get(0), interval1, mockItem, ReservationStatus.APPROVED);
        IReservation res2 = new Reservation(informationsteknik.getUsers().get(0), interval2, mockItem2, ReservationStatus.APPROVED);
        ReservationHandler resHandler = informationsteknik.getReservationHandler();
        List<IReservation> reservations = resHandler.getReservations();
        reservations.add(res);
        reservations.add(res2);

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

    /**
     * Creates a new user with only a name
     *
     * @param name Name of the User
     */
    public static void createUser(String name) {
        currentOrganisation.addUser(new User(name));
    }

    /**
     * Creates a new user with all the possible information
     *
     * @param name               Name of the User
     * @param password           Password for login
     * @param description        Some information the user provides about themself
     * @param contactInformation Some sort of way to contact the User, preferably phone/mail
     */
    public static void createUser(String name, String password, String description, String contactInformation) {
        currentOrganisation.addUser(new User(name, password, description, contactInformation));
    }
}
