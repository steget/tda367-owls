package storagesystem.services;

import org.junit.Assert;
import org.junit.Test;
import storagesystem.model.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static storagesystem.services.GSONHandler.getListFromJson;

public class GSONHandlerTest {

    @Test
    public void shouldAddItemsToJSON() throws IOException {
        GSONHandler.clearJson("src/main/resources/json/itemDB.json");
        Location location = new Location("Mock Location", "This is a temporary location");
        List<IReservable> itemList = new ArrayList<>();

        IReservable item1 = IReservableFactory.createReservableItem("name", "description", "UserReq", 10, Condition.BAD, false, location, location.getImage());
        IReservable item2 = IReservableFactory.createReservableItem("name", "description", "UserReq", 10, Condition.GREAT, false, location, location.getImage());

        itemList.add(item1);
        itemList.add(item2);

        GSONHandler.addListToJson(itemList, "src/main/resources/json/itemDB.json");

        List<IReservable> listFromJson = getListFromJson("src/main/resources/json/itemDB.json", Item.class);

        Assert.assertEquals(listFromJson.get(0).getName(), itemList.get(0).getName());
        Assert.assertEquals(listFromJson.get(1).getName(), itemList.get(1).getName());
    }
}
