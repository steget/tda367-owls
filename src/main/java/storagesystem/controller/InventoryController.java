package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import storagesystem.StorageSystem;
import storagesystem.model.Item;
import storagesystem.model.Organisation;
import storagesystem.model.Team;
import storagesystem.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    Organisation currentOrganisation;
    Team userTeam;
    User currentUser;
    List<Item> inventory;

    @FXML
    FlowPane itemPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        currentOrganisation = StorageSystem.getCurrentOrganisation();
        currentUser = StorageSystem.getCurrentUser();

        InventoryListItemController panePane = new InventoryListItemController("Bollhavsboll", "100", "toppen", "Ingen", "HASen");
        itemPane.getChildren().add(panePane);
    }
}
