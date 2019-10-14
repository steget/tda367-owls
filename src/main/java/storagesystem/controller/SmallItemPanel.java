package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import storagesystem.StorageSystem;
import storagesystem.model.IReservable;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Hugo Stegrell
 * Loads in a SmallItemPanel from FXML and represents it with only text and an image.
 */
public class SmallItemPanel extends AnchorPane {

    private IReservable reservableItem;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView itemImage;

    @FXML
    private Label itemNameLabel;

    SmallItemPanel(IReservable reservableItem) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/smallItemPanel.fxml"));
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

    /**
     * Open detailed view of item so it can be reserved
     */
    @FXML
    void handlePanePressed() {
        AnchorPane parent = (AnchorPane) rootPane.getScene().lookup("#itemListRootPane");

        ItemPageController detailedItemView = new ItemPageController(reservableItem, StorageSystem.getCurrentOrganisation().getItemOwner(reservableItem));

        parent.getChildren().add(detailedItemView);
    }
}
