package storagesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import storagesystem.controller.ProjectController;

import java.net.URL;
import java.util.Locale;
import java.util.ResourceBundle;

public class StorageSystem extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/itemPage.fxml"));

        ProjectController projectController = new ProjectController();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
