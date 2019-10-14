package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import storagesystem.StorageSystem;
import storagesystem.model.Item;

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

    @FXML
    private Label name;

    @FXML
    private Label amount;

    @FXML
    private Label itemLocation;

    @FXML
    private Label condition;

    @FXML
    AnchorPane rootPane;

    Item thisItem;

    public InventoryListItemController(Item item, AnchorPane rootpane) {

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/InventoryListItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.name.setText(item.getName());
        this.amount.setText("" + item.getAmount());
        this.itemLocation.setText("" + item.getLocation());
        this.condition.setText("" + item.getCondition());

        this.rootPane = rootpane;
        this.thisItem = item;
    }

    @FXML
    private void onClick(){

        rootPane.getChildren().add(new ItemPageController(this.thisItem, StorageSystem.getCurrentTeam()))

    }

}
