package storagesystem.controller;

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
import storagesystem.StorageSystem;
import storagesystem.model.Organisation;
import storagesystem.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

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

    private User loginUser;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //Fill list with organisations from database
        ObservableList<String> organisationNames = FXCollections.observableArrayList();
        for (Organisation org :
                StorageSystem.getOrganisations()) {
            organisationNames.add(org.getName());
        }

        organisationChoiceBox.setItems(organisationNames);
        organisationChoiceBox.setValue(organisationNames.get(0)); //show first value in box

        assignHandlers();

        Platform.runLater(()->userNameTextField.requestFocus()); //Need to do this since Stage is not set yet when in initialize
    }

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
     * attempts to login with the entered credentials in userNameTextField and passwordTextField
     */
    @FXML
    private void loginButtonPressed() throws IOException {
        //check username and password against database
        if (doesUserExist()) {
            //set current user
            StorageSystem.setCurrentUser(loginUser);
            StorageSystem.setCurrentOrganisation(getSelectedOrganisation());

            //open dashboard
            Parent root = FXMLLoader.load(getClass().getResource("/dashboard.fxml"));
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            System.out.println("User with name \"" + userNameTextField.getText() + "\" does not exist");
        }
    }

    @FXML
    private void registerButtonPressed() throws IOException {
        //make sure there is no user with the name
        if (!doesUserExist()) {
            String name = userNameTextField.getText();
            getSelectedOrganisation().createUser(name);
            fadeTransition(userRegisteredLabel);
        } else {
            System.out.println("A user with that name already exists");
        }
    }

    /**
     * Check if selected value in the Organisation Choicebox actually corresponds to an existing organisation in the database
     *
     * @return The actual organisation from the database
     */
    private Organisation getSelectedOrganisation() {
        String selectedOrganisation = organisationChoiceBox.getValue().toString();
        for (Organisation org :
                StorageSystem.getOrganisations()) {
            if (org.getName().equals(selectedOrganisation)) {
                return org;
            }
        }
        //todo throw exception? or write error message saying the admin needs to add organisations
        return null;
    }

    /**
     * Checks in the selected organisation if there is an user with the name currently written in the Username textfield
     *
     * @return
     */
    private boolean doesUserExist() {
        Organisation selectedOrganisation = getSelectedOrganisation();

        //is an organisation selected?
        if (selectedOrganisation != null) {
            //check if user exists in the organisation
            for (User user :
                    selectedOrganisation.getUsers()) {
                if (user.getName().equals(userNameTextField.getText())) { //todo check ID instead?
                    setLoginUser(user);
                    //todo password?
                    return true;
                }
            }
        }
        setLoginUser(null); //to prevent someone from logging in with a previous users credentials
        return false;
    }

    /**
     * Saves the latest found User from doesUserExist
     *
     * @param user An actual User
     */
    private void setLoginUser(User user) {
        loginUser = user;
    }

    private void fadeTransition(Node node) {
        TranslateTransition transition = new TranslateTransition();

        transition.setOnFinished((e) -> {
            FadeTransition fadeOut = new FadeTransition(Duration.seconds(3), node);
            fadeOut.setFromValue(1.0);
            fadeOut.setToValue(0.0);
            fadeOut.play();
        });
        transition.play();
    }
}
