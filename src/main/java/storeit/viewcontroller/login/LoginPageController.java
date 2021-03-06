package storeit.viewcontroller.login;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import storeit.model.Organisation;
import storeit.model.StoreIT;
import storeit.viewcontroller.AbstractFader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    private Label loginErrorMessage;

    @FXML
    private TextField userNameTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ChoiceBox<String> organisationChoiceBox;

    @FXML
    private Button loginButton;

    @FXML
    private Button registrationButton;

    @FXML
    private AnchorPane rootPane;

    @FXML
    private Label userRegisteredLabel;

    @FXML
    private Label userAlreadyExistsLabel;

    @FXML
    private Label enterAllFieldsLabel;

    @FXML
    private TextField regUserNameTextField;

    @FXML
    private PasswordField regPasswordTextField;

    @FXML
    private ChoiceBox<String> regOrganisationChoiceBox;

    @FXML
    private TextField regContactInfoTextField;

    @FXML
    private TextArea regUserDescriptionTextArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Fill list with organisations
        ObservableList<String> organisationNames = FXCollections.observableArrayList();
        for (Organisation org :
                StoreIT.getOrganisations()) {
            organisationNames.add(org.getName());
        }

        //load all organisation names into the choiceboxes
        organisationChoiceBox.setItems(organisationNames);
        regOrganisationChoiceBox.setItems(organisationNames);
        //show first value in box
        organisationChoiceBox.setValue(organisationNames.get(0));
        regOrganisationChoiceBox.setValue(organisationNames.get(0));

        assignHandlers();

        Platform.runLater(() -> userNameTextField.requestFocus()); //Need to do this since Stage is not set yet when in initialize
        if(StoreIT.getCurrentUser() != null){
            userNameTextField.setText(StoreIT.getCurrentUser().getName());
        }
    }

    /**
     * Add function handlers to each field on the login page so that if the ENTER key is pressed the program parses it as if the users wants to login.
     */
    private void assignHandlers() {
        ArrayList<Control> loginFields = new ArrayList<>();
        loginFields.add(userNameTextField);
        loginFields.add(passwordField);
        loginFields.add(loginButton);
        loginFields.add(organisationChoiceBox);

        for (Control loginField :
                loginFields) {
            loginField.setOnKeyPressed(event -> {
                if (event.getCode() == KeyCode.ENTER) {
                    try {
                        loginButtonPressed();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * Attempts to login with the entered credentials in userNameTextField and passwordTextField.
     * If the user doesn't exist a message shows up and fades out in the view.
     * If the login is successful the dashboard will open.
     */
    @FXML
    private void loginButtonPressed() throws IOException {
        Organisation selectedOrganisation = getSelectedLoginOrganisation();
        StoreIT.setCurrentOrganisation(selectedOrganisation);
        //check username and password against database
        if (checkLoginCredentials()) {
            //set current user
            StoreIT.setCurrentUser(userNameTextField.getText());
            StoreIT.setCurrentOrganisation(selectedOrganisation);
            if (StoreIT.getCurrentOrganisation().getUsersTeams(StoreIT.getCurrentUser()).size() > 0) {
                StoreIT.setCurrentTeam(StoreIT.getCurrentOrganisation().getUsersTeams(StoreIT.getCurrentUser()).get(0));
            }

            //open dashboard
            Parent root = FXMLLoader.load(getClass().getResource("/framework.fxml"));
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            AbstractFader.fadeTransition(loginErrorMessage, 10);
        }
    }

    /**
     * Attempts to register a user with the values in the TextFields. If there already is a user with that name an error message shows in the console.
     */
    @FXML
    private void registerButtonPressed() {
        //make sure there is no user with the name
        if (!doesUserExist(regUserNameTextField.getText())) {
            //make sure there is data in all fields
            if (isAllRegisterFieldsEntered()) {
                String name = regUserNameTextField.getText();
                String password = regPasswordTextField.getText();
                String desc = regUserDescriptionTextArea.getText();
                String contactInfo = regContactInfoTextField.getText();

                StoreIT.setCurrentOrganisation(getSelectedRegisterOrganisation());
                StoreIT.createUser(name, password, desc, contactInfo);
                AbstractFader.fadeTransition(userRegisteredLabel, 2);
            } else {
                AbstractFader.fadeTransition(enterAllFieldsLabel, 3);
            }
        } else {
            AbstractFader.fadeTransition(userAlreadyExistsLabel, 3);
        }
    }

    private boolean isAllRegisterFieldsEntered() {
        return regUserNameTextField.getLength() > 0 &&
                regPasswordTextField.getLength() > 0 &&
                regUserDescriptionTextArea.getLength() > 0 &&
                regContactInfoTextField.getLength() > 0 &&
                regOrganisationChoiceBox.getValue() != null;
    }

    /**
     * Check if selected value in the Login Organisation Choicebox actually corresponds to an existing organisation in the database and then get it
     *
     * @return The actual organisation from the database
     */
    private Organisation getSelectedLoginOrganisation() {
        return StoreIT.findOrganisation(organisationChoiceBox.getValue());
    }

    /**
     * Check if selected value in the Register Organisation Choicebox actually corresponds to an existing organisation in the database and then get it
     *
     * @return The actual organisation from the database
     */
    private Organisation getSelectedRegisterOrganisation() {
        return StoreIT.findOrganisation(regOrganisationChoiceBox.getValue());
    }

    private boolean checkLoginCredentials() {
        if (userNameTextField.getLength() > 0 && passwordField.getLength() > 0) {
            return StoreIT.doesLoginMatch(userNameTextField.getText(), passwordField.getText());
        } else {
            return false;
        }
    }

    /**
     * Checks in the selected organisation if there is an user with the name entered
     *
     * @return If the written username exists within the selected Organisation
     */
    private boolean doesUserExist(String name) {
        return StoreIT.doesUserExist(getSelectedLoginOrganisation(), name);
    }
}
