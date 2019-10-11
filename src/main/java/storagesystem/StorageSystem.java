package storagesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import storagesystem.model.*;
import storagesystem.services.GSONHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StorageSystem extends Application {
    private static List<Organisation> organisations = new ArrayList<>();
    private static List<Item> items = new ArrayList<>();
    private static User currentUser;
    private static Organisation currentOrganisation;



    @Override
    public void start(Stage stage) throws Exception {
        initializeBackend();

        Parent root = FXMLLoader.load(getClass().getResource("/loginPage.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads all data into the program. Should be run at start.
     */
    private void initializeBackend() throws IOException {
        Organisation informationsteknik = new Organisation("Informationsteknik");
        Organisation data = new Organisation("Data");

        Team tempTeam = new Team("sexNollK");
        Team tempTeam2 = new Team("P.R.NollK");

        File itemFile = new File("src/main/resources/json/itemDB.json");
        Image itemImage = new Image("pictures/art.png");
        Location hasen = new Location("Hasen", "outside of Hubben 2.1");

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

        Item shoes = new Item();
        GSONHandler.clearJson(itemFile.getPath());
        GSONHandler.addToJson(shoes, itemFile.getPath());

        items = GSONHandler.getListFromJson(itemFile.getPath(), Item.class);

        organisations.add(informationsteknik);
        organisations.add(data);
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
