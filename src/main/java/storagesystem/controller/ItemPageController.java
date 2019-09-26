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
        setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam id vehicula lectus, quis sagittis erat. Fusce feugiat, dui a facilisis laoreet, nisi risus aliquam neque," +
                " non volutpat nunc nulla ultricies dui. Proin lobortis nulla quis leo " +
                "consequat varius. Aliquam eu erat ornare diam imperdiet interdum pellentesque et libero. Curabitur volutpat eleifend facilisis. Duis molestie blandit" +
                " suscipit. Nam varius lacus turpis, nec venenatis erat ultrices sed. Aenean pretium nisi" +
                "sed volutpat lobortis. \n Suspendisse interdum vulputate suscipit. Maecenas in dui a tellus condimentum commodo. Proin auctor massa et dictum " +
                "malesuada. Curabitur eu dui iaculis, semper eros nec, mollis nisi. Nam porta lectus sed magna pellentesque, id fringilla leo posuere." +
                "Donec nisl erat, sollicitudin id faucibus in, posuere eget lectus. Nunc ultrices felis vel elit faucibus, eu efficitur est sagittis.\n" +
                "\n" + "Nullam maximus arcu ac elit ultricies, at maximus nibh dignissim. In commodo nibh et varius imperdiet. Suspendisse sed " +
                "nulla quis nunc porta accumsan. Proin ac elementum odio. Duis consectetur, urna nec facilisis euismod, felis nisi efficitur " +
                "dolor, a eleifend odio velit dapibus ante. Praesent malesuada quis risus vitae congue. \n Vestibulum ante ipsum primis in faucibus " +
                "orci luctus et ultrices posuere cubilia Curae; Maecenas mauris quam, semper at venenatis varius, pulvinar et nibh. Duis sollicitudin " +
                "ligula at arcu tristique, ut pretium sem condimentum. \n Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras ultricies risus " +
                "nec fringilla vulputate. Aenean volutpat, nunc et aliquet luctus, erat mi tempus ligula, et tristique arcu odio sit amet dolor. Donec " +
                "egestas orci vitae quam facilisis gravida");
        setUserRequirements("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam id vehicula lectus, quis sagittis erat. Fusce feugiat, dui a facilisis laoreet, nisi risus aliquam neque," +
                " non volutpat nunc nulla ultricies dui. Proin lobortis nulla quis leo " +
                "consequat varius. Aliquam eu erat ornare diam imperdiet interdum pellentesque et libero. Curabitur volutpat eleifend facilisis. Duis molestie blandit" +
                " suscipit. Nam varius lacus turpis, nec venenatis erat ultrices sed. Aenean pretium nisi" +
                "sed volutpat lobortis. \n Suspendisse interdum vulputate suscipit. Maecenas in dui a tellus condimentum commodo. Proin auctor massa et dictum " +
                "malesuada. Curabitur eu dui iaculis, semper eros nec, mollis nisi. Nam porta lectus sed magna pellentesque, id fringilla leo posuere." +
                "Donec nisl erat, sollicitudin id faucibus in, posuere eget lectus. Nunc ultrices felis vel elit faucibus, eu efficitur est sagittis.\n" +
                "\n" + "Nullam maximus arcu ac elit ultricies, at maximus nibh dignissim. In commodo nibh et varius imperdiet. Suspendisse sed " +
                "nulla quis nunc porta accumsan. Proin ac elementum odio. Duis consectetur, urna nec facilisis euismod, felis nisi efficitur " +
                "dolor, a eleifend odio velit dapibus ante. Praesent malesuada quis risus vitae congue. \n Vestibulum ante ipsum primis in faucibus " +
                "orci luctus et ultrices posuere cubilia Curae; Maecenas mauris quam, semper at venenatis varius, pulvinar et nibh. Duis sollicitudin " +
                "ligula at arcu tristique, ut pretium sem condimentum. \n Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras ultricies risus " +
                "nec fringilla vulputate. Aenean volutpat, nunc et aliquet luctus, erat mi tempus ligula, et tristique arcu odio sit amet dolor. Donec " +
                "egestas orci vitae quam facilisis gravida");
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

    protected void setDescription(String string){
        itemPageDescriptionTA.setText(string);
    }

    protected void setUserRequirements(String string){
        itemPageUserRequirementsTA.setText(string);
    }
}





