package storagesystem.model;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import storagesystem.services.JSONHandler;
import storagesystem.services.IDHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * StoreIT has an overview of the whole application by containing static variables and methods that corresponds to the
 * current user, team and organisation. Also contains a list of all available organisations in the application.
 *
 * @author Hugo Stegrell, Pär Aronsson, Carl Lindh, William Albertson, Jonathan Eksberg
 */

public class StoreIT {
    private static List<Organisation> organisations = new ArrayList<>();
    private static User currentUser;
    private static Team currentTeam;
    private static Organisation currentOrganisation;


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
     * @return {@code true} if the username and password matches, {@code false} if there is no match
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

    public static List<IReservation> getAllReservations() {
        return currentOrganisation.getAllReservations();
    }

    public static List<IReservation> getCurrentTeamsIncomingReservations() {
        return currentOrganisation.getTeamsReservations(currentTeam);
    }

    //TODO write tests for class

    /**
     * Loads all data into the program. Should be run at start.
     */
    public void initializeBackend() throws IOException {
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

    /**
     * Resets ALL data in the Json files and initializes mockData. Should be run first in initializeBackend if
     * there is a need to reset the whole database with mockData.
     *
     * @throws IOException if Json-file is not found
     */
    public void resetWithMockData() throws IOException { //Run if fresh start or after tests!!!
        JSONHandler.clearAllJsonFiles();
        mockData();
    }

    /**
     * Creates hard-coded data in StoreIT. Should only be run through resetWithMockData.
     */
    private void mockData() {
        //Hardcoded stuff for testing
        Organisation informationsteknik = new Organisation("Informationsteknik");
        Organisation data = new Organisation("Data");
        organisations.add(informationsteknik);
        organisations.add(data);
        setCurrentOrganisation(informationsteknik);


        Team tempTeam = new Team("sexNollK");
        Team tempTeam2 = new Team("P.R.NollK");

        createUser("Albert", "1", "1", "1");
        createUser("admin", "1", "1", "1");
        createUser("eke", "1", "1", "1");
        createUser("kvick", "1", "1", "1");
        createUser("sponken", "1", "1", "1");
        createUser("giff", "1", "1", "1");
        createUser("steget", "1", "1", "1");
        tempTeam.setTermsAndConditions("För att låna våra prylar måste prylen vara i samma skick som den var när den lånades ut. Behövs den diskas så diska den osv. Prylen ska också vara tillbaka på samma plats igen");
        tempTeam2.setTermsAndConditions("text 2");

        informationsteknik.addTeam(tempTeam);
        informationsteknik.addTeam(tempTeam2);

        tempTeam.addMember(informationsteknik.getUsers().get(0).getID());
        tempTeam.addMember(informationsteknik.getUsers().get(1).getID());
        tempTeam2.addMember(informationsteknik.getUsers().get(0).getID());
        tempTeam2.addMember(informationsteknik.getUsers().get(1).getID());

        Location mockLocation = new Location("Hubben", "This location does not exist");
        Location mockLocation2 = new Location("Garaget", "This location is unavailable");
        Location mockLocation3 = new Location("Maskinhuset", "This location is unavailable");

        informationsteknik.getLocations().add(mockLocation);
        informationsteknik.getLocations().add(mockLocation2);
        informationsteknik.getLocations().add(mockLocation3);
        IReservable mockItem = IReservableFactory.createReservableItem("mockItem", "This is a description", "Behave please.",
                2, Condition.GOOD, true, mockLocation.getID());
        IReservable mockItem2 = IReservableFactory.createReservableItem("mockItem nr 2", "This is a description", "Behave please.",
                2, Condition.GOOD, true, mockLocation2.getID());
        IReservable mockItem3 = IReservableFactory.createReservableItem("mockItem nr 3", "This is a description", "Behave please.",
                2, Condition.GOOD, true, mockLocation3.getID());

        Interval interval1 = new Interval(new DateTime(2019, 9, 10, 12, 40), new DateTime(2019, 9, 10, 15, 0));
        Interval interval2 = new Interval(new DateTime(2019, 9, 12, 17, 30), new DateTime(2019, 10, 16, 20, 0));
        IReservation res = new Reservation(tempTeam.getID(), interval1, mockItem.getID(), ReservationStatus.APPROVED);
        IReservation res2 = new Reservation(tempTeam.getID(), interval2, mockItem2.getID(), ReservationStatus.APPROVED);
        ReservationHandler resHandler = informationsteknik.getReservationHandler();
        List<IReservation> reservations = resHandler.getAllReservations();
        reservations.add(res);
        reservations.add(res2);

        informationsteknik.addItem(mockItem, tempTeam);
        informationsteknik.addItem(mockItem2, tempTeam);
        informationsteknik.addItem(mockItem3, tempTeam2);
    }

}
