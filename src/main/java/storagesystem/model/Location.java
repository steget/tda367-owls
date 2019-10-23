package storagesystem.model;
/**
 * Location tells us information about a place.
 * Description contains information about the location, such as how to get to the location and other information.
 * @author Jonathan Eksberg, Carl Lindh
 */

public class Location {

    private String name;
    private String description;
    private final int ID;
    private static int nextID;

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
