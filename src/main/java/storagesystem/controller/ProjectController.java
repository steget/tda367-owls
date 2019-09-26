package storagesystem.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class ProjectController implements Initializable {
    @FXML
    private ImageView imageView;
    @FXML
    private Label itemPageName;

    public void initialize(URL url, ResourceBundle rb) {
        imageView.setImage(new Image("art.png"));
    }
}
