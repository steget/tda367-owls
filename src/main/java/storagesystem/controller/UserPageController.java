package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import storagesystem.StoreIT;
import storagesystem.model.User;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control for userPage
 * @author Jonathan Eksberg, Pär Aronsson, Hugo Stegrell
 */

public class UserPageController extends AbstractFader implements Initializable {

    @FXML
    private AnchorPane editProfileAnchorPane;

    @FXML
    private AnchorPane viewProfileAnchorPane;

    @FXML
    private TextField profileNameInput;

    @FXML
    private TextField profileContactInput;

    @FXML
    private TextField profileDescriptionInput;

    @FXML
    private TextArea profileDescriptionTextArea;

    @FXML
    private Text nameInputEmptyError;

    @FXML
    private Text contactInputEmptyError;

    @FXML
    private Label profileNameLabel;

    @FXML
    private Label profileContactLabel;

    @FXML
    private Label profileOrganisationLabel;

    @FXML
    private Button editProfileButton;

    @FXML
    private Button cancelButton;
    
    private User currentUser;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        currentUser = StoreIT.getCurrentUser();
        writeProfileInfo();
    }

    /**
     * Save the current users data that was put in.
     */
    @FXML
    public void saveUser() {
        if(validateInfo(nameInputEmptyError, profileNameInput) && validateInfo(contactInputEmptyError, profileContactInput)){
            currentUser.setName(profileNameInput.getText());
            currentUser.setDescription(profileDescriptionInput.getText());
            currentUser.setContactInformation(profileContactInput.getText());
            writeProfileInfo();
            viewProfileAnchorPane.toFront();
        }
    }

    private void writeProfileInfo(){
        profileNameLabel.setText(currentUser.getName());
        profileContactLabel.setText(currentUser.getContactInformation());
        profileDescriptionTextArea.setText(currentUser.getDescription());
        profileOrganisationLabel.setText(StoreIT.getCurrentOrganisation().getName());
        profileNameInput.setText(currentUser.getName());
        profileContactInput.setText(currentUser.getContactInformation());
        profileDescriptionInput.setText(currentUser.getDescription());
    }

    private boolean validateInfo(Text text, TextField textField){
        if(textField.getText().trim().isEmpty()){
            fadeTransition(text, 3);
            return false;
        }
        return true;
    }

    @FXML
    public void editProfileButtonPressed(){
        editProfileAnchorPane.toFront();
    }

    @FXML
    public void cancelButtonPressed(){
        viewProfileAnchorPane.toFront();
    }

}
