package storagesystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An Organisation holds a collection of teams with the purpose being that the teams can communicate with each other.
 * The teams that belong to an organisation should be relevant to one another.
 *
 * @author Hugo Stegrell, Pär Aronsson, Carl Lindh, William Albertson, Jonathan Eksberg
 */
public class Organisation {
    private final List<IReservable> items = new ArrayList<>();
    private final List<Team> teams = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Location> locations = new ArrayList<>();
    private final ReservationHandler reservationHandler;
    private String name;
    private String imageUrl;


    public Organisation(String name) {
        this.name = name;
        this.reservationHandler = new ReservationHandler();
    }

    public ReservationHandler getReservationHandler() {
        return reservationHandler;
    }

    /**
     * @return List of all items.
     */
    public List<IReservable> getAllItems() {
        return items;
    }

    /**
     * @return A list of all teams
     */
    public List<Team> getTeams() {
        return teams;
    }

    /**
     * @return A list of all users
     */
    public List<User> getUsers() {
        return users;
    }

    /**
     * @return A list of all locations
     */
    public List<Location> getLocations() {
        return locations;
    }

    /**
     * @return A list of all reservations
     */
    public List<IReservation> getAllReservations() {
        return reservationHandler.getAllReservations();
    }

    /**
     * Gets a team's reservations
     *
     * @param team Team to get reservations from
     * @return A list of all reservations belonging to the selected team
     */
    List<IReservation> getTeamsReservations(Team team) {
        return reservationHandler.getTeamsReservations(team);
    }

    /**
     * Adds an item to the organisation and the item's ID to a team's inventory.
     *
     * @param iReservable item to add
     * @param team team to add itemID to
     */
    public void addItem(IReservable iReservable, Team team) {
        items.add(iReservable);
        team.addItemIDToInventory(iReservable.getID());
    }

    /**
     * Get a specific item.
     *
     * @param ID The ID of the item to get
     * @return the requested item if found
     * @throws NoSuchElementException if item ID not found
     */
    public IReservable getItem(int ID) throws NoSuchElementException {
        for (IReservable i :
                items) {
            if (i.getID() == ID) {
                return i;
            }
        }
        throw new NoSuchElementException("ItemID not found in list of items");
    }

    /**
     * Checks if the selected user is a part of any team.
     *
     * @param user user to check if part of a team
     * @return true if a user is part of a team.
     */
    public boolean isUserPartOfTeam(User user) {

        return StoreIT.getCurrentOrganisation().getUsersTeams(user).size() > 0;
    }


    /**
     * Get a specific location.
     *
     * @param ID The ID of the location to get
     * @return the requested item if found
     * @throws NoSuchElementException if item ID not found
     */
    public Location getLocation(int ID) throws NoSuchElementException {
        for (Location location : locations) {
            if (location.getID() == ID) {
                return location;
            }
        }
        throw new NoSuchElementException("Location not found in list of items");
    }

    /**
     * Use this to find out which teams one specific User is part of.
     * Can be used for example if the user wants to switch which team it is currently doing an action for.
     *
     * @param user user with teams
     * @return List of teams that the sent in user is a part of
     */
    public List<Team> getUsersTeams(User user) {
        List<Team> usersTeams = new ArrayList<>();
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
     * Gets a specific team's Items through checking it's itemIDs.
     *
     * @param team team with items
     * @return a List of Items
     */

    public List<IReservable> getTeamsItems(Team team) {
        List<IReservable> teamsItems = new ArrayList<>();
        for (IReservable item : items) {
            if (team.getAllItemIDs().contains(item.getID())) {
                teamsItems.add(item);
            }
        }
        return teamsItems;
    }

    /**
     * Gets a team with the selected teamName
     *
     * @param teamName name of a team
     * @return a Team with name equal to teamName
     * @throws NoSuchElementException if no Team exists with the name teamName
     */
    public Team getTeamFromName(String teamName) throws NoSuchElementException {
        for (Team t : teams) {
            if (t.getName().equals(teamName)) {
                return t;
            }
        }
        throw new NoSuchElementException("No team with that name");
    }

    /**
     * Gets a team with the selected teamID
     *
     * @param teamID ID of a team
     * @return a Team with ID equal to teamID
     * @throws NoSuchElementException if no Team exists with the ID teamID
     */
    public Team getTeamFromID(int teamID) throws NoSuchElementException {
        for (Team t : teams) {
            if (t.getID() == teamID) {
                return t;
            }
        }
        throw new NoSuchElementException("No team with that ID");
    }

    /**
     * Gets a team which has the selected item
     *
     * @param item item that belongs to a team
     * @return a Team with an itemID equal to the selected item's ID
     * @throws NoSuchElementException if no Team exists with the item
     */
    public Team getItemOwner(IReservable item) {
        for (Team t :
                teams) {
            if (t.getAllItemIDs().contains(item.getID())) {
                return t;
            }
        }
        throw new NoSuchElementException("Item owner could not be found");
    }

    /**
     * Add a team to the organisation's list of teams
     *
     * @param teamToBeAdded team which belongs in the organisation
     */
    public void addTeam(Team teamToBeAdded) {
        teams.add(teamToBeAdded);
    }

    /**
     * Creates a new user with only a name
     *
     * @param name name of user
     */
    public void createUser(String name) {
        users.add(new User(name));
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

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
