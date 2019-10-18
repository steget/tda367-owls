package storagesystem.services;

import javafx.embed.swing.JFXPanel;
import javafx.scene.image.Image;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.junit.Assert;
import org.junit.Test;
import storagesystem.model.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static storagesystem.services.GSONHandler.*;

public class GSONHandlerTest {

    @Test
    public void shouldAddItemsToJSON() throws IOException {
        JFXPanel mockJfxPanel = new JFXPanel(); //Stupid line to prevent "java.lang.RuntimeException: Internal graphics not initialized yet"

        GSONHandler.clearJson("src/main/resources/json/itemDB.json");
        Location location = new Location("Mock Location", "This is a temporary location");
        List<Item> itemList = new ArrayList<>();

        Item item1 = new Item();
        Item item2 = new Item("name", "description", "UserReq", 2, 10, Condition.GREAT, false, location, new Image("pictures/art.png"));

        itemList.add(item1);
        itemList.add(item2);

        GSONHandler.addListToJson(itemList);
        //Item item3 = new Item();

        //GSONHandler.addToJson(item3);
        List<Item> listFromJson = getListFromJson(Item.class);


        Assert.assertTrue(listFromJson.get(0).getName().equals(itemList.get(0).getName()));
        Assert.assertTrue(listFromJson.get(1).getName().equals(itemList.get(1).getName()));
        Assert.assertTrue(listFromJson.equals(itemList));
        //Assert.assertTrue(item3.getName().equals(listFromJson.get(2).getName()));
    }

    @Test
    public void shouldAddLocationsToJSON() throws IOException {
        JFXPanel mockJfxPanel = new JFXPanel(); //Stupid line to prevent "java.lang.RuntimeException: Internal graphics not initialized yet"


        GSONHandler.clearJson("src/main/resources/json/locationDB.json");

        Location location1 = new Location("Location without Image", "Outside of my mind");
        Location location2 = new Location("Test Location With Image", "Inside of my mind", new Image("pictures/art.png"));

        List<Location> locationList = new ArrayList<>();

        locationList.add(location1);
        locationList.add(location2);

        GSONHandler.addListToJson(locationList);

        List<Location> listFromJson = getListFromJson(Location.class);
        for (int i = 0; i < locationList.size(); i++) {
            Assert.assertTrue(listFromJson.get(i).getName().equals(locationList.get(i).getName()));
        }
    }
    @Test
    public void shouldAddOrganisationsToJSON() throws IOException {
        JFXPanel mockJfxPanel = new JFXPanel(); //Stupid line to prevent "java.lang.RuntimeException: Internal graphics not initialized yet"


        GSONHandler.clearJson("src/main/resources/json/organisationDB.json");

        Organisation organisation1 = new Organisation("mockOrganisation");
        Organisation organisation2 = new Organisation("another Mock Organisation (with image)");

        List<Organisation> organisations = new ArrayList<>();
        organisations.add(organisation1);
        organisation2.setImage(new Image("pictures/informationstekniklogga.png"));
        organisations.add(organisation2);

        addListToJson(organisations);

        List<Organisation> listFromJson = getListFromJson(Organisation.class);

        for (int i = 0; i < organisations.size(); i++) {
            Assert.assertTrue(listFromJson.get(i).getName().equals(organisations.get(i).getName()));
        }


    }

    @Test
    public void shouldAddTeamsToJSON() throws IOException {
        JFXPanel mockJfxPanel = new JFXPanel(); //Stupid line to prevent "java.lang.RuntimeException: Internal graphics not initialized yet"

        GSONHandler.clearJson("src/main/resources/json/teamDB.json");

        Team team1 = new Team("MockTeam");
        Team team2 = new Team("Team With Image");

        List<Team> teams = new ArrayList<>();
        teams.add(team1);
        team2.setImage(new Image("pictures/creepy.jpg"));
        teams.add(team2);

        addListToJson(teams);

        List<Team> listFromJson = getListFromJson(Team.class);

        for (int i = 0; i < teams.size(); i++) {
            Assert.assertTrue(listFromJson.get(i).getName().equals(teams.get(i).getName()));
        }

    }

    @Test
    public void shouldAddUsersAndReservations() throws IOException {
        JFXPanel mockJfxPanel = new JFXPanel(); //Stupid line to prevent "java.lang.RuntimeException: Internal graphics not initialized yet"

        GSONHandler.clearJson("src/main/resources/json/userDB.json");
        GSONHandler.clearJson("src/main/resources/json/reservationDB.json");
        Organisation mockOrg = new Organisation("IT");

        mockOrg.createUser("Alberto");
        mockOrg.createUser("Alberto2");

        GSONHandler.addListToJson(mockOrg.getUsers());
        Team team = new Team("team");
        Item item = new Item();

        ReservationHandler reservationHandler = new ReservationHandler(new ArrayList<>());

        DateTime time1 = new DateTime();
        DateTime time2 = time1.plusDays(1);
        Interval interval1 = new Interval(time1, time2);


        reservationHandler.createReservation(mockOrg.getUsers().get(0), interval1, item);

        List<IReservation> reservations = new ArrayList<>();
        reservations.addAll(reservationHandler.getReservations());
        addToJson(reservations.get(0));

        List<IReservation> reservations2 = new ArrayList<>();

        reservations2.addAll(getListFromJson(Reservation.class));

        Assert.assertTrue(reservations.get(0).equals(reservations2.get(0)));



    }
}
