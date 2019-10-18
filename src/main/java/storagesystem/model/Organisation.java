package storagesystem.model;

import javafx.scene.image.Image;
import storagesystem.services.GSONHandler;

import java.awt.image.ImagingOpException;
import java.io.IOException;
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
    private transient final List<Item> items = new ArrayList<>();
    private final List<Team> teams = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final List<Location> locations = new ArrayList<>();
    private String name;
    private ReservationHandler reservationHandler;
    private String imageUrl;
    //todo reservationHandler

    public Organisation(String name) throws IOException {
        this.name = name;
        this.reservationHandler = new ReservationHandler();
        List<Location> locationList = GSONHandler.getListFromJson(Location.class);
        locations.addAll(locationList);
        List<Item> itemList = GSONHandler.getListFromJson(Item.class);
        items.addAll(itemList);
        List<Team> teamList = GSONHandler.getListFromJson(Team.class);
        teams.addAll(teamList);
        List<User> userList = GSONHandler.getListFromJson(User.class);
        users.addAll(userList);

    }

    private Organisation(Organisation organisationToCopy) throws IOException {
        this.name = organisationToCopy.name;
        this.teams.addAll(organisationToCopy.getTeams());
        this.users.addAll(organisationToCopy.getUsers());
        List<Location> locationList = GSONHandler.getListFromJson(Location.class);
        locations.addAll(locationList);
        List<Item> itemList = GSONHandler.getListFromJson(Item.class);
        items.addAll(itemList);
        List<Team> teamList = GSONHandler.getListFromJson(Team.class);
        teams.addAll(teamList);
        List<User> userList = GSONHandler.getListFromJson(User.class);
        users.addAll(userList);
        reservationHandler = new ReservationHandler();
    }

    public ReservationHandler getReservationHandler() {
        return reservationHandler;
    }

    /**
     * @return List of all items.
     */
    public List<Item> getAllItems() {
        return items;
    }

    /**
     * Get a specific item.
     *
     * @param ID The ID of the item to get
     * @return the requested item if found
     * @throws NoSuchElementException if item ID not found
     */
    public Item getItem(int ID) throws NoSuchElementException {
        for(Item item : items) {
            if(item.getID() == ID) {
                System.out.println("Item found");
                return item;
            }
        }
        throw new NoSuchElementException("ItemID not found in list of items");
    }
    /**
     * Get a specific location.
     *
     * @param ID The ID of the location to get
     * @return the requested item if found
     * @throws NoSuchElementException if item ID not found
     */
    public Location getLocation(int ID) throws NoSuchElementException {
        for(Location location : locations) {
            if(location.getID() == ID) {
                System.out.println("Item found");
                return location;
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
     * @return A list of all the users
     */
    public List<User> getUsers() {
        return users;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public List<Location> getLocations() {
        return locations;
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
     * @param description        Some information the user provides about themselves
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
