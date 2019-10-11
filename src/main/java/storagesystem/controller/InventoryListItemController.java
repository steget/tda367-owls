package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import java.io.IOException;

public class InventoryListItemController extends AnchorPane {

    public Label getName() {
        return name;
    }

    public Label getAmount() {
        return amount;
    }

    public Label getLocation() {
        return itemLocation;
    }

    public Label getCondition() {
        return condition;
    }

    public Label getCurrentRenter() {
        return currentRenter;
    }
    @FXML
    private Label name;

    @FXML
    private Label amount;

    @FXML
    private Label itemLocation;

    @FXML
    private Label condition;

    @FXML
    private Label currentRenter;

    public InventoryListItemController(String name, String amount, String condition, String currentRenter, String location) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/InventoryListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.name.setText(name);
        this.amount.setText(amount);
        this.itemLocation.setText(location);
        this.condition.setText(condition);
        this.currentRenter.setText(currentRenter);
    }

    @FXML
    private void onClick(){
        //todo göra så att ett klick öppnar itemVy
    }

}
