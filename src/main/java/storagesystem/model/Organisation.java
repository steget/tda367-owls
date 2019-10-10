package storagesystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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
        //todo fill stuff from db
    }

    /**
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
     * Get a specific item.
     *
     * @param ID The ID of the item to get
     * @return the requested item if found
     * @throws NoSuchElementException if item ID not found
     */
    public Item getItem(int ID) throws NoSuchElementException {
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
        throw new NoSuchElementException("ItemID not found in list of items");
    }

    /**
     * Use this to find out which teams one specific User is part of.
     * Can be used for example if the user wants to switch which team it is currently doing an action for.
     *
     * @param user
     * @return List of teams that the sent in user is a part of
     */
    public List<Team> getUsersTeams(User user) {
        List<Team> usersTeams = new ArrayList<Team>();
        for (Team t : teams) {
            for (int memberID : t.getAllMemberIDs()) {
                if (user.getID() == memberID) {
                    usersTeams.add(t);
                }
            }
        }
        return usersTeams;
    }

    /**
     * @return A list of all the users
     */
    public List<User> getUsers() {
        return users;
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

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

}
