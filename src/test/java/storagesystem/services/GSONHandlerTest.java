package storagesystem.services;

import org.junit.Assert;
import org.junit.Test;
import storagesystem.model.Condition;
import storagesystem.model.Item;
import storagesystem.model.Location;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static storagesystem.services.GSONHandler.getListFromJson;

public class GSONHandlerTest {

    @Test
    public void shouldAddItemsToJSON() throws IOException {
        GSONHandler.clearJson("src/main/resources/json/itemDB.json");
        Location location = new Location();
        List<Item> itemList = new ArrayList();

        Item item1 = new Item();
        Item item2 = new Item("name", "description", "UserReq", 2, 10, Condition.GREAT, false, location, location.getImage());

        itemList.add(item1);
        itemList.add(item2);

        GSONHandler.addListToJson(itemList, "src/main/resources/json/itemDB.json");

        List<Item> listFromJson = getListFromJson("src/main/resources/json/itemDB.json", Item.class);

        Assert.assertTrue(listFromJson.get(0).getName().equals(itemList.get(0).getName()));
        Assert.assertTrue(listFromJson.get(1).getName().equals(itemList.get(1).getName()));
    }
}
