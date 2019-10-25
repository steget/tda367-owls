package storagesystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * An Organisation holds a collection of teams with the purpose being that the teams can communicate with each other.
 * The teams that belong to an organisation should be relevant to one another.
 *
 * @author Hugo Stegrell, Pär Aronsson
 */
public class Organisation {
    private String name;
    private String imageUrl;
    private final List<IReservable> items = new ArrayList<>();
    private final List<Team> teams = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Location> locations = new ArrayList<>();

    private final ReservationHandler reservationHandler;

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
     * Adds an item to the organisation and a team's inventory.
     *
     * @param iReservable
     * @param team
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
    IReservable getItem(int ID) throws NoSuchElementException {
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
     * @param user
     * @return true if a user is part of a team.
     */
    public boolean isUserPartOfTeam(User user){

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
     * @param user
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
     * @param team
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
     * @return A list of all the users
     */
    public List<User> getUsers() {
        return users;
    }

    public List<Team> getTeams() {
        return teams;
    }


    public Team getTeamFromName(String teamName) throws NoSuchElementException{
        for(Team t : teams){
            if (t.getName().equals(teamName)){
                return t;
            }
        }
        throw new NoSuchElementException("No team with that name");
    }

    /**
     * Creates a new user with only a name
     *
     * @param name
     */
    public void createUser(String name) {
        users.add(new User(name));
    }

    public List<Location> getLocations() {
        return locations;
    }

    /**
     * Add a team to the organisations list of teams
     *
     * @param teamToBeAdded team which belongs in the organisation
     */
    public void addTeam(Team teamToBeAdded) {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Team getItemOwner(IReservable item) {
        for (Team t :
                teams) {
            if (t.getAllItemIDs().contains(item.getID())) {
                return t;
            }
        }
        throw new NoSuchElementException("Item owner could not be found");
    }

    public List<IReservation> getAllReservations() {
        return reservationHandler.getReservations();
    }

    List<IReservation> getTeamsReservations(Team team) {
        return reservationHandler.getTeamsReservations(team);
    }
}
