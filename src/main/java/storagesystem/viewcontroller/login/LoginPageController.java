package storagesystem.viewcontroller.login;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import storagesystem.model.StoreIT;
import storagesystem.model.Organisation;
import storagesystem.model.User;

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
    private TextField passwordTextField;

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
    private TextField regUserNameTextField;

    @FXML
    private TextField regPasswordTextField;

    @FXML
    private ChoiceBox<String> regOrganisationChoiceBox;

    @FXML
    private TextField regContactInfoTextField;

    @FXML
    private TextArea regUserDescriptionTextArea;

    private User loginUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Fill list with organisations from database
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
        userNameTextField.setText("admin");
    }

    /**
     * Add function handlers to each field on the login page so that if the ENTER key is pressed the program parses it as if the users wants to login.
     */
    private void assignHandlers() {
        ArrayList<Control> loginFields = new ArrayList<>();
        loginFields.add(userNameTextField);
        loginFields.add(passwordTextField);
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
        //check username and password against database
        if (doesUserExist(selectedOrganisation, userNameTextField.getText())) {
            //set current user
            StoreIT.setCurrentUser(loginUser);
            StoreIT.setCurrentOrganisation(selectedOrganisation);

            //open dashboard
            Parent root = FXMLLoader.load(getClass().getResource("/framework.fxml"));
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            fadeTransition(loginErrorMessage, 10);
        }
    }

    /**
     * Attempts to register a user with the values in the TextFields. If there already is a user with that name an error message shows in the console.
     */
    @FXML
    private void registerButtonPressed() {
        Organisation selectedOrganisation = getSelectedRegisterOrganisation();
        //make sure there is no user with the name
        if (!doesUserExist(selectedOrganisation, regUserNameTextField.getText())) {
            String name = regUserNameTextField.getText();
            String password = regPasswordTextField.getText();
            String desc = regUserDescriptionTextArea.getText();
            String contactInfo = regContactInfoTextField.getText();
            StoreIT.createUser(name, password, desc, contactInfo);
            fadeTransition(userRegisteredLabel, 2);
        } else {
            fadeTransition(userAlreadyExistsLabel, 3);
        }
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



    /**
     * Checks in the selected organisation if there is an user with the name currently written in the Username textfield
     *
     * @return If the written username exists within the selected Organisation
     */
    private boolean doesUserExist(Organisation organisationToSearch, String name) {
        for (User user :
                organisationToSearch.getUsers()) {
            if (user.getName().equals(name)) { //todo check ID instead?
                setLoginUser(user);
                //todo password?
                return true;
            }
        }
        setLoginUser(null); //to prevent someone from logging in with a previous users credentials
        return false;
    }

    /**
     * Save a User which is trying to login
     *
     * @param user An actual User
     */
    private void setLoginUser(User user) {
        loginUser = user;
    }

    /**
     * Takes in a JavaFX Node and starts with setting the opacity to full and fading out to 0 so the Node no longer shows.
     *
     * @param node          Thing to be faded
     * @param timeInSeconds How long it should take for the opacity to go from full to not visible.
     */
    private void fadeTransition(Node node, int timeInSeconds) {
        TranslateTransition transition = new TranslateTransition();

        transition.setOnFinished((e) -> {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(timeInSeconds), node);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();
        });
        transition.play();
    }
}
