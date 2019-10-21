package storagesystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An Organisation holds a collection of teams with the purpose being that the teams can communicate with each other.
 * The teams that belong to an organisation should be relevant to one another.
 * An Organisation should keep track of all the reservations between its teams.
 *
 * @author Hugo Stegrell, Pär Aronsson
 */
public class Organisation {
    private final List<Team> teams = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private String name;
    private ReservationHandler reservationHandler;

    public Organisation(String name) {
        this.name = name;
        this.reservationHandler = new ReservationHandler();
        //todo fill stuff from db
    }

    private Organisation(Organisation organisationToCopy) {
        this.name = organisationToCopy.name;
        this.teams.addAll(organisationToCopy.getTeams());
        this.users.addAll(organisationToCopy.getUsers());
        //todo load from db
        reservationHandler = new ReservationHandler();
    }

    public ReservationHandler getReservationHandler() {
        return reservationHandler;
    }

    /**
     * @return List of all the items from all the teams.
     */
    public List<IReservable> getAllItems() {
        List<IReservable> allItems = new ArrayList<IReservable>();
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
    IReservable getItem(int ID) throws NoSuchElementException {
        for (Team t :
                teams) {
            for (IReservable i :
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

    public List<Team> getTeams() {
        return teams;
    }

    /**
     * Add a team to the organisations list of teams
     *
     * @param teamToBeAdded team which belongs in the organisation
     */
    void addTeam(Team teamToBeAdded) {
        teams.add(teamToBeAdded);
    }

    /**
     * Add a user to the organisations list of users
     *
     * @param userToBeAdded user which belongs in the organisation
     */
    void addUser(User userToBeAdded) {
        users.add(userToBeAdded);
    }

    public String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }
}
