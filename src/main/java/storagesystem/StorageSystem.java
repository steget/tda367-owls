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
        stage.show();
    }

    private void initializeBackend() {
        //Hardcoded stuff for testing
        Organisation informationsteknik = new Organisation("Informationsteknik");
        Team tempTeam = new Team("sexNollK");
        Team tempTeam2 = new Team("P.R.NollK");
        informationsteknik.createUser("admin");
        informationsteknik.createUser("Albert");
        informationsteknik.createUser("eke");
        informationsteknik.createUser("kvick");
        informationsteknik.createUser("sponken");
        informationsteknik.createUser("giff");
        informationsteknik.createUser("steget");

        Organisation data = new Organisation("Data");

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

    /**
     * @return A deep copy of all organisations
     */
    public static List<Organisation> getDeepCopyOrganisations() {
        List<Organisation> deepCopyOrganisations = new ArrayList<>();
        for (Organisation org :
                organisations) {
            deepCopyOrganisations.add(org.getDeepCopy());
        }
        return deepCopyOrganisations;
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
