package storeit.model;

/**
 * Location is used to represent a location where things can be stored.
 *
 * @author Jonathan Eksberg, Carl Lindh
 */

public class Location {
    private final int ID;
    private static int nextID;
    private String name;
    private String description;

    /**
     * Creates a Location, sets its ID to the nextID, then increments nextID
     *
     * @param name name of the Location
     * @param description description of the Location
     */

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        ID = nextID;
        nextID++;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getID() {
        return ID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static void setNextID(int nextID) {
        Location.nextID = nextID;
    }
}
