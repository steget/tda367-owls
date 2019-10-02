package storagesystem.model;

/**
 * A user should represent a person.
 * A user can be a part of a team and manage the teams properties such as: Inventory, name, mambers, and borrowing etc.
 * A user can browse items within its organisation and borrow from other teams.
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

    void setName(String name) {
        this.name = name;
    }

    String getDescription() {
        return description;
    }

    void setDescription(String description) {
        this.description = description;
    }

    String getContactInformation() {
        return contactInformation;
    }

    void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public int getID() {
        return ID;
    }

    public User getDeepCopy() {
        return new User(this);
    }
}
