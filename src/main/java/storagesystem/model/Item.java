package storagesystem.model;

public class Item implements IReservable {
    private static int LST_ID=0;
    private final int id;
    private boolean isReservable;

    @Override
    public int getID() {
        return id;
    }

    public Item(){
        id=LST_ID;
        LST_ID++;
    }

    private Item(IReservable old){
        id = old.getID();
    }

    public Item copy(){
        return new Item(this);
    }

    @Override
    public boolean isReservable() {
        return isReservable;
    }

    @Override
    public boolean equals(Object o){
        if(o == this){
            return true;
        }
        if(!(o instanceof Item))
            return false;

        Item i = (Item) o;

        if(i.getID() == this.getID())
            return true;

        return false;
    }

    @Override
    public String getName() {
        return "TempName";
    }
}
