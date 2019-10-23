package storagesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import storagesystem.model.StoreIT;
import storagesystem.services.JSONHandler;

import java.io.IOException;

public class Main extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StoreIT storeIT = new StoreIT();
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

    @Override
    public void stop() throws IOException {
        JSONHandler.save(StoreIT.getOrganisations());
    }
}
