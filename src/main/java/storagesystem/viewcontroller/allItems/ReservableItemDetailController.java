package storagesystem.viewcontroller.allItems;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import storagesystem.model.Condition;
import storagesystem.model.IReservable;
import storagesystem.model.StoreIT;
import storagesystem.model.Team;
import storagesystem.services.PictureHandler;

import java.io.IOException;

/**
 * Controls a detailed view of an item. Can be used to book an item.
 *
 * @author Jonathan Eksberg, Carl Lindh, Hugo Stegrell
 */
public class ReservableItemDetailController extends AnchorPane {

    private final IReservable item;
    private final Team itemOwner;

    @FXML
    private ImageView detailViewImageView;
    @FXML
    private Label detailViewNameLabel;
    @FXML
    private Label detailViewAmountLabel;
    @FXML
    private Label detailViewLocationLabel;
    @FXML
    private Label detailViewTeamOwnerLabel;
    @FXML
    private Label detailViewReservableLabel;
    @FXML
    private Slider detailViewConditionSlider;
    @FXML
    private TextArea detailViewDescriptionTA;
    @FXML
    private TextArea detailViewUserRequirementsTA;
    @FXML
    private Button detailViewReserveBtn;
    @FXML
    private AnchorPane contentPane;

    public ReservableItemDetailController(IReservable item) {
        this.item = item;
        this.itemOwner = StoreIT.getCurrentOrganisation().getItemOwner(item);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/allItems/reservableItemDetailView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        initialize();
    }

    public void initialize() {
        updateAllVisibleFields();

        contentPane.addEventHandler(MouseEvent.MOUSE_CLICKED, Event::consume);
    }

    /**
     * Fill all fields from the item
     */
    private void updateAllVisibleFields() {
        setNameLabel(item.getName());
        setDescription(item.getDescription());
        setUserRequirements(item.getUserRequirements());
        setAmountLabel(item.getAmount() + "");
        setConditionSlider(item.getCondition());
        setReservableLabel(item.isReservable() + "");
        setReservableBtn(item.isReservable());
        setLocationLabel(StoreIT.getCurrentOrganisation().getLocation(item.getLocationID()).getName());
        setImage(PictureHandler.getItemImage(item.getID()));
        setTeamOwnerLabel(itemOwner.getName());
    }


    private void setConditionSlider(Condition condition) {
        int value = 0;
        switch (condition) {
            case BAD:
                value = 0;
                break;
            case GOOD:
                value = 1;
                break;
            case GREAT:
                value = 2;
                break;
            default:
                break;
        }
        detailViewConditionSlider.setValue(value);
    }

    private void setDescription(String string) {
        detailViewDescriptionTA.setText(string);
    }

    private void setUserRequirements(String string) {
        detailViewUserRequirementsTA.setText(string);
    }

    private void setImage(Image image) {
        detailViewImageView.setImage(image);
    }

    private void setNameLabel(String name) {
        detailViewNameLabel.setText(name);
    }

    private void setAmountLabel(String amount) {
        detailViewAmountLabel.setText("Amount: " + amount);
    }

    private void setLocationLabel(String location) {
        detailViewLocationLabel.setText("Location: " + location);
    }

    private void setTeamOwnerLabel(String teamOwner) {
        detailViewTeamOwnerLabel.setText("Owner: " + teamOwner);
    }

    private void setReservableLabel(String reservable) {
        detailViewReservableLabel.setText("Reservable: " + reservable);
    }

    private void setReservableBtn(boolean reservable) {
        detailViewReserveBtn.setDisable(!reservable);
    }
}





