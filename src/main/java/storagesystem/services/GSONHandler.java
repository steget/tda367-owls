package storagesystem.services;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import org.apache.commons.io.FileUtils;
import storagesystem.model.*;


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

    private static String itemDB = "src/main/resources/json/itemDB.json";
    private static String locationDB = "src/main/resources/json/locationDB.json";
    private static String organisationDB = "src/main/resources/json/organisationDB.json";
    private static String reservationDB = "src/main/resources/json/reservationDB.json";
    private static String teamDB = "src/main/resources/json/teamDB.json";
    private static String userDB = "src/main/resources/json/userDB.json";

    /**
     * addToJson() adds an object of choice to a json file of choice without erasing the contents that are already in the json file.
     *
     * @param objectToAdd
     * @throws IOException
     */
    public static void addToJson(Object objectToAdd) throws IOException {
        String fileName;
        if (objectToAdd instanceof Item) {
            fileName = itemDB;
        } else if (objectToAdd instanceof Location) {
            fileName = locationDB;
        } else if (objectToAdd instanceof Organisation) {
            fileName = organisationDB;
        } else if (objectToAdd instanceof IReservation) {
            fileName = reservationDB;
        } else if (objectToAdd instanceof Team) {
            fileName = teamDB;
        } else if (objectToAdd instanceof User) {
            fileName = userDB;
        } else {
            throw new IllegalArgumentException("Object does not have a corresponding json file.");
        }
        addToJson(objectToAdd, fileName);
    }

    private static void addToJson(Object objectToAdd, String fileName) throws IOException {
        GsonBuilder gsonBuilder = Converters
                .registerDateMidnight(Converters
                        .registerLocalTime(Converters
                                .registerDateTime(Converters.registerInterval(new GsonBuilder()))));
        gsonBuilder.registerTypeAdapter(IBorrower.class, new BorrowerSerialiser());
        gsonBuilder.registerTypeAdapter(IReservable.class, new ReservableSerialiser());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        JsonArray jsonContent = gson.fromJson(new FileReader(fileName), JsonArray.class);
        Writer writer = new FileWriter(fileName);
        JsonObject jsonObject;
        if (jsonContent == null) {
            jsonContent = new JsonArray();
        }
        if (objectToAdd instanceof IHasImageAndName) {//If the objectToAdd has an Image, add imageData to jsonObject
            try {
                jsonObject = createJsonObjectFromIHasImageAndName((IHasImageAndName) objectToAdd, gson); //will throw NullPointerException if image isn't set.
            } catch (NullPointerException e) {
                jsonObject = (JsonObject) gson.toJsonTree(objectToAdd);
                jsonObject.addProperty("imageData", "");
            }
        } else { //if objectToAdd hasn't got an Image, add to jsonObject
            jsonObject = (JsonObject) gson.toJsonTree(objectToAdd);
        }
        jsonContent.add(jsonObject);
        gson.toJson(jsonContent, writer);

        writer.flush();
        writer.close();
    }

    /**
     * addListToJson() adds a list of choice to a json file of choice without erasing the contents that are already in the json file.
     *
     * @param listToAdd
     * @throws IOException
     */

    public static void addListToJson(List listToAdd) throws IOException {
        try {
            if (listToAdd.get(0) instanceof Item) {
                addList(listToAdd, itemDB);
            } else if (listToAdd.get(0) instanceof Location) {
                addList(listToAdd, locationDB);
            } else if (listToAdd.get(0) instanceof Organisation) {
                addList(listToAdd, organisationDB);
            } else if (listToAdd.get(0) instanceof Reservation) {
                addList(listToAdd, reservationDB);
            } else if (listToAdd.get(0) instanceof Team) {
                addList(listToAdd, teamDB);
            } else if (listToAdd.get(0) instanceof User) {
                addList(listToAdd, userDB);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("List is empty!");
        }
    }


    private static void addList(List listToAdd, String database) throws IOException {
        for (Object object : listToAdd) {
            addToJson(object, database);
        }
    }

    private static JsonObject createJsonObjectFromIHasImageAndName(IHasImageAndName o, Gson gson) throws IOException {
        BufferedImage bImage = SwingFXUtils.fromFXImage(o.getImage(), null);
        File newImageFile = createNewImageFile(o);
        ImageIO.write(bImage, "jpg", newImageFile);
        byte[] imageInBinary = FileUtils.readFileToByteArray(newImageFile);
        String encodedString = Base64.getEncoder().encodeToString(imageInBinary);
        Image imageCopy = o.getImage();
        o.setImage(null);
        JsonObject jsonObject = (JsonObject) gson.toJsonTree(o);
        o.setImage(imageCopy);
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
     * getListFromJson() returns a list with objects from a json file corresponding to the type of list chosen.
     *
     * @param typeOfList
     * @return
     * @throws IOException
     */

    public static List getListFromJson(Type typeOfList) throws IOException {
        if (typeOfList.equals(Item.class)) {
            return getItemList();
        } else if (typeOfList.equals(Location.class)) {
            return getLocationList();
        } else if (typeOfList.equals(Organisation.class)) {
            return getOrganisationList();
        } else if (typeOfList.equals(Reservation.class)) {
            return getReservationList();
        } else if (typeOfList.equals(Team.class)) {
            return getTeamList();
        } else if (typeOfList.equals(User.class)) {
            return getUserList();
        } else {
            throw new IllegalArgumentException("There is no JSON file for chosen type of list!");
        }
    }

    private static List getItemList() throws IOException {
        GsonBuilder gsonBuilder = Converters
                .registerDateMidnight(Converters
                        .registerLocalTime(Converters
                                .registerDateTime(Converters.registerInterval(new GsonBuilder()))));
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        JsonArray jsonList = gson.fromJson(new FileReader(itemDB), JsonArray.class);
        List<Item> itemList = new ArrayList<>();
        for (Object o : jsonList) {
            itemList.add(gson.fromJson(o.toString(), Item.class));
        }
        assignImages(itemList, jsonList);
        return itemList;
    }

    private static List getLocationList() throws IOException {
        GsonBuilder gsonBuilder = Converters
                .registerDateMidnight(Converters
                        .registerLocalTime(Converters
                                .registerDateTime(Converters.registerInterval(new GsonBuilder()))));
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        JsonArray jsonList = gson.fromJson(new FileReader(locationDB), JsonArray.class);
        List<Location> locationList = new ArrayList<>();
        for (Object o : jsonList) {
            locationList.add(gson.fromJson(o.toString(), Location.class));
        }
        assignImages(locationList, jsonList);
        return locationList;
    }

    private static List getOrganisationList() throws IOException {
        GsonBuilder gsonBuilder = Converters
                .registerDateMidnight(Converters
                        .registerLocalTime(Converters
                                .registerDateTime(Converters.registerInterval(new GsonBuilder()))));
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        JsonArray jsonList = gson.fromJson(new FileReader(organisationDB), JsonArray.class);
        List<Organisation> organisationList = new ArrayList<>();
        for (Object o : jsonList) {
            organisationList.add(gson.fromJson(o.toString(), Organisation.class));
        }
        assignImages(organisationList, jsonList);
        return organisationList;
    }

    private static List getReservationList() throws IOException {
        GsonBuilder gsonBuilder = Converters
                .registerDateMidnight(Converters
                        .registerLocalTime(Converters
                                .registerDateTime(Converters.registerInterval(new GsonBuilder()))));
        gsonBuilder.registerTypeAdapter(IBorrower.class, new BorrowerSerialiser());
        gsonBuilder.registerTypeAdapter(IReservable.class, new ReservableSerialiser());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        List<Reservation> list = new ArrayList<>();
        JsonArray jsonList = gson.fromJson(new FileReader(reservationDB), JsonArray.class);
        for (Object o : jsonList) {
            list.add(gson.fromJson(o.toString(), Reservation.class));
        }
        return list;
    }


    private static List getTeamList() throws IOException {
        GsonBuilder gsonBuilder = Converters
                .registerDateMidnight(Converters
                        .registerLocalTime(Converters
                                .registerDateTime(Converters.registerInterval(new GsonBuilder()))));
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        JsonArray jsonList = gson.fromJson(new FileReader(teamDB), JsonArray.class);
        List<Team> teamList = new ArrayList<>();
        for (Object o : jsonList) {
            teamList.add(gson.fromJson(o.toString(), Team.class));
        }
        assignImages(teamList, jsonList);
        return teamList;
    }

    private static List getUserList() throws IOException {
        GsonBuilder gsonBuilder = Converters
                .registerDateMidnight(Converters
                        .registerLocalTime(Converters
                                .registerDateTime(Converters.registerInterval(new GsonBuilder()))));
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        List<Object> list = new ArrayList<>();
        JsonArray jsonList = gson.fromJson(new FileReader(userDB), JsonArray.class);
        for (Object o : jsonList) {
            list.add(gson.fromJson(o.toString(), User.class));
        }
        return list;
    }

    private static void assignImage(IHasImageAndName o, String imageData) throws IOException {
        byte[] decodedBytes = Base64.getDecoder().decode(imageData);
        File newImageFile = createNewImageFile(o);
        FileUtils.writeByteArrayToFile(newImageFile, decodedBytes);
        Image itemImage = new Image(newImageFile.toURI().toString());
        o.setImage(itemImage);
    }

    private static void assignImages(List list, JsonArray jsonList) throws IOException {
        for (int i = 0; i < jsonList.size(); i++) {
            String imageData = jsonList.get(i).getAsJsonObject().get("imageData").getAsString();
            if (!imageData.isEmpty()) {//If imageData is not empty, create an image with the data.
                assignImage((IHasImageAndName)list.get(i), imageData);
            }
        }
    }

    private static File createNewImageFile(IHasImageAndName o) {
        String itemPictureFolder = "src/main/resources/pictures/item/";
        String locationPictureFolder = "src/main/resources/pictures/location/";
        String organisationPictureFolder = "src/main/resources/pictures/organisation/";
        String teamPictureFolder = "src/main/resources/pictures/team/";
        if (o instanceof Item) {
            return new File(itemPictureFolder + ((Item) o).getID() + ".jpg");
        } else if (o instanceof Location) {
            return new File(locationPictureFolder + o.getName() + ".jpg");
        } else if (o instanceof Organisation) {
            return new File(organisationPictureFolder + o.getName() + ".jpg");
        } else if (o instanceof Team) {
            return new File(teamPictureFolder + o.getName() + ".jpg");
        } else {
            throw new IllegalArgumentException("Object does not have a corresponding json file.");
        }
    }


}
