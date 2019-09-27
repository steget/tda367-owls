package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        Location location = new Location("MockLocation", "This location does not exist", new Image("creepy.jpg"));
        Item mockItem = new Item("mockItem", "This is a description", "Behave please.", 1, 2, Condition.GOOD, false, location, location.getImage());
        Team mockTeam = new Team();
        setAll(mockItem, mockTeam);

    }

    protected void setAll(Item item, Team owner) { //TODO: better method name?
        setNameLabel(item.getName());
        setDescription(item.getDescription());
        setUserRequirements(item.getUserRequirements());
        setIDLabel("" + item.getID());
        setAmountLabel(item.getAmount() + "");
        setConditionSlider(item.getCondition());
        setReservableLabel(item.isReservable() + "");
        setLocationLabel(item.getLocation().getName());
        setImage(item.getImage());
        setTeamOwnerLabel(owner.getName());
    }


    protected void setNameLabel(String name) {
        itemPageNameLabel.setText(name);
    }

    protected void setIDLabel(String id) {
        itemPageIDLabel.setText(id);
    }

    protected void setAmountLabel(String amount) {
        itemPageAmountLabel.setText(amount);
    }

    protected void setLocationLabel(String location) {
        itemPageLocationLabel.setText(location);
    }

    protected void setTeamOwnerLabel(String teamOwner) {
        itemPageTeamOwnerLabel.setText(teamOwner);
    }

    protected void setReservableLabel(String reservable) {
        itemPageReservableLabel.setText(reservable);
    }

    protected void setConditionSlider(Condition condition) {
        int value = 0;
        switch(condition) {
            case BAD:
                value = 0;
                break;
            case GOOD:
                value = 1;
                break;
            case GREAT:
                value = 2;
                break;
            default://TODO: Exception handling
                value = 0;
                break;
        }
        itemPageConditionSlider.setValue(value);
    }

    protected void setDescription(String string){
        itemPageDescriptionTA.setText(string);
    }

    protected void setUserRequirements(String string){
        itemPageUserRequirementsTA.setText(string);
    }

    protected void setImage(Image image) {
        itemPageImageView.setImage(image);
    }
}





