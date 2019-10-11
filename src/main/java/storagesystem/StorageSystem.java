package storagesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import storagesystem.model.*;

import java.util.ArrayList;
import java.util.List;

public class StorageSystem extends Application {
    private static List<Organisation> organisations = new ArrayList<>();
    private static User currentUser;
    private static Organisation currentOrganisation;

    @Override
    public void start(Stage stage) throws Exception {
        initializeBackend();

        Parent root = FXMLLoader.load(getClass().getResource("/loginPage.fxml"));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.setResizable(false);
        stage.show();

    }

    /**
     * Loads all data into the program. Should be run at start.
     */
    private void initializeBackend() {
        //Hardcoded stuff for testing
        Organisation informationsteknik = new Organisation("Informationsteknik");
        Organisation data = new Organisation("Data");

        Team tempTeam = new Team("sexNollK");
        Team tempTeam2 = new Team("P.R.NollK");

        informationsteknik.createUser("Albert");
        informationsteknik.createUser("admin");
        informationsteknik.createUser("eke");
        informationsteknik.createUser("kvick");
        informationsteknik.createUser("sponken");
        informationsteknik.createUser("giff");
        informationsteknik.createUser("steget");

        tempTeam.setTermsAndConditions("För att låna våra prylar måste prylen vara i samma skick som den var när den lånades ut. Behövs den diskas så diska den osv. Prylen ska också vara tillbaka på samma plats igen");
        tempTeam2.setTermsAndConditions("text 2");

        informationsteknik.addTeam(tempTeam);
        informationsteknik.addTeam(tempTeam2);

        tempTeam.addMember(informationsteknik.getUsers().get(0).getID());
        tempTeam.addMember(informationsteknik.getUsers().get(1).getID());
        tempTeam2.addMember(informationsteknik.getUsers().get(0).getID());
        tempTeam2.addMember(informationsteknik.getUsers().get(1).getID());

        Location location = new Location("MockLocation", "This location does not exist", new Image("creepy.jpg"));
        Item mockItem = new Item("mockItem", "This is a description", "Behave please.",
                2, Condition.GOOD, true, location, location.getImage());
        Item mockItem2 = new Item("mockItem nr 2", "This is a description", "Behave please.",
                2, Condition.GOOD, true, location, new Image("art.png"));

        organisations.add(informationsteknik);
        organisations.add(data);

        tempTeam.addItemToInventory(mockItem);
        tempTeam.addItemToInventory(mockItem2);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setCurrentUser(User currentUser) {
        StorageSystem.currentUser = currentUser;
    }

    public static void setCurrentOrganisation(Organisation currentOrganisation) {
        StorageSystem.currentOrganisation = currentOrganisation;
    }

    public static List<Organisation> getOrganisations() {
        return organisations;
    }

    public static Organisation getCurrentOrganisation() {
        return currentOrganisation;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
