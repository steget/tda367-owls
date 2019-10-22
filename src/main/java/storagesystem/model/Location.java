package storagesystem.model;

import javafx.scene.image.Image;

/**
 * Location tells us information about a place.
 * Description contains information about the location, such as how to get to the location and other information.
 * ImageUrl is the url to an image which shows the user a picture of the location.
 */

public class Location {

    private String name;
    private String description;
    private String imageUrl;
    private final int ID;
    private static int nextID;

    public Location(String name, String description, String imageUrl) {
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.ID = nextID;
        nextID++;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
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

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static void setNextID(int nextID) {
        Location.nextID = nextID;
    }


}
