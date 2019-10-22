package storagesystem.services;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import storagesystem.model.*;


import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Carl Lindh
 * JSONHandler is a handler with static methods with which it is possible to write and read from the json files corresponding to the Strings declared.
 */

public class JSONHandler {

    private static String itemDB = "src/main/resources/json/itemDB.json";
    private static String locationDB = "src/main/resources/json/locationDB.json";
    private static String organisationDB = "src/main/resources/json/organisationDB.json";
    private static String reservationDB = "src/main/resources/json/reservationDB.json";
    private static String teamDB = "src/main/resources/json/teamDB.json";
    private static String userDB = "src/main/resources/json/userDB.json";

    /**
     * addToJson() automatically adds an Object to the corresponding json file without erasing its contents, if it exists.
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

    /**
     * Writes an Object to the json file corresponding to fileName
     *
     * @param objectToAdd
     * @param fileName
     * @throws IOException
     */
    private static void addToJson(Object objectToAdd, String fileName) throws IOException {
        GsonBuilder gsonBuilder = Converters.registerInterval(new GsonBuilder());
        gsonBuilder.registerTypeAdapter(IBorrower.class, new BorrowerSerializer());
        gsonBuilder.registerTypeAdapter(IReservable.class, new ReservableSerializer());
        gsonBuilder.registerTypeAdapter(IReservation.class, new ReservationSerializer());
        gsonBuilder.serializeNulls();
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        JsonArray jsonContent = gson.fromJson(new FileReader(fileName), JsonArray.class);
        Writer writer = new FileWriter(fileName);
        JsonObject jsonObject;
        if (jsonContent == null) {
            jsonContent = new JsonArray();
        }
        jsonObject = (JsonObject) gson.toJsonTree(objectToAdd);
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

    /**
     * @param listToAdd
     * @param database
     * @throws IOException
     */
    private static void addList(List listToAdd, String database) throws IOException {
        for (Object object : listToAdd) {
            addToJson(object, database);
        }
    }

    /**
     * clearJson() clears a json file completely.
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
     * clearAllJsonFiles() clears all json files declared completely.
     *
     * @throws IOException
     */

    public static void clearAllJsonFiles() throws IOException {
        clearJson(itemDB);
        clearJson(locationDB);
        clearJson(organisationDB);
        clearJson(reservationDB);
        clearJson(teamDB);
        clearJson(userDB);
        System.out.println("CLEARED ALL JSON FILES");
    }


    /**
     * Uses private methods to get a list.
     *
     * @param typeOfList
     * @return a list with objects from a json file corresponding to typeOfList
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

    /**
     * @return a list of Items from the itemDB file
     * @throws IOException
     */
    private static List getItemList() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonList = gson.fromJson(new FileReader(itemDB), JsonArray.class);
        List<Item> itemList = new ArrayList<>();
        for (Object o : jsonList) {
            itemList.add(gson.fromJson(o.toString(), Item.class));
        }
        return itemList;
    }

    /**
     * @return a list of Locations from the locationDB file
     * @throws IOException
     */
    private static List getLocationList() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonList = gson.fromJson(new FileReader(locationDB), JsonArray.class);
        List<Location> locationList = new ArrayList<>();
        for (Object o : jsonList) {
            locationList.add(gson.fromJson(o.toString(), Location.class));
        }
        return locationList;
    }

    /**
     * Handles IBorrower, IReservable and IReservation interface types through BorrowerSerialiser, ReservableSerialiser and ReservationSerializer classes. Handles org.joda.time.Interval through Converters.registerInterval()
     *
     * @return a list of Organisations from the organisationDB file
     * @throws IOException
     */
    private static List getOrganisationList() throws IOException {
        GsonBuilder gsonBuilder = Converters.registerInterval(new GsonBuilder()); //Needed to handle Interval in Reservation
        gsonBuilder.registerTypeAdapter(IBorrower.class, new BorrowerSerializer()); //Needed to handle IBorrower in Reservation
        gsonBuilder.registerTypeAdapter(IReservable.class, new ReservableSerializer()); //Needed to handle IReservable in Reservation
        gsonBuilder.registerTypeAdapter(IReservation.class, new ReservationSerializer()); //Needed to handle IReservation in Organisation
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        JsonArray jsonList = gson.fromJson(new FileReader(organisationDB), JsonArray.class);
        List<Organisation> organisationList = new ArrayList<>();
        for (Object o : jsonList) {
            organisationList.add(gson.fromJson(o.toString(), Organisation.class));
        }
        return organisationList;
    }

    /**
     * Handles IBorrower and IReservable interface types through BorrowerSerialiser and ReservableSerialiser classes. Handles org.joda.time.Interval through Converters.registerInterval()
     *
     * @return a list of Reservations from the reservationDB file
     * @throws IOException
     */

    private static List getReservationList() throws IOException {
        GsonBuilder gsonBuilder = Converters.registerInterval(new GsonBuilder()); //Needed to handle Interval in Reservation
        gsonBuilder.registerTypeAdapter(IBorrower.class, new BorrowerSerializer()); //Needed to handle IBorrower in Reservation
        gsonBuilder.registerTypeAdapter(IReservable.class, new ReservableSerializer()); //Needed to handle IReservable in Reservation
        gsonBuilder.registerTypeAdapter(IReservation.class, new ReservationSerializer());
        Gson gson = gsonBuilder.setPrettyPrinting().create();
        List<Reservation> reservationList = new ArrayList<>();
        JsonArray jsonList = gson.fromJson(new FileReader(reservationDB), JsonArray.class);
        for (Object o : jsonList) {
            reservationList.add(gson.fromJson(o.toString(), Reservation.class));
        }
        return reservationList;
    }

    /**
     * @return a list of Teams from the teamDB file
     * @throws IOException
     */

    private static List getTeamList() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonList = gson.fromJson(new FileReader(teamDB), JsonArray.class);
        List<Team> teamList = new ArrayList<>();
        for (Object o : jsonList) {
            teamList.add(gson.fromJson(o.toString(), Team.class));
        }
        return teamList;
    }

    /**
     * @return a list of Users from the userDB file
     * @throws IOException
     */

    private static List getUserList() throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        List<User> userList = new ArrayList<>();
        JsonArray jsonList = gson.fromJson(new FileReader(userDB), JsonArray.class);
        for (Object o : jsonList) {
            userList.add(gson.fromJson(o.toString(), User.class));
        }
        return userList;
    }

}
