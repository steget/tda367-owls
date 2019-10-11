package storagesystem.model;

import javafx.scene.image.Image;

/**
 * Interface to use for classes that represent something that can be borrowed.
 * @author William Albertsson
 */

public interface IReservable {
    /**
     *
     * @return An unique ID.
     */
    int getID();

    /**
     * Should be implemented by all implementors
     *
     * @param o An object which to test against
     * @return True if o is instance of and has the same ID's as this IReservable.
     */
    @Override
    boolean equals(Object o);

    public String getUserRequirements();

    public int getAmount();

    public Condition getCondition();

    public boolean isReservable();

    public Location getLocation();

    public Image getImage();

    public String getName();
}
