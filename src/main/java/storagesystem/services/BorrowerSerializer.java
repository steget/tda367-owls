package storagesystem.services;

import com.google.gson.*;
import storagesystem.model.IBorrower;

import java.lang.reflect.Type;

/**
 * BorrowerSerializer overwrites the serialize and deserialize methods in gson by typing the classname of the serialized object as a separate field in json, and the data as another.
 */

public class BorrowerSerializer implements JsonSerializer<IBorrower>, JsonDeserializer<IBorrower> {

    private static final String CLASSNAME = "CLASSNAME";
    private static final String DATA = "DATA";

    /**
     * Deserializes a Json Element to an IBorrower
     *
     * @param jsonElement                a Json Element
     * @param type                       a Type
     * @param jsonDeserializationContext a JsonSerializationContext
     * @return a deserialized IBorrower
     * @throws JsonParseException
     */

    public IBorrower deserialize(JsonElement jsonElement, Type type,
                                 JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {

        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonPrimitive prim = (JsonPrimitive) jsonObject.get(CLASSNAME);
        String className = prim.getAsString();
        Class klass = getObjectClass(className);
        return jsonDeserializationContext.deserialize(jsonObject.get(DATA), klass);
    }

    /**
     * Serializes an IBorrower to a Json Element. Is handled by gson.
     *
     * @param iBorrower                an IBorrower
     * @param type                     a Type
     * @param jsonSerializationContext a JsonSerializationContext
     * @return a serialized Json Element
     */
    public JsonElement serialize(IBorrower iBorrower, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty(CLASSNAME, iBorrower.getClass().getName());
        jsonObject.add(DATA, jsonSerializationContext.serialize(iBorrower));
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