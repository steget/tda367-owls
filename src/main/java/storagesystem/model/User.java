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
