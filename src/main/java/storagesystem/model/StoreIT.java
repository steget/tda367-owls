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

    /**
     * Compares the username and password and then returns if there is a user in the currentOrganisation with those credentials
     *
     * @param username Name to find in the currently selected Organisation
     * @param password Password supposed to match with the username
     * @return {@code True} if the username and password matches, {@code False} if there is no match
     */
    public static boolean doesLoginMatch(String username, String password) {
        for (User user :
                currentOrganisation.getUsers()) {
            if (user.getName().toLowerCase().equals(username.toLowerCase()) && user.getPassword().equals(password)) {
                currentUser = user;
                return true;
            }
        }
        return false;
    }

    /**
     * Checks in the selected organisation if there is an user with the name currently written in the Username textfield
     *
     * @return If the written username exists within the selected Organisation
     */
    public static boolean doesUserExist(Organisation organisationToSearch, String name) {
        for (User user :
                organisationToSearch.getUsers()) {
            if (user.getName().toLowerCase().equals(name.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    public static List<Organisation> getOrganisations() {
        return organisations;
    }


    public static Organisation getCurrentOrganisation() {
        return currentOrganisation;
    }

    public static void setCurrentOrganisation(Organisation currentOrganisation) {
        StoreIT.currentOrganisation = currentOrganisation;
    }

    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(String username) {
        for (User u :
                currentOrganisation.getUsers()) {
            if (u.getName().equals(username)) {
                currentUser = u;
                break;
            }
        }
    }

    public static void setCurrentUser(User currentUser) {
        StoreIT.currentUser = currentUser;
    }

    public static Team getCurrentTeam() {
        return currentTeam;
    }

    public static void setCurrentTeam(Team currentTeam) {
        StoreIT.currentTeam = currentTeam;
    }

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

        createUser("Albert", "1", "1", "1");
        createUser("admin", "1", "1", "1");
        createUser("eke", "1", "1", "1");
        createUser("kvick", "1", "1", "1");
        createUser("sponken", "1", "1", "1");
        createUser("giff", "1", "1", "1");
        createUser("steget", "1", "1", "1");

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

        tempTeam.addItemToInventory(mockItem);
        tempTeam.addItemToInventory(mockItem2);
    }
}
