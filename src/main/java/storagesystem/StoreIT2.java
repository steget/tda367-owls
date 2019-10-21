package storagesystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import storagesystem.model.*;
import storagesystem.services.GSONHandler;
import storagesystem.services.IDHandler;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class StoreIT2 extends Application {
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
    private void initializeBackend() throws IOException {
        //mockData();

        organisations.addAll(GSONHandler.getListFromJson(Organisation.class));
        IDHandler.updateAllIDs(organisations);
        currentOrganisation = organisations.get(0);
    }




    @Override
    public void stop() throws IOException {
        GSONHandler.clearAllJsonFiles();
        GSONHandler.addListToJson(organisations);
        GSONHandler.addListToJson(currentOrganisation.getUsers());
        GSONHandler.addListToJson(currentOrganisation.getTeams());
        GSONHandler.addListToJson(currentOrganisation.getAllItems());
        GSONHandler.addListToJson(currentOrganisation.getLocations());
        GSONHandler.addListToJson(currentOrganisation.getReservationHandler().getReservations());

    }

    private static void mockData() throws IOException {
        //Hardcoded stuff for testing

        Location location = new Location("MockLocation", "This location does not exist", "creepy.jpg");
        Item mockItem = new Item("mockItem", "This is a description", "Behave please.",
                2, Condition.GOOD, true, location.getID(), "pictures/art.png");
        Item mockItem2 = new Item("mockItem nr 2", "This is a description", "Behave please.",
                2, Condition.GOOD, true, location.getID(), "art.png");
        GSONHandler.addToJson(location);
        GSONHandler.addToJson(mockItem);
        GSONHandler.addToJson(mockItem2);

        Team tempTeam = new Team("sexNollK");
        Team tempTeam2 = new Team("P.R.NollK");
        GSONHandler.addToJson(tempTeam);
        GSONHandler.addToJson(tempTeam2);

        Organisation informationsteknik = new Organisation("Informationsteknik");
        Organisation data = new Organisation("Data");


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


        Interval interval1 = new Interval(new DateTime(2019, 9, 10, 12, 40), new DateTime(2019, 9, 10, 15, 0));
        Interval interval2 = new Interval(new DateTime(2019, 9, 12, 17, 30), new DateTime(2019, 10, 16, 20, 0));
        IReservation res = new Reservation(informationsteknik.getTeams().get(0), interval1, mockItem, ReservationStatus.APPROVED);
        IReservation res2 = new Reservation(informationsteknik.getTeams().get(0), interval2, mockItem, ReservationStatus.APPROVED);
        ReservationHandler resHandler = informationsteknik.getReservationHandler();
        List<IReservation> reservations = resHandler.getReservations();
        reservations.add(res);
        reservations.add(res2);

        organisations.add(informationsteknik);
        organisations.add(data);
        setCurrentOrganisation(informationsteknik);

        tempTeam.addItemToInventory(mockItem);
        tempTeam.addItemToInventory(mockItem2);


    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setCurrentUser(User currentUser) {
        StoreIT2.currentUser = currentUser;
    }

    public static void setCurrentOrganisation(Organisation currentOrganisation) {
        StoreIT2.currentOrganisation = currentOrganisation;
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
