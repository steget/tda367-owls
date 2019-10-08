package storagesystem.model;

/**
 * Interface to use for classes that represent something that can be borrowed.
 */

public interface IReservable {
    int getID();
    @Override
    boolean equals(Object o);
    IReservable copy();
    boolean isReservable();
}
