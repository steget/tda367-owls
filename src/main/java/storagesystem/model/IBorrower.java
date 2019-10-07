package storagesystem.model;

public interface IBorrower {

    int getID();
    IBorrower copy();
    @Override
    boolean equals(Object o);


}
