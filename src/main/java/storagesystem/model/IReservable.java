package storagesystem.model;
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

    int getAmount();

    Condition getCondition();

    boolean isReservable();

    int getLocationID();

    String getName();

    String getDescription();

    void setName(String name);

    void setDescription(String description);

    void setUserRequirements(String userRequirements);

    void setAmount(int amount);

    void setCondition(Condition condition);

    void setReservable(boolean reservable);

    void setLocationID(int locationID);


    /**
     * Should be implemented by all implementors
     *
     * @param o An object which to test against
     * @return True if o is instance of and has the same ID's as this IReservable.
     */
    @Override
    boolean equals(Object o);
}
