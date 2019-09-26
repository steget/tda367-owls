package storagesystem.model;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

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
}