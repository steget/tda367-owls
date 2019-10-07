package storagesystem.model;

public class MockItem implements IReservable {

    private static int LST_ID=0;
    private final int id;

    @Override
    public int getID() {
        return id;
    }

    public MockItem(){
        id=LST_ID;
        LST_ID++;
    }

    private MockItem(IReservable old){
        id = old.getID();
    }



    public MockItem copy(){
        return new MockItem(this);
    }

    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof MockItem))
            return false;

        MockItem i = (MockItem) o;

        if(i.getID() == this.getID())
            return true;

        return false;
    }
}
