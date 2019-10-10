package storagesystem.model;

import javafx.scene.image.Image;

public interface IReservable {
    String name = "Default Item";
    String description = "Default Description";
    String userRequirements = "Default Requirements";
    int ID = 0;
    int amount = 0;
    Condition condition = Condition.BAD;
    boolean reservable = false;
    Location location = new Location("Location Name", "Location Description", new Image("pictures/art.png"));
    Image image = new Image("pictures/cute-owl.jpg");

    public String getName();

    public String getDescription();

    public String getUserRequirements();

    public int getID();

    public int getAmount();

    public Condition getCondition();

    public boolean isReservable();

    public Location getLocation();

    public Image getImage();

}
