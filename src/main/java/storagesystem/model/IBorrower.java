package storagesystem.model;


/**
 * Interface to use for classes that should have the ability to borrow objects.
 */
public interface IBorrower {

    int getID();
    IBorrower copy();
    @Override
    boolean equals(Object o);


}
