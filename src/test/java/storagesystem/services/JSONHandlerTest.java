package storagesystem.services;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import storagesystem.model.*;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static storagesystem.services.JSONHandler.*;

public class JSONHandlerTest {
    //makes sure we have the mockData from storeIT in organisationDB
    @AfterClass
    public static void resetDatabase() throws IOException {
        IDHandler.clearAllNextIDs();
        StoreIT storeIT = new StoreIT();
        storeIT.reset();
        JSONHandler.save(StoreIT.getOrganisations());
    }

    @Test
    public void addToJson() throws IOException {
        JSONHandler.clearAllJsonFiles();
        Organisation mockOrg = new Organisation("Mock Organisation");

        Organisation mockOrg2 = new Organisation("Mock Organisation 2");
        List<Organisation> organisations = new ArrayList<>();
        organisations.add(mockOrg);
        organisations.add(mockOrg2);
        Location location = new Location("Test Location", "Exists in mockorg2");
        Team testTeam = new Team("testTeam");
        mockOrg2.addTeam(testTeam);

        mockOrg2.addItem(IReservableFactory.createReservableItem("testItem", "exists in mockOrg2", "userRequirements", 1, Condition.GREAT, false, location.getID()), testTeam);

        JSONHandler.save(organisations);

        List<Organisation> organisationList = getOrganisationList();

        Assert.assertEquals(organisationList.get(0).getName(), mockOrg.getName());
        Assert.assertNotEquals(organisationList.get(1).getName(), mockOrg.getName());
        Assert.assertEquals(organisationList.get(1).getName(), mockOrg2.getName());
        Assert.assertEquals(organisationList.get(1).getAllItems(), mockOrg2.getAllItems());
    }

    @Test
    public void addListToJson() throws IOException {
        JSONHandler.clearAllJsonFiles();
        Organisation mockOrg = new Organisation("Mock Organisation");

        Organisation mockOrg2 = new Organisation("Mock Organisation 2");
        Location location = new Location("Test Location", "Exists in mockorg2");
        Team testTeam = new Team("testTeam");
        mockOrg2.addTeam(testTeam);

        mockOrg2.addItem(IReservableFactory.createReservableItem("testItem", "exists in mockOrg2", "userRequirements", 1, Condition.GREAT, false, location.getID()), testTeam);

        List<Organisation> organisations = new ArrayList<>();
        organisations.add(mockOrg);
        organisations.add(mockOrg2);

        JSONHandler.save(organisations);

        List<Organisation> organisationList = getOrganisationList();

        Assert.assertEquals(organisationList.get(0).getName(), mockOrg.getName());
        Assert.assertNotEquals(organisationList.get(1).getName(), mockOrg.getName());
        Assert.assertEquals(organisationList.get(1).getName(), mockOrg2.getName());
        Assert.assertEquals(organisationList.get(1).getAllItems(), mockOrg2.getAllItems());

    }
}