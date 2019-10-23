package storagesystem.viewcontroller.allItems;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import storagesystem.model.IReservable;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Hugo Stegrell
 * Loads in a SmallItemPanel from FXML and represents it with only text and an image.
 */
public class SmallItemPanel extends AnchorPane {

    private IReservable reservableItem;

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemNameLabel;


    public SmallItemPanel(IReservable reservableItem) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/allItems/smallItemPanel.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.reservableItem = reservableItem;

        itemImage.setImage(reservableItem.getImage());
        itemNameLabel.setText(reservableItem.getName());
    }

    IReservable getReservableItem() {
        return reservableItem;
    }
}
