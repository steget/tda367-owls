package storagesystem.model;

import org.junit.Assert;
import org.junit.Test;

public class OrganisationTest {

    @Test
    public void nameTest(){
        String name = "big Long namz3";
        String name2 = "short";
        Organisation org = new Organisation(name);

        Assert.assertEquals(name, org.getName());

        org.setName(name2);
        Assert.assertEquals(name2, org.getName());
    }

    @Test
    public void getAllItemTest(){
        Organisation org = new Organisation("name");
        Assert.assertTrue(org.getAllItems().isEmpty());
    }
}
