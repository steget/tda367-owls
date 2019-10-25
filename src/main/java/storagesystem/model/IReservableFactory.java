package storagesystem.model;


/**
 * Factory for creating reservable things. Returns objects of the Interface
 *
 * @author Hugo Stegrell
 * @see IReservable
 */
public final class IReservableFactory {
    private IReservableFactory() {
    }

    /**
     * Creates an item.
     *
     * @param name             name of item
     * @param description      description of item
     * @param userRequirements user requirements of item
     * @param amount           amount of items
     * @param condition        condition of item
     * @param reservable       item's current reservability
     * @param locationID       item's location's ID
     */

    public static IReservable createReservableItem(String name, String description, String userRequirements, int amount, Condition condition, boolean reservable, int locationID) {
        return new Item(name, description, userRequirements, amount, condition, reservable, locationID);
    }
}
