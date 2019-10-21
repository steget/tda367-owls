package storagesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import storagesystem.model.StoreIT;

public class Main extends Application {
    private StoreIT storeIT;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        storeIT = new StoreIT();
        storeIT.initializeBackend();

        stage.setTitle("StoreIT");

        Parent root = FXMLLoader.load(getClass().getResource("/login/loginPage.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.setResizable(false);
        stage.show();
    }
}
