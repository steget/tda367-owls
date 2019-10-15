package storagesystem.model;

import javafx.scene.image.Image;

public interface IHasImageAndName {
    Image getImage();
    void setImage(Image image);
    String getName();
    void setName(String name);
}
