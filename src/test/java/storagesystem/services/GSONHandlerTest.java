package storagesystem.services;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import org.junit.Assert;
import org.junit.Test;
import storagesystem.model.Condition;
import storagesystem.model.Item;
import storagesystem.model.Location;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static storagesystem.services.GSONHandler.*;

public class GSONHandlerTest {

    @Test
    public void shouldAddItemsToJSON() throws IOException {
        JFXPanel mockJfxPanel = new JFXPanel(); //Stupid line to prevent "java.lang.RuntimeException: Internal graphics not initialized yet"

        GSONHandler.clearJson("src/main/resources/json/itemDB.json");
        Location location = new Location("Mock Location", "This is a temporary location");
        List<Item> itemList = new ArrayList<>();

        Item item1 = new Item();
        Item item2 = new Item("name", "description", "UserReq", 2, 10, Condition.GREAT, false, location, new Image("pictures/art.png"));

        itemList.add(item1);
        itemList.add(item2);

        GSONHandler.addListToJson(itemList);
        //Item item3 = new Item();

        //GSONHandler.addToJson(item3);
        List<Item> listFromJson = getListFromJson(Item.class);


        Assert.assertTrue(listFromJson.get(0).getName().equals(itemList.get(0).getName()));
        Assert.assertTrue(listFromJson.get(1).getName().equals(itemList.get(1).getName()));
        Assert.assertTrue(listFromJson.equals(itemList));
        //Assert.assertTrue(item3.getName().equals(listFromJson.get(2).getName()));
    }

    @Test
    public void shouldAddLocationsToJSON() throws IOException {
        JFXPanel mockJfxPanel = new JFXPanel(); //Stupid line to prevent "java.lang.RuntimeException: Internal graphics not initialized yet"


        GSONHandler.clearJson("src/main/resources/json/locationDB.json");

        Location location1 = new Location("Location without Image", "Outside of my mind");
        Location location2 = new Location("Test Location With Image", "Inside of my mind", new Image("pictures/art.png"));

        List<Location> locationList = new ArrayList<>();

        locationList.add(location1);
        locationList.add(location2);

        GSONHandler.addListToJson(locationList);

        List<Location> listFromJson = getListFromJson(Location.class);
        for (int i = 0; i < locationList.size(); i++) {
            Assert.assertTrue(listFromJson.get(i).getName().equals(locationList.get(i).getName()));
        }

    }
}
