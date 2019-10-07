package storagesystem.model;

import java.util.Objects;

public class MockUser implements IBorrower {

    static int LST_ID =1;
    private int id;

    public MockUser(IBorrower old){
        id=old.getID();
    }

    public MockUser(){
        LST_ID++;
        id = LST_ID;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MockUser mockUser = (MockUser) o;
        return id == mockUser.id;
    }

    @Override
    public IBorrower copy() {
        return new MockUser(this);
    }
}
