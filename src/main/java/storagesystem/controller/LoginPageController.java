package storagesystem.controller;

import javafx.animation.ParallelTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    TextField userNameTextField;

    @FXML
    TextField passwordTextField;

    @FXML
    ChoiceBox organisationChoiceBox;

    @FXML
    Button loginButton;

    @FXML
    Button registrationButton;

    @FXML
    AnchorPane rootPane;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //todo Fill list with organisations from database
        ObservableList<String> organisations = FXCollections.observableArrayList("Informationsteknik", "Data");

        organisationChoiceBox.setItems(organisations);
        organisationChoiceBox.setValue(organisations.get(0)); //show first value in box
    }

    /**
     * attempts to login with the entered credentials in userNameTextField and passwordTextField
     */
    @FXML
    private void loginButtonPressed() throws IOException {
        //check username and password against database
        if (/*login succeeded*/ true) {
            //open dashboard

            Parent root = FXMLLoader.load(getClass().getResource("/dashboard.fxml"));
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }
}
