package storagesystem.model;

import java.util.Objects;


/**
 * A class that represents an item. An item can be added by a team to their own inventory.
 * description consists of a short text about the item and what can be done with it.
 * userRequirements is the "terms and conditions" for a specific item.
 * Every item has an ID, to help identifying an item.
 * Amount tells us how many copies of an item there is.
 * Condition describes if an item is in bad, good or great condition.
 * Reservable tells us if an item is able to be borrowed or not.
 * Location has the information about where the item is located
 * @author Jonathan Eksberg, Carl Lindh
 */
public class Item implements IReservable {
    private static int nextID;
    private final int ID;
    private String name;
    private String description;
    private String userRequirements;
    private int amount;
    private Condition condition;
    private boolean reservable;
    private int locationID;
    private String imageUrl;

    public Item(String name, String description, String userRequirements, int amount, Condition condition, boolean reservable, int locationID, String imageUrl) {
        this.name = name;
        this.description = description;
        this.userRequirements = userRequirements;
        this.ID = nextID;
        nextID++;
        this.amount = amount;
        this.condition = condition;
        this.reservable = reservable;
        this.locationID = locationID;
        this.imageUrl = imageUrl;
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

    public int getLocationID() {
        return locationID;
    }

    public void setLocation(int locationID) {
        this.locationID = locationID;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static void setNextID(int nextID) {
        Item.nextID = nextID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return ID == item.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, userRequirements, ID, amount, condition, reservable, locationID, imageUrl);
    }
}
