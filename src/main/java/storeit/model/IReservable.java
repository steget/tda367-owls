package storeit.model;

/**
 * Interface to use for classes that represent something that can be borrowed.
 *
 * @author William Albertsson
 */

public interface IReservable {
    /**
     * @return An unique ID.
     */
    int getID();

    String getUserRequirements();

    void setUserRequirements(String userRequirements);

    int getAmount();

    void setAmount(int amount);

    Condition getCondition();

    void setCondition(Condition condition);

    boolean isReservable();

    void setReservable(boolean reservable);

    int getLocationID();

    void setLocationID(int locationID);

    String getName();

    void setName(String name);

    String getDescription();

    void setDescription(String description);

    /**
     * Should be implemented by all implementors
     *
     * @param o An object which to test against
     * @return True if o is instance of and has the same ID's as this IReservable.
     */
    @Override
    boolean equals(Object o);
}
