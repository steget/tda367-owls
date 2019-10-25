package storeit.model;

/**
 * Location tells us information about a place.
 * Description contains information about the location, such as how to get to the location and other information.
 *
 * @author Jonathan Eksberg, Carl Lindh
 */

public class Location {

    private static int nextID;
    private final int ID;
    private String name;
    private String description;

    public Location(String name, String description) {
        this.name = name;
        this.description = description;
        ID = nextID;
        nextID++;
    }

    public static void setNextID(int nextID) {
        Location.nextID = nextID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getID() {
        return ID;
    }
}
