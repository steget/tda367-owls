package storagesystem.model;

import javafx.scene.image.Image;

public class Item {


    private String name;
    private String description;
    private String userRequirements;
    private int ID;
    private int amount;
    private int condition;
    private boolean reservable;
    private Location location;
    private Image image;

    Item(String name, String description, String userRequirements, int ID, int amount, int condition, boolean reservable, Location location, Image image) {
        this.name = name;
        this.description = description;
        this.userRequirements = userRequirements;
        this.ID = ID;
        this.amount = amount;
        this.condition = condition;
        this.reservable = reservable;
        this.location = location;
        this.image = image;
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

    public String getUserRequirements() {
        return userRequirements;
    }

    public void setUserRequirements(String userRequirements) {
        this.userRequirements = userRequirements;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getCondition() {
        return condition;
    }

    public void setCondition(int condition) {
        this.condition = condition;
    }

    public boolean isReservable() {
        return reservable;
    }

    public void setReservable(boolean reservable) {
        this.reservable = reservable;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
