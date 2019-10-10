package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import storagesystem.model.Condition;
import storagesystem.model.Item;
import storagesystem.model.Location;
import storagesystem.model.Team;

import java.net.URL;
import java.util.ResourceBundle;

public class ItemPageController implements Initializable {

    private Item mockItem;

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

    /**
     * initialize() initializes the controller with neccessary variables.
     * @param url
     * @param resources
     */

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        Item mockItem = new Item("mockItem", "This is a description", "Behave please.", 1, 2, Condition.GOOD, true, location, location.getImage());
        Team mockTeam = new Team("mockTeam");
        setAll(mockItem, mockTeam);
        Location location = new Location("MockLocation", "This location does not exist", new Image("pictures/creepy.jpg"));
    }

    /**
     * setAll() utilizes ALL set methods in ItemPageController to set all fxml objects according to the Item and Team that is provided.
     * @param item
     * @param owner
     */
    
    protected void setAll(Item item, Team owner) { //TODO: better method name?
        setNameLabel(item.getName());
        setDescription(item.getDescription());
        setUserRequirements(item.getUserRequirements());
        setIDLabel("" + item.getID());
        setAmountLabel(item.getAmount() + "");
        setConditionSlider(item.getCondition());
        setReservableLabel(item.isReservable() + "");
        setReservableBtn(item.isReservable());
        setLocationLabel(item.getLocation().getName());
        setTeamOwnerLabel(owner.getName());
        setReservableLabel(item.isReservable() + "");
        setNameLabel(item.getName());
        setConditionSlider(Condition.GOOD);
        setDescription(item.getDescription());
        setUserRequirements(item.getUserRequirements());
        setImage(item.getImage());
    }


    protected void setNameLabel(String name) {
        itemPageNameLabel.setText(name);
    }

    protected void setIDLabel(String id) {
        itemPageIDLabel.setText("ID: " + id);
    }

    protected void setAmountLabel(String amount) {
        itemPageAmountLabel.setText("Amount: " + amount);
    }

    protected void setLocationLabel(String location) {
        itemPageLocationLabel.setText("Location: " + location);
    }

    protected void setTeamOwnerLabel(String teamOwner) {
        itemPageTeamOwnerLabel.setText("Owner: " + teamOwner);
    }

    protected void setReservableLabel(String reservable) {
        itemPageReservableLabel.setText("Reservable: " + reservable);
    }

    protected void setReservableBtn(boolean reservable) {
        itemPageReserveBtn.setDisable(!reservable);
    }

    /**
     * updates the fxml reservable label and button to fit the mockItem
      */

    protected void updateReservable() {
        mockItem.setReservable(!mockItem.isReservable());
        setReservableBtn(mockItem.isReservable());
        setReservableLabel(""+mockItem.isReservable());
    }

    protected void setConditionSlider(Condition condition) {
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

    protected void setDescription(String string) {
        itemPageDescriptionTA.setText(string);
    }

    protected void setUserRequirements(String string) {
        itemPageUserRequirementsTA.setText(string);
    }

    protected void setImage(Image image) {
        itemPageImageView.setImage(image);
    }

    /**
     * Runs updateReservable() when itemPageReserveBtn is pressed.
     */

    @FXML
    protected void itemPageReserveBtnPressed() {
        updateReservable();
    }
}





