package storagesystem.viewcontroller.settings;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import storagesystem.model.StoreIT;
import storagesystem.model.User;
import storagesystem.viewcontroller.AbstractFader;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control for userPage
 * @author Jonathan Eksberg, Pär Aronsson, Hugo Stegrell
 */

public class UserPageController implements Initializable {

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
     * If the textfields are filled in, save the current users data that was put in.
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

    /**
     * Sets the information from the current user.
     */
    private void writeProfileInfo(){
        profileNameLabel.setText(currentUser.getName());
        profileContactLabel.setText(currentUser.getContactInformation());
        profileDescriptionTextArea.setText(currentUser.getDescription());
        if(profileDescriptionTextArea.getText().trim().isEmpty()){
            profileDescriptionTextArea.setText("User does not yet have a description... :(");
        }
        profileOrganisationLabel.setText(StoreIT.getCurrentOrganisation().getName());
        profileNameInput.setText(currentUser.getName());
        profileContactInput.setText(currentUser.getContactInformation());
        profileDescriptionInput.setText(currentUser.getDescription());
    }

    /**
     * Checks if a texfield is empty
     * @param text The error message that will show if an empty field is found.
     * @param textField The field that is being checked.
     * @return If empty fields are found, return false, else true.
     */
    private boolean validateInfo(Text text, TextField textField){
        if(textField.getText().trim().isEmpty()){
            AbstractFader.fadeTransition(text, 3);
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
