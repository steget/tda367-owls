package storagesystem.services;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;
import storagesystem.model.Condition;
import storagesystem.model.Item;


import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


/**
 * GSONHandler is a handler with static methods with which it is possible to write and read from a json file of choice.
 */

public class GSONHandler {

    /**
     * addToJson() adds an object of choice to a json file of choice without erasing the contents that are already in the json file.
     *
     * @param fileName
     * @throws IOException
     */
    public static void addToJson(Object objectToAdd, String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonContent = gson.fromJson(new FileReader(fileName), JsonArray.class);
        Writer writer = new FileWriter(fileName);
        JsonObject jsonObject;
        if (jsonContent == null) {
            jsonContent = new JsonArray();
        }

        if (objectToAdd.getClass().equals(Item.class)) { //TODO: If objectToAdd has an Image, convert it to a Base64 String
            Item newItem = (Item) objectToAdd;
            try {
                jsonObject = getJSONObjectFromItem(newItem, gson);
            } catch (NullPointerException e) { //Sets imageData to an empty string if objectToAdd has no image
                jsonObject = (JsonObject) gson.toJsonTree(newItem);
                jsonObject.addProperty("imageData", "");
            }
        } else { //TODO: if objectToAdd hasn't got an Image, add as usual
            jsonObject = (JsonObject) gson.toJsonTree(objectToAdd);
        }

        jsonContent.add(jsonObject);
        gson.toJson(jsonContent, writer);

        writer.flush();
        writer.close();
    }


    private static JsonObject getJSONObjectFromItem(Item item, Gson gson) throws IOException {
        BufferedImage bImage = SwingFXUtils.fromFXImage(item.getImage(), null);
        File newImageFile = new File("src/main/resources/pictures/item-" + item.getID() + "-image.png");
        ImageIO.write(bImage, "png", newImageFile);
        byte[] imageInBinary = FileUtils.readFileToByteArray(newImageFile);
        String encodedString = Base64.getEncoder().encodeToString(imageInBinary);
        Image imageCopy = item.getImage();
        item.setImage(null);
        JsonObject jsonObject = (JsonObject) gson.toJsonTree(item);
        item.setImage(imageCopy);
        jsonObject.addProperty("imageData", encodedString);
        return jsonObject;
    }


    /**
     * clearJson() clears the json file completely.
     *
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
     *
     * @param listToAdd
     * @param fileName
     * @throws IOException
     */

    public static void addListToJson(List listToAdd, String fileName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonContent = gson.fromJson(new FileReader(fileName), JsonArray.class);
        Writer writer = new FileWriter(fileName);
        if (jsonContent == null) {
            jsonContent = new JsonArray();
        }
        JsonArray newJsonContent = gson.toJsonTree(listToAdd, List.class).getAsJsonArray();
        jsonContent.addAll(newJsonContent);
        gson.toJson(jsonContent, writer);
        writer.flush();
        writer.close();
    }

    /**
     * getListFromJson() returns a list with objects from a json file of choice.
     *
     * @param fileName
     * @param typeOfList
     * @return
     * @throws IOException
     */

    public static List getListFromJson(String fileName, Type typeOfList) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonList = gson.fromJson(new FileReader(fileName), JsonArray.class);
        List<Object> list =  new ArrayList<>();

        if(typeOfList.equals(Item.class)) {
            List<Item> itemList = new ArrayList<>();
            for (Object o : jsonList) {
                itemList.add(gson.fromJson(o.toString(), Item.class));
            }
            for (int i = 0; i<jsonList.size(); i++) {
                byte[] decodedBytes = Base64.getDecoder().decode(jsonList.get(i).getAsJsonObject().get("imageData").getAsString());
                File newImageFile = new File("src/main/resources/pictures/item-" + itemList.get(i).getID() + "-image.png");
                FileUtils.writeByteArrayToFile(newImageFile, decodedBytes);
                Image itemImage = new Image(newImageFile.toURI().toString());
                itemList.get(i).setImage(itemImage);
            }
            return itemList;
        } else {
            for (Object o : jsonList) {
                list.add(gson.fromJson(o.toString(), typeOfList));
            }
        }
        return list;
    }


}
