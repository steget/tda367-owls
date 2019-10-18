package storagesystem.model;

import javafx.scene.image.Image;

public interface IHasImageAndName {
    String getImageUrl();
    void setImage(Image image);
    String getName();
    void setName(String name);
}
