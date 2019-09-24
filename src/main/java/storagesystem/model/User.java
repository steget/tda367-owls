package storagesystem.model;

public class User {
    private String name;
    private String description;
    private String contactInformation;

    public User(String name) {
        this.name = name;
    }

    public User(String name, String description, String contactInformation) {
        this.name = name;
        this.description = description;
        this.contactInformation = contactInformation;
    }

    String getName() {
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
}
