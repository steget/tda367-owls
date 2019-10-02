package storagesystem.model;

import java.util.ArrayList;
import java.util.List;


/**
 * An Organisation holds a collection of teams with the purpose being that the teams can communicate with each other.
 * The teams that belong to an organisation should be relevant to one another.
 * An Organisation should keep track of all the reservations between its teams.
 */
public class Organisation {
    private final List<Team> teams = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private String name;
    //todo reservationHandler

    public Organisation(String name) {
        this.name = name;
        //fill users from db
        //fill teams from db
    }

    //deep copy
    private Organisation(Organisation organisationToCopy) {
        this.name = organisationToCopy.name;
        this.teams.addAll(organisationToCopy.getTeams());
        this.users.addAll(organisationToCopy.getUsers());
        //todo reservationHandler = organisationToCopy.reservationHandlerDeepCopy
    }

    /**
     * Exist to show a user all the items available to borrow.
     *
     * @return List of all the items from all the teams.
     */
    List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<Item>();
        for (Team t :
                teams) {
            allItems.addAll(t.getAllItems());
        }
        return allItems;
    }

    /**
     * The point of this method exist to show specific items for teams/users.
     *
     * @param ID every item has its own ID connected to it.
     * @return the requested item if found
     * @throws Exception if item ID not found
     */
    private Item getItem(int ID) throws Exception {
        for (Team t :
                teams) {
            for (Item i :
                    t.getAllItems()) {
                //i.getID == ID
                if (true) {
                    System.out.println("Item found");
                    return i;
                }
            }
        }
        throw new Exception("ItemID not found in list of items");
    }

    /**
     * @param user
     * @return
     */
    public List<Team> getUserTeams(User user) {
        List<Team> userTeams = new ArrayList<Team>();
        for (Team t : teams) {
            for (int i : t.getAllMemberIDs()) {
                if (user.getID() == i) {
                    userTeams.add(t);
                }
            }
        }
        if (userTeams.isEmpty()) {
            System.out.println("User is not appart of any team");
            return null;
        }
        return userTeams;
    }

    /**
     * @return A deep copy of the teams
     */
    private List<Team> getTeams() {
        List<Team> deepCopyTeams = new ArrayList<>();
        for (Team team :
                teams) {
            deepCopyTeams.add(team.getDeepCopy());
        }
        return deepCopyTeams;
    }

    /**
     * @return A deep copy of all the users
     */
    public List<User> getUsers() {
        List<User> deepCopyUsers = new ArrayList<>();
        for (User user :
                users) {
            deepCopyUsers.add(user.getDeepCopy());
        }
        return deepCopyUsers;
    }

    /**
     * Creates a new user with only a name
     *
     * @param name
     */
    public void createUser(String name) {
        users.add(new User(name));
    }

    /**
     * Creates a new user with all the possible information
     *
     * @param name               Name of the User
     * @param description        Some information the user provides about themself
     * @param contactInformation Some sort of way to contact the User, preferably phone/mail
     */
    public void createUser(String name, String description, String contactInformation) {
        users.add(new User(name, description, contactInformation));
    }

    /**
     * Add an already existing team to the organisations list of teams
     *
     * @param teamToBeAdded
     */
    public void addTeam(Team teamToBeAdded) {
        teams.add(teamToBeAdded);
    }

    /**
     * @return A new instance of Organisation with the same attribute values as this
     */
    public Organisation getDeepCopy() {
        return new Organisation(this);
    }

    /**
     * @return A new instance of Organisation with the same attribute values as this
     */
    public Organisation getDeepCopy(Organisation organisationToCopy) {
        return new Organisation(organisationToCopy);
    }

    void getAllReservations() {

    }

    void getReservation() {

    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}