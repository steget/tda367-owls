package storagesystem.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class TeamTest {

    @Test
    public void getAllItems() {
    }

    @Test
    public void addMember() {
    }

    @Test
    public void removeMember() {
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
        assertTrue(testTeam.getAllMemberIDs().size() == 3);
        assertEquals(id3, testTeam.getAllMemberIDs().get(2));

        
    }
}