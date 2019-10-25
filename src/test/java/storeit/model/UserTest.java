package storeit.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void getName() {
        User testUser = new User("name1", "short desc", "1");
        assertEquals("name1", testUser.getName());
    }

    @Test
    public void setName() {
        User testUser = new User("name1", "short desc", "1");
        testUser.setName("newName");
        assertEquals("newName", testUser.getName());
    }

    @Test
    public void getDescription() {
        User testUser = new User("name1", "short desc", "1");
        assertEquals("short desc", testUser.getDescription());
    }

    @Test
    public void setDescription() {
        User testUser = new User("name1", "short desc", "1");
        testUser.setDescription("asd 123 longer desc");
        assertEquals("asd 123 longer desc", testUser.getDescription());
    }

    @Test
    public void getContactInformation() {
        User testUser = new User("name1", "short desc", "1");
        assertEquals("1", testUser.getContactInformation());
    }

    @Test
    public void setContactInformation() {
        User testUser = new User("name1", "short desc", "1");
        testUser.setContactInformation("0734111337");
        assertEquals("0734111337", testUser.getContactInformation());
    }

    @Test
    @Before
    public void IDTest() {
        User oneUser = new User("name1");
        User twoUser = new User("name2");
        User threeUser = new User("name3");
        User fourUser = new User("name4");

        assertEquals(threeUser.getID() + 1, fourUser.getID());

    }
}