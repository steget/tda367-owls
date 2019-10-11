package storagesystem.model;


/**
 * Interface to use for classes that should have the ability to borrow objects.
 */
public interface IBorrower {
    /**
     *
     * @return An unique ID.
     */
    int getID();

    String getName();

    /**
     * Should be implemented by all implementors
     * @param o An object which to test against
     * @return True if o is instance of and has the same ID's as this IBorrower.
     */
    @Override
    boolean equals(Object o);


}
