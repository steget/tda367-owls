package storagesystem.model;

public interface IReservable {
    int getID();
    @Override
    boolean equals(Object o);
    IReservable copy();
}
