package storagesystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import storagesystem.StorageSystem;
import storagesystem.model.Organisation;
import storagesystem.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private ChoiceBox organisationChoiceBox;

    @FXML
    private Button loginButton;

    @FXML
    private Button registrationButton;

    @FXML
    private AnchorPane rootPane;

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

            //open dashboard
            Parent root = FXMLLoader.load(getClass().getResource("/dashboard.fxml"));
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } else {
            System.out.println("User with name \"" +  userNameTextField.getText() + "\" does not exist");
        }
    }

    /**
     * Check if selected value in the Organisation Choicebox actually correspsonds to an existing organisation in the database
     * @return The actual organisation from the database
     */
    private Organisation getSelectedOrganisation(){
        String selectedOrganisation = organisationChoiceBox.getValue().toString();
        for (Organisation org :
                StorageSystem.getOrganisations()) {
            if (org.getName().equals(selectedOrganisation)) {
                return org;
            }
        }
        //todo throw exception?
        return null;
    }

    /**
     * Checks in the selected organisation if there is an user with the name currently written in the Username textfield
     * @return
     */
    private boolean doesUserExist() {
        Organisation selectedOrganisation = getSelectedOrganisation();

        //is an organisation selected?
        if (selectedOrganisation != null) {
            //check if user exists in the organisation
            for (User user :
                    selectedOrganisation.getUsers()) {
                if (user.getName().equals(userNameTextField.getText())) {
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
     * @param user An actual User
     */
    private void setLoginUser(User user) {
        loginUser = user;
    }
}
