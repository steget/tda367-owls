package storagesystem.model;

import java.util.Objects;


/**
 * A class that represents a physical item, that can be reserved.
 *
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

    /**
     * Creates an item. Should only be used by IReservableFactory.
     *
     * @param name             name of item
     * @param description      description of item
     * @param userRequirements user requirements of item
     * @param amount           amount of items
     * @param condition        condition of item
     * @param reservable       item's current reservability
     * @param locationID       item's location's ID
     * @see IReservableFactory
     */

    Item(String name, String description, String userRequirements, int amount, Condition condition, boolean reservable, int locationID) {
        this.name = name;
        this.description = description;
        this.userRequirements = userRequirements;
        this.ID = nextID;
        nextID++;
        this.amount = amount;
        this.condition = condition;
        this.reservable = reservable;
        this.locationID = locationID;
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

    public void setLocationID(int locationID) {
        this.locationID = locationID;
    }

    public static void setNextID(int nextID) {
        Item.nextID = nextID;
    }

    /**
     * Checks if o has the same ID as item
     *
     * @param o An object which to test against
     * @return true if the Object o's ID is the same as the item's ID
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return ID == item.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description, userRequirements, ID, amount, condition, reservable, locationID);
    }
}
