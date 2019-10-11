package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import storagesystem.model.Condition;
import storagesystem.model.Item;
import storagesystem.model.Team;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controls a detailed view of an item. Can be used to book an item.
 */
public class ItemPageController {

    private final Item item;
    private final Team itemOwner;

    @FXML
    private ImageView itemPageImageView;
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

    public ItemPageController(Item item, Team itemOwner) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("itemPage.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.item = item;
        this.itemOwner = itemOwner;
        initialize();
    }

    public void initialize() {
        updateAllVisibleFields();
    }

    /**
     * Fill all fields from the item
     */
    private void updateAllVisibleFields() {
        setNameLabel(item.getName());
        setDescription(item.getDescription());
        setUserRequirements(item.getUserRequirements());
        setIDLabel("" + item.getID());
        setAmountLabel(item.getAmount() + "");
        setConditionSlider(item.getCondition());
        setReservableLabel(item.isReservable() + "");
        setReservableBtn(item.isReservable());
        setLocationLabel(item.getLocation().getName());
        setTeamOwnerLabel(itemOwner.getName());
        setReservableLabel(item.isReservable() + "");
        setNameLabel(item.getName());
        setConditionSlider(item.getCondition());
        setDescription(item.getDescription());
        setUserRequirements(item.getUserRequirements());
        setImage(item.getImage());
    }

    @FXML
    protected void itemPageReserveBtnPressed() {
        //TODO: create a new reservation if possible
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
                //TODO: Exception handling
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





