package storagesystem.model;

import org.junit.Assert;
import org.junit.Test;
import storagesystem.services.IDHandler;
import storagesystem.services.JSONHandler;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrganisationTest {

    @Test
    public void nameTest() throws IOException {
        JSONHandler.clearAllJsonFiles();

        String name = "big Long namz3";
        String name2 = "short";
        Organisation org = new Organisation(name);

        assertEquals(name, org.getName());

        org.setName(name2);
        assertEquals(name2, org.getName());
    }

    @Test
    public void getAllItemTest() throws IOException {
        JSONHandler.clearAllJsonFiles();

        Organisation org = new Organisation("name");
        Assert.assertTrue(org.getAllItems().isEmpty());
    }

    @Test(expected = Exception.class)
    public void getItemTest() throws Exception {
        JSONHandler.clearAllJsonFiles();

        Organisation informationsteknik = new Organisation("Informationsteknik");
        Team tempTeam = new Team("sexNollK");
        informationsteknik.addTeam(tempTeam);
        Location mockLocation = new Location("MockLocation", "this is a mock location in getItemTest()", null);
        IReservable mockItem = IReservableFactory.createReservableItem("mockItem", "This is a description", "Behave please.", 2, Condition.GOOD, true, mockLocation.getID(), null);

        assertEquals(0, informationsteknik.getTeamsItems(tempTeam).size());
        assertEquals(0, informationsteknik.getAllItems().size());
        informationsteknik.addItem(mockItem, tempTeam);
        assertEquals(1, informationsteknik.getTeamsItems(tempTeam).size());
        assertEquals(1, informationsteknik.getAllItems().size());


        assertEquals(mockItem, informationsteknik.getItem(0));
        assertEquals(mockItem, informationsteknik.getItem(133));
    }

    @Test
    public void createUserTest() throws IOException {
        JSONHandler.clearAllJsonFiles();

        Organisation informationsteknik = new Organisation("Informationsteknik");
        Team tempTeam = new Team("sexNollK");
        informationsteknik.createUser("Albert");

        assertEquals(1, informationsteknik.getUsers().size());
        assertEquals("Albert", informationsteknik.getUsers().get(0).getName());

        informationsteknik.createUser("asd", "pass", "desc", "112");
        assertEquals("desc", informationsteknik.getUsers().get(1).getDescription());
    }

    @Test
    public void getUsersTeamsTest() throws IOException {
        JSONHandler.clearAllJsonFiles();

        Organisation informationsteknik = new Organisation("Informationsteknik");
        Team tempTeam = new Team("sexNollK");
        Team tempTeam2 = new Team("team2");
        informationsteknik.createUser("Albert");
        informationsteknik.addTeam(tempTeam);
        informationsteknik.addTeam(tempTeam2);

        User testUser = informationsteknik.getUsers().get(0);
        tempTeam.addMember(testUser.getID());
        tempTeam2.addMember(testUser.getID());

        assertTrue(informationsteknik.getUsersTeams(testUser).contains(tempTeam));
        assertTrue(informationsteknik.getUsersTeams(testUser).contains(tempTeam2));

        informationsteknik.createUser("Kalle");
        User noTeamsUser = informationsteknik.getUsers().get(1);
        assertEquals(0, informationsteknik.getUsersTeams(noTeamsUser).size());
    }
}
