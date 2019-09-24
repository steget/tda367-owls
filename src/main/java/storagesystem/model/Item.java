package storagesystem.model;

import javafx.scene.image.Image;

public class Item extends Team{ //TODO: implement IReservable
    private Team teamOwner = super.getTeam();
    private String name = "Temp Item";
    private String description = "This is a temporary item";
    private String userRequirements = "don't remove me unless done";
    private int ID = 0;
    private int amount = 25;
    private Condition condition = Condition.GOOD;
    private boolean reservable = false; //TODO: Change from boolean to IReservable
    private Location location = new Location("Temp Loc", "This is a temporary location", null); //TODO: add image
    private Image image;

    Item(String name, String description, String userRequirements, int ID, int amount, Condition condition, boolean reservable, Location location, Image image) {
        this.name = name;
        this.description = description;
        this.userRequirements = userRequirements;
        this.ID = ID;
        this.amount = amount;
        this.condition = condition;
        this.reservable = reservable;
        this.location = location;
        this.image = image;
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

    public String getUserRequirements() {
        return userRequirements;
    }

    public void setUserRequirements(String userRequirements) {
        this.userRequirements = userRequirements;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public boolean isReservable() {
        return reservable;
    }

    public void setReservable(boolean reservable) {
        this.reservable = reservable;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
