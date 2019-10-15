package storagesystem.model;

import javafx.scene.image.Image;

/**
 * Location tells us information about a place.
 * Description contains information about the location, such as how to get to the location and other information.
 * Image shows the user a picture of the location.
 */

public class Location implements IHasImageAndName{

    private String name;
    private String description;
    private Image image;

    public Location(String name, String description, Image image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Location(String name, String description){
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Image getImage() {
        return image;
    }

    public void setName(String name) { this.name = name; }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
