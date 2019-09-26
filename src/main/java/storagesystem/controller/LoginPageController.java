package storagesystem.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //todo Fill list with organisations from database
        ObservableList<String> organisations = FXCollections.observableArrayList("Informationsteknik", "Data");

        organisationChoiceBox.setItems(organisations);
        organisationChoiceBox.setValue(organisations.get(0)); //show first value in box
    }
}
