package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectController implements Initializable {

    @FXML
    AnchorPane mainAnchorPane;

    public void initialize(URL url, ResourceBundle rb) {

        mainAnchorPane.getChildren().add(new Text("Hello"));


    }
}
