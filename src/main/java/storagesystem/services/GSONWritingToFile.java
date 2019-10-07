package storagesystem.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import storagesystem.model.Item;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GSONWritingToFile {

    public static void main(String[] args) throws IOException {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Item testItem = new Item();
        Writer writer = new FileWriter("src/main/resources/package.json");
        List<Item> itemList = new ArrayList<>();

        testItem.setName("This is now in the json file");

        Item testItem2 = new Item();
        itemList.add(testItem2);
        itemList.add(testItem);
        gson.toJson(itemList, writer);
        writer.flush();
        writer.close();

        List itemList2 = gson.fromJson(new FileReader("src/main/resources/package.json"), ArrayList.class);

        System.out.println(itemList2.get(1).getClass()); //TODO: Fix LinkedTreeMap (to Item)
    }
}
