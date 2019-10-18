package storagesystem.model;

import java.util.Objects;

/**
 * A user should represent a person.
 *
 * @author Hugo Stegrell, Pär Aronsson
 */
public class User {
    private String name;
    private String description;
    private String contactInformation;
    private int ID;
    private static int nextID;

    public User(String name, String description, String contactInformation) {
        this.name = name;
        this.description = description;
        this.contactInformation = contactInformation;

        //set ID and update lastID
        ID = nextID;
        nextID++;
    }

    public User(String name) {
        this(name, "", "");
    }

    private User(User userToCopy) {
        this.name = userToCopy.name;
        this.description = userToCopy.description;
        this.contactInformation = userToCopy.contactInformation;
        this.ID = userToCopy.ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public int getID() {
        return ID;
    }
}
