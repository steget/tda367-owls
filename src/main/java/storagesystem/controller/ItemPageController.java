package storagesystem.controller;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import storagesystem.model.Condition;
import storagesystem.model.IReservable;
import storagesystem.model.Team;

import java.io.IOException;

/**
 * Controls a detailed view of an item. Can be used to book an item.
 *
 * @author Jonathan Eksberg, Carl Lindh
 */
public class ItemPageController extends AnchorPane {

    private final IReservable reservableItem;
    private final Team itemOwner;

    @FXML
    private AnchorPane rootPane;
    @FXML
    private AnchorPane contentPane;
    @FXML
    private ImageView itemPageImageView;
    @FXML
    private ImageView closeButtonImageView;
    @FXML
    private Label itemPageNameLabel;
    @FXML
    private Label itemPageIDLabel;
    @FXML
    private Label itemPageAmountLabel;
    @FXML
    private Label itemPageLocationLabel;
    @FXML
    private Label itemPageTeamOwnerLabel;
    @FXML
    private Label itemPageReservableLabel;
    @FXML
    private Slider itemPageConditionSlider;
    @FXML
    private TextArea itemPageDescriptionTA;
    @FXML
    private TextArea itemPageUserRequirementsTA;
    @FXML
    private Button itemPageReserveBtn;

    public ItemPageController(IReservable reservableItem, Team itemOwner) {
        this.reservableItem = reservableItem;
        this.itemOwner = itemOwner;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/itemPage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        initialize();
    }

    private void initialize() {
        updateAllVisibleFields();
        closeButtonImageView.setImage(new Image("pictures/close-button.png"));

        //consume click so the box doesn't close itself
        contentPane.setOnMouseClicked(Event::consume);
    }

    /**
     * Fill all fields from the item
     */
    private void updateAllVisibleFields() {
        setNameLabel(reservableItem.getName());
        setDescription(reservableItem.getDescription());
        setUserRequirements(reservableItem.getUserRequirements());
        setIDLabel("" + reservableItem.getID());
        setAmountLabel("" + reservableItem.getAmount());
        setConditionSlider(reservableItem.getCondition());
        setReservableLabel("" + reservableItem.isReservable());
        setReservableBtn(reservableItem.isReservable());
        setLocationLabel(reservableItem.getLocation().getName());
        setImage(reservableItem.getImage());
    }

    @FXML
    protected void itemPageReserveBtnPressed() {
        //TODO: create a new reservation if possible
    }

    @FXML
    private void closeItemPage() {
        //todo test and fix
        AnchorPane parent = (AnchorPane) rootPane.getScene().lookup("#itemListRootPane");
        parent.getChildren().remove(this);
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
        itemPageConditionSlider.setValue(value);
    }

    private void setDescription(String string) {
        itemPageDescriptionTA.setText(string);
    }

    private void setUserRequirements(String string) {
        itemPageUserRequirementsTA.setText(string);
    }

    private void setImage(Image image) {
        itemPageImageView.setImage(image);
    }

    private void setNameLabel(String name) {
        itemPageNameLabel.setText(name);
    }

    private void setIDLabel(String id) {
        itemPageIDLabel.setText("ID: " + id);
    }

    private void setAmountLabel(String amount) {
        itemPageAmountLabel.setText("Amount: " + amount);
    }

    private void setLocationLabel(String location) {
        itemPageLocationLabel.setText("Location: " + location);
    }

    private void setTeamOwnerLabel(String teamOwner) {
        itemPageTeamOwnerLabel.setText("Owner: " + teamOwner);
    }

    private void setReservableLabel(String reservable) {
        itemPageReservableLabel.setText("Reservable: " + reservable);
    }

    private void setReservableBtn(boolean reservable) {
        itemPageReserveBtn.setDisable(!reservable);
    }
}





