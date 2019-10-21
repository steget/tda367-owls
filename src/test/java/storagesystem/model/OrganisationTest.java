package storagesystem.model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OrganisationTest {

    @Test
    public void nameTest() {
        String name = "big Long namz3";
        String name2 = "short";
        Organisation org = new Organisation(name);

        assertEquals(name, org.getName());

        org.setName(name2);
        assertEquals(name2, org.getName());
    }

    @Test
    public void getAllItemTest() {
        Organisation org = new Organisation("name");
        Assert.assertTrue(org.getAllItems().isEmpty());
    }

    @Test(expected = Exception.class)
    public void getItemTest() throws Exception {
        Organisation informationsteknik = new Organisation("Informationsteknik");
        Team tempTeam = new Team("sexNollK");
        informationsteknik.addTeam(tempTeam);
        IReservable mockItem = IReservableFactory.createReservableItem("mockItem", "This is a description", "Behave please.", 2, Condition.GOOD, true, new Location("testLocation", "test desc", null), null);

        assertEquals(0, informationsteknik.getAllItems().size());
        tempTeam.addItemToInventory(mockItem);
        assertEquals(1, informationsteknik.getAllItems().size());

        assertEquals(mockItem, informationsteknik.getItem(1));
        assertEquals(mockItem, informationsteknik.getItem(133));
    }

    @Test
    public void createUserTest() {
        Organisation informationsteknik = new Organisation("Informationsteknik");
        Team tempTeam = new Team("sexNollK");
        informationsteknik.addUser(new User("asd", "pass", "desc", "112"));

        assertEquals(1, informationsteknik.getUsers().size());
        assertEquals("Albert", informationsteknik.getUsers().get(0).getName());

        informationsteknik.addUser(new User("asd", "pass", "desc", "112"));
        assertEquals("desc", informationsteknik.getUsers().get(1).getDescription());
    }

    @Test
    public void getUsersTeamsTest() {
        Organisation informationsteknik = new Organisation("Informationsteknik");
        Team tempTeam = new Team("sexNollK");
        Team tempTeam2 = new Team("team2");
        informationsteknik.addUser(new User("asd", "pass", "desc", "112"));
        informationsteknik.addTeam(tempTeam);
        informationsteknik.addTeam(tempTeam2);

        User testUser = informationsteknik.getUsers().get(0);
        tempTeam.addMember(testUser.getID());
        tempTeam2.addMember(testUser.getID());

        assertTrue(informationsteknik.getUsersTeams(testUser).contains(tempTeam));
        assertTrue(informationsteknik.getUsersTeams(testUser).contains(tempTeam2));

        informationsteknik.addUser(new User("asd", "pass", "desc", "112"));
        User noTeamsUser = informationsteknik.getUsers().get(1);
        assertEquals(0, informationsteknik.getUsersTeams(noTeamsUser).size());
    }
}
