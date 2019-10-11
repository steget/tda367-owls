package storagesystem.model;

import org.junit.Test;

import java.lang.reflect.Member;
import java.util.List;

import static org.junit.Assert.*;

public class TeamTest {

    @Test
    public void getAllItems() {
        Team testTeam = new Team("owls");
        List<Item> allItems = testTeam.getAllItems();
        assertEquals(0, allItems.size());
        Location hasen = new Location("hasen","ha senare", null);

        Item mockItem = new Item("mockItem", "desc","requirements",1,Condition.GREAT,true, hasen, null);
        allItems.add(mockItem);
        assertEquals(1, allItems.size());
    }

    @Test
    public void addMember() {
        Team testTeam = new Team("owls");
        int ID = 12;
        testTeam.addMember(ID);

        assertEquals(1, testTeam.getAllMemberIDs().size()); //check if 1 member
        assertTrue(testTeam.getAllMemberIDs().contains(ID));
    }

    @Test
    public void removeMember() {
        Team testTeam = new Team("owls");
        testTeam.addMember(12);

        assertEquals(1, testTeam.getAllMemberIDs().size()); //check if 1 member
        testTeam.removeMember(12);
        assertEquals(0, testTeam.getAllMemberIDs().size());
    }

    @Test
    public void getAllMemberIDs() {
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