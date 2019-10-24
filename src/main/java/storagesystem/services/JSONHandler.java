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
 * JSONHandler is a handler with static methods with which it is possible to write and read from the json file organisationDB.
 */

public class JSONHandler {
    private static String organisationDB = "src/main/resources/json/organisationDB.json";

    /**
     * Clears the json files then saves all data of a list of organisations.
     * @throws IOException
     */

    public static void save(List<Organisation> organisations) throws IOException {
        clearAllJsonFiles();
        addListToJson(organisations);
        System.out.println("Saved Organisations.");
    }

    /**
     * addToJson() adds an Object to the json file without erasing its contents.
     *
     * @param objectToAdd
     * @throws IOException
     */
    private static void addToJson(Object objectToAdd) throws IOException {
        Gson gson = createGsonBuilder().setPrettyPrinting().create();
        JsonArray jsonContent = gson.fromJson(new FileReader(organisationDB), JsonArray.class);
        Writer writer = new FileWriter(organisationDB);
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

    private static void addListToJson(List listToAdd) throws IOException {
        try {
            for (Object object : listToAdd) {
                addToJson(object);
            }
        } catch (IndexOutOfBoundsException e) {
            System.out.println("List is empty!");
        }
    }

    /**
     * clearJson() clears a json file completely.
     *
     * @param fileName
     * @throws IOException
     */

    private static void clearJson(String fileName) throws IOException {
        Writer writer = new FileWriter(fileName);
        writer.flush();
        writer.close();
    }

    /**
     * clearAllJsonFiles() clears all json files completely.
     *
     * @throws IOException
     */

    public static void clearAllJsonFiles() throws IOException {
        clearJson(organisationDB);
        System.out.println("CLEARED ALL JSON FILES");
    }

    /**
     * @return a list of Organisations from the organisationDB file
     * @throws IOException
     */
    public static List<Organisation> getOrganisationList() throws IOException {
        Gson gson = createGsonBuilder().setPrettyPrinting().create();
        JsonArray jsonList = gson.fromJson(new FileReader(organisationDB), JsonArray.class);
        List<Organisation> organisationList = new ArrayList<>();
        for (Object o : jsonList) {
            organisationList.add(gson.fromJson(o.toString(), Organisation.class));
        }
        return organisationList;
    }

    /**
     * Creates a custom GsonBuilder that handles necessary custom Interfaces and imports. Also utilizes serializeNulls() as not to skip writing fields that contains null.
     *
     * @return a custom gsonBuilder
     */

    private static GsonBuilder createGsonBuilder() {
        GsonBuilder gsonBuilder = Converters.registerInterval(new GsonBuilder()); //Needed to handle Interval in Reservation
        gsonBuilder.registerTypeAdapter(IBorrower.class, new BorrowerSerializer()); //Needed to handle IBorrower in Reservation
        gsonBuilder.registerTypeAdapter(IReservable.class, new ReservableSerializer()); //Needed to handle IReservable in Reservation
        gsonBuilder.registerTypeAdapter(IReservation.class, new ReservationSerializer()); //Needed to handle IReservation in Organisation
        gsonBuilder.serializeNulls(); //Needed as not to skip writing null fields
        return gsonBuilder;
    }

}
