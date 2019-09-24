package storagesystem.model;

import java.util.ArrayList;
import java.util.List;

public class Organisation {
    private final List<Team> teams;
    //todo reservationHandler

    public Organisation() {
        teams = new ArrayList<>();
        //fill teams from db
    }

    private List<Item> getAllItems() {
        List<Item> allItems = new ArrayList<Item>();
        for (Team t :
                teams) {
            allItems.addAll(t.getAllItems());
        }
        return allItems;
    }

    private Item getItem(int ID) throws Exception {
        for (Team t :
                teams) {
            for (Item i :
                    t.getAllItems()) {
                //i.getID == ID
                if (true) {
                    System.out.println("Item found");
                    return i;
                }
            }
        }
        throw new Exception("ItemID not found in list of items");
    }

    void getAllReservations() {

    }

    void getReservation() {

    }
}
