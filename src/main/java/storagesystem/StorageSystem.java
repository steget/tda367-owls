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
    public static List<Organisation> organisations = new ArrayList<>();
    public static User currentUser;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/loginPage.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();

        initializeBackend();
    }

    private void initializeBackend() {
        Organisation informationsteknik = new Organisation("Informationsteknik");
        Team tempTeam = new Team("sexNollK");
        informationsteknik.createUser("Albert");

        Organisation data = new Organisation("Data");

        informationsteknik.addTeam(tempTeam);
        tempTeam.addMember(informationsteknik.getUsers().get(0).getID());

        organisations.add(informationsteknik);
        organisations.add(data);
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setCurrentUser(User currentUser){
        StorageSystem.currentUser = currentUser;
    }
}
