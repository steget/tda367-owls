package storagesystem.services;

import storagesystem.model.Condition;
import storagesystem.model.Item;
import storagesystem.services.GSONHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static storagesystem.services.GSONHandler.getListFromJson;

public class GSONHandlerTest {

    public static void main(String[] args) throws IOException { //TODO: make actual tests
        GSONHandler.clearJson("src/main/resources/json/itemDB.json");
        List itemList = new ArrayList();
        Item item1 = new Item();
        Item item2 = new Item("name", "description", "UserReq", 2, 10, Condition.GREAT, false);
        itemList.add(item1);
        itemList.add(item2);
        GSONHandler.addListToJson(itemList, "src/main/resources/json/itemDB.json");
        List<Item> listFromJson = getListFromJson("src/main/resources/json/itemDB.json", Item.class);
        System.out.println(listFromJson.get(0).getDescription());
    }


}
