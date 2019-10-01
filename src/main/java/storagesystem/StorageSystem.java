package storagesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import storagesystem.model.Organisation;
import storagesystem.model.Team;
import storagesystem.model.User;

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
        Organisation informationsteknik = new Organisation("Informationsteknik");
        Team tempTeam = new Team("sexNollK");
        Team tempTeam2 = new Team("P.R.NollK");
        informationsteknik.createUser("Albert");

        Organisation data = new Organisation("Data");

        tempTeam.setTermsAndConditions("För att låna våra prylar måste prylen vara i samma skick som den var när den lånades ut. Behövs den diskas så diska den osv. Prylen ska också vara tillbaka på samma plats igen");

        informationsteknik.addTeam(tempTeam);
        informationsteknik.addTeam(tempTeam2);
        tempTeam.addMember(informationsteknik.getUsers().get(0).getID());
        tempTeam2.addMember(informationsteknik.getUsers().get(0).getID());

        organisations.add(informationsteknik);
        organisations.add(data);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setCurrentUser(User currentUser){
        StorageSystem.currentUser = currentUser;
    }

    public static void setCurrentOrganisation(Organisation currentOrganisation) {
        StorageSystem.currentOrganisation = currentOrganisation;
    }

    public static List<Organisation> getOrganisations() {
        return organisations;
    }
    public static Organisation getCurrentOrganisation(){
        return currentOrganisation;
    }

    public static User getCurrentUser() {
        return currentUser;
    }
}
