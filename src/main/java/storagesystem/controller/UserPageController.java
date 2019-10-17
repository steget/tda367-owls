package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import storagesystem.StoreIT;
import storagesystem.model.User;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control for userPage
 * @author Jonathan Eksberg, Pär Aronsson, Hugo Stegrell
 */

public class UserPageController implements Initializable {

    @FXML
    private TextField settingsNameInput;

    @FXML
    private TextField settingsContactInput;

    @FXML
    private TextField settingsDescriptionInput;

    private User currentUser;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentUser = StoreIT.getCurrentUser();

        //fill user text boxes
        settingsNameInput.setText(currentUser.getName());
        settingsDescriptionInput.setText(currentUser.getDescription());
        settingsContactInput.setText(currentUser.getContactInformation());
    }

    /**
     * Save the current users data that was put in.
     */
    @FXML
    public void saveUser() {
        currentUser.setName(settingsNameInput.getText());
        currentUser.setDescription(settingsDescriptionInput.getText());
        currentUser.setContactInformation(settingsContactInput.getText());
    }
}
