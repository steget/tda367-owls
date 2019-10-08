package storagesystem.model;

import storagesystem.StorageSystem;

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
    Item getItem(int ID) throws Exception {
        for (Team t :
                teams) {
            for (Item i :
                    t.getAllItems()) {
                if (i.getID() == ID) {
                    System.out.println("Item found");
                    return i;
                }
            }
        }
        throw new Exception("ItemID not found in list of items");
    }

    /**
     * Use this to find out which teams one specific User is part of.
     * Can be used for example if the user wants to switch which team it is currently doing an action for.
     *
     * @param user
     * @return List of teams that the sent in user is a part of
     */
    public List<Team> getUsersTeams(User user) {
        List<Team> userTeams = new ArrayList<Team>();
        for (Team t : this.getTeams()) {
            for (int memberID : t.getAllMemberIDs()) {
                if (user.getID() == memberID) {
                    userTeams.add(t);
                }
            }
        }
        if (userTeams.isEmpty()) {
            //todo remove? don't think we need to do anything if this happens
            System.out.println("User is not apart of any team");
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


    public void saveUser(User user){
        this.getUsers().get(user.getID()).set(user);
    }

    /**
     * this method saves the altered team into the organisations list of team.
     * If a team has been altered then this method updates the organisation
     * @param team to be saved in organisation
     * @return true if successfull
     */
    public boolean saveTeam(Team team){
        for(Team t: this.getTeams()){
          if(t.getTeamID() == team.getTeamID()){
              t.setTermsAndConditions(team.getTermsAndConditions());
              t.setName(team.getName());
                return true;
          }

        }
        return false;
    }

}
