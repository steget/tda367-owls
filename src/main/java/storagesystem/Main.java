package storagesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import storagesystem.model.StoreIT;
import storagesystem.services.GSONHandler;

import java.io.IOException;

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
    @Override
    public void stop() throws IOException {
        GSONHandler.clearAllJsonFiles();
        GSONHandler.addListToJson(StoreIT.getOrganisations());
        System.out.println("Saved Organisations.");
        GSONHandler.addListToJson(StoreIT.getCurrentOrganisation().getUsers());
        System.out.println("Saved Users.");
        GSONHandler.addListToJson(StoreIT.getCurrentOrganisation().getTeams());
        System.out.println("Saved Teams.");
        GSONHandler.addListToJson(StoreIT.getCurrentOrganisation().getAllItems());
        System.out.println("Saved Items.");
        GSONHandler.addListToJson(StoreIT.getCurrentOrganisation().getLocations());
        System.out.println("Saved Locations.");
        GSONHandler.addListToJson(StoreIT.getCurrentOrganisation().getReservationHandler().getReservations());
        System.out.println("Saved Reservations.");
    }
}
