package storagesystem.model;

import javafx.scene.image.Image;

/**
 * Factory for creating reservable things. Returns objects of the Interface
 * @see IReservable
 */
public class IReservableFactory {

    public static IReservable createReservableItem(String name, String description, String userRequirements, int amount, Condition condition, boolean reservable, Location location, Image image) {
        return new Item(name, description, userRequirements, amount, condition, reservable, location, image);
    }
}
