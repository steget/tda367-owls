package storagesystem.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import storagesystem.model.Condition;
import storagesystem.model.Item;


import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GSONHandler {

    public static void main(String[] args) throws IOException {

        /*Gson gson = new GsonBuilder().setPrettyPrinting().create(); //TODO: remove
        Item testItem = new Item();
        Writer writer = new FileWriter("src/main/resources/itemDB.json");
        List<Item> itemList = new ArrayList<>();

        testItem.setName("This is now in the json file");

        Item testItem2 = new Item();
        itemList.add(testItem2);
        itemList.add(testItem);

        addToJson(testItem, "src/main/resources/itemDB.json");
        addToJson(testItem2, "src/main/resources/itemDB.json");
        addListToJson(itemList,"src/main/resources/itemDB.json");
        JsonArray itemList2 = gson.fromJson(new FileReader("src/main/resources/itemDB.json"), JsonArray.class);
        JsonObject jsonObject = (JsonObject)gson.toJsonTree(itemList2.get(1));
        Item item = gson.fromJson(jsonObject.toString(), Item.class);
        System.out.println(item.getLocation().getName());
        List<Item> itemListTest = (List)getListFromJson("src/main/resources/itemDB.json", Item.class);
        System.out.println(itemList2.get(1).toString());

        Item item2 = gson.fromJson(itemList2.get(1).toString(), Item.class);
        for(Item i : itemListTest) {
            System.out.println(i.getName());
        }
        addListToJson(itemListTest, "src/main/resources/itemDB.json");*///TODO: remove


        clearJson("src/main/resources/json/itemDB.json");
        List itemList = new ArrayList();
        Item item1 = new Item();
        Item item2 = new Item("name", "description", "UserReq", 2, 10, Condition.GREAT, false);
        itemList.add(item1);
        itemList.add(item2);
        addListToJson(itemList, "src/main/resources/json/itemDB.json");
    }



    private static void addToJson(Object objectToAdd, String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray oldJsonContent = gson.fromJson(new FileReader(fileName), JsonArray.class);
        Writer writer = new FileWriter(fileName);

        if (oldJsonContent == null) {
            oldJsonContent = new JsonArray();
        }
        JsonObject jsonObject = (JsonObject)gson.toJsonTree(objectToAdd);
        oldJsonContent.add(jsonObject);
        JsonArray newJsonContent = oldJsonContent;
        gson.toJson(newJsonContent, writer);
        writer.flush();
        writer.close();
    }


    private static void clearJson(String fileName) throws IOException {
        Writer writer = new FileWriter(fileName);
        writer.flush();
        writer.close();
    }

    private static void addListToJson(List listToAdd, String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray oldJsonContent = gson.fromJson(new FileReader(fileName), JsonArray.class);
        Writer writer = new FileWriter(fileName);
        if (oldJsonContent == null) {
            oldJsonContent = new JsonArray();
        }
        JsonArray newJsonContent = gson.toJsonTree(listToAdd, List.class).getAsJsonArray();
        oldJsonContent.addAll(newJsonContent);
        newJsonContent = oldJsonContent;
        gson.toJson(newJsonContent, writer);
        writer.flush();
        writer.close();
    }


    private static List<Object> getListFromJson(String fileName, Type typeOfList) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonList = gson.fromJson(new FileReader(fileName), JsonArray.class);
        List<Object> itemList = new ArrayList<>();
                for(Object o : jsonList) {

                    itemList.add(gson.fromJson(o.toString(), typeOfList));
                }
        return itemList;
    }


}
