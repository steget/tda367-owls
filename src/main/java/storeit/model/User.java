package storeit.model;

/**
 * A user should represent a person.
 *
 * @author Hugo Stegrell, Pär Aronsson
 */
public class User {
    private static int nextID;
    private String name;
    private String password;
    private String description;
    private String contactInformation;
    private int ID;

    public User(String name, String password, String description, String contactInformation) {
        this.name = name;
        this.password = password;
        this.description = description;
        this.contactInformation = contactInformation;

        //set ID and update lastID
        ID = nextID;
        nextID++;
    }

    public User(String name, String description, String contactInformation) {
        this(name, "", description, contactInformation);
    }

    public User(String name) {
        this(name, "", "", "");
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

    String getPassword() {
        return password;
    }

    public static void setNextID(int nextID) {
        User.nextID = nextID;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Compares ID's if o is a reservation
     *
     * @param obj the reference object with which to compare.
     * @return {@code true} if this object is the same as the obj argument; {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        User comparingUser = (User) obj;
        return ID == comparingUser.ID;
    }
}
