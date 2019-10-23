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

    public static IReservable createReservableItem(String name, String description, String userRequirements, int amount, Condition condition, boolean reservable, int locationID, String imageUrl) {
        return new Item(name, description, userRequirements, amount, condition, reservable, locationID);
    }
}
