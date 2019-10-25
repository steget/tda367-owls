package storagesystem.model;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import storagesystem.services.IDHandler;
import storagesystem.services.JSONHandler;

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


    //Change this if you want to reset the database.
    private boolean reset = false;


    /**
     * Loads all data into the program. Should be run at start.
     */
    public void initializeBackend() throws IOException {
        if (reset) {
            resetWithMockData();
        }

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

    public static List<IReservation> getCurrentTeamIngoingReservations() {
        return currentOrganisation.getReservationHandler().getTeamsIngoingReservations(currentTeam);
    }

    public static List<IReservation> getCurrentTeamOutgoingReservations() {
        return currentOrganisation.getReservationHandler().getTeamsOutgoingReservations(currentTeam);
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


        Team nollkit = new Team("NollKIT");
        Team prit = new Team("P.R.I.T.");
        Team sexit = new Team("sexIT");
        Team eightbit = new Team("8-bIT");

        createUser("admin", "password", "admin", "see github");
        User admin = currentOrganisation.getUsers().get(0);

        createUser("William", "1", "Ordförande 8-bIT, Kassör P.R.I.T.", "william@test.se");
        createUser("Hugo", "1", "Infochef", "hugo@test.se");
        createUser("Jonathan", "1", "Ordförande", "eke@test.se");


        nollkit.setTermsAndConditions("Var rimlig");
        prit.setTermsAndConditions("Var rimlig");
        sexit.setTermsAndConditions("Var rimlig");
        eightbit.setTermsAndConditions("Var rimlig");


        informationsteknik.addTeam(nollkit);
        informationsteknik.addTeam(prit);
        informationsteknik.addTeam(sexit);
        informationsteknik.addTeam(eightbit);

        nollkit.addMember(admin.getID());
        nollkit.addMember(2);
        prit.addMember(admin.getID());
        prit.addMember(1);
        sexit.addMember(admin.getID());
        sexit.addMember(3);
        eightbit.addMember(admin.getID());
        eightbit.addMember(1);

        Location hubben = new Location("Hubben", "Massa plats");
        Location garage = new Location("Garaget", "Kaos, men får plats med stora saker");
        Location hasen = new Location("HASen", "Håll er borta");

        informationsteknik.getLocations().add(hubben);
        informationsteknik.getLocations().add(garage);
        informationsteknik.getLocations().add(hasen);
        IReservable hammer = IReservableFactory.createReservableItem("Hammare", "En stor hammare", "Slå inte dina vänner!", 1, Condition.GREAT, true, hasen.getID());
        IReservable ballPool = IReservableFactory.createReservableItem("Bollhav", "Väldigt många bollar", "Ge tillbaka alla", 1, Condition.GOOD, true, hubben.getID());
        IReservable fabric = IReservableFactory.createReservableItem("Tyger", "Olika skick och färg", "Lämna tillbaka i samma skick", 5, Condition.BAD, true, hasen.getID());
        IReservable nintendo = IReservableFactory.createReservableItem("Nintendo switch", "Switch med tillhörande spel", "Var försiktig!!", 1, Condition.GOOD, true, hubben.getID());


        informationsteknik.addItem(hammer, prit);
        informationsteknik.addItem(ballPool, nollkit);
        informationsteknik.addItem(fabric, sexit);
        informationsteknik.addItem(nintendo, eightbit);
    }

}
