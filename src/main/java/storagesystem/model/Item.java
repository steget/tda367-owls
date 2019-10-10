package storagesystem.model;

import javafx.scene.image.Image;

public class Item implements IReservable {
/**
 * A class that represents an item. An item can be added by a team to their own inventory.
 * description consists of a short text about the item and what can be done with it.
 * userRequirements is the "terms and conditions" for a specific item.
 * Every item has an ID, to help identifying an item.
 * Amount tells us how many copies of an item there is.
 * Condition describes if an item is in bad, good or great condition.
 * Reservable tells us if an item is able to be borrowed or not.
 * Location has the information about where the item is located
 */

    private String name;
    private String description;
    private String userRequirements;
    private int ID;
    private int amount;
    private Condition condition;
    private boolean reservable;
    private Location location;
    private Image image;

    public Item(String name, String description, String userRequirements, int ID, int amount, Condition condition, boolean reservable, Location location, Image image) {
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

    public Item(){
        this.name = "Shoes";
        this.description = "Wear them";
        this.userRequirements = "Dont break them";
        this.ID = 1;
        this.amount = 2;
        this.condition = Condition.BAD;
        this.reservable = true;
        this.location = new Location();
        this.image = new Location().getImage();
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

    public void setID(int ID) { this.ID = ID; }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
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


    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }

        Item i = (Item) o;

        return i.getID() == this.getID();
    }
}
