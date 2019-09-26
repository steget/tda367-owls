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
        Item item = new Item("mockItem", "This is a description", "Behave please.", 1, 2, Condition.GOOD, false, location, location.getImage());
        Team mockTeam = new Team();

        setIDLabel("" + item.getID());
        setAmountLabel(item.getAmount() + "");
        setLocationLabel(item.getLocation().getName());
        setTeamOwnerLabel(mockTeam.getName());
        setReservableLabel(item.isReservable() + "");
        setNameLabel(item.getName());
        setConditionSlider(2);
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

    protected void setConditionSlider(int condition) {
        itemPageConditionSlider.setValue(condition);
    }






}





