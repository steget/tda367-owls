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
    private static Team currentTeam;
    private static Organisation currentOrganisation;


    /**
     * Loads all data into the program. Should be run at start.
     */
    public void initializeBackend() throws IOException {
        reset();
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

    public static List<IReservation> getAllReservations(){
        return currentOrganisation.getAllReservations();
    }

    public static List<IReservation> getCurrentTeamIngoingReservations(){
        return currentOrganisation.getReservationHandler().getTeamsIngoingReservations(currentTeam);
    }

    public static List<IReservation> getCurrentTeamOutgoingReservations(){
        return currentOrganisation.getReservationHandler().getTeamsOutgoingReservations(currentTeam);
    }

    //TODO write tests for class

    public void reset() throws IOException { //Run if fresh start or after tests!!!
        JSONHandler.clearAllJsonFiles();
        mockData();
    }


    private void mockData() {
        //Hardcoded stuff for testing
        Organisation informationsteknik = new Organisation("Informationsteknik");
        Organisation data = new Organisation("Data");
        organisations.add(informationsteknik);
        organisations.add(data);
        setCurrentOrganisation(informationsteknik);



        Team team1 = new Team("Teknologer");
        Team team2 = new Team("Vänner");

        Team team3 = new Team("NollKIT");
        Team team4 = new Team("P.R.I.T.");
        Team team5 = new Team("sexIT");
        Team team6 = new Team("8-bIT");

        createUser("admin", "password", "admin", "see github");
        User admin = currentOrganisation.getUsers().get(0);
        createUser("Emil", "Emilia", "En go grabb", "emil@test.se");
        createUser("Emilia", "Emil", "En fin tös", "emilia@test.se");
        createUser("William", "1", "Ordförande 8-bIT, Kassör P.R.I.T.", "william@test.se");
        createUser("Hugo", "1", "Infochef", "hugo@test.se");
        createUser("Eke", "1", "Ordförande", "eke@test.se");

        team1.setTermsAndConditions("För att låna våra prylar måste prylen vara i samma skick som den var när den lånades ut. Behövs den diskas så diska den osv. Prylen ska också vara tillbaka på samma plats igen");
        team2.setTermsAndConditions("Var rimlig");
        team3.setTermsAndConditions("Var rimlig");
        team4.setTermsAndConditions("Var rimlig");
        team5.setTermsAndConditions("Var rimlig");
        team6.setTermsAndConditions("Var rimlig");

        informationsteknik.addTeam(team1);
        informationsteknik.addTeam(team2);
        informationsteknik.addTeam(team3);
        informationsteknik.addTeam(team4);
        informationsteknik.addTeam(team5);
        informationsteknik.addTeam(team6);

        team1.addMember(0);
        team1.addMember(1);
        team1.addMember(2);
        team2.addMember(admin.getID());
        team3.addMember(admin.getID());
        team3.addMember(4);
        team4.addMember(admin.getID());
        team4.addMember(3);
        team5.addMember(admin.getID());
        team5.addMember(5);
        team6.addMember(admin.getID());
        team6.addMember(3);

        Location mockLocation = new Location("Hubben", "Massa plats");
        Location mockLocation2 = new Location("Garaget", "Kaos, men får plats med stora saker");
        Location mockLocation3 = new Location("Basen", "Håll er borta");

        informationsteknik.getLocations().add(mockLocation);
        informationsteknik.getLocations().add(mockLocation2);
        informationsteknik.getLocations().add(mockLocation3);
        IReservable mockItem = IReservableFactory.createReservableItem("Gjutjärnspanna", "Tung och välanvänd, men väldigt bra", "Inget diskmedel!!",
                2, Condition.GREAT, true, mockLocation.getID());
        IReservable mockItem2 = IReservableFactory.createReservableItem("Bollhav", "Mer bollar än plats", "All bollar måste tillbaka",
                2, Condition.GOOD, true, mockLocation2.getID());
        IReservable mockItem3 = IReservableFactory.createReservableItem("Högtalare", "Han kallas Roffe, och är lite sönder", "Snälla förstör honom inte mer",
                2, Condition.BAD, true, mockLocation2.getID());

        informationsteknik.addItem(mockItem, team1);
        informationsteknik.addItem(mockItem2, team1);
        informationsteknik.addItem(mockItem3, team1);

        Interval interval1 = new Interval(new DateTime(), new DateTime().plusHours(1));
        Interval interval2 = new Interval(new DateTime(), new DateTime().plusHours(1));

        ReservationHandler resHandler = informationsteknik.getReservationHandler();
        resHandler.createReservation(team1.getID(), interval1, mockItem.getID());
        resHandler.createReservation(team2.getID(), interval2, mockItem2.getID());

    }

}
