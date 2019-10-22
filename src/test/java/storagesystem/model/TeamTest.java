package storagesystem.model;

import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;

public class TeamTest {

    @Test
    public void getAllItems() throws IOException {
        Organisation org = new Organisation("Test org");
        Team testTeam = new Team("owls");
        List<IReservable> allItems = org.getTeamsItems(testTeam);

        assertEquals(0, allItems.size());
        String imageUrl = "/pictures/art.png";

        Location hasen = new Location("hasen","ha senare", imageUrl);

        IReservable mockItem = IReservableFactory.createReservableItem("mockItem", "desc","requirements",1,Condition.GREAT,true, hasen.getID(), imageUrl);
        allItems.add(mockItem);
        assertEquals(1, allItems.size());
    }

    @Test
    public void addMember() throws IOException{
        Team testTeam = new Team("owls");
        int ID = 12;
        testTeam.addMember(ID);

        assertEquals(1, testTeam.getAllMemberIDs().size()); //check if 1 member
        assertTrue(testTeam.getAllMemberIDs().contains(ID));
    }

    @Test
    public void removeMember() throws IOException {
        Team testTeam = new Team("owls");
        testTeam.addMember(12);

        assertEquals(1, testTeam.getAllMemberIDs().size()); //check if 1 member
        testTeam.removeMember(12);
        assertEquals(0, testTeam.getAllMemberIDs().size());
    }

    @Test
    public void getAllMemberIDs() throws IOException {
        Team testTeam = new Team("owls");
        int id1 = 15;
        int id2 = 16;
        int id3 = 1337;
        testTeam.addMember(id1);
        testTeam.addMember(id2);
        testTeam.addMember(id3);
        assertEquals(3, testTeam.getAllMemberIDs().size());
    }
}