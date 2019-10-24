package storagesystem.services;

import com.google.gson.*;
import storagesystem.model.IReservation;

import java.lang.reflect.Type;

/**
 * ReservationSerializer overwrites the serialize and deserialize methods in gson by typing the classname of the serialized object as a separate field in json, and the data as another.
 */

public class ReservationSerializer implements JsonSerializer<IReservation>, JsonDeserializer<IReservation> {

    private static final String CLASSNAME = "CLASSNAME";
    private static final String DATA = "DATA";

    public IReservation deserialize(JsonElement jsonElement, Type type,
                                    JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();
        Class klass = getObjectClass(className);
        return jsonDeserializationContext.deserialize(jsonObject.get(DATA), klass);
    }

    public JsonElement serialize(IReservation jsonElement, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, jsonElement.getClass().getName());
        jsonObject.add(DATA, jsonSerializationContext.serialize(jsonElement));
        return jsonObject;
    }

    /**
     * Helper method to get the className of the object to be deserialized.
     */

    private Class getObjectClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            //e.printStackTrace();
            throw new JsonParseException(e.getMessage());
        }
    }
}