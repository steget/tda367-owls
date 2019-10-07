package storagesystem.services;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import storagesystem.model.Item;

import java.io.*;

public class GSONWritingToFile {

    public static void main(String[] args) {

        Gson gson = new Gson();
        


        /*String json = gson.toJson(testItem);
        JsonReader reader = new JsonReader(new StringReader(json));
        reader.setLenient(true);
        Item testItem1 = gson.fromJson(json, Item.class);
        System.out.print(testItem1.getName());
        */
    }
}
