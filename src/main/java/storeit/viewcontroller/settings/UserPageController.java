package storeit.viewcontroller.settings;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import storeit.model.StoreIT;
import storeit.viewcontroller.AbstractFader;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Control for userPage
 *
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
    private PasswordField profilePasswordInput;

    @FXML
    private TextField profileContactInput;

    @FXML
    private TextArea profileDescriptionInput;

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



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        viewProfileAnchorPane.toFront();
        writeProfileInfo();
    }

    /**
     * If the textfields are filled in, save the current users data that was put in.
     */
    @FXML
    public void saveUser() {
        if (validateInfo(nameInputEmptyError, profileNameInput) && validateInfo(contactInputEmptyError, profileContactInput)) {
            StoreIT.getCurrentUser().setName(profileNameInput.getText());
            StoreIT.getCurrentUser().setDescription(profileDescriptionInput.getText());
            StoreIT.getCurrentUser().setContactInformation(profileContactInput.getText());
            if(!profilePasswordInput.getText().isEmpty())
                StoreIT.getCurrentUser().setPassword(profilePasswordInput.getText());
            writeProfileInfo();
            viewProfileAnchorPane.toFront();
        }
    }

    /**
     * Sets the information from the current user.
     */
    private void writeProfileInfo() {
        profileNameLabel.setText(StoreIT.getCurrentUser().getName());
        profileContactLabel.setText(StoreIT.getCurrentUser().getContactInformation());
        profileDescriptionTextArea.setText(StoreIT.getCurrentUser().getDescription());
        if (profileDescriptionTextArea.getText().trim().isEmpty()) {
            profileDescriptionTextArea.setText("User does not yet have a description... :(");
        }
        profileOrganisationLabel.setText(StoreIT.getCurrentOrganisation().getName());
        profileNameInput.setText(StoreIT.getCurrentUser().getName());
        profileContactInput.setText(StoreIT.getCurrentUser().getContactInformation());
        profileDescriptionInput.setText(StoreIT.getCurrentUser().getDescription());
    }

    /**
     * Checks if a texfield is empty
     *
     * @param text      The error message that will show if an empty field is found.
     * @param textField The field that is being checked.
     * @return If empty fields are found, return false, else true.
     */
    private boolean validateInfo(Text text, TextField textField) {
        if (textField.getText().trim().isEmpty()) {
            AbstractFader.fadeTransition(text, 3);
            return false;
        }
        return true;
    }

    @FXML
    public void editProfileButtonPressed() {
        editProfileAnchorPane.toFront();
    }

    @FXML
    public void cancelButtonPressed() {
        viewProfileAnchorPane.toFront();
    }

}
