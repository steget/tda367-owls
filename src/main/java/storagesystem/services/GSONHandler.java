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


/**
 * GSONHandler is a handler with static methods with which it is possible to write and read from a json file of choice.
 *
 */

public class GSONHandler {

    public static void main(String[] args) {

    }
            /**
             * addToJson() adds an object of choice to a json file of choice without erasing the contents that are already in the json file.
             * @param fileName
             * @throws IOException
             */

    public static void addToJson(Object objectToAdd, String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray oldJsonContent = gson.fromJson(new FileReader(fileName), JsonArray.class);
        Writer writer = new FileWriter(fileName);

        if (oldJsonContent == null) {
            oldJsonContent = new JsonArray();
        }
        JsonObject jsonObject = (JsonObject) gson.toJsonTree(objectToAdd);
        oldJsonContent.add(jsonObject);
        JsonArray newJsonContent = oldJsonContent;
        gson.toJson(newJsonContent, writer);
        writer.flush();
        writer.close();
    }


            /**
             * clearJson() clears the json file completely.
             * @param fileName
             * @throws IOException
             */

    public static void clearJson(String fileName) throws IOException {
        Writer writer = new FileWriter(fileName);
        writer.flush();
        writer.close();
    }

            /**
             * addListToJson() adds a list of choice to a json file of choice without erasing the contents that are already in the json file.
             * @param listToAdd
             * @param fileName
             * @throws IOException
             */

    public static void addListToJson(List listToAdd, String fileName) throws IOException {
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

            /**
             * getListFromJson() returns a list with objects from a json file of choice.
             * @param fileName
             * @param typeOfList
             * @return
             * @throws IOException
             */

    public static List getListFromJson(String fileName, Type typeOfList) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonList = gson.fromJson(new FileReader(fileName), JsonArray.class);
        List<Object> itemList = new ArrayList<>();
                for(Object o : jsonList) {

                    itemList.add(gson.fromJson(o.toString(), typeOfList));
                }
        return itemList;
    }


}
